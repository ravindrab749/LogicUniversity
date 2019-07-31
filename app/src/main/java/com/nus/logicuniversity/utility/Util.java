package com.nus.logicuniversity.utility;

import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

public class Util {

    public static void updateTitle(String title, FragmentActivity activity) {
        activity.setTitle(title);
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
