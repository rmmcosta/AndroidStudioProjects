package com.example.materialme;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.transition.Explode;
import android.util.Pair;
import android.view.LayoutInflater;
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
    private Activity mActivity;

    public SportsAdapter(ArrayList<Sport> mSportsData, Context mContext, Activity activity) {
        this.mSportsData = mSportsData;
        this.mContext = mContext;
        this.mActivity = activity;
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

    public class SportsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvSportsTitle, tvNewsTitle, tvSportsInfo;
        ImageView ivSportsBanner;

        public SportsViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSportsBanner = itemView.findViewById(R.id.ivSportsBanner);
            tvSportsTitle = itemView.findViewById(R.id.tvSportsTitle);
            tvNewsTitle = itemView.findViewById(R.id.tvNewsTitle);
            tvSportsInfo = itemView.findViewById(R.id.tvSportsInfo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, DetailActivity.class);
            Pair<View, String> p1 = Pair.create(ivSportsBanner, mContext.getString(R.string.banner_animation_name));
            Pair<View, String> p2 = Pair.create(tvSportsTitle, mContext.getString(R.string.title_animation_name));
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(mActivity, p1, p2);
            intent.putExtra(MainActivity.SELECTED_SPORT_ITEM, mSportsData.get(getAdapterPosition()));
            mContext.startActivity(intent, activityOptions.toBundle());
        }
    }
}
