package com.nus.logicuniversity.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.adapter.MyDisbursementRecyclerViewAdapter;
import com.nus.logicuniversity.listener.OnCallbackListener;
import com.nus.logicuniversity.listener.OnViewCallbackListener;
import com.nus.logicuniversity.model.DisbursementDetailItem;
import com.nus.logicuniversity.model.DisbursementDetailsResponse;
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

public class PendingDisbursementFragment extends Fragment implements OnCallbackListener<DisbursementItem>, OnViewCallbackListener {

    private ArrayList<DisbursementItem> disbursements;
    private RecyclerView recyclerView;
    private TextView emptyView;

    public PendingDisbursementFragment() {
        disbursements = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_disbursement_list, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        updateToolbarTitle();
        emptyView = view.findViewById(R.id.tv_empty);

        // Set the adapter
        recyclerView = view.findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyDisbursementRecyclerViewAdapter(disbursements, true,this, this));

        getAllDisbursements();
    }

    private void updateToolbarTitle() {
        Util.updateTitle(getString(R.string.title_fragment_pending_dis), Objects.requireNonNull(getActivity()));
    }

    @Override
    public void onCallback(DisbursementItem disbursementItem, int position) {
        acknowledgeDisbursement(disbursementItem, position);
    }

    private void getAllDisbursements() {

        String header = Util.getHeaderValueFromSharedPreferences(getActivity());

        Util.showProgressBar(getActivity(), true);
        Api api = RetrofitClient.getInstance().getApi();
        api.getPendingDisbursements(header).enqueue(new Callback<DisbursementsResponse>() {
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

    private void getAllDisbursementDetails(long listId) {

        String header = Util.getHeaderValueFromSharedPreferences(getActivity());

        Util.showProgressBar(getActivity(), true);
        Api api = RetrofitClient.getInstance().getApi();
        api.getDisbursementDetails(header, listId).enqueue(new Callback<DisbursementDetailsResponse>() {
            @Override
            public void onResponse(@NotNull Call<DisbursementDetailsResponse> call, @NotNull Response<DisbursementDetailsResponse> response) {
                DisbursementDetailsResponse res = response.body();
                if(res != null && !res.getDisbursementDetails().isEmpty()) {
                    popupView(res.getDisbursementDetails());
                }
                Util.showProgressBar(getActivity(), false);
            }

            @Override
            public void onFailure(@NotNull Call<DisbursementDetailsResponse> call, @NotNull Throwable t) {
                Util.showProgressBar(getActivity(), false);
            }
        });
    }

    private void popupView(ArrayList<DisbursementDetailItem> detailItems) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.popup_disbursement_details, null);

        LinearLayout parent = view.findViewById(R.id.lin_lay_dd);
        for(DisbursementDetailItem item : detailItems) {
            @SuppressLint("InflateParams") View subView = getLayoutInflater().inflate(R.layout.popup_dd_item, null);
            TextView descView = subView.findViewById(R.id.tv_item_desc);
            TextView qtyView = subView.findViewById(R.id.tv_item_qty);
            descView.setText(item.getItem().getDescription());
            qtyView.setText(getString(R.string.text_for_number, item.getQuantity()));
            parent.addView(subView);
        }

        dialogBuilder.setView(view);
        dialogBuilder.setTitle(R.string.title_details);

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        Util.showProgressBar(getActivity(), false);

    }

    private void acknowledgeDisbursement(final DisbursementItem item, final int position) {
        String header = Util.getHeaderValueFromSharedPreferences(getActivity());
        Util.showProgressBar(getActivity(), true);
        Api api = RetrofitClient.getInstance().getApi();
        api.acknowledgeDisbursement(header,item.getListId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                String msg = response.body();
                if("Success".equalsIgnoreCase(msg)) {
                    disbursements.remove(item);
                    Objects.requireNonNull(recyclerView.getAdapter()).notifyItemRemoved(position);
                }
                Util.showProgressBar(getActivity(), false);
                showEmptyView(disbursements.isEmpty());
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                Util.showProgressBar(getActivity(), false);
                showEmptyView(disbursements.isEmpty());
            }
        });
    }

    private void showEmptyView(boolean show) {
        emptyView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public <T> void onViewCallback(T item, int position, View view) {
        DisbursementItem disItem = (DisbursementItem) item;
        getAllDisbursementDetails(disItem.getListId());
    }
}
