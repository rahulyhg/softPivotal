package com.pivotalsoft.pivotallearning.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.pivotalsoft.pivotallearning.Items.OffersItem;
import com.pivotalsoft.pivotallearning.R;

import java.util.List;

/**
 * Created by Gangadhar on 9/22/2017.
 */

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.MyViewHolder> {

    private Context mContext;
    private List<OffersItem> coursesItemList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView offerName, code,expDate;
        public ImageView thumbnail;
        public CardView parentLayout;


        public MyViewHolder(View view) {
            super(view);
            offerName = (TextView) view.findViewById(R.id.txtOfferName);
            code = (TextView) view.findViewById(R.id.txtCode);
            expDate = (TextView) view.findViewById(R.id.txtExpDate);

            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            parentLayout =(CardView)view.findViewById(R.id.parentLayout);

        }
    }


    public OffersAdapter(Context mContext, List<OffersItem> coursesItemList) {
        this.mContext = mContext;
        this.coursesItemList = coursesItemList;
    }

    @Override
    public OffersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offer_card, parent, false);

        return new OffersAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OffersAdapter.MyViewHolder holder, final int position) {
        final OffersItem album = coursesItemList.get(position);
        holder.offerName.setText(album.getOffername());

        holder.code.setText(album.getOffercode() );

        holder.expDate.setText("Exp Date : "+album.getStartdate() +" - " +album.getEnddate());
       // holder.discountPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // Toast.makeText(mContext, "Add to favourite"+coursesItemList.get(position), Toast.LENGTH_SHORT).show();
                // custom dialog
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                /*dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));*/
                dialog.setContentView(R.layout.offer_details_layout);

                // set the custom dialog components - text, image and button
                TextView offerName = (TextView) dialog.findViewById(R.id.offerName);
                offerName.setText(album.getOffername());

                TextView code = (TextView) dialog.findViewById(R.id.code);
               code.setText(album.getOffercode());

                TextView expDate = (TextView) dialog.findViewById(R.id.expDate);
                expDate.setText("Exp Date : "+album.getStartdate() +" - " +album.getEnddate());

                TextView description = (TextView) dialog.findViewById(R.id.description);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    description.setText(Html.fromHtml(album.getDescription(),Html.FROM_HTML_MODE_LEGACY));
                } else {
                    description.setText(Html.fromHtml(album.getDescription()));
                }

                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                image.setImageResource(R.drawable.offersgrid_img);

                dialog.show();
            }
        });


        // loading album cover using Glide library
       // Glide.with(mContext).load(album.getImage()).into(holder.thumbnail);


    }



    @Override
    public int getItemCount() {
        return coursesItemList.size();
    }
}


