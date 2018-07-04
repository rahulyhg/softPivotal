package com.pivotalsoft.pivotallearning.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pivotalsoft.pivotallearning.Items.CourseVideosItem;
import com.pivotalsoft.pivotallearning.R;

import java.util.List;

/**
 * Created by Gangadhar on 9/22/2017.
 */

public class CourseVideosListAdapter extends RecyclerView.Adapter<CourseVideosListAdapter.MyViewHolder> {

    private Context mContext;
    private List<CourseVideosItem> coursesItemList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView unitName,topicName;

       // public LinearLayout parentLayout;

        public MyViewHolder(View view) {
            super(view);
            unitName = (TextView) view.findViewById(R.id.txtCourseName);
            topicName = (TextView) view.findViewById(R.id.txtVideos);
//            parentLayout =(LinearLayout)view.findViewById(R.id.parentLayout);
        }
    }


    public CourseVideosListAdapter(Context mContext, List<CourseVideosItem> coursesItemList) {
        this.mContext = mContext;
        this.coursesItemList = coursesItemList;
    }

    @Override
    public CourseVideosListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.courses_videos_card, parent, false);

        return new CourseVideosListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CourseVideosListAdapter.MyViewHolder holder, int position) {
        CourseVideosItem album = coursesItemList.get(position);
        holder.unitName.setText(album.getUnitName());
        holder.topicName.setText(album.getTopicName() );

       /* holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               *//* Intent pivotal = new Intent(mContext, CourseFullViewActivity.class);
                pivotal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(pivotal);*//*
            }
        });*/


    }



    @Override
    public int getItemCount() {
        return coursesItemList.size();
    }
}


