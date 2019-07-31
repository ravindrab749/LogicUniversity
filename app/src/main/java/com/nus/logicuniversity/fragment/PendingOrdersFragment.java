package com.nus.logicuniversity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.adapter.MyOrderRecyclerViewAdapter;
import com.nus.logicuniversity.listener.OnListFragmentInteractionListener;
import com.nus.logicuniversity.model.Order;
import com.nus.logicuniversity.model.OrderedItem;
import com.nus.logicuniversity.utility.Util;

import java.util.ArrayList;

public class PendingOrdersFragment extends Fragment implements OnListFragmentInteractionListener {

    private ArrayList<Order> orders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);

        updateToolbarTitle();
        orders = new ArrayList<>();
        OrderedItem item = new OrderedItem();
        item.setQuantity(12);
        item.setTotal(22.22);
        ArrayList<OrderedItem> orderedItems = new ArrayList<>();
        orderedItems.add(item);
        Order order = new Order();
        order.setOrderedItems(orderedItems);
        orders.add(order);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyOrderRecyclerViewAdapter(getActivity(), orders, true, this));
        }
        return view;
    }

    private void updateToolbarTitle() {
        Util.updateTitle(getString(R.string.title_activity_pending), getActivity());
    }

    @Override
    public void onListFragmentInteraction(Order orderedItem) {
    }

}
