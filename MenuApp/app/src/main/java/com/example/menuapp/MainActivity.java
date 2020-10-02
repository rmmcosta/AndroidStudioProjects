package com.example.menuapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ActionMode actionMode;

    class MyActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.menu_action_mode, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action1Id:
                    showMessage("perform action 1");
                    mode.finish();
                    return true;
                default:
                    showMessage("perform default action");
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    }


    private TextView tvHello;
    private TextView tvCenas;
    private TextView tvActionMode;
    private int selectedTextViewId;
    private static final String LOG = "MyLog";
    private ActionMode.Callback actionModeCallback = new MyActionModeCallback();
    private View.OnLongClickListener actionModeClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (actionMode != null) {
                return false;
            } else {
                actionMode = MainActivity.this.startActionMode(actionModeCallback);
                v.setSelected(true);
                return true;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvHello = findViewById(R.id.tvHello);
        tvCenas = findViewById(R.id.tvCenas);
        tvActionMode = findViewById(R.id.tvActionMode);
        tvActionMode.setOnLongClickListener(actionModeClick);
        registerForContextMenu(tvHello);
        registerForContextMenu(tvCenas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context_operations, menu);
        selectedTextViewId = v.getId();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast toast = Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT);
        toast.show();
        return true;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        int viewId = selectedTextViewId;
        switch (itemId) {
            case R.id.itemShow:
                showDetail(viewId);
                return true;
            case R.id.itemEdit:
                editDetail(viewId);
                return true;
            case R.id.itemDelete:
                deleteDetail(viewId);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void editDetail(int viewId) {
        String message;
        switch (viewId) {
            case R.id.tvHello:
                message= "edit Hello";
                break;
            case R.id.tvCenas:
                message = "edit Cenas";
                break;
            default:
                message = "edit Unknown";
        }
        showMessage(message);
    }

    private void deleteDetail(int viewId) {
        String message;
        switch (viewId) {
            case R.id.tvHello:
                message= "delete Hello";
                break;
            case R.id.tvCenas:
                message = "delete Cenas";
                break;
            default:
                message = "delete Unknown";
        }
        showMessage(message);
    }

    private void showDetail(int viewId) {
        String message;
        switch (viewId) {
            case R.id.tvHello:
                message= "show detail of Hello";
                break;
            case R.id.tvCenas:
                message = "show detail of Cenas";
                break;
            default:
                message = "show Unknown";
        }
        showMessage(message);
    }

    private void showMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}