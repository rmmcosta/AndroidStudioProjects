package com.example.uiwidgets;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String LOG = "MyLog";
    TextView tvPresentMe;
    private boolean areNotificationsActive;
    private Person me;

    public void change2Female(View view) {
        me.gender = Gender.FEMALE;
        updateResult();
    }

    public void change2Male(View view) {
        me.gender = Gender.MALE;
        updateResult();
    }

    private void updateResult() {
        tvPresentMe.setText(me.toString());
    }

    public void onHobbyClick(View view) {
        updateHobbies(view.getId(), ((CheckBox)view).isChecked());
    }

    private void updateHobbies(int id, boolean checked) {
        CheckBox clickedCheckBox = findViewById(id);
        String hobby = clickedCheckBox.getText().toString();
        if (checked) {
            me.addHobby(hobby);
        } else {
            me.removeHobby(hobby);
        }
        updateResult();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTimerNotify();
        me = new Person();
        tvPresentMe = findViewById(R.id.tvPresentMe);
    }

    private void initTimerNotify() {
        final Handler handlerNotifications = new Handler();
        Runnable notify = new Runnable() {
            @Override
            public void run() {
                if (areNotificationsActive) {
                    doDummyNotify();
                }
                handlerNotifications.postDelayed(this, 2000);
            }

            private void doDummyNotify() {
                Toast dummyNotify = Toast.makeText(getApplicationContext(), "Hello from the dummy notify", Toast.LENGTH_SHORT);
                dummyNotify.show();
            }
        };
        handlerNotifications.post(notify);
    }

    public void onNotificationsChange(View view) {
        boolean is2Notify = ((SwitchCompat) view).isChecked();
        Log.d(LOG, "is 2 notify:" + is2Notify);
        areNotificationsActive = is2Notify;
    }

    public void onGenderChange(View view) {
    }

    private enum Gender {MALE, FEMALE}

    class Person {
        Gender gender;
        ArrayList<String> hobbies;

        public Person() {
            hobbies = new ArrayList<>();
        }

        private void addHobby(String hobby) {
            if (hobbies.indexOf(hobby) == -1) {
                hobbies.add(hobby);
            }
        }

        private void removeHobby(String hobby) {
            if (hobbies.indexOf(hobby) != -1) {
                hobbies.remove(hobby);
            }
        }

        @NonNull
        @Override
        public String toString() {
            String present = "Hello I'm a ";
            switch (gender) {
                case MALE:
                    present += "Male.";
                    break;
                case FEMALE:
                    present += "Female.";
                    break;
                default:
                    present += "N/A gender.";
                    break;
            }
            if (hobbies.size() > 0) {
                present += "\nAnd these are my hobbies:\n";
            }
            for (String s : hobbies) {
                present += s + "\n";
            }
            return present;
        }
    }
}