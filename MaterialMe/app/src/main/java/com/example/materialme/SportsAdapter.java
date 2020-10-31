package com.example.materialme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.SportsViewHolder> {
    private ArrayList<Sport> mSportsData;
    private Context mContext;

    public SportsAdapter(ArrayList<Sport> mSportsData, Context mContext) {
        this.mSportsData = mSportsData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.sports_item, parent, false);
        return new SportsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SportsViewHolder holder, int position) {
        Glide.with(mContext).load(mSportsData.get(position).getBanner()).into(holder.ivSportsBanner);
        //holder.ivSportsBanner.setImageResource(mSportsData.get(position).getBanner());
        holder.tvSportsTitle.setText(mSportsData.get(position).getSportsTitle());
        holder.tvSportsInfo.setText(mSportsData.get(position).getSportsInfo());
    }

    @Override
    public int getItemCount() {
        return mSportsData.size();
    }

    public class SportsViewHolder extends RecyclerView.ViewHolder {
        TextView tvSportsTitle, tvNewsTitle, tvSportsInfo;
        ImageView ivSportsBanner;

        public SportsViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSportsBanner = itemView.findViewById(R.id.ivSportsBanner);
            tvSportsTitle = itemView.findViewById(R.id.tvSportsTitle);
            tvNewsTitle = itemView.findViewById(R.id.tvNewsTitle);
            tvSportsInfo = itemView.findViewById(R.id.tvSportsInfo);
        }
    }
}
