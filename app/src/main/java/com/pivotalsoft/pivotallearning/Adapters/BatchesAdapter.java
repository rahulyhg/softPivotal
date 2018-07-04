package com.pivotalsoft.pivotallearning.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pivotalsoft.pivotallearning.CourseDetailsActivity;
import com.pivotalsoft.pivotallearning.EnrollActivity;
import com.pivotalsoft.pivotallearning.Items.BatchesItem;
import com.pivotalsoft.pivotallearning.R;

import java.util.List;

/**
 * Created by Gangadhar on 9/21/2017.
 */

public class BatchesAdapter extends RecyclerView.Adapter<BatchesAdapter.MyViewHolder> {

    private Context mContext;
    private List<BatchesItem> coursesItemList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView courseName,learnersCount;
        public ImageView thumbnail;
        public LinearLayout parentLayout;
        public Button enrollBtn;

        public MyViewHolder(View view) {
            super(view);
            courseName = (TextView) view.findViewById(R.id.courseName);
            learnersCount = (TextView) view.findViewById(R.id.learnersCount);
            /*price = (TextView) view.findViewById(R.id.price);
            discountPrice = (TextView) view.findViewById(R.id.discountPrice);*/

            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            // ratingBar =(RatingBar)view.findViewById(R.id.ratingBar);
            parentLayout =(LinearLayout)view.findViewById(R.id.batchLayout);
            enrollBtn =(Button)view.findViewById(R.id.enrollBtn);
        }
    }


    public BatchesAdapter(Context mContext, List<BatchesItem> coursesItemList) {
        this.mContext = mContext;
        this.coursesItemList = coursesItemList;
    }

    @Override
    public BatchesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.batch_card, parent, false);

        return new BatchesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BatchesAdapter.MyViewHolder holder, int position) {
        final BatchesItem album = coursesItemList.get(position);
        holder.courseName.setText(album.getCoursename());
         holder.learnersCount.setText(album.getLearners() );
       /* holder.price.setText(album.getCourseFee() );

        holder.discountPrice.setText(album.getDiscountFee() );
        holder.discountPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        // rating bar
        holder.ratingBar.setRating(Float.parseFloat(album.getRatingBar()));
        LayerDrawable stars = (LayerDrawable) holder.ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);*/


        // loading album cover using Glide library
        try {
            Glide.with(mContext).load(album.getCourseimage()).into(holder.thumbnail);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent details = new Intent(mContext, CourseDetailsActivity.class);
                details.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                details.putExtra("courseImage",album.getCourseimage());
                details.putExtra("colorCode",album.getColorcode());
                details.putExtra("courseName",album.getCoursename());
                details.putExtra("courseId",album.getCourseid());
                mContext.startActivity(details);
            }
        });


        holder.enrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pivotal = new Intent(mContext, EnrollActivity.class);
                pivotal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                pivotal.putExtra("courseName",album.getCoursename());
                mContext.startActivity(pivotal);
            }
        });


    }



    @Override
    public int getItemCount() {
        return coursesItemList.size();
    }
}
