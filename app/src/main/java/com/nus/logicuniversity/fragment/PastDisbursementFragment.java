package com.nus.logicuniversity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.adapter.MyDisbursementRecyclerViewAdapter;
import com.nus.logicuniversity.model.DisbursementItem;
import com.nus.logicuniversity.model.DisbursementsResponse;
import com.nus.logicuniversity.retrofit.Api;
import com.nus.logicuniversity.retrofit.RetrofitClient;
import com.nus.logicuniversity.utility.Util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PastDisbursementFragment extends Fragment {

    private ArrayList<DisbursementItem> disbursements;
    private RecyclerView recyclerView;
    private TextView emptyView;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        updateToolbarTitle();
        disbursements = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_disbursement_list, container, false);
        emptyView = view.findViewById(R.id.tv_empty);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyDisbursementRecyclerViewAdapter(disbursements, false,null));
        }

        getAllDisbursements();
        return view;
    }

    private void updateToolbarTitle() {
        Util.updateTitle(getString(R.string.title_fragment_past_dis), Objects.requireNonNull(getActivity()));
    }

    private void getAllDisbursements() {

        String header = Util.getHeaderValueFromSharedPreferences(getActivity());

        Util.showProgressBar(getActivity(), true);
        Api api = RetrofitClient.getInstance().getApi();
        api.getPastDisbursements(header).enqueue(new Callback<DisbursementsResponse>() {
            @Override
            public void onResponse(@NotNull Call<DisbursementsResponse> call, @NotNull Response<DisbursementsResponse> response) {
                DisbursementsResponse res = response.body();
                disbursements.clear();
                if(res != null) {
                    disbursements.addAll(res.getDisbursementList());
                    Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                }
                Util.showProgressBar(getActivity(), false);
                showEmptyView(disbursements.isEmpty());
            }

            @Override
            public void onFailure(@NotNull Call<DisbursementsResponse> call, @NotNull Throwable t) {
                Util.showProgressBar(getActivity(), false);
                showEmptyView(disbursements.isEmpty());
            }
        });
    }

    private void showEmptyView(boolean show) {
        emptyView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
