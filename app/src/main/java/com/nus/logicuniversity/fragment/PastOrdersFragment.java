package com.nus.logicuniversity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.adapter.MyOrderRecyclerViewAdapter;
import com.nus.logicuniversity.model.Requisition;
import com.nus.logicuniversity.model.RequisitionResponse;
import com.nus.logicuniversity.retrofit.Api;
import com.nus.logicuniversity.retrofit.RetrofitClient;
import com.nus.logicuniversity.utility.Util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PastOrdersFragment extends Fragment {

    private ArrayList<Requisition> orders;
    private RecyclerView recyclerView;
    private TextView emptyView;

    public PastOrdersFragment() {
        orders = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        emptyView = view.findViewById(R.id.tv_empty);

        updateToolbarTitle();

        // Set the adapter
        recyclerView = view.findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyOrderRecyclerViewAdapter(getActivity(), orders, false, null));
        getPastOrders();
    }

    private void getPastOrders() {

        String header = Util.getHeaderValueFromSharedPreferences(getActivity());

        Util.showProgressBar(getActivity(), true);

        Api api = RetrofitClient.getInstance().getApi();
        api.getAllPastOrders(header).enqueue(new Callback<RequisitionResponse>() {
            @Override
            public void onResponse(@NotNull Call<RequisitionResponse> call, @NotNull Response<RequisitionResponse> response) {
                RequisitionResponse res = response.body();
                orders.clear();
                if(res != null) {
                    orders.addAll(res.getReqList());
                }
                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                Util.showProgressBar(getActivity(), false);
                showEmptyView(orders.isEmpty());
            }

            @Override
            public void onFailure(@NotNull Call<RequisitionResponse> call, @NotNull Throwable t) {
                Log.e("Exception", t.getMessage(), t);
                Util.showToast(getActivity(), "Failed...");
                Util.showProgressBar(getActivity(), false);
                showEmptyView(orders.isEmpty());
            }
        });
    }

    private void updateToolbarTitle() {
        Util.updateTitle(getString(R.string.title_fragment_past_orders), Objects.requireNonNull(getActivity()));
    }

    private void showEmptyView(boolean show) {
        emptyView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
