package com.pivotalsoft.pivotallearning.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pivotalsoft.pivotallearning.Items.PaymentAnalaticItem;
import com.pivotalsoft.pivotallearning.R;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Gangadhar on 10/16/2017.
 */

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {
    String url;
    private Context mContext;
    private List<PaymentAnalaticItem> coursesItemList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView recoNo,courseName,mobile,amountPaid,amountDue,day,month,dueDate;
        public ImageView thumbnail,overflow;

        public MyViewHolder(View view) {
            super(view);
            recoNo = (TextView) view.findViewById(R.id.txtReceptno);
            courseName = (TextView) view.findViewById(R.id.txtCourseName);
            mobile = (TextView) view.findViewById(R.id.txtMobile);
            amountPaid = (TextView) view.findViewById(R.id.txtAmountPaid);
            amountDue = (TextView) view.findViewById(R.id.txtAmountDue);
            day = (TextView) view.findViewById(R.id.day);
            month = (TextView) view.findViewById(R.id.month);
            dueDate = (TextView) view.findViewById(R.id.txtDueDate);
            thumbnail = (ImageView) view.findViewById(R.id.imageView);
           // overflow=(ImageView)view.findViewById(R.id.overflow);

        }
    }


    public PaymentAdapter(Context mContext, List<PaymentAnalaticItem> coursesItemList) {
        this.mContext = mContext;
        this.coursesItemList = coursesItemList;
    }

    @Override
    public PaymentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_card, parent, false);

        return new PaymentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PaymentAdapter.MyViewHolder holder, final int position) {
        final PaymentAnalaticItem album = coursesItemList.get(position);
        holder.recoNo.setText(album.getReceiptno());
        holder.courseName.setText(album.getCoursename() );
        holder.amountPaid.setText(album.getAmountpaid());
        holder.mobile.setText(album.getMobile() );
        holder.amountDue.setText(album.getAmountdue());
        holder.dueDate.setText(album.getDuedate() );

        String test = album.getFullname();
        String firstLetter=test.substring(0,1);


        String startDate = formateDateFromstring("yyyy-MM-dd", "dd,MMM,yyyy", album.getPaiddate());
        Log.e("startDateformat",""+startDate);

        StringTokenizer tokens = new StringTokenizer(startDate, ",");
        String day = tokens.nextToken();
        String month = tokens.nextToken();// this will contain " they taste good"
        String year = tokens.nextToken();// this will contain "Fruit"

        Log.e("startDatetoken",""+day+"\n"+month);

        holder.day.setText(day);
        holder.month.setText(month);


        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color1 = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(60)  // width in px
                .height(60) // height in px
                .endConfig()
                .buildRound(firstLetter, color1);

        holder.thumbnail.setImageDrawable(drawable);



/*

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // inflate menu
                PopupMenu popup = new PopupMenu(mContext, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_update, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.action_edit:

                                Intent intent =new Intent(mContext,EditPaymentActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("receiptno",album.getReceiptno());
                                intent.putExtra("amountpaid",album.getAmountpaid());
                                intent.putExtra("amountdue",album.getAmountdue());
                                intent.putExtra("paiddate",album.getPaiddate());
                                intent.putExtra("duedate",album.getDuedate());
                                intent.putExtra("remarks",album.getRemarks());


                                mContext.startActivity(intent);

                                // Toast.makeText(mContext,"Postion"+position,Toast.LENGTH_LONG).show();
                                return true;


                            default:
                        }


                        return false;
                    }
                });
                popup.show();

            }
        });

*/

    }



    @Override
    public int getItemCount() {
        return coursesItemList.size();
    }

    private void delete(){

        StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        // Toast.makeText(mContext, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.

                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(dr);
    }

    // convert date format

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate){

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            // LOGE(TAG, "ParseException - dateFormat");
        }

        return outputDate;

    }
}



