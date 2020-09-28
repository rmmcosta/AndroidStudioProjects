package com.example.backpro;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ActivityManager.RunningAppProcessInfo> runningApps;
    private ActivityManager activityManager;
    private RecyclerView rvListApps;
    private RecyclerView.Adapter adapterListApps;
    private RecyclerView.LayoutManager lmListApps;
    private TextView tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getRunningApps();
    }

    private void getRunningApps() {
        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        runningApps = activityManager.getRunningAppProcesses();
        rvListApps = findViewById(R.id.rvListApps);
        adapterListApps = new AdapterListApps();
        rvListApps.setAdapter(adapterListApps);
        lmListApps = new LinearLayoutManager(this);
        rvListApps.setLayoutManager(lmListApps);
        int appsCount = rvListApps.getAdapter().getItemCount();
        tvCount = findViewById(R.id.tvCount);
        tvCount.setText(String.valueOf(appsCount));
    }

    public void refreshApps(View view) {
        getRunningApps();
    }

    class VHolderListApps extends RecyclerView.ViewHolder {
        public TextView tvApp;

        public VHolderListApps(@NonNull View itemView) {
            super(itemView);
            tvApp = itemView.findViewById(R.id.tvBackApp);
        }
    }

    class AdapterListApps extends RecyclerView.Adapter<VHolderListApps> {
        @NonNull
        @Override
        public VHolderListApps onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View viewApps = inflater.inflate(R.layout.item_app, parent, false);
            VHolderListApps vHolderListApps = new VHolderListApps(viewApps);
            return vHolderListApps;
        }

        @Override
        public void onBindViewHolder(@NonNull VHolderListApps holder, int position) {
            ActivityManager.RunningAppProcessInfo app = runningApps.get(position);
            TextView textView = holder.tvApp;
            textView.setText(app.processName);
        }

        @Override
        public int getItemCount() {
            return runningApps.size();
        }
    }
}