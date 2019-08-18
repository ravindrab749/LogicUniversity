package com.nus.logicuniversity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.model.CollectionPoint;
import com.nus.logicuniversity.model.Employee;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class CollectionPointPopupAdapter extends ArrayAdapter<CollectionPoint> {

    public CollectionPointPopupAdapter(Context context, int resource, ArrayList<CollectionPoint> items) {
        super(context, resource, items);
    }

    @Override
    public CollectionPoint getItem(int position) {
        return super.getItem(position);
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {

        if(convertView == null) {
            convertView = super.getView(position, null, parent);
        }

        TextView textView = convertView.findViewById(R.id.tv_rep_name);
        textView.setText(Objects.requireNonNull(getItem(position)).getName());

        return convertView;
    }
}
