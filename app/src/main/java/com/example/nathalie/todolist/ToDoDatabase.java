package com.example.nathalie.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Nathalie on 20-11-2017.
 */

public class ToDoDatabase extends SQLiteOpenHelper {


    private static ToDoDatabase instance;
    private static final String TABLE_NAME = "todos";
    private static final String ID = "_id";
    private static final String TITLE = "title";
    private static final String COMPLETED = "completed";

    public static ToDoDatabase getInstance (Context context) {


        if(instance == null) {
            // call the private constructor
            instance = new ToDoDatabase(context);
        }
        return instance;

    }

    private ToDoDatabase(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME +  " (" + ID +  " INTEGER PRIMARY KEY AUTOINCREMENT, " + COMPLETED + " INTEGER, " + TITLE +   " TEXT);");

        db.execSQL("INSERT INTO " + TABLE_NAME + "(" + TITLE + ", " + COMPLETED + ") VALUES (\'Do laundry\', 0);");
        db.execSQL("INSERT INTO " + TABLE_NAME + "(" + TITLE + ", " + COMPLETED + ") VALUES (\'Get started\', 0);");
        db.execSQL("INSERT INTO " + TABLE_NAME + "(" + TITLE + ", " + COMPLETED + ") VALUES (\'More items\', 0);");


//        Log.d("hallo_data", String.valueOf());




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // database as input value? or getWritableDatabase?
    public Cursor selectAll() {

        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("hallo", String.valueOf(db));
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;

    }


    public void insert(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues(2);
        contentValues.put(TITLE, item);
        contentValues.put(COMPLETED, 0);

        db.insert(TABLE_NAME, null, contentValues);
    }

    public void delete(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues(3);

        int x = db.delete(TABLE_NAME,"_id = " + id, null);
        Log.d("hallo_delete", String.valueOf(x));
    }

    public void update(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE " + TABLE_NAME + " SET " + COMPLETED + " = (CASE " + COMPLETED + " WHEN 1 THEN 0 ELSE 1 END) WHERE " + ID + "= " + id + ";");

    }
}


