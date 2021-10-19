package com.example.todolistwithloginregister.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.todolistwithloginregister.model.Task;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DB_NAME = "ToDoListDatabase";
    private static final String USER_TABLE = "users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASS = "password";
    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String TITLEDOES = "titledoes";
    private static final String DATEDOES = "datedoes";
    private static final String DESCDOES = "descdoes";

    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TITLEDOES + " TEXT, "
            + DATEDOES + " TEXT, "
            + DESCDOES + " TEXT)";

    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASS + " TEXT);";

    private SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableAccount = CREATE_TABLE_USERS;
        String createTableTodo = CREATE_TODO_TABLE;

        db.execSQL(createTableAccount);
        db.execSQL(createTableTodo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertTask(Task task) {
        ContentValues cv = new ContentValues();
        cv.put(TITLEDOES, task.getTitledoes());
        cv.put(DATEDOES, task.getDatedoes());
        cv.put(DESCDOES, task.getDescdoes());
        db.insert(TODO_TABLE, null, cv);
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try {
            cursor = db.query(TODO_TABLE, null, null, null, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        Task task = new Task();
                        task.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                        task.setTitledoes(cursor.getString(cursor.getColumnIndex(TITLEDOES)));
                        task.setDatedoes(cursor.getString(cursor.getColumnIndex(DATEDOES)));
                        task.setDescdoes(cursor.getString(cursor.getColumnIndex(DESCDOES)));
                        taskList.add(task);
                    }
                    while (cursor.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            assert cursor != null;
            cursor.close();
        }
        return taskList;
    }

    public void updateTask(int id, String task, String date, String hours) {
        ContentValues cv = new ContentValues();
        cv.put(TITLEDOES, task);
        cv.put(DATEDOES, date);
        cv.put(DESCDOES, hours);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id) {
        db.delete(TODO_TABLE, ID + "= ?", new String[]{String.valueOf(id)});
    }

    public void addUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASS, password);

        long id = db.insert(USER_TABLE, null, values);
        db.close();
    }

    public boolean getUser(String email, String pass){
        String selectQuery = "select * from  " + USER_TABLE + " where " +
                COLUMN_EMAIL + " = " + "'"+email+"'" + " and " + COLUMN_PASS + " = " + "'"+pass+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return true;
        }
        cursor.close();
        db.close();

        return false;
    }
}