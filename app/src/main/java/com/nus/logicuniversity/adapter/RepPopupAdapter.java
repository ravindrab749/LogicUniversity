package com.nus.logicuniversity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.model.Employee;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class RepPopupAdapter extends ArrayAdapter<Employee> {

    public RepPopupAdapter(Context context, int resource, ArrayList<Employee> items) {
        super(context, resource, items);
    }

    @Override
    public Employee getItem(int position) {
        return super.getItem(position);
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {

        if(convertView == null) {
            convertView = super.getView(position, null, parent);
        }

        TextView textView = convertView.findViewById(R.id.tv_rep_name);
        textView.setText(Objects.requireNonNull(getItem(position)).getEmpName());

        return convertView;
    }
}
