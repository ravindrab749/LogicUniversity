package com.nus.logicuniversity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.model.Inventory;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyInventoryRecyclerViewAdapter extends RecyclerView.Adapter<MyInventoryRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Inventory> mValues;
    private final FragmentActivity mActivity;

    public MyInventoryRecyclerViewAdapter(FragmentActivity activity, ArrayList<Inventory> items) {
        mValues = items;
        mActivity = activity;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_inventory, parent, false);
        return new ViewHolder(view);
    }

    private Inventory getItem(int position) { return mValues.get(position);}

    @Override
    public void onBindViewHolder(@NotNull final ViewHolder holder, final int position) {
        final Inventory item = getItem(position);
        holder.mItemNumView.setText(item.getItemCode());
        holder.mItemCatView.setText(item.getCategory());
        holder.mItemDescView.setText(item.getDescription());
        holder.mItemUomView.setText(item.getUnitOfMeasure());
        holder.mItemSLastView.setText(mActivity.getString(R.string.text_for_number, item.getStockLevel()));
        holder.mItemSActView.setText(mActivity.getString(R.string.text_for_number, item.getStockLevel()));
        holder.mItemSActView.setTag(item.getItemId());
/*        holder.mItemSActView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                int actual = s.isEmpty() ? 0 : Integer.parseInt(s);
                item.setActualStock(actual);
//                mValues.get(position).setActualStock(actual);
//                notifyDataSetChanged();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mItemNumView;
        private final TextView mItemCatView;
        private final TextView mItemDescView;
        private final TextView mItemUomView;
        private final TextView mItemSLastView;
        private final EditText mItemSActView;

        private ViewHolder(View view) {
            super(view);
            mItemNumView = view.findViewById(R.id.tv_item_num);
            mItemCatView = view.findViewById(R.id.tv_item_cat);
            mItemDescView = view.findViewById(R.id.tv_item_desc);
            mItemUomView = view.findViewById(R.id.tv_item_uom);
            mItemSLastView = view.findViewById(R.id.tv_item_stock_last);
            mItemSActView = view.findViewById(R.id.et_item_stock_actual);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItemNumView.getText() + "'";
        }
    }
}
