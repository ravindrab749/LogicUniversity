package com.nus.logicuniversity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.model.RequisitionDetails;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;

public class MyOrderedItemRecyclerViewAdapter extends RecyclerView.Adapter<MyOrderedItemRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<RequisitionDetails> mValues;
    private FragmentActivity mActivity;

    public MyOrderedItemRecyclerViewAdapter(FragmentActivity activity, ArrayList<RequisitionDetails> orderedItems) {
        mValues = orderedItems;
        mActivity = activity;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ordereditem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull final ViewHolder holder, int position) {
        RequisitionDetails item = getItemByPosition(position);
        assert item != null;
        holder.mItemDesc.setText(item.getItem().getDescription());
        holder.mCategory.setText(item.getItem().getCategory());
        holder.mQty.setText(String.format(Locale.getDefault(), "%d", item.getQuantity()));
//        holder.mUnitPrice.setText(mActivity.getString(R.string.text_price, item.getItem().getUnitOfMeasure()));
//        holder.mPrice.setText(mActivity.getString(R.string.text_price, (item.getQuantity()*item.getItem().getPrice())));
    }

    private RequisitionDetails getItemByPosition(int position) {
        return (position > mValues.size()) ? null : mValues.get(position);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mItemDesc;
        private final TextView mCategory;
        private final TextView mQty;
        private final TextView mUnitPrice;
        private final TextView mPrice;

        private ViewHolder(View view) {
            super(view);
            mItemDesc = view.findViewById(R.id.tv_item_desc);
            mCategory = view.findViewById(R.id.tv_category);
            mQty = view.findViewById(R.id.tv_qty);
            mUnitPrice = view.findViewById(R.id.tv_up);
            mPrice = view.findViewById(R.id.tv_price);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItemDesc.getText() + "'";
        }
    }
}
