package com.nus.logicuniversity.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.nus.logicuniversity.R;
import com.nus.logicuniversity.fragment.DelegateFragment;
import com.nus.logicuniversity.fragment.PastDisbursementFragment;
import com.nus.logicuniversity.fragment.PendingDisbursementFragment;
import com.nus.logicuniversity.fragment.FingerprintFragment;
import com.nus.logicuniversity.fragment.DeptHeadHomeFragment;
import com.nus.logicuniversity.fragment.PastOrdersFragment;
import com.nus.logicuniversity.fragment.PendingOrdersFragment;
import com.nus.logicuniversity.fragment.RepresentativeFragment;
import com.nus.logicuniversity.fragment.StockClerkHomeFragment;
import com.nus.logicuniversity.listener.OnMenuButtonSelectedListener;
import com.nus.logicuniversity.model.Roles;
import com.nus.logicuniversity.retrofit.Api;
import com.nus.logicuniversity.retrofit.RetrofitClient;
import com.nus.logicuniversity.utility.Util;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMenuButtonSelectedListener {

    private NavigationView navigationView;
    private boolean doubleBackPressed = false;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        setNamesInNav();
        if(savedInstanceState == null)
            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_home));
    }

    public void showProgressBar(boolean show) {
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void setNamesInNav() {

        TextView nameView = navigationView.getHeaderView(0).findViewById(R.id.user_name);
        if(nameView != null)
            nameView.setText(Util.getValueFromSharedPreferences("name", MainActivity.this));

        TextView roleView = navigationView.getHeaderView(0).findViewById(R.id.role_name);
        if(roleView != null)
            roleView.setText(Util.getValueFromSharedPreferences("role", MainActivity.this));
    }

    private void goToHomeFragment() {
        replaceFragment(new DeptHeadHomeFragment(this));
    }

    private void goToStockClerkHomeFragment() {
        replaceFragment(new StockClerkHomeFragment());
    }

    private void goToRepresentativeHomeFragment() {
        replaceFragment(new PendingDisbursementFragment());
    }

    private void goToPendingOrderFragment() {
        replaceFragment(new PendingOrdersFragment());
    }

    private void goToPastOrderFragment() {
        replaceFragment(new PastOrdersFragment());
    }

    private void goToFingerPrintFragment() {
        replaceFragment(new FingerprintFragment());
    }

    private void goToRepChangeFragment() {
        replaceFragment(new RepresentativeFragment());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void goToDelegateFragment() {
        replaceFragment(new DelegateFragment());
    }

    private void goToPastDisbursementsFragment() {
        replaceFragment(new PastDisbursementFragment());
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
    }

    private void checkForUserRoleHome() {
        String role = Util.getValueFromSharedPreferences("role", MainActivity.this);
        switch (Roles.valueOf(role)) {
            case HEAD:
                showDeptHeadNavMenus();
                goToHomeFragment();
                break;
            case STORE_CLERK:
                goToStockClerkHomeFragment();
                break;
            case REPRESENTATIVE:
                showRepNavMenus();
                goToRepresentativeHomeFragment();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
                super.onBackPressed();
            } else if (doubleBackPressed) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                this.doubleBackPressed = true;
                Util.showToast(this, "Please click BACK again to exit");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackPressed = false;
                    }
                }, 2000);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onNavigationItemSelected(@NotNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {
//            goToHomeFragment();
            checkForUserRoleHome();
        } else if (id == R.id.nav_pending) {
            goToPendingOrderFragment();
        } else if (id == R.id.nav_past_orders) {
            goToPastOrderFragment();
        } else if (id == R.id.nav_delegate) {
            goToDelegateFragment();
        } else if(id == R.id.nav_fingerprint) {
            goToFingerPrintFragment();
        } else if(id == R.id.nav_rep_change) {
            goToRepChangeFragment();
        } else if(id == R.id.nav_past_dis) {
            goToPastDisbursementsFragment();
        }

        item.setChecked(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showDeptHeadNavMenus() {
        navigationView.getMenu().findItem(R.id.nav_pending).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_past_orders).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_delegate).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_rep_change).setVisible(true);
    }

    private void showRepNavMenus() {
        navigationView.getMenu().findItem(R.id.nav_past_dis).setVisible(true);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onMenuButtonSelected(int id) {
        switch (id) {
            case R.id.id_pending:
                onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_pending));
                break;
            case R.id.id_past_orders:
                onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_past_orders));
                break;
            case R.id.id_delegate:
                onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_delegate));
                break;
        }
    }

    private void logout() {
        String header = Util.getHeaderValueFromSharedPreferences(MainActivity.this);
        Api api = RetrofitClient.getInstance().getApi();
        api.logout(header).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                String msg = response.body();
                goToLoginView();
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                goToLoginView();
            }
        });
    }

    private void goToLoginView() {
        Util.clearSharedPref(MainActivity.this);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
