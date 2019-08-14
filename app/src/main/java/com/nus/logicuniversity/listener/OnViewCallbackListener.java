package com.nus.logicuniversity.listener;

import android.view.View;

public interface OnViewCallbackListener {
    <T> void onViewCallback(T item, int position, View view);
}
