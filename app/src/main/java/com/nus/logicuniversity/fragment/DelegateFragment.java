package com.nus.logicuniversity.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nus.logicuniversity.R;

public class DelegateFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delegate, container, false);
        EditText etFromDate = view.findViewById(R.id.et_from_date);
        etFromDate.setKeyListener(null);
        return view;
    }

}
