package com.nus.logicuniversity.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.nus.logicuniversity.model.User;

public class DBAdapter {

    private DBHelper dbHelper = null;

    private static DBAdapter instance = null;

    public DBAdapter(Context context) {
        dbHelper = new DBHelper(context);
    }

    public boolean insertLogin(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.LOGIN_KEY_USERNAME, username);
        values.put(DBHelper.LOGIN_KEY_PASSWORD, password);
        long row = db.insert(DBHelper.TABLE_NAME_LOGIN, null, values);
        return (row > 0);
    }

    public boolean updateFingerprint(boolean enable, String username) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.LOGIN_KEY_FINGERPRINT, enable ? 1 : 0);
        return (db.update(DBHelper.TABLE_NAME_LOGIN, values, DBHelper.LOGIN_KEY_USERNAME+"=?", new String[]{username}) > 0);
    }

    public int getRowCount() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, DBHelper.TABLE_NAME_LOGIN);
    }

    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABLE_NAME_LOGIN, null, null);
    }

    public Cursor getData(String query) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery(query, null);
    }

    public User getUser() {
        Cursor cursor = getData("SELECT * FROM " + DBHelper.TABLE_NAME_LOGIN);
        cursor.moveToFirst();
        User user = null;
        while (!cursor.isAfterLast()) {
            user = new User();
            user.setUsername(cursor.getString(cursor.getColumnIndex(DBHelper.LOGIN_KEY_USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(DBHelper.LOGIN_KEY_PASSWORD)));
            user.setEnrolledFingerprint(cursor.getInt(cursor.getColumnIndex(DBHelper.LOGIN_KEY_FINGERPRINT)) == 1);
            break;
        }
        return user;
    }

}
