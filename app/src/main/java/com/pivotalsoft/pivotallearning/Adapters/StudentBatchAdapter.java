package com.pivotalsoft.pivotallearning.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.pivotalsoft.pivotallearning.Items.StudenBatchItem;
import com.pivotalsoft.pivotallearning.R;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Gangadhar on 10/16/2017.
 */

public class StudentBatchAdapter extends RecyclerView.Adapter<StudentBatchAdapter.MyViewHolder> {
    String url;
    private Context mContext;
    private List<StudenBatchItem> coursesItemList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView courseName,batchCode,day,month,fee;
        public ImageView thumbnail,overflow;
        public LinearLayout parentLayout;


        public MyViewHolder(View view) {
            super(view);
            courseName = (TextView) view.findViewById(R.id.courseName);
            batchCode = (TextView) view.findViewById(R.id.batchCode);
            day =(TextView)view.findViewById(R.id.day);
            month =(TextView)view.findViewById(R.id.month);
            fee =(TextView)view.findViewById(R.id.txtFee);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            parentLayout =(LinearLayout)view.findViewById(R.id.batchLayout);
           // overflow =(ImageView) view.findViewById(R.id.overflow);
        }
    }


    public StudentBatchAdapter(Context mContext, List<StudenBatchItem> coursesItemList) {
        this.mContext = mContext;
        this.coursesItemList = coursesItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_batch_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final StudenBatchItem album = coursesItemList.get(position);
        holder.courseName.setText(album.getCoursename());
        holder.batchCode.setText(album.getBatchcode() );
        holder.fee.setText(album.getFee()+" INR");

        String startDate = formateDateFromstring("yyyy-MM-dd", "dd,MMM,yyyy", album.getRegdate());
        Log.e("startDateformat",""+startDate);

        StringTokenizer tokens = new StringTokenizer(startDate, ",");
        String day = tokens.nextToken();
        String month = tokens.nextToken();// this will contain " they taste good"
        String year = tokens.nextToken();// this will contain "Fruit"

        Log.e("startDatetoken",""+day+"\n"+month);

        holder.day.setText(day);
        holder.month.setText(month);

        // loading album cover using Glide library
        try {
            Glide.with(mContext).load(album.getCourseimage()).into(holder.thumbnail);
        } catch (Exception e) {
            e.printStackTrace();
        }




       /* holder.overflow.setOnClickListener(new View.OnClickListener() {
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

                                Intent intent =new Intent(mContext,EditStudentBatchActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("adminid",album.getAdmid());
                                intent.putExtra("regno",album.getRegno());
                                intent.putExtra("regdate",album.getRegdate());
                                intent.putExtra("fee",album.getFee());

                                mContext.startActivity(intent);

                                //Toast.makeText(mContext,"Postion"+position,Toast.LENGTH_LONG).show();
                                return true;

                            *//*case R.id.action_delete:

                                url = Apis.BATCHES_URL+"/"+album.getBatchid();
                                Log.e("URL:",""+url);
                                delete();
                                coursesItemList.remove(position);
                                notifyDataSetChanged();
                                return true;*//*

                            default:
                        }


                        return false;
                    }
                });
                popup.show();

            }
        });*/


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

