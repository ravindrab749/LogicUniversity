package com.nus.logicuniversity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.model.OrderedItem;

import java.util.List;

public class MyOrderedItemRecyclerViewAdapter extends RecyclerView.Adapter<MyOrderedItemRecyclerViewAdapter.ViewHolder> {

    private final List<OrderedItem> mValues;
    private FragmentActivity mActivity;

    public MyOrderedItemRecyclerViewAdapter(FragmentActivity activity, List<OrderedItem> orderedItems) {
        mValues = orderedItems;
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ordereditem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        OrderedItem item = getItemByPosition(position);
        holder.mItem = item;
        holder.mItemDesc.setText(item.getItem().getDescription());
        holder.mCategory.setText(item.getItem().getCategory());
        holder.mQty.setText(item.getQuantity());
        holder.mUnitPrice.setText(mActivity.getString(R.string.text_price, item.getItem().getPrice()));
        holder.mPrice.setText(mActivity.getString(R.string.text_price, (item.getQuantity()*item.getItem().getPrice())));
    }

    private OrderedItem getItemByPosition(int position) {
        return (mValues.size() > position) ? null : mValues.get(position);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final TextView mItemDesc;
        private final TextView mCategory;
        private final TextView mQty;
        private final TextView mUnitPrice;
        private final TextView mPrice;
        private OrderedItem mItem;

        private ViewHolder(View view) {
            super(view);
            mView = view;
            mItemDesc = (TextView) view.findViewById(R.id.tv_item_desc);
            mCategory = (TextView) view.findViewById(R.id.tv_category);
            mQty = (TextView) view.findViewById(R.id.tv_qty);
            mUnitPrice = (TextView) view.findViewById(R.id.tv_up);
            mPrice = (TextView) view.findViewById(R.id.tv_price);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItemDesc.getText() + "'";
        }
    }
}
