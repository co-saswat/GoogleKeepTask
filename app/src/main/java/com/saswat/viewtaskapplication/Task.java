package com.saswat.viewtaskapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Task {
    public int id;
    public String task_name;
    public String task_items;

    public static ArrayList<TaskItems> convertItemsToArrayList(String items){
        ArrayList<TaskItems> taskItems = new ArrayList<>();
        try{
            JSONArray itemsArray = new JSONArray(items);
            for(int i=0;i<itemsArray.length();i++){
                JSONObject itemsObjects = itemsArray.getJSONObject(i);
                TaskItems taskItems1 = new TaskItems();
                taskItems1.items_id = itemsObjects.getInt(TaskItems.CONST_ITEM_ID);
                taskItems1.items_name = itemsObjects.getString(TaskItems.CONST_ITEM_NAME);
                taskItems1.itemIsCompleted = itemsObjects.getBoolean(TaskItems.CONST_ITEM_COMPLETED);
                taskItems.add(taskItems1);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return taskItems;

    }

    public static String convertItemsListToString(ArrayList<TaskItems> taskItems){
        String itemsArrayValue = "";
        JSONArray itemsArray = new JSONArray();
        for(TaskItems items:taskItems){
            try{
                JSONObject object = new JSONObject();
                object.put(TaskItems.CONST_ITEM_ID,items.items_id);
                object.put(TaskItems.CONST_ITEM_NAME,items.items_name);
                object.put(TaskItems.CONST_ITEM_COMPLETED,items.itemIsCompleted);
                itemsArray.put(object);
            }catch (JSONException e){
                e.printStackTrace();
            }

        }
        itemsArrayValue = itemsArray.toString();
        return  itemsArrayValue;
    }
}
