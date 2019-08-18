package com.nus.logicuniversity.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.adapter.RepPopupAdapter;
import com.nus.logicuniversity.model.Employee;
import com.nus.logicuniversity.model.RepresentativesResponse;
import com.nus.logicuniversity.retrofit.Api;
import com.nus.logicuniversity.retrofit.RetrofitClient;
import com.nus.logicuniversity.utility.Util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepresentativeFragment extends Fragment implements View.OnClickListener {

    private TextView repNameTextView;
    private RepresentativesResponse repRes;
    private Button changeBtn;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_representative, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        updateToolbarTitle();
        repNameTextView = view.findViewById(R.id.tv_rep_name);
        getRepresentatives();
        changeBtn = view.findViewById(R.id.btn_change);
        changeBtn.setOnClickListener(this);
        changeBtn.setClickable(false);
    }

    private void updateToolbarTitle() {
        Util.updateTitle(getString(R.string.title_fragment_change_rep), Objects.requireNonNull(getActivity()));
    }

    @Override
    public void onClick(View view) {
        showDialog();
    }

    private void showDialog() {

        if(repRes == null || repRes.getRepList() == null || repRes.getRepList().isEmpty()) {
            Util.showToast(getActivity(), "No Representatives Found!");
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle(getString(R.string.title_select_rep));
        final ArrayList<Employee> list = getFilteredReps();
        if(list.isEmpty()) {
            Util.showToast(getActivity(), "No Representatives Found!");
            return;
        }

        RepPopupAdapter adapter = new RepPopupAdapter(getActivity(), R.layout.popup_rep, list);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                changeBtn.setClickable(false);
                changeRep(list.get(i));
            }
        });

        builder.create().show();

    }

    private ArrayList<Employee> getFilteredReps() {
        ArrayList<Employee> emps = new ArrayList<>(repRes.getRepList());
        if(repRes.getCurRep() == null) {
            return emps;
        }

        for(int i=0; i<emps.size(); i++) {
            if(repRes.getCurRep().getEmpId() == emps.get(i).getEmpId()) {
                emps.remove(emps.get(i));
                break;
            }
        }
        return emps;
    }

    private void changeRep(final Employee e) {

        String header = Util.getHeaderValueFromSharedPreferences(getActivity());
        Util.showProgressBar(getActivity(), false);

        Api api = RetrofitClient.getInstance().getApi();
        api.changeRepresentative(header, e.getEmpId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                String msg = response.body();
                if("Success".equalsIgnoreCase(msg)) {
                    repNameTextView.setText(e.getEmpName());
                    repRes.setCurRep(e);
                }
                Util.showProgressBar(getActivity(), false);
                changeBtn.setClickable(true);
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                Util.showProgressBar(getActivity(), false);
                changeBtn.setClickable(true);
            }
        });
    }

    private void getRepresentatives() {

        String header = Util.getHeaderValueFromSharedPreferences(getActivity());
        Util.showProgressBar(getActivity(), true);

        Api api = RetrofitClient.getInstance().getApi();
        api.getAllRepresentatives(header).enqueue(new Callback<RepresentativesResponse>() {
            @Override
            public void onResponse(@NotNull Call<RepresentativesResponse> call, @NotNull Response<RepresentativesResponse> response) {
                RepresentativesResponse res = response.body();
                repRes = res;
                assert res != null;
                Employee crntRep = res.getCurRep();
                if(crntRep != null)
                    repNameTextView.setText(crntRep.getEmpName());
                changeBtn.setClickable(true);
                Util.showProgressBar(getActivity(), false);
            }

            @Override
            public void onFailure(@NotNull Call<RepresentativesResponse> call, @NotNull Throwable t) {
                Util.showProgressBar(getActivity(), false);
                changeBtn.setClickable(true);
            }
        });
    }

}
