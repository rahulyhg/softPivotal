package com.pivotalsoft.pivotallearning.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pivotalsoft.pivotallearning.Items.CoursesDetailsItem;
import com.pivotalsoft.pivotallearning.R;

import java.util.List;

/**
 * Created by Gangadhar on 9/22/2017.
 */

public class CoursesDetailAdapter extends RecyclerView.Adapter<CoursesDetailAdapter.MyViewHolder> {

    private Context mContext;
    private List<CoursesDetailsItem> coursesItemList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView courseName,videos, ebooks,tests;
        public LinearLayout parentLayout;

        public MyViewHolder(View view) {
            super(view);
            courseName = (TextView) view.findViewById(R.id.txtCourseName);
            videos = (TextView) view.findViewById(R.id.txtVideos);
            ebooks = (TextView) view.findViewById(R.id.txtBooks);
            tests = (TextView) view.findViewById(R.id.txtTests);
            parentLayout =(LinearLayout)view.findViewById(R.id.parentLayout);
        }
    }


    public CoursesDetailAdapter(Context mContext, List<CoursesDetailsItem> coursesItemList) {
        this.mContext = mContext;
        this.coursesItemList = coursesItemList;
    }

    @Override
    public CoursesDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.courses_details_card, parent, false);

        return new CoursesDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CoursesDetailAdapter.MyViewHolder holder, int position) {
        CoursesDetailsItem album = coursesItemList.get(position);
        holder.courseName.setText(album.getChaptername());
        /*holder.videos.setText(album.getVideos() );
        holder.ebooks.setText(album.getEbook() );

        holder.tests.setText(album.getTests() );*/
       // holder.discountPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);


        /*holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pivotal = new Intent(mContext, CourseFullViewActivity.class);
                pivotal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(pivotal);
            }
        });*/


    }



    @Override
    public int getItemCount() {
        return coursesItemList.size();
    }
}

