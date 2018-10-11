package com.example.sveto.zavrsnirad.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.sveto.zavrsnirad.models.User;
import com.example.sveto.zavrsnirad.models.UserContract.*;


/**
 * Created by Sveto on 12.4.2018..
 */

public class UserDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "User.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " +
                UserTable.TABLE_NAME + " ( " +
                UserTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserTable.COLUMN_USERNAME + " TEXT, " +
                UserTable.COLUMN_EMAIL + " TEXT, " +
                UserTable.COLUMN_PASSWORD + " TEXT" +
                ")";

        db.execSQL(SQL_CREATE_USER_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserTable.TABLE_NAME);
        onCreate(db);
    }

    public boolean addUser(User user) {
        db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(UserTable.COLUMN_USERNAME, user.getUsername());
        cv.put(UserTable.COLUMN_EMAIL, user.getEmail());
        cv.put(UserTable.COLUMN_PASSWORD, user.getPassword());

        long result = db.insert(UserTable.TABLE_NAME, null, cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getUser() {
        db = this.getReadableDatabase();
        Cursor cursor = db.query(UserTable.TABLE_NAME, null, null, null, null, null, null, null);
        return cursor;
    }

}
