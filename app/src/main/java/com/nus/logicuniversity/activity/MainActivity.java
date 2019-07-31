package com.nus.logicuniversity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
import com.nus.logicuniversity.fragment.FingerprintFragment;
import com.nus.logicuniversity.fragment.HomeFragment;
import com.nus.logicuniversity.fragment.PastOrdersFragment;
import com.nus.logicuniversity.fragment.PendingOrdersFragment;
import com.nus.logicuniversity.listener.OnMenuButtonSelectedListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMenuButtonSelectedListener {

    private NavigationView navigationView;

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
        if(savedInstanceState == null)
            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_home));
    }

    private void goToHomeFragment() {
        replaceFragment(new HomeFragment(this));
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

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            goToHomeFragment();
        } else if (id == R.id.nav_pending) {
            goToPendingOrderFragment();
        } else if (id == R.id.nav_past_orders) {
            goToPastOrderFragment();
        } else if (id == R.id.nav_delegate) {

        } else if(id == R.id.nav_fingerprint) {
            goToFingerPrintFragment();
        }

        item.setChecked(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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

}
