package com.nus.logicuniversity.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.nus.logicuniversity.activity.MainActivity;
import com.nus.logicuniversity.model.Employee;

import java.util.Map;
import java.util.Objects;

public class Util {

    private static final String SF_KEY = "Lu_Pref";

    public static final String AUTH_HEADER = "Authorization";
    public static final String FP_KEY = "finger_print";

    public static void updateTitle(String title, FragmentActivity activity) {
        activity.setTitle(title);
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showProgressBar(FragmentActivity activity, boolean show) {
        ((MainActivity) Objects.requireNonNull(activity)).showProgressBar(show);
    }

    public static ProgressBar initProgressBar(FragmentActivity activity, ViewGroup viewGroup) {
        ProgressBar progressBar = new ProgressBar(activity,null,android.R.attr.progressBarStyleLarge);

//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
//        params.addRule(RelativeLayout.CENTER_IN_PARENT);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);

        viewGroup.addView(progressBar,params);
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
        progressBar.setVisibility(View.VISIBLE);
//        progressBar.setVisibility(View.GONE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        return progressBar;
    }

    private static SharedPreferences getSharedPreferences(FragmentActivity activity) {
        return activity.getSharedPreferences(SF_KEY, Context.MODE_PRIVATE);
    }

    public static String getValueFromSharedPreferences(String key, FragmentActivity activity) {
        return getSharedPreferences(activity).getString(key, null);
    }

    public static String getHeaderValueFromSharedPreferences(FragmentActivity activity) {
        return getSharedPreferences(activity).getString(AUTH_HEADER, null);
    }

    public static Map<String, ?> getAllFromSharedPreferences(FragmentActivity activity) {
        return getSharedPreferences(activity).getAll();
    }

    public static void addHeaderToSharedPref(FragmentActivity activity, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(activity).edit();
        editor.putString(AUTH_HEADER, value);
        editor.apply();
    }

    public static void addToSharedPref(FragmentActivity activity, String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(activity).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void addUserToSharedPref(FragmentActivity activity, Employee emp) {
        SharedPreferences.Editor editor = getSharedPreferences(activity).edit();
        editor.putString("name", emp.getEmpName());
        editor.putString("username", emp.getUserName());
        editor.putString("password", emp.getPassword());
        editor.putString("role", emp.getEmpRole());
        editor.putString("displayRole", emp.getEmpDisplayRole());
        editor.apply();
    }

    public static void clearSharedPref(FragmentActivity activity) {
        SharedPreferences.Editor editor = getSharedPreferences(activity).edit();
        editor.clear();
        editor.apply();
    }

}
