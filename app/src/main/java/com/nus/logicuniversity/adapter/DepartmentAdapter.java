package com.nus.logicuniversity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.fragment.UpdateDisbursementDetailsFragment;
import com.nus.logicuniversity.model.DisbursementItem;

import java.util.ArrayList;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.ViewHolder> {

    private ArrayList<DisbursementItem> depts;
    private FragmentActivity mActivity;

    public DepartmentAdapter(FragmentActivity activity, ArrayList<DisbursementItem> depts) {
        this.depts = depts;
        mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dept, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DisbursementItem item = depts.get(position);
        holder.mItemView.setText(item.getDepartment().getDeptName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new UpdateDisbursementDetailsFragment(item)).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return depts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mItemView;
        private final View mView;

        private ViewHolder(View view) {
            super(view);
            mView = view;
            mItemView = view.findViewById(R.id.text_view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItemView.getText() + "'";
        }
    }

}
