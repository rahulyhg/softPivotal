package com.pivotalsoft.pivotallearning.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pivotalsoft.pivotallearning.Items.WorkItem;
import com.pivotalsoft.pivotallearning.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Gangadhar on 10/16/2017.
 */

public class WorksAdapter extends RecyclerView.Adapter<WorksAdapter.MyViewHolder> {
    String url;
    private Context mContext;
    private List<WorkItem> coursesItemList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,description,status,day,month,score;
        public ImageView thumbnail,overflow;



        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txtTitle);
            description = (TextView) view.findViewById(R.id.txtDescription);
            status = (TextView) view.findViewById(R.id.txtstatus);
            score = (TextView) view.findViewById(R.id.txtScore);
            day = (TextView) view.findViewById(R.id.day);
            month = (TextView) view.findViewById(R.id.month);
           // overflow=(ImageView)view.findViewById(R.id.overflow);


        }
    }


    public WorksAdapter(Context mContext, List<WorkItem> coursesItemList) {
        this.mContext = mContext;
        this.coursesItemList = coursesItemList;
    }

    @Override
    public WorksAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_work_card, parent, false);

        return new WorksAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WorksAdapter.MyViewHolder holder, final int position) {
        final WorkItem album = coursesItemList.get(position);
        holder.title.setText(album.getTitle());
        holder.description.setText(album.getDescription());

        holder.score.setText(album.getGivenScore()+"/"+album.getActualScore());

        if (album.getEndDate().trim().equals("0000-00-00")){

            holder.status.setText("In Progress..");
        }else {

            holder.status.setText("Finished");
        }


       /* String test = album.getGivenScore()+"/"+album.getActualScore();
        Log.e("percentage",""+test);
        //String percentage=test.substring(0,2)+"%";*/


        String startDate = formateDateFromstring("yyyy-MM-dd", "dd,MMM,yyyy", album.getStartDate());
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
        int color1 = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(60)  // width in px
                .height(60) // height in px
                .endConfig()
                .buildRound(test, color1);

        holder.thumbnail.setImageDrawable(drawable);*/

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

                                Intent intent =new Intent(mContext,EditWorkActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("workid",album.getWorkId());
                                intent.putExtra("title",album.getTitle());
                                intent.putExtra("startdate",album.getStartDate());
                                intent.putExtra("enddate",album.getEndDate());
                                intent.putExtra("actualscore",album.getActualScore());
                                intent.putExtra("givenscore",album.getGivenScore());
                                intent.putExtra("description",album.getDescription());

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

