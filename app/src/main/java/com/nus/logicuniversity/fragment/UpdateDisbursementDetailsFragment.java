package com.nus.logicuniversity.fragment;

import android.annotation.SuppressLint;
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
import androidx.fragment.app.Fragment;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.model.DisbursementDTO;
import com.nus.logicuniversity.model.DisbursementDetailItem;
import com.nus.logicuniversity.model.DisbursementDetailsResponse;
import com.nus.logicuniversity.model.DisbursementItem;
import com.nus.logicuniversity.model.PerItem;
import com.nus.logicuniversity.retrofit.Api;
import com.nus.logicuniversity.retrofit.RetrofitClient;
import com.nus.logicuniversity.utility.Util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDisbursementDetailsFragment extends Fragment implements View.OnClickListener {

    private EditText[] qtyEts = null;
    private Button updateBtn;
    private TextView emptyView;
    private DisbursementItem disbursementItem;
    private LinearLayout parent;
    private ArrayList<DisbursementDetailItem> disbursementDetailItems;

    public UpdateDisbursementDetailsFragment(DisbursementItem disbursementItem) {
        disbursementDetailItems = new ArrayList<>();
        this.disbursementItem = disbursementItem;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sc_disbursement_update, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        updateToolbarTitle();
        emptyView = view.findViewById(R.id.tv_empty);
        updateBtn = view.findViewById(R.id.btn_update);
        parent = view.findViewById(R.id.lin_lay_stock_items_body);
        updateBtn.setOnClickListener(this);
        showUpdateButton(false);
        getDetailsForms(disbursementItem.getListId());
    }

    private void updateToolbarTitle() {
        Util.updateTitle(getString(R.string.title_frag_disbursement), Objects.requireNonNull(getActivity()));
    }

    private void enableUpdBtn(boolean enable) {
        updateBtn.setEnabled(enable);
    }

    @Override
    public void onClick(View view) {

        enableUpdBtn(false);

        boolean isNeg = false;
        ArrayList<PerItem> perItems = new ArrayList<>();
        for (EditText et : qtyEts) {
            DisbursementDetailItem item = (DisbursementDetailItem) et.getTag();
            String txt = et.getText().toString();
            int qty = txt.isEmpty() ? -1 : Integer.parseInt(txt);
            if(qty < 0 || qty > item.getQuantity()) {
                Util.showToast(getActivity(), "Enter valid quantity");
                isNeg = true;
                break;
            } else if(qty == item.getQuantity()) {
                continue;
            }
            PerItem perItem = new PerItem();
            perItem.setItemId(item.getItem().getItemId());
            perItem.setQuantity(qty);
            perItems.add(perItem);
        }

        if(perItems.isEmpty()) {
            enableUpdBtn(true);
            Util.showToast(getActivity(), "No change found!");
            return;
        }

        if(isNeg) {
            enableUpdBtn(true);
            perItems.clear();
            return;
        }

        DisbursementDTO dto = new DisbursementDTO();
        dto.setListId(disbursementItem.getListId());
        dto.setItems(perItems);

        updateDisbursement(dto);
    }

    private void prepareView() {

        parent.removeAllViewsInLayout();

        qtyEts = new EditText[disbursementDetailItems.size()];
        int i = 0;

        for(DisbursementDetailItem item : disbursementDetailItems) {

            @SuppressLint("InflateParams") View subView = getLayoutInflater().inflate(R.layout.frag_update_dis_form_dyn, null);

            TextView deptView = subView.findViewById(R.id.tv_dept_code);
            deptView.setText(item.getItem().getDescription());

            TextView needView = subView.findViewById(R.id.tv_stock_need);
            needView.setText(getString(R.string.text_for_number, item.getQuantity()));

            final EditText actView = subView.findViewById(R.id.tv_stock_actual);
            actView.setText(getString(R.string.text_for_number, item.getQuantity()));
            actView.setTag(item);

            qtyEts[i++] = actView;

            parent.addView(subView);

        }
    }

    private void getDetailsForms(long listId) {

        String header = Util.getHeaderValueFromSharedPreferences(getActivity());
        Util.showProgressBar(getActivity(), true);

        Api api = RetrofitClient.getInstance().getApi();

        api.getDisbursementDetailsToUpdate(header, listId).enqueue(new Callback<DisbursementDetailsResponse>() {
            @Override
            public void onResponse(@NotNull Call<DisbursementDetailsResponse> call, @NotNull Response<DisbursementDetailsResponse> response) {
                DisbursementDetailsResponse res = response.body();
                assert res != null;
                assert res.getDisbursementDetails() != null;
                Util.showProgressBar(getActivity(), false);
                enableUpdBtn(true);
                disbursementDetailItems.clear();
                disbursementDetailItems.addAll(res.getDisbursementDetails());
                showUpdateButton(!disbursementDetailItems.isEmpty());
                showEmptyView(disbursementDetailItems.isEmpty());
                prepareView();
            }

            @Override
            public void onFailure(@NotNull Call<DisbursementDetailsResponse> call, @NotNull Throwable t) {
                Util.showToast(getActivity(), "Failed");
                showUpdateButton(false);
                Util.showProgressBar(getActivity(), false);
                showEmptyView(disbursementDetailItems.isEmpty());
                enableUpdBtn(true);
            }
        });
    }

    private void showUpdateButton(boolean show) {
        updateBtn.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void updateDisbursement(DisbursementDTO dto) {

        String header = Util.getHeaderValueFromSharedPreferences(getActivity());
        Util.showProgressBar(getActivity(), true);

        Api api = RetrofitClient.getInstance().getApi();

        api.updateDisbursement(header, dto).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                String msg = response.body();
                if("Success".equalsIgnoreCase(msg)) {
                    Util.showToast(getActivity(), msg);
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.frame, new OutstandingDisbursementFragment()).commit();
                }
                enableUpdBtn(true);
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                Util.showProgressBar(getActivity(), false);
                enableUpdBtn(true);
                showUpdateButton(true);
            }
        });
    }

    private void showEmptyView(boolean show) {
        emptyView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
