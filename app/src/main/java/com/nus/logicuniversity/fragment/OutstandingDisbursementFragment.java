package com.nus.logicuniversity.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.adapter.CollectionPointPopupAdapter;
import com.nus.logicuniversity.adapter.DepartmentAdapter;
import com.nus.logicuniversity.adapter.RepPopupAdapter;
import com.nus.logicuniversity.model.CollectionPoint;
import com.nus.logicuniversity.model.CollectionPointListResponse;
import com.nus.logicuniversity.model.DisbursementItem;
import com.nus.logicuniversity.model.DisbursementsResponse;
import com.nus.logicuniversity.model.Employee;
import com.nus.logicuniversity.model.RetrievalResponse;
import com.nus.logicuniversity.retrofit.Api;
import com.nus.logicuniversity.retrofit.RetrofitClient;
import com.nus.logicuniversity.utility.Util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutstandingDisbursementFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private ArrayList<DisbursementItem> depts;
    private ArrayList<CollectionPoint> collectionPoints;
    private TextView emptyView;
    private TextView cpView;
    private Button changeBtn;
    private long currentPlaceId = 0;
    private AlertDialog dialog;

    public OutstandingDisbursementFragment() {
        depts = new ArrayList<>();
        collectionPoints = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sc_depts, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        updateToolbarTitle();
        getCollectionPoints();
        emptyView = view.findViewById(R.id.tv_empty);
        cpView = view.findViewById(R.id.tv_cp_name);
        changeBtn = view.findViewById(R.id.btn_change);
        changeBtn.setOnClickListener(this);
        recyclerView = view.findViewById(R.id.rv_retrieval_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new DepartmentAdapter(getActivity(), depts));
    }

    private void enableChangeBtn(boolean enable) {
        changeBtn.setEnabled(enable);
    }

    @Override
    public void onClick(View view) {

        if(collectionPoints .isEmpty()) {
            enableChangeBtn(false);
            return;
        }

        dialog.show();

    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle(getString(R.string.title_select_cp));

        CollectionPointPopupAdapter adapter = new CollectionPointPopupAdapter(getActivity(), R.layout.popup_rep, collectionPoints);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                CollectionPoint cp = collectionPoints.get(i);
                if(currentPlaceId != cp.getPlacedId()) {
                    cpView.setText(cp.getName());
                    getDepts(cp.getPlacedId());
                }
            }
        });

        dialog = builder.create();
    }

    private void updateToolbarTitle() {
        Util.updateTitle(getString(R.string.title_frag_disbursement_os), Objects.requireNonNull(getActivity()));
    }

    private void getCollectionPoints() {
        String header = Util.getHeaderValueFromSharedPreferences(getActivity());
        Util.showProgressBar(getActivity(), true);
        Api api = RetrofitClient.getInstance().getApi();
        api.getAllCollectionPoints(header).enqueue(new Callback<CollectionPointListResponse>() {
            @Override
            public void onResponse(@NotNull Call<CollectionPointListResponse> call, @NotNull Response<CollectionPointListResponse> response) {
                CollectionPointListResponse res = response.body();
                collectionPoints.clear();
                assert res != null;
                collectionPoints.addAll(res.getCollectionPoints());
                CollectionPoint defaultCp = new CollectionPoint();
                defaultCp.setName("All");
                defaultCp.setPlacedId(0);
                collectionPoints.add(0, defaultCp);
                Util.showProgressBar(getActivity(), false);
                enableChangeBtn(true);
                createDialog();
                selectDefaultCollectionPoint();
            }

            @Override
            public void onFailure(@NotNull Call<CollectionPointListResponse> call, @NotNull Throwable t) {
                Util.showProgressBar(getActivity(), false);
                enableChangeBtn(true);
            }
        });
    }

    private void getDepts(final long collectionPoint) {

        String header = Util.getHeaderValueFromSharedPreferences(getActivity());
        Util.showProgressBar(getActivity(), true);

        Api api = RetrofitClient.getInstance().getApi();
        api.getAllPendingDisbursements(header, collectionPoint).enqueue(new Callback<DisbursementsResponse>() {
            @Override
            public void onResponse(@NotNull Call<DisbursementsResponse> call, @NotNull Response<DisbursementsResponse> response) {
                DisbursementsResponse res = response.body();
                assert res != null;
                depts.clear();
                depts.addAll(res.getDisbursementList());
                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                currentPlaceId = collectionPoint;
                showEmptyView(depts.isEmpty());
                Util.showProgressBar(getActivity(), false);
            }

            @Override
            public void onFailure(@NotNull Call<DisbursementsResponse> call, @NotNull Throwable t) {
                Util.showToast(getActivity(), "Failed");
                Util.showProgressBar(getActivity(), false);
                showEmptyView(depts.isEmpty());
            }
        });
    }

    private void showEmptyView(boolean show) {
        emptyView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void selectDefaultCollectionPoint() {
        cpView.setText(collectionPoints.get(0).getName());
        getDepts(collectionPoints.get(0).getPlacedId());
    }

}
