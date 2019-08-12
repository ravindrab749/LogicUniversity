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
import com.nus.logicuniversity.model.Requisition;
import com.nus.logicuniversity.model.RequisitionDetailResponse;
import com.nus.logicuniversity.retrofit.Api;
import com.nus.logicuniversity.retrofit.RetrofitClient;
import com.nus.logicuniversity.utility.Util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderRecyclerViewAdapter extends RecyclerView.Adapter<MyOrderRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Requisition> mOrders;
    private final OnListFragmentInteractionListener mListener;
    private final FragmentActivity mActivity;
    private boolean isPending;

    public MyOrderRecyclerViewAdapter(FragmentActivity activity, ArrayList<Requisition> items, boolean isPending, OnListFragmentInteractionListener listener) {
        mOrders = items;
        mListener = listener;
        mActivity = activity;
        this.isPending = isPending;
    }

    @org.jetbrains.annotations.Nullable
    private Requisition getItemByPosition(int position) {
        return (mOrders.isEmpty()) ? null : mOrders.get(position);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull final ViewHolder holder, int position) {
        final Requisition order = getItemByPosition(position);
        if(order == null) return;
        holder.mIdView.setText(mActivity.getString(R.string.title_order_id, order.getReqId()));
        holder.mEmpView.setText(order.getEmployee().getEmpName());
//        holder.mRepView.setText(order.getRepresentativeName());
//        holder.mLocView.setText(order.getCollectionPoint());
        holder.mView.setClickable(isPending);

        if(isPending) {
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
//                        mListener.onListFragmentInteraction(holder.mItem);
//                        mActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new PendingOrderedItemsFragment()).commit();
                        getRequisitionDetails(order.getReqId());
                    }
                }
            });
        } else {
            boolean isRejected = "Rejected".equalsIgnoreCase(order.getStatus());
            holder.mAppRejView.setImageDrawable(mActivity.getDrawable(isRejected ? R.drawable.ic_order_reject : R.drawable.ic_order_approve));
        }
    }

    private void getRequisitionDetails(final long id) {

        String header = Util.getHeaderValueFromSharedPreferences(mActivity);
        Util.showProgressBar(mActivity, true);

        Api api = RetrofitClient.getInstance().getApi();
        api.getPendingOrderByOrderId(header, id).enqueue(new Callback<RequisitionDetailResponse>() {
            @Override
            public void onResponse(@NotNull Call<RequisitionDetailResponse> call, @NotNull Response<RequisitionDetailResponse> response) {
                RequisitionDetailResponse res = response.body();
                if(res != null) {
                    res.setReqId(id);
                    Util.showProgressBar(mActivity, false);
                    mActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new PendingOrderedItemsFragment(res)).commit();
                }
            }

            @Override
            public void onFailure(@NotNull Call<RequisitionDetailResponse> call, @NotNull Throwable t) {
                Util.showProgressBar(mActivity, false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mOrders == null) ? 0 : mOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final TextView mIdView;
        private final TextView mEmpView;
        private final TextView mRepView;
        private final TextView mLocView;
        private final ImageView mAppRejView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.order_id);
            mEmpView = view.findViewById(R.id.employee_name);
            mRepView = view.findViewById(R.id.rep_name);
            mRepView.setVisibility(View.GONE);
            mLocView = view.findViewById(R.id.collection_point);
            mLocView.setVisibility(View.GONE);
            mAppRejView = view.findViewById(R.id.id_app_rej);
            mAppRejView.setVisibility(isPending ? View.INVISIBLE : View.VISIBLE);
       }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }
}
