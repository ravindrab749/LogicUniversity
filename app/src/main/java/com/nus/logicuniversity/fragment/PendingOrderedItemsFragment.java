package com.nus.logicuniversity.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.adapter.MyOrderedItemRecyclerViewAdapter;
import com.nus.logicuniversity.adapter.RepPopupAdapter;
import com.nus.logicuniversity.model.OrderedItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PendingOrderedItemsFragment extends Fragment implements View.OnClickListener {

    private ArrayList<OrderedItem> orderedItems;
    private TextView repView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        orderedItems = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_ordereditem_list, container, false);

        TextView totView = view.findViewById(R.id.total_view);
        repView = view.findViewById(R.id.tv_rep_name);
        Button btnRepChange = view.findViewById(R.id.btn_change);
        EditText etComment = view.findViewById(R.id.et_comment);
        Button btnAccept = view.findViewById(R.id.btn_accept);
        Button btnReject = view.findViewById(R.id.btn_reject);

        btnRepChange.setOnClickListener(this);

        RecyclerView recyclerView = view.findViewById(R.id.pending_ordered_item_list);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL), R.drawable.divider);
        recyclerView.setAdapter(new MyOrderedItemRecyclerViewAdapter(getActivity(), orderedItems));

        return view;
    }

    @Override
    public void onClick(View view) {
        showDialog();
    }

    private void showDialog() {
        Map<String, String> map1 = new HashMap<>();
        map1.put("name", "Vicky");
        map1.put("id", "123");

        Map<String, String> map2 = new HashMap<>();
        map2.put("name", "Bunny");
        map2.put("id", "456");

        final ArrayList<Map<String, String>> list = new ArrayList<>();
        list.add(map1);
        list.add(map2);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Rep");

        RepPopupAdapter adapter = new RepPopupAdapter(getActivity(), R.layout.popup_rep, list);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                repView.setText(list.get(i).get("name"));
                dialogInterface.dismiss();
            }
        });

        builder.setCancelable(false);
        builder.create().show();

    }
}
