package com.nus.logicuniversity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.listener.OnMenuButtonSelectedListener;
import com.nus.logicuniversity.utility.Util;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private final OnMenuButtonSelectedListener mListener;

    public HomeFragment(OnMenuButtonSelectedListener listener) {
        mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        updateToolbarTitle();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button pendingBtn = view.findViewById(R.id.id_pending);
        Button pastRecBtn = view.findViewById(R.id.id_past_orders);
        Button delegateBtn = view.findViewById(R.id.id_delegate);
        pendingBtn.setOnClickListener(this);
        pastRecBtn.setOnClickListener(this);
        delegateBtn.setOnClickListener(this);
        return view;
    }

    private void updateToolbarTitle() {
        Util.updateTitle(getString(R.string.title_activity_home), getActivity());
    }

    @Override
    public void onClick(View view) {
        mListener.onMenuButtonSelected(view.getId());
    }
}
