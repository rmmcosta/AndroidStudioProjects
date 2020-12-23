package com.developer.notificationscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int JOB_ID = 3322;
    private JobScheduler jobScheduler;
    private TextView tvOverrideDeadline;
    private int overrideDeadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvOverrideDeadline = findViewById(R.id.tvOverrideDeadline);
        SeekBar sbOverrideDeadline = findViewById(R.id.sbOverrideDeadline);
        sbOverrideDeadline.setOnSeekBarChangeListener(new CustomSeekBarChangeListener());
    }

    class CustomSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            tvOverrideDeadline.setText(String.format(getString(R.string.override_deadline_progress_text), i));
            overrideDeadline = i;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }


    public void scheduleJobs(View view) {
        if (!hasMinimumRequirements()) {
            showMessage("You must define at least one constraint!");
            return;
        }
        jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        ComponentName componentName = new ComponentName(getPackageName(), NotificationJobService.class.getName());
        JobInfo.Builder jobInfoBuilder = getJobBuilder(componentName);
        JobInfo jobInfo = jobInfoBuilder.build();
        jobScheduler.schedule(jobInfo);
        showMessage(String.format(getString(R.string.job_scheduled_text), JOB_ID));
    }

    private boolean hasMinimumRequirements() {
        return getSelectedNetworkType() != JobInfo.NETWORK_TYPE_NONE || getCharging() || getIdle() || overrideDeadline > 0;
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private JobInfo.Builder getJobBuilder(ComponentName componentName) {
        JobInfo.Builder jobInfoBuilder = new JobInfo.Builder(JOB_ID, componentName);

        jobInfoBuilder.setRequiredNetworkType(getSelectedNetworkType());

        jobInfoBuilder.setRequiresCharging(getCharging());

        jobInfoBuilder.setRequiresDeviceIdle(getIdle());

        jobInfoBuilder.setOverrideDeadline(overrideDeadline * 1000);

        return jobInfoBuilder;
    }

    private int getSelectedNetworkType() {
        int selectedNetworkType = JobInfo.NETWORK_TYPE_NONE;
        RadioGroup rgNetworkType = findViewById(R.id.rgNetworkType);
        int rbChecked = rgNetworkType.getCheckedRadioButtonId();
        if (rbChecked == R.id.rbAny)
            selectedNetworkType = JobInfo.NETWORK_TYPE_ANY;
        if (rbChecked == R.id.rbWiFi)
            selectedNetworkType = JobInfo.NETWORK_TYPE_UNMETERED;
        if (rbChecked == R.id.rbCellular)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                selectedNetworkType = JobInfo.NETWORK_TYPE_CELLULAR;
            }
        return selectedNetworkType;
    }

    private boolean getCharging() {
        SwitchCompat switchCompatCharging = findViewById(R.id.switchCharging);
        return switchCompatCharging.isChecked();
    }

    private boolean getIdle() {
        SwitchCompat switchCompatIdle = findViewById(R.id.switchIdle);
        return switchCompatIdle.isChecked();
    }

    public void cancelJobs(View view) {
        jobScheduler.cancel(JOB_ID);
        showMessage(String.format(getString(R.string.job_cancelled), JOB_ID));
    }
}