package com.nus.logicuniversity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.adapter.MyOrderedItemRecyclerViewAdapter;
import com.nus.logicuniversity.model.RequisitionDetailResponse;
import com.nus.logicuniversity.model.RequisitionDetails;
import com.nus.logicuniversity.retrofit.Api;
import com.nus.logicuniversity.retrofit.RetrofitClient;
import com.nus.logicuniversity.utility.Util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingOrderedItemsFragment extends Fragment {

    private TextView repView;
    private ArrayList<RequisitionDetails> orderedItems = new ArrayList<>();
    private RequisitionDetailResponse response;

    public PendingOrderedItemsFragment(RequisitionDetailResponse response) {
        this.response = response;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ordereditem_list, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        updateToolbarTitle();
        TextView totView = view.findViewById(R.id.total_view);
        repView = view.findViewById(R.id.tv_rep_name);
        EditText etComment = view.findViewById(R.id.et_comment);
        Button btnAccept = view.findViewById(R.id.btn_accept);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                approve();
            }
        });
        Button btnReject = view.findViewById(R.id.btn_reject);
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reject();
            }
        });

        orderedItems.addAll(response.getReqDataList());
        RecyclerView recyclerView = view.findViewById(R.id.pending_ordered_item_list);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new MyOrderedItemRecyclerViewAdapter(getActivity(), orderedItems));
    }

    private void updateToolbarTitle() {
        Util.updateTitle(getString(R.string.title_fragment_pending_order_items), Objects.requireNonNull(getActivity()));
    }

    private void approve() {

        String header = Util.getHeaderValueFromSharedPreferences(getActivity());
        Util.showProgressBar(getActivity(), true);

        Api api = RetrofitClient.getInstance().getApi();
        api.approveOrder(header, response.getReqId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                String msg = response.body();
                if("Success".equalsIgnoreCase(msg)) {
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.frame, new PendingOrdersFragment()).commit();
                    Util.showProgressBar(getActivity(), false);
                }
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                Util.showToast(getActivity(), t.getMessage());
                Util.showProgressBar(getActivity(), false);
            }
        });
    }

    private void reject() {

        String header = Util.getHeaderValueFromSharedPreferences(getActivity());

        Util.showProgressBar(getActivity(), true);

        Api api = RetrofitClient.getInstance().getApi();
        api.rejectOrder(header, response.getReqId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                String msg = response.body();
                if("Success".equalsIgnoreCase(msg)) {
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.frame, new PendingOrdersFragment()).commit();
                    Util.showProgressBar(getActivity(), false);
                }
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                Util.showToast(getActivity(), t.getMessage());
                Util.showProgressBar(getActivity(), false);
            }
        });
    }

}
