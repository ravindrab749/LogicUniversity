package com.nus.logicuniversity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.listener.OnCallbackListener;
import com.nus.logicuniversity.model.DisbursementItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyDisbursementRecyclerViewAdapter extends RecyclerView.Adapter<MyDisbursementRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<DisbursementItem> mValues;
    private OnCallbackListener<DisbursementItem> mListener;
    private boolean isPending;

    public MyDisbursementRecyclerViewAdapter(ArrayList<DisbursementItem> items, boolean isPending, OnCallbackListener<DisbursementItem> listener) {
        mValues = items;
        mListener = listener;
        this.isPending = isPending;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_disbursement, parent, false);
        return new ViewHolder(view);
    }

    private DisbursementItem getItem(int pos) {
        return mValues.get(pos);
    }

    @Override
    public void onBindViewHolder(@NotNull final ViewHolder holder, final int position) {
        final DisbursementItem item = getItem(position);
        holder.mDeptView.setText(item.getDepartment().getName());
        holder.mCPointView.setText(item.getCollectionPoint().getName());
        holder.mDateView.setText(item.getDate().toString());

        if(isPending) {
            holder.mAckButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onCallback(item, position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mDeptView;
        private final TextView mCPointView;
        private final TextView mDateView;
        private final Button mAckButton;

        private ViewHolder(View view) {
            super(view);
            mDeptView = view.findViewById(R.id.dept_name);
            mCPointView = view.findViewById(R.id.collection_point);
            mDateView = view.findViewById(R.id.dis_date);
            mAckButton = view.findViewById(R.id.btn_ack);
            if(!isPending)
                mAckButton.setVisibility(View.GONE);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDeptView.getText() + "'";
        }
    }
}
