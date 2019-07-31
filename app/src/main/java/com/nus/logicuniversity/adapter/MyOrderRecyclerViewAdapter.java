package com.nus.logicuniversity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.fragment.PendingOrderedItemsFragment;
import com.nus.logicuniversity.listener.OnListFragmentInteractionListener;
import com.nus.logicuniversity.model.Order;

import java.util.ArrayList;

public class MyOrderRecyclerViewAdapter extends RecyclerView.Adapter<MyOrderRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Order> mOrders;
    private final OnListFragmentInteractionListener mListener;
    private final FragmentActivity mActivity;
    private boolean isPending;

    public MyOrderRecyclerViewAdapter(FragmentActivity activity, ArrayList<Order> items, boolean isPending, OnListFragmentInteractionListener listener) {
        mOrders = items;
        mListener = listener;
        mActivity = activity;
        this.isPending = isPending;
    }

    @org.jetbrains.annotations.Nullable
    private Order getItemByPosition(int position) {
        return (mOrders.isEmpty()) ? null : mOrders.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Order order = getItemByPosition(position);
        if(order == null) return;
        holder.mItem = order;
        holder.mIdView.setText(mActivity.getString(R.string.title_order_id, order.getId()));
        holder.mEmpView.setText(order.getEmployeeName());
        holder.mRepView.setText(order.getRepresentativeName());
        holder.mLocView.setText(order.getCollectionPoint());
        holder.mView.setClickable(isPending);

        if(isPending) {
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
//                        mListener.onListFragmentInteraction(holder.mItem);
                        mActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new PendingOrderedItemsFragment()).commit();
                    }
                }
            });
        } else
            holder.mAppRejView.setImageDrawable(mActivity.getDrawable(order.isApproved() ? R.drawable.ic_order_approve : R.drawable.ic_order_reject));
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final TextView mIdView;
        private final TextView mEmpView;
        private final TextView mRepView;
        private final TextView mLocView;
        private Order mItem;
        private final ImageView mAppRejView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.order_id);
            mEmpView = (TextView) view.findViewById(R.id.employee_name);
            mRepView = (TextView) view.findViewById(R.id.rep_name);
            mLocView = (TextView) view.findViewById(R.id.collection_point);
            mAppRejView = (ImageView) view.findViewById(R.id.id_app_rej);
            mAppRejView.setVisibility(isPending ? View.INVISIBLE : View.VISIBLE);
       }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }
}
