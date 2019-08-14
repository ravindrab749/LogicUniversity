package com.nus.logicuniversity.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.adapter.MyRetrievalFormItemRecyclerViewAdapter;
import com.nus.logicuniversity.listener.OnCallbackListener;
import com.nus.logicuniversity.listener.OnRetrievalFormListFragmentListener;
import com.nus.logicuniversity.model.DeptNeeded;
import com.nus.logicuniversity.model.DisbursementDetailItem;
import com.nus.logicuniversity.model.RetrievalForm;
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

public class StockClerkHomeFragment extends Fragment implements OnRetrievalFormListFragmentListener, View.OnClickListener, OnCallbackListener<RetrievalForm> {

    private RecyclerView recyclerView;
    private ArrayList<RetrievalForm> retrievalForms;
    private EditText[] qtyEts = null;
    private Button generateBtn;
    private TextView emptyView;

    public StockClerkHomeFragment() {
        retrievalForms = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sc_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        updateToolbarTitle();
        emptyView = view.findViewById(R.id.tv_empty);
        recyclerView = view.findViewById(R.id.rv_retrieval_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyRetrievalFormItemRecyclerViewAdapter(getActivity(), retrievalForms, this, this));
        generateBtn = view.findViewById(R.id.btn_generate);
        generateBtn.setOnClickListener(this);
        showGenerateButton(false);
        getRetrievalForms();
    }

    private void updateToolbarTitle() {
        Util.updateTitle(getString(R.string.title_frag_disbursement), Objects.requireNonNull(getActivity()));
    }

    @Override
    public void onRetrievalFormInteraction(RetrievalForm form) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.popup_stock_info, null);
        LinearLayout linearLayout = view.findViewById(R.id.lin_lay_info);

        for(DeptNeeded dept : form.getDeptNeeds()) {
            @SuppressLint("InflateParams") View subView = getLayoutInflater().inflate(R.layout.popup_dd_item, null);
            TextView descView = subView.findViewById(R.id.tv_item_desc);
            TextView qtyView = subView.findViewById(R.id.tv_item_qty);
            descView.setText(dept.getDeptCode());
            qtyView.setText(getString(R.string.text_for_number, dept.getDeptActual()));
            linearLayout.addView(subView);
        }

        dialogBuilder.setView(view);
        dialogBuilder.setTitle(form.getDescription());
        dialogBuilder.setCancelable(false);
        dialogBuilder.setNegativeButton(getString(R.string.action_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    private void enableGenBtn(boolean enable) {
        generateBtn.setEnabled(enable);
    }

    @Override
    public void onClick(View view) {

        enableGenBtn(false);

        ArrayList<DisbursementDetailItem> items = new ArrayList<>();
        for(RetrievalForm form : retrievalForms) {
            for(DeptNeeded dept : form.getDeptNeeds()) {
                DisbursementDetailItem item = new DisbursementDetailItem();
                item.setItemId(form.getItemId());
                item.setDeptId(dept.getDeptId());
                item.setQuantity(dept.getDeptActual());
                items.add(item);
            }
        }

        generateDisbursement(items);
    }

    @Override
    public void onCallback(RetrievalForm retrievalForm, int position) {
        showEditFormDialog(retrievalForm, position);
    }

    private void showEditFormDialog(final RetrievalForm retrievalForm, final int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.fragment_retrieval_form_edit, null);

        LinearLayout linearLayout = view.findViewById(R.id.lin_lay_stock_items_body);
        prepareView(linearLayout, retrievalForm);

        final Button confirmBtn = view.findViewById(R.id.btn_confirm);
        confirmBtn.setVisibility(View.GONE);

        dialogBuilder.setView(view);
        dialogBuilder.setTitle(retrievalForm.getDescription());
        dialogBuilder.setCancelable(false);

        dialogBuilder.setPositiveButton(getString(R.string.action_confirm), null);
        dialogBuilder.setNegativeButton(getString(R.string.action_cancel), null);

        final AlertDialog dialog = dialogBuilder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialogInterface) {
                Button posBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                posBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        confirm(retrievalForm, position, dialogInterface);
                    }
                });

                Button negBtn = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogInterface.dismiss();
                    }
                });

            }
        });
        dialog.show();
    }

    private void confirm(RetrievalForm retrievalForm, int position, DialogInterface dialogInterface) {
        for(int i=0; i<retrievalForm.getDeptNeeds().size(); i++) {
            DeptNeeded deptNeed = retrievalForm.getDeptNeeds().get(i);
            for (EditText et : qtyEts) {
                DeptNeeded deptTag = (DeptNeeded) et.getTag();
                if (deptNeed.getDeptId() == deptTag.getDeptId()) {
                    int actQty = Integer.parseInt(et.getText().toString());
                    retrievalForm.getDeptNeeds().get(i).setDeptActual(Math.abs(actQty));
                    break;
                }
            }
        }

        int total = retrievalForm.getTotalRetrieved();
        int actTotal = 0;
        int needTotal = 0;
        for (DeptNeeded dept : retrievalForm.getDeptNeeds()) {
            actTotal += dept.getDeptActual();
            needTotal += dept.getDeptNeeded();
        }

        if(actTotal > needTotal || actTotal > total) {
            Util.showToast(getActivity(), "Out of range values");
            return;
        }

        dialogInterface.dismiss();

        retrievalForms.set(position, retrievalForm);
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();

    }

    private void prepareView(LinearLayout parent, RetrievalForm retrievalForm) {

        qtyEts = new EditText[retrievalForm.getDeptNeeds().size()];
        int i = 0;

        for(DeptNeeded dept : retrievalForm.getDeptNeeds()) {

            @SuppressLint("InflateParams") View subView = getLayoutInflater().inflate(R.layout.popup_edit_form_dyn, null);

            TextView deptView = subView.findViewById(R.id.tv_dept_code);
            deptView.setText(dept.getDeptCode());

            TextView needView = subView.findViewById(R.id.tv_stock_need);
            needView.setText(getString(R.string.text_for_number, dept.getDeptNeeded()));

            final EditText actView = subView.findViewById(R.id.tv_stock_actual);
            actView.setText(getString(R.string.text_for_number, dept.getDeptActual()));
            actView.setTag(dept);

            qtyEts[i++] = actView;

            parent.addView(subView);

        }
    }

    private void getRetrievalForms() {

        String header = Util.getHeaderValueFromSharedPreferences(getActivity());
        Util.showProgressBar(getActivity(), true);

        Api api = RetrofitClient.getInstance().getApi();
        api.getRetrievalForms(header).enqueue(new Callback<RetrievalResponse>() {
            @Override
            public void onResponse(@NotNull Call<RetrievalResponse> call, @NotNull Response<RetrievalResponse> response) {
                RetrievalResponse res = response.body();
                assert res != null;
                updateWithDefaultValues(res.getRetrievalForms());
                Util.showProgressBar(getActivity(), false);
                enableGenBtn(true);
            }

            @Override
            public void onFailure(@NotNull Call<RetrievalResponse> call, @NotNull Throwable t) {
                Util.showToast(getActivity(), "Failed");
                showGenerateButton(!retrievalForms.isEmpty());
                Util.showProgressBar(getActivity(), false);
                showEmptyView(retrievalForms.isEmpty());
                enableGenBtn(true);
            }
        });
    }

    private void updateWithDefaultValues(ArrayList<RetrievalForm> retrievalForms1) {

        for(int i=0; i<retrievalForms1.size(); i++) {
            RetrievalForm form = retrievalForms1.get(i);

            int rem = form.getTotalRetrieved();
            for(int j=0; j<form.getDeptNeeds().size(); j++) {
                DeptNeeded dept = form.getDeptNeeds().get(j);
                if(dept.getDeptActual() == 0) {
                    if(dept.getDeptNeeded() <= rem) {
                        rem -= dept.getDeptNeeded();
                        retrievalForms1.get(i).getDeptNeeds().get(j).setDeptActual(dept.getDeptNeeded());
                    } else if(rem > 0) {
                        rem -= rem;
                        retrievalForms1.get(i).getDeptNeeds().get(j).setDeptActual(rem);
                    } else break;
                }
            }
        }

        retrievalForms.clear();
        retrievalForms.addAll(retrievalForms1);
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();

        showGenerateButton(!retrievalForms.isEmpty());
        showEmptyView(retrievalForms.isEmpty());
    }

    private void showGenerateButton(boolean show) {
        generateBtn.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void generateDisbursement(ArrayList<DisbursementDetailItem> items) {

        String header = Util.getHeaderValueFromSharedPreferences(getActivity());
        Util.showProgressBar(getActivity(), true);

        Api api = RetrofitClient.getInstance().getApi();
        api.generateDisbursement(header, items).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                String msg = response.body();
                if("Success".equalsIgnoreCase(msg)) {
                    Util.showToast(getActivity(), msg);
                    retrievalForms.clear();
                    Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                }
                Util.showProgressBar(getActivity(), false);
                showEmptyView(retrievalForms.isEmpty());
                enableGenBtn(true);
                showGenerateButton(!retrievalForms.isEmpty());
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                Util.showProgressBar(getActivity(), false);
                showEmptyView(retrievalForms.isEmpty());
                enableGenBtn(true);
                showGenerateButton(!retrievalForms.isEmpty());
            }
        });
    }

    private void showEmptyView(boolean show) {
        emptyView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
