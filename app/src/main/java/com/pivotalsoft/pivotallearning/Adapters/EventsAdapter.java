package com.pivotalsoft.pivotallearning.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pivotalsoft.pivotallearning.EnrollActivity;
import com.pivotalsoft.pivotallearning.Items.EventsItem;
import com.pivotalsoft.pivotallearning.R;

import java.util.List;

/**
 * Created by Gangadhar on 9/26/2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    private Context mContext;
    private List<EventsItem> coursesItemList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView courseName,date,venue;
        public ImageView thumbnail;
        public LinearLayout batchLayout;
        public Button enrollBtn;


        public MyViewHolder(View view) {
            super(view);
            courseName = (TextView) view.findViewById(R.id.courseName);
            date = (TextView) view.findViewById(R.id.date);
            venue = (TextView) view.findViewById(R.id.venue);
           /*
            discountPrice = (TextView) view.findViewById(R.id.discountPrice);*/
           // thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            // ratingBar =(RatingBar)view.findViewById(R.id.ratingBar);
            enrollBtn =(Button)view.findViewById(R.id.enrollBtn);
            batchLayout =(LinearLayout)view.findViewById(R.id.batchLayout);
        }
    }


    public EventsAdapter(Context mContext, List<EventsItem> coursesItemList) {
        this.mContext = mContext;
        this.coursesItemList = coursesItemList;
    }

    @Override
    public EventsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_card, parent, false);

        return new EventsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final EventsAdapter.MyViewHolder holder, final int position) {
        final EventsItem album = coursesItemList.get(position);
        holder.courseName.setText(album.getEventname());
        holder.date.setText(album.getEventdate() );
        holder.venue.setText(album.getVenue() );
        /*

        holder.discountPrice.setText(album.getDiscountFee() );
        holder.discountPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);*/

       /* // rating bar
        holder.ratingBar.setRating(Float.parseFloat(album.getRatingBar()));
        LayerDrawable stars = (LayerDrawable) holder.ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);*/


        // loading album cover using Glide library
//        Glide.with(mContext).load(album.getEventname()).into(holder.thumbnail);

        holder.enrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pivotal = new Intent(mContext, EnrollActivity.class);
                pivotal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                pivotal.putExtra("courseName",album.getEventname());
                mContext.startActivity(pivotal);
            }
        });

        holder.batchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
               // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.events_details_layout);

                // set the custom dialog components - text, image and button
                TextView eventName = (TextView) dialog.findViewById(R.id.eventName);
                eventName.setText(album.getEventname());

                TextView venue = (TextView) dialog.findViewById(R.id.venue);
                venue.setText(album.getVenue());

                TextView date = (TextView) dialog.findViewById(R.id.date);
                date.setText(album.getEventdate());

                TextView description = (TextView) dialog.findViewById(R.id.description);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    description.setText(Html.fromHtml(album.getDescription(),Html.FROM_HTML_MODE_LEGACY));
                } else {
                    description.setText(Html.fromHtml(album.getDescription()));
                }

                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                image.setImageResource(R.drawable.eventgrid_img);

                dialog.show();
            }
        });


    }



    @Override
    public int getItemCount() {
        return coursesItemList.size();
    }
}

