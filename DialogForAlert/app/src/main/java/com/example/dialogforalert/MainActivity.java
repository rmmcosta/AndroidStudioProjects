package com.example.dialogforalert;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private AlertDialog continueDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDialog();
    }

    public void showAlert(View view) {

        continueDialog.show();
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.alert_dialog_continue_message)
                .setNegativeButton(getString(R.string.alert_dialog_negative_option), new MyDialogInterface("Pressed Cancel"))
                .setPositiveButton(getString(R.string.alert_dialog_continue_positive_option), new MyDialogInterface("Pressed Ok"));
        builder.setTitle(R.string.alert_dialog_continue_title);
        continueDialog = builder.create();
    }

    private void showMessage(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.MainLayout), message, BaseTransientBottomBar.LENGTH_SHORT);
        snackbar.show();
    }

    class MyDialogInterface implements DialogInterface.OnClickListener {

        private String message;

        public MyDialogInterface(String message) {
            this.message = message;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            showMessage(message);
        }
    }
}