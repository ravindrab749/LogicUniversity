package com.nus.logicuniversity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.fragment.RetrievalFormEditFragment;
import com.nus.logicuniversity.listener.OnCallbackListener;
import com.nus.logicuniversity.listener.OnRetrievalFormListFragmentListener;
import com.nus.logicuniversity.model.DeptNeeded;
import com.nus.logicuniversity.model.RetrievalForm;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyRetrievalFormItemRecyclerViewAdapter extends RecyclerView.Adapter<MyRetrievalFormItemRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<RetrievalForm> mValues;
    private FragmentActivity mActivity;
    private OnRetrievalFormListFragmentListener mListener;
    private OnCallbackListener<RetrievalForm> nListener;

    public MyRetrievalFormItemRecyclerViewAdapter(FragmentActivity activity, ArrayList<RetrievalForm> retrievalForms, OnRetrievalFormListFragmentListener listener, OnCallbackListener<RetrievalForm> listener2) {
        mValues = retrievalForms;
        mActivity = activity;
        mListener = listener;
        nListener = listener2;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_retrieval_form_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull final ViewHolder holder, final int position) {
        final RetrievalForm item = getItemByPosition(position);
        assert item != null;
        holder.mItemDesc.setText(item.getDescription());
        holder.mEditImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nListener.onCallback(item, position);
            }
        });
        holder.mInfoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onRetrievalFormInteraction(item);
            }
        });
        holder.mItemCountView.setText(mActivity.getString(R.string.text_slash_sep, item.getTotalNeeded(),item.getTotalRetrieved()));
        prepareChildViews(holder.mLinLay, item.getDeptNeeds());
    }

    private RetrievalForm getItemByPosition(int position) {
        return (position > mValues.size()) ? null : mValues.get(position);
    }

    private void prepareChildViews(LinearLayout parentView, ArrayList<DeptNeeded> childes) {
        parentView.removeAllViewsInLayout();
        int MAX_SIZE = 3;
        int count = childes.size() > MAX_SIZE ? MAX_SIZE : childes.size();
        for(int i=0; i<count; i++) {
            DeptNeeded dept = childes.get(i);
            TextView textView = new TextView(mActivity);
            textView.setText(mActivity.getString(R.string.text_colon_sep, dept.getDeptCode(), dept.getDeptActual()));
            parentView.addView(textView);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mItemDesc;
        private final ImageView mEditImageView;
        private final LinearLayout mLinLay;
        private final ImageView mInfoImageView;
        private final TextView mItemCountView;

        private ViewHolder(View view) {
            super(view);
            mItemDesc = view.findViewById(R.id.tv_item_name);
            mEditImageView = view.findViewById(R.id.iv_edit);
            mLinLay = view.findViewById(R.id.lin_lay_items);
            mInfoImageView = view.findViewById(R.id.iv_info);
            mItemCountView = view.findViewById(R.id.tv_actual_req_items);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItemDesc.getText() + "'";
        }
    }

}
