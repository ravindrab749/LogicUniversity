package com.nus.logicuniversity.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.fragment.StockClerkHomeFragment;

public class StockClerkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_stock_clerk);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().add(R.id.frame, new StockClerkHomeFragment()).commit();
    }

}
