package com.nus.logicuniversity.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.model.AdjustmentVoucher;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyAdjustmentVoucherRecyclerViewAdapter extends RecyclerView.Adapter<MyAdjustmentVoucherRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<AdjustmentVoucher> mValues;
    private FragmentActivity mActivity;

    public MyAdjustmentVoucherRecyclerViewAdapter(FragmentActivity activity, ArrayList<AdjustmentVoucher> items) {
        mValues = items;
        mActivity = activity;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_adjustmentvoucher, parent, false);
        return new ViewHolder(view);
    }

    private AdjustmentVoucher getItem(int position) {
        return mValues.get(position);
    }

    @Override
    public void onBindViewHolder(@NotNull final ViewHolder holder, final int position) {
        AdjustmentVoucher voucher = getItem(position);
        holder.mItemNumView.setText(voucher.getItem().getItemCode());
        holder.mItemDescView.setText(voucher.getItem().getDescription());
        holder.mQtyAdjView.setText(mActivity.getString(R.string.text_for_number, voucher.getAdjQty()));
        holder.mItemUpView.setText(mActivity.getString(R.string.text_price,voucher.getItem().getItemSuppliersDetails().getSupplier1UnitPrice()));
        holder.mItemTpView.setText(mActivity.getString(R.string.text_price,(voucher.getItem().getItemSuppliersDetails().getSupplier1UnitPrice() * voucher.getAdjQty())));
        holder.mItemReasonView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mValues.get(position).setReason(editable.toString());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mItemNumView;
        private final TextView mItemDescView;
        private final TextView mQtyAdjView;
        private final EditText mItemReasonView;
        private final TextView mItemUpView;
        private final TextView mItemTpView;

        private ViewHolder(View view) {
            super(view);
            mItemNumView = view.findViewById(R.id.tv_item_num);
            mItemDescView = view.findViewById(R.id.tv_item_desc);
            mQtyAdjView= view.findViewById(R.id.tv_item_qty_adj);
            mItemReasonView = view.findViewById(R.id.et_item_reason);
            mItemUpView = view.findViewById(R.id.tv_item_up);
            mItemTpView = view.findViewById(R.id.tv_item_tp);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItemNumView.getText() + "'";
        }
    }
}
