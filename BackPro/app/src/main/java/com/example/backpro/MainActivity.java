package com.example.backpro;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.UserManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class Killapp {
    private int pid;
    private String name;

    public Killapp(int pid, String name) {
        this.pid = pid;
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }
}

public class MainActivity extends AppCompatActivity {

    private List<Killapp> runningApps;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            assignRunningApps((UsageStatsManager) getSystemService(USAGE_STATS_SERVICE));
        } else {
            assignRunningApps((ActivityManager) getSystemService(ACTIVITY_SERVICE));
        }
        rvListApps = findViewById(R.id.rvListApps);
        adapterListApps = new AdapterListApps();
        rvListApps.setAdapter(adapterListApps);
        lmListApps = new LinearLayoutManager(this);
        rvListApps.setLayoutManager(lmListApps);
        int appsCount = rvListApps.getAdapter().getItemCount();
        tvCount = findViewById(R.id.tvCount);
        tvCount.setText(String.valueOf(appsCount));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void assignRunningApps(UsageStatsManager mUsageStatsManager) {
        UserManager userManager = (UserManager) this.getSystemService(Context.USER_SERVICE);
        boolean isUserUnlocked = userManager.isUserUnlocked();
        Toast toast = Toast.makeText(this, "The user is unlocked:" + isUserUnlocked, Toast.LENGTH_SHORT);
        toast.show();
        runningApps = new ArrayList<>();
        long currentTime = System.currentTimeMillis();
        // get usage stats for the last 10 seconds
        List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, currentTime - 1000 * 10, currentTime);
        // search for app with most recent last used time
        if (stats != null) {
            for (UsageStats usageStats : stats) {
                runningApps.add(new Killapp(-1, usageStats.getPackageName()));
            }
        }
    }

    private void assignRunningApps(ActivityManager activityManager) {
        runningApps = new ArrayList<>();
        for (ActivityManager.RunningAppProcessInfo app : activityManager.getRunningAppProcesses()) {
            runningApps.add(new Killapp(app.pid, app.processName));
        }
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
            Killapp app = runningApps.get(position);
            TextView textView = holder.tvApp;
            textView.setText(app.getName());
        }

        @Override
        public int getItemCount() {
            return runningApps.size();
        }
    }
}