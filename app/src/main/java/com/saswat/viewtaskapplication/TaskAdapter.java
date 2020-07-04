package com.saswat.viewtaskapplication;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    private Context context;
    private ArrayList<Task> tasks;
    private TaskUpdateListener listener;

    public TaskAdapter (Context context , ArrayList<Task> tasks){
        this.context = context;
        this.tasks = tasks;
    }

    public void setListener(TaskUpdateListener listener){
        this.listener=listener;
    }
    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskHolder(LayoutInflater.from(context).inflate(R.layout.cell_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        final Task item = tasks.get(position);
        holder.mTvTaskTitle.setText(item.task_name);
        ArrayList<TaskItems> taskItems = Task.convertItemsToArrayList(item.task_items);
        holder.mllDynamicView.removeAllViews();
        for (final TaskItems taskItems1: taskItems){
            View view = LayoutInflater.from(context).inflate(R.layout.cell_task_view,null);
            TextView mTvTitle = view.findViewById(R.id.tv_items_display);
            CheckBox mChkBoxItems = view.findViewById(R.id.chk_view);
            if(taskItems1.itemIsCompleted){
                mChkBoxItems.setChecked(true);
                mChkBoxItems.setFocusable(false);
                mChkBoxItems.setFocusableInTouchMode(false);
                mTvTitle.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
            mTvTitle.setText(taskItems1.items_name);
            mChkBoxItems.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        listener.onTaskUpdate(taskItems1 , item);
                    }
                }
            });
            holder.mllDynamicView.addView(view);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class TaskHolder extends RecyclerView.ViewHolder{
        private TextView mTvTaskTitle;
        private LinearLayout mllDynamicView;
        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mTvTaskTitle = itemView.findViewById(R.id.tv_task_title);
            mllDynamicView = itemView.findViewById(R.id.ll_dynamic_view);
        }
    }

    public interface TaskUpdateListener{
        void onTaskUpdate(TaskItems item , Task task);
    }
}
