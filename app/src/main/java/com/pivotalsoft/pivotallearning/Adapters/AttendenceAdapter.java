package com.pivotalsoft.pivotallearning.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.pivotalsoft.pivotallearning.Items.AttendenceItem;
import com.pivotalsoft.pivotallearning.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Gangadhar on 10/16/2017.
 */

public class AttendenceAdapter extends RecyclerView.Adapter<AttendenceAdapter.MyViewHolder> {
    String url;
    private Context mContext;
    private List<AttendenceItem> coursesItemList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView courseName,batchCode,totalDays,presentDays,day,month,percentage;
        public ImageView thumbnail,overflow;



        public MyViewHolder(View view) {
            super(view);
            courseName = (TextView) view.findViewById(R.id.txtCourseName);
            batchCode = (TextView) view.findViewById(R.id.txtBatchCode);
            totalDays = (TextView) view.findViewById(R.id.txtTotalDays);
            presentDays = (TextView) view.findViewById(R.id.txtPresentDays);
            day = (TextView) view.findViewById(R.id.day);
            month = (TextView) view.findViewById(R.id.month);
            percentage =(TextView)view.findViewById(R.id.txtScore);
           // thumbnail = (ImageView) view.findViewById(R.id.imageView);
           // overflow=(ImageView)view.findViewById(R.id.overflow);


        }
    }


    public AttendenceAdapter(Context mContext, List<AttendenceItem> coursesItemList) {
        this.mContext = mContext;
        this.coursesItemList = coursesItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendance_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AttendenceItem album = coursesItemList.get(position);
        holder.courseName.setText(album.getCoursename());
        holder.batchCode.setText(album.getBatchcode());
        holder.totalDays.setText(album.getTotaldays());
        holder.presentDays.setText(album.getDayspresent());


        //get entered texts from the edittexts,and convert to integers.
        Double value1 = Double.parseDouble(album.getDayspresent());
        Double value2 = Double.parseDouble(album.getTotaldays());
        Double value3 = Double.parseDouble("100");
        //do the calculation
        Double calculatedValue = (value1/value2)*value3;



        String test = String.valueOf(calculatedValue);
        Log.e("percentage",""+test);
        String percentage=test.substring(0,2)+"%";


        String startDate = formateDateFromstring("yyyy-MM-dd", "dd,MMM,yyyy", album.getRegdate());
        Log.e("startDateformat",""+startDate);

        StringTokenizer tokens = new StringTokenizer(startDate, ",");
        String day = tokens.nextToken();
        String month = tokens.nextToken();// this will contain " they taste good"
        String year = tokens.nextToken();// this will contain "Fruit"

        Log.e("startDatetoken",""+day+"\n"+month);

        holder.day.setText(day);
        holder.month.setText(month);


      /*  ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color1 = generator.getRandomColor();*/

        holder.percentage.setText(percentage);

       /* TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(60)  // width in px
                .height(60) // height in px
                .endConfig()
                .buildRound(percentage, color1);*/



      /*  holder.overflow.setOnClickListener(new View.OnClickListener() {
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

                                Intent intent =new Intent(mContext,EditAttendanceActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("attendanceid",album.getAttendanceid());
                                intent.putExtra("totalDays",album.getTotaldays());
                                intent.putExtra("presentDays",album.getDayspresent());
                                intent.putExtra("absentDays",album.getAbsentdays());
                               // intent.putExtra("regno",album.getRegno());


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
        });*/




    }

    @Override
    public int getItemCount() {
        return coursesItemList.size();
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

