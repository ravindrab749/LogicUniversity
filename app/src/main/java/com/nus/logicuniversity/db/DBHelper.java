package com.nus.logicuniversity.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "logic_university.db";
    protected static final String TABLE_NAME_LOGIN = "login";
    protected static final String LOGIN_KEY_USERNAME = "username";
    protected static final String LOGIN_KEY_PASSWORD = "password";
    protected static final String LOGIN_KEY_FINGERPRINT = "enrolled_biometric";

    private static final String TABLE_LOGIN_PRIMARY_KEY = LOGIN_KEY_USERNAME + " VARCHAR PRIMARY KEY, ";
    private static final String CREATE_LOGIN_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_LOGIN + " (" + TABLE_LOGIN_PRIMARY_KEY + LOGIN_KEY_PASSWORD + " TEXT, " + LOGIN_KEY_FINGERPRINT + " INTEGER DEFAULT 0 NOT NULL CHECK ("+LOGIN_KEY_FINGERPRINT+" IN (0,1)))";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_LOGIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LOGIN);
        onCreate(sqLiteDatabase);
    }
}
