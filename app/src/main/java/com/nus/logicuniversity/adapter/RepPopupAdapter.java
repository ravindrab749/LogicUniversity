package com.nus.logicuniversity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.nus.logicuniversity.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RepPopupAdapter extends ArrayAdapter<Map<String, String>> {

    private ArrayList<Map<String, String>> items;
    private LayoutInflater inflater = null;

    public RepPopupAdapter(Context context, int resource, ArrayList<Map<String, String>> items) {
        super(context, resource, items);
        this.items = items;
    }

    @Override
    public Map<String, String> getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = super.getView(position, convertView, parent);
        }
        TextView textView = convertView.findViewById(R.id.tv_rep_name);
        textView.setText(getItem(position).get("name"));

        return convertView;
    }
}
