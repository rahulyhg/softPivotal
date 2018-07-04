package com.pivotalsoft.pivotallearning.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
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
import com.pivotalsoft.pivotallearning.Items.CoursesItem;
import com.pivotalsoft.pivotallearning.Items.WalletItem;
import com.pivotalsoft.pivotallearning.R;

import java.util.List;

/**
 * Created by USER on 2/7/2018.
 */

public class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.MyViewHolder> {

    private Context mContext;
    private List<WalletItem> coursesItemList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView bonusType,bonusAmount;
        public ImageView imageWallet;
        public CardView parentLayout;
        public LinearLayout BAmountlayout;

        public MyViewHolder(View view) {
            super(view);
            bonusType = (TextView) view.findViewById(R.id.bonusType);
            bonusAmount = (TextView) view.findViewById(R.id.txtbonusamount);
            imageWallet = (ImageView) view.findViewById(R.id.imageWallet);
            parentLayout =(CardView)view.findViewById(R.id.parentLayout);
            BAmountlayout =(LinearLayout)view.findViewById(R.id.BAmountlayout);

        }
    }


    public WalletHistoryAdapter(Context mContext, List<WalletItem> coursesItemList) {
        this.mContext = mContext;
        this.coursesItemList = coursesItemList;
    }

    @Override
    public WalletHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wallet_card, parent, false);

        return new WalletHistoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WalletHistoryAdapter.MyViewHolder holder, int position) {
        final WalletItem album = coursesItemList.get(position);

        holder.bonusAmount.setText(album.getBonusamount() );

        if (album.getStatus().equalsIgnoreCase("Approved")){

            holder.bonusType.setText(album.getBonustype());
            holder.imageWallet.setImageResource(R.drawable.ic_correct);

        }
        else {
            holder.parentLayout.setCardBackgroundColor(Color.parseColor("#f44242"));
            holder.imageWallet.setImageResource(R.drawable.ic_pending);
            holder.bonusType.setText("Waiting for Approval !");
            holder.bonusType.setTextColor(Color.WHITE);
        }


    }



    @Override
    public int getItemCount() {
        return coursesItemList.size();
    }
}

