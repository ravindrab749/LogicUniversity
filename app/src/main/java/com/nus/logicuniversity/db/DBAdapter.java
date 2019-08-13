package com.nus.logicuniversity.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.nus.logicuniversity.model.User;

import java.util.ArrayList;

public class DBAdapter {

    private DBHelper dbHelper;

    public DBAdapter(Context context) {
        dbHelper = new DBHelper(context);
    }

    public boolean insertLogin(String username, String password) {
        return insertLogin(username, password, false);
    }

    public boolean insertLogin(String username, String password, boolean isFingerprint) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.LOGIN_KEY_USERNAME, username);
        values.put(DBHelper.LOGIN_KEY_PASSWORD, password);
        values.put(DBHelper.LOGIN_KEY_FINGERPRINT, isFingerprint ? 1 : 0);
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

    private Cursor getData(String query) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery(query, null);
    }

    public ArrayList<User> getAllUsers() {
        Cursor cursor = getData("SELECT * FROM " + DBHelper.TABLE_NAME_LOGIN);
        cursor.moveToFirst();
        ArrayList<User> users = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            User user = new User();
            user.setUsername(cursor.getString(cursor.getColumnIndex(DBHelper.LOGIN_KEY_USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(DBHelper.LOGIN_KEY_PASSWORD)));
            user.setEnrolledFingerprint(cursor.getInt(cursor.getColumnIndex(DBHelper.LOGIN_KEY_FINGERPRINT)) == 1);
            users.add(user);
        }
        return users;
    }

    public User getUser() {
        User user = null;
        Cursor cursor = getData("SELECT * FROM " + DBHelper.TABLE_NAME_LOGIN);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            user = new User();
            user.setUsername(cursor.getString(cursor.getColumnIndex(DBHelper.LOGIN_KEY_USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(DBHelper.LOGIN_KEY_PASSWORD)));
            user.setEnrolledFingerprint(cursor.getInt(cursor.getColumnIndex(DBHelper.LOGIN_KEY_FINGERPRINT)) == 1);
        }
        return user;
    }

    public User getUserByUsername(String username) {
        User user = null;
        Cursor cursor = getData("SELECT * FROM " + DBHelper.TABLE_NAME_LOGIN + " WHERE " + DBHelper.LOGIN_KEY_USERNAME + "='" + username + "'");
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            user = new User();
            user.setUsername(cursor.getString(cursor.getColumnIndex(DBHelper.LOGIN_KEY_USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(DBHelper.LOGIN_KEY_PASSWORD)));
            user.setEnrolledFingerprint(cursor.getInt(cursor.getColumnIndex(DBHelper.LOGIN_KEY_FINGERPRINT)) == 1);
        }
        return user;
    }
}
