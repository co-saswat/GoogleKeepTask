package com.saswat.viewtaskapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "view_details.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "task_view";
    private static final String COL_ID = "id";
    private static final String COL_CARD_NAME = "task_name";
    private static final String COL_ITEMS = "items";

    private static final String CREATE_TABLE = "CREATE TABLE " +TABLE_NAME+ " (" +COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_CARD_NAME + " TEXT," + COL_ITEMS + " TEXT);";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insertTasks(SQLiteDatabase database,Task task){
        ContentValues cv = new ContentValues();
        cv.put(COL_CARD_NAME,task.task_name);
        cv.put(COL_ITEMS,task.task_items);
        long result = database.insert(TABLE_NAME,null,cv);
        if (result == -1){
            Toast.makeText(context, "Error!!!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Success!!!", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<Task> getTasksFromDatabase(SQLiteDatabase database){
        ArrayList<Task> tasks = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()){
            do{
                Task items = new Task();
                items.id = cursor.getInt(cursor.getColumnIndex(COL_ID));
                items.task_name = cursor.getString(cursor.getColumnIndex(COL_CARD_NAME));
                items.task_items = cursor.getString(cursor.getColumnIndex(COL_ITEMS));
                tasks.add(items);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return  tasks;
    }

    public void updateTasks(SQLiteDatabase database , Task task){
        ContentValues cv = new ContentValues();
        cv.put(COL_CARD_NAME, task.task_name);
        cv.put(COL_ITEMS,task.task_items);
        database.update(TABLE_NAME,cv,COL_ID+"="+task.id,null);
    }

    public void deleteTasks(SQLiteDatabase database , Task task)
    {
        database.delete(TABLE_NAME,COL_ID+"="+ task.id,null);
    }
}
