package com.example.nathalie.todolist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import static com.example.nathalie.todolist.R.layout.row_todo;

/**
 * Created by Nathalie on 20-11-2017.
 */

public abstract class Adapter extends ResourceCursorAdapter {
    String priorityColor;
    int lastItem;

    // Default constructor
    public Adapter(Context context, Cursor cursor) {
        super(context, row_todo, cursor);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        TextView textViewTitle = (TextView) view.findViewById(R.id.toDoItem);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);

        textViewTitle.setTextColor(Color.rgb(64,68,71));
        textViewTitle.setPaintFlags(0);

        int id = cursor.getInt(0);
        int completed = cursor.getInt(cursor.getColumnIndex("completed"));
        String title = cursor.getString(cursor.getColumnIndex("title"));

        // Set text to information from database
        textViewTitle.setText(title);

        // Check checkbox if task is completed
        if(completed == 1) {
            checkBox.setChecked(true);
            textViewTitle.setTextColor(Color.rgb(23,140,0));
            textViewTitle.setPaintFlags(textViewTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            checkBox.setChecked(false);
        }
    }

    public void textColor (String color) {
        Log.d("hallo_color", color);

        priorityColor = color;

    }

}


