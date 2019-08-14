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

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DeptHeadHomeFragment extends Fragment implements View.OnClickListener {

    private final OnMenuButtonSelectedListener mListener;

    public DeptHeadHomeFragment(OnMenuButtonSelectedListener listener) {
        mListener = listener;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        updateToolbarTitle();
        Button pendingBtn = view.findViewById(R.id.id_pending);
        Button pastRecBtn = view.findViewById(R.id.id_past_orders);
        Button delegateBtn = view.findViewById(R.id.id_delegate);
        pendingBtn.setOnClickListener(this);
        pastRecBtn.setOnClickListener(this);
        delegateBtn.setOnClickListener(this);
    }

    private void updateToolbarTitle() {
        Util.updateTitle(getString(R.string.title_activity_home), Objects.requireNonNull(getActivity()));
    }

    @Override
    public void onClick(View view) {
        mListener.onMenuButtonSelected(view.getId());
    }
}
