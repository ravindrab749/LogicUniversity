package com.nus.logicuniversity.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.model.AdjustmentVoucher;
import com.nus.logicuniversity.model.PriceList;
import com.nus.logicuniversity.model.PriceListResponse;
import com.nus.logicuniversity.retrofit.Api;
import com.nus.logicuniversity.retrofit.RetrofitClient;
import com.nus.logicuniversity.utility.Util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdjustmentVoucherFragment extends Fragment implements View.OnClickListener {

    private ArrayList<Long> mItemIds;
    private ArrayList<AdjustmentVoucher> mAdjustmentVouchers;
    private LinearLayout parent;
    private EditText[] etArray;
    private Button btnSubmit;

    public AdjustmentVoucherFragment(ArrayList<Long> itemIds, ArrayList<AdjustmentVoucher> adjustmentVouchers) {
        mItemIds = itemIds;
        mAdjustmentVouchers = adjustmentVouchers;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_adjustmentvoucher_list, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        updateToolbarTitle();
        retrievePriceListOfItems();
        parent = view.findViewById(R.id.parent_lin_lay);

        btnSubmit = view.findViewById(R.id.btnSubStkAdj);
        btnSubmit.setOnClickListener(this);

        showSubmitBtn(false);
    }

    private void updateToolbarTitle() {
        Util.updateTitle(getString(R.string.title_fragment_adj_voucher), Objects.requireNonNull(getActivity()));
    }

    private void showSubmitBtn(boolean show) {
        btnSubmit.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View view) {
        boolean emptyReason = false;
        for(int i=0; i<mAdjustmentVouchers.size(); i++) {
            for(EditText et : etArray) {
                String reason = et.getText().toString();
                long itemId = (long) et.getTag();
                if(TextUtils.isEmpty(reason)) {
                    emptyReason = true;
                    break;
                }
                if(mAdjustmentVouchers.get(i).getItemId() == itemId) {
                    mAdjustmentVouchers.get(i).setReason(reason);
                    break;
                }
            }
            if (emptyReason)
                break;
        }

        if(emptyReason) {
            Util.showToast(getActivity(), "Reason should not be empty");
            return;
        }

        if(!mAdjustmentVouchers.isEmpty())
            submitAdjVoucher();
    }

    private void submitAdjVoucher() {
        String header = Util.getHeaderValueFromSharedPreferences(getActivity());
        Api api = RetrofitClient.getInstance().getApi();
        api.generateInventoryAdjustment(header, mAdjustmentVouchers).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                String msg = response.body();
                showDialog(msg);
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                Util.showToast(getActivity(), "Failed");
            }
        });
    }

    private void showDialog(String msg) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        dialogBuilder.setTitle(R.string.title_popup_adj_voucher);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setMessage(msg);

        dialogBuilder.setPositiveButton(getString(R.string.action_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.frame, new InventoryFragment()).commit();
            }
        });

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    private void retrievePriceListOfItems() {
        String header = Util.getHeaderValueFromSharedPreferences(getActivity());
        Api api = RetrofitClient.getInstance().getApi();
        api.getInventoryPriceLists(header, mItemIds).enqueue(new Callback<PriceListResponse>() {
            @Override
            public void onResponse(@NotNull Call<PriceListResponse> call, @NotNull Response<PriceListResponse> response) {
                PriceListResponse res = response.body();
                assert res != null;
                if(res.getPriceLists() == null || res.getPriceLists().isEmpty()) {
                    return;
                }

                for(PriceList price : res.getPriceLists()) {
                    for(int i=0; i<mAdjustmentVouchers.size(); i++) {
                        if(mAdjustmentVouchers.get(i).getItemId() == price.getItem().getItemId()) {
                            mAdjustmentVouchers.get(i).getItem().setItemSuppliersDetails(price);
                            break;
                        }
                    }
                }
                renderView();

            }

            @Override
            public void onFailure(@NotNull Call<PriceListResponse> call, @NotNull Throwable t) {
                Util.showToast(getActivity(), "Failed");
            }
        });
    }

    private void renderView() {
        etArray = new EditText[mAdjustmentVouchers.size()];
        for(int i=0; i<mAdjustmentVouchers.size(); i++) {
            AdjustmentVoucher voucher = mAdjustmentVouchers.get(i);
            @SuppressLint("InflateParams")
            View view = getLayoutInflater().inflate(R.layout.fragment_adjustmentvoucher, null);
            CardView cardView = (CardView) view;

            TextView mItemNumView = view.findViewById(R.id.tv_item_num);
            TextView mItemDescView = view.findViewById(R.id.tv_item_desc);
            TextView mQtyAdjView= view.findViewById(R.id.tv_item_qty_adj);
            EditText mItemReasonView = view.findViewById(R.id.et_item_reason);
            TextView mItemUpView = view.findViewById(R.id.tv_item_up);
            TextView mItemTpView = view.findViewById(R.id.tv_item_tp);

            mItemNumView.setText(voucher.getItem().getItemCode());
            mItemDescView.setText(voucher.getItem().getDescription());
            mQtyAdjView.setText(Objects.requireNonNull(getActivity()).getString(R.string.text_for_number, voucher.getAdjQty()));
            mItemUpView.setText(getActivity().getString(R.string.text_for_decimal_price,voucher.getItem().getItemSuppliersDetails().getSupplier1UnitPrice()));
            double totalPrice = voucher.getItem().getItemSuppliersDetails().getSupplier1UnitPrice() * voucher.getAdjQty();
            mAdjustmentVouchers.get(i).setTotalPrice(totalPrice);
            mItemTpView.setText(getActivity().getString(R.string.text_for_decimal_price,totalPrice));
            mItemReasonView.setTag(voucher.getItem().getItemId());

            etArray[i] = mItemReasonView;
            cardView.setElevation(2.0f);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) cardView.getLayoutParams();
            if(params != null)
                params.setMargins(0,0,0,10);
            cardView.requestLayout();
            parent.addView(cardView);
        }
        showSubmitBtn(true);
    }

}
