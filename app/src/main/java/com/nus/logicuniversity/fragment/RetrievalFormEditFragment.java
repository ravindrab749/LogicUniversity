package com.nus.logicuniversity.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.listener.OnCallbackListener;
import com.nus.logicuniversity.model.DeptNeeded;
import com.nus.logicuniversity.model.RetrievalForm;
import com.nus.logicuniversity.utility.Util;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetrievalFormEditFragment extends Fragment implements View.OnClickListener {

    private RetrievalForm mForm;
    private int mPos;
    private OnCallbackListener<RetrievalForm> mListener;
    private EditText[] qtyEts = null;

    public RetrievalFormEditFragment(RetrievalForm form, int pos, OnCallbackListener<RetrievalForm> listener) {
        mForm = form;
        mPos = pos;
        mListener = listener;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_retrieval_form_edit, container, false);
        LinearLayout linearLayout = view.findViewById(R.id.lin_lay_stock_items_body);
        prepareView(linearLayout);
        Button confirmBtn = view.findViewById(R.id.btn_confirm);
        confirmBtn.setOnClickListener(this);
        return view;
    }

    private void prepareView(LinearLayout parent) {

        parent.removeAllViewsInLayout();

        qtyEts = new EditText[mForm.getDeptNeeds().size()];
        int i = 0;

        for(DeptNeeded dept : mForm.getDeptNeeds()) {
            LinearLayout linearLayout = new LinearLayout(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(params);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView deptView = new TextView(getActivity());
            deptView.setText(dept.getDeptCode());
            deptView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            linearLayout.addView(deptView);

            TextView needView = new TextView(getActivity());
            needView.setText(getString(R.string.text_for_number, dept.getDeptNeeded()));
            needView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            linearLayout.addView(needView);

            final EditText actView = new EditText(getActivity());
            actView.setText(getString(R.string.text_for_number, dept.getDeptActual()));
            actView.setInputType(InputType.TYPE_CLASS_NUMBER);
            actView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            actView.setTag(dept);

            qtyEts[i++] = actView;
            linearLayout.addView(actView);

            parent.addView(linearLayout);
        }
    }

    @Override
    public void onClick(View view) {
        for(int i=0; i<mForm.getDeptNeeds().size(); i++) {
            DeptNeeded deptNeed = mForm.getDeptNeeds().get(i);
            for (EditText et : qtyEts) {
                DeptNeeded deptTag = (DeptNeeded) et.getTag();
                if (deptNeed.getDeptId() == deptTag.getDeptId()) {
                    int actQty = Integer.parseInt(et.getText().toString());
                    mForm.getDeptNeeds().get(i).setDeptActual(actQty);
                    break;
                }
            }
        }

        int total = mForm.getTotalRetrieved();
        int actTotal = 0;
        int needTotal = 0;
        for (DeptNeeded dept : mForm.getDeptNeeds()) {
            actTotal += dept.getDeptActual();
            needTotal += dept.getDeptNeeded();
        }

        if(actTotal > needTotal || actTotal > total) {
            Util.showToast(getActivity(), "Incorrect input");
            return;
        }

        mListener.onCallback(mForm, mPos);
    }

}
