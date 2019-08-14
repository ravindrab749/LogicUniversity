package com.nus.logicuniversity.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.model.AdjustmentVoucher;
import com.nus.logicuniversity.model.Inventory;
import com.nus.logicuniversity.model.InventoryListResponse;
import com.nus.logicuniversity.retrofit.Api;
import com.nus.logicuniversity.retrofit.RetrofitClient;
import com.nus.logicuniversity.utility.Util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventoryFragment extends Fragment implements View.OnClickListener {

    private ArrayList<Inventory> inventories;
    private TextView emptyView;
    private LinearLayout parent;
    private EditText[] etArray;
    private Button genBtn;

    public InventoryFragment() {
        inventories = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inventory_list, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        updateToolbarTitle();
        emptyView = view.findViewById(R.id.tv_empty);
        parent = view.findViewById(R.id.parent_lin_lay);

        getAllInventories();

        genBtn = view.findViewById(R.id.btnGenStockAdj);
        genBtn.setOnClickListener(this);
        showGenButton(false);

    }

    private void updateToolbarTitle() {
        Util.updateTitle(getString(R.string.title_fragment_inventory), Objects.requireNonNull(getActivity()));
    }

    private void showEmptyView(boolean show) {
        emptyView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void showGenButton(boolean show) {
        genBtn.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View view) {

        @SuppressLint("UseSparseArrays") HashMap<Long, Integer> qtyMap = new HashMap<>(etArray.length);

        if(etArray.length > 0) {
            for(EditText et : etArray) {
                String qty = et.getText().toString();
                long id = (long) et.getTag();
                qtyMap.put(id, Integer.parseInt(qty));
            }
        }

        ArrayList<AdjustmentVoucher> adjustmentVouchers = new ArrayList<>();
        ArrayList<Long> itemIds = new ArrayList<>();
        for(Inventory inventory : inventories) {
            if(inventory == null) continue;
            int actual = (qtyMap.get(inventory.getItemId()) == null) ? 0 : qtyMap.get(inventory.getItemId());
            int qty =  actual - inventory.getStockLevel();
            if (qty < 0)
            {
                itemIds.add(inventory.getItemId());
                AdjustmentVoucher voucher = new AdjustmentVoucher();
                voucher.setItem(inventory);
                voucher.setItemId(inventory.getItemId());
                voucher.setAdjQty(qty);
                adjustmentVouchers.add(voucher);
            }
        }

        if(itemIds.isEmpty()) {
            Util.showToast(getActivity(), "Nothing to adjust");
            return;
        }

        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.frame, new AdjustmentVoucherFragment(itemIds, adjustmentVouchers)).commit();

    }

    private void getAllInventories() {
        String header = Util.getHeaderValueFromSharedPreferences(getActivity());
        Util.showProgressBar(getActivity(), true);
        Api api = RetrofitClient.getInstance().getApi();
        api.getAllInventories(header).enqueue(new Callback<InventoryListResponse>() {
            @Override
            public void onResponse(@NotNull Call<InventoryListResponse> call, @NotNull Response<InventoryListResponse> response) {
                InventoryListResponse res = response.body();
                if(res != null && res.getInventories() != null) {
                    inventories.clear();
                    ArrayList<Inventory> invs = res.getInventories();
                    for(int i=0; i<invs.size();i++) {
                        invs.get(i).setActualStock(invs.get(i).getStockLevel());
                    }
                    inventories.addAll(invs);
                    if(!inventories.isEmpty())
                        renderView();
                }

                showEmptyView(inventories.isEmpty());
                Util.showProgressBar(getActivity(), false);
            }

            @Override
            public void onFailure(@NotNull Call<InventoryListResponse> call, @NotNull Throwable t) {
                Util.showToast(getActivity(), "Failed to retrieve");
                showEmptyView(inventories.isEmpty());
                Util.showProgressBar(getActivity(), false);
            }
        });
    }

    private void renderView() {
        etArray = new EditText[inventories.size()];
        for(int i=0; i<inventories.size(); i++) {
            Inventory item = inventories.get(i);
            @SuppressLint("InflateParams")
            View view = getLayoutInflater().inflate(R.layout.fragment_inventory, null);
            CardView cardView = (CardView) view;
            TextView mItemNumView = cardView.findViewById(R.id.tv_item_num);
            TextView mItemCatView = cardView.findViewById(R.id.tv_item_cat);
            TextView mItemDescView = cardView.findViewById(R.id.tv_item_desc);
            TextView mItemUomView = cardView.findViewById(R.id.tv_item_uom);
            TextView mItemSLastView = cardView.findViewById(R.id.tv_item_stock_last);
            EditText mItemSActView = cardView.findViewById(R.id.et_item_stock_actual);
            mItemNumView.setText(item.getItemCode());
            mItemCatView.setText(item.getCategory());
            mItemDescView.setText(item.getDescription());
            mItemUomView.setText(item.getUnitOfMeasure());
            mItemSLastView.setText(Objects.requireNonNull(getActivity()).getString(R.string.text_for_number, item.getStockLevel()));
            mItemSActView.setText(getActivity().getString(R.string.text_for_number, item.getStockLevel()));
            mItemSActView.setTag(item.getItemId());
            etArray[i] = mItemSActView;
            cardView.setElevation(2.0f);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) cardView.getLayoutParams();
            if(params != null)
                params.setMargins(0,0,0,10);
            cardView.requestLayout();
            parent.addView(cardView);
        }

        showGenButton(true);
        Util.showProgressBar(getActivity(), false);
    }
}
