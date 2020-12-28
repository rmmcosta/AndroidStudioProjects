package com.developer.appwithsettings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder> {

    private final String[] settingsArray;
    private final int[] icons;
    private final Context context;

    public SettingsAdapter(String[] settingsArray, int[] icons, Context context) {
        this.settingsArray = settingsArray;
        this.icons = icons;
        this.context = context;
    }

    @NonNull
    @Override
    public SettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting, parent, false);
        return new SettingsViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull SettingsViewHolder holder, int position) {
        holder.tvSetting.setText(settingsArray[position]);
        holder.ivIcon.setImageDrawable(context.getDrawable(icons[position]));
    }

    @Override
    public int getItemCount() {
        return settingsArray.length;
    }

    class SettingsViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvSetting;
        private final ImageView ivIcon;

        public SettingsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(view -> {
                Toast.makeText(context, settingsArray[getLayoutPosition()], Toast.LENGTH_SHORT).show();
            });
            tvSetting = itemView.findViewById(R.id.tvSetting);
            ivIcon = itemView.findViewById(R.id.ivSettingIcon);
        }
    }

}
