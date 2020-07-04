package com.saswat.viewtaskapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class ViewTasksActivity extends AppCompatActivity implements TaskAdapter.TaskUpdateListener{
    private RecyclerView mRcTaskView;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);
        mRcTaskView = findViewById(R.id.rc_view_notes_card);
        mRcTaskView.setLayoutManager(new StaggeredGridLayoutManager(2,RecyclerView.VERTICAL));

        dbHelper = new DatabaseHelper(this);
        getDataFromDatabase();
    }
    public void onAddTaskClicked(View view){
        startActivityForResult( new Intent(ViewTasksActivity.this,AddActivity.class),1111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1111 && resultCode == Activity.RESULT_OK){
            getDataFromDatabase();
        }
    }
    private void getDataFromDatabase(){
        ArrayList<Task> tasks = dbHelper.getTasksFromDatabase(dbHelper.getReadableDatabase());
        TaskAdapter adapter = new TaskAdapter(this,tasks);
        adapter.setListener(this);
        mRcTaskView.setAdapter(adapter);


    }

    @Override
    public void onTaskUpdate(TaskItems item, Task task) {
        ArrayList<TaskItems> taskItems = Task.convertItemsToArrayList(task.task_items);

        for (TaskItems taskItemsobj : taskItems){
            if(taskItemsobj.items_id == item.items_id){
                taskItemsobj.itemIsCompleted = true;
            }
        }

        String itemArrayValue = Task.convertItemsListToString(taskItems);

        Task updateTask = new Task();
        updateTask.id = task.id;
        updateTask.task_name = task.task_name;
        updateTask.task_items = itemArrayValue;

        dbHelper.updateTasks(dbHelper.getWritableDatabase(),updateTask);
        getDataFromDatabase();
    }
}