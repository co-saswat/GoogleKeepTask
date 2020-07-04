package com.saswat.viewtaskapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {
    private EditText mEtTaskTitle;
    private LinearLayout llDynamicHolder;
    private RelativeLayout rlAddItem;
    private int row = 0;
    private DatabaseHelper dbHelper;
    private ArrayList<TaskItems> taskItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Toolbar toolbar = findViewById(R.id.tl_add_task);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Add Task");
        }

        mEtTaskTitle = findViewById(R.id.et_task_title);
        llDynamicHolder = findViewById(R.id.ll_dynamic_items);
        rlAddItem = findViewById(R.id.rl_add_item);
        taskItems = new ArrayList<>();
        dbHelper = new DatabaseHelper(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }


    public void onTaskAddItemClicked(View view) {
        String taskTitle = mEtTaskTitle.getText().toString();
        String items = Task.convertItemsListToString(taskItems);

        Task task = new Task();
        task.task_name = taskTitle;
        task.task_items = items;
        dbHelper.insertTasks(dbHelper.getWritableDatabase(), task);

        setResult(Activity.RESULT_OK);
        finish();
    }

    public void onAddListItemClicked(View view) {
        rlAddItem.setEnabled(false);
        row++;
        View view1 = LayoutInflater.from(AddActivity.this).inflate(R.layout.cell_task_edit, null);
        final EditText mEtTaskItem = view1.findViewById(R.id.et_text_items);
        ImageView mIvAddIcon = view1.findViewById(R.id.iv_edit_done);
        mIvAddIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskItems newTaskItems = new TaskItems();
                newTaskItems.items_id = row;
                newTaskItems.items_name = mEtTaskItem.getText().toString();
                newTaskItems.itemIsCompleted = false;

                taskItems.add(newTaskItems);
                rlAddItem.setEnabled(true);
            }
        });
        llDynamicHolder.addView(view1);
    }
}