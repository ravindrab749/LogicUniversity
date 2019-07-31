package com.nus.logicuniversity.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.biometric.BiometricCallback;
import com.nus.logicuniversity.biometric.BiometricManager;
import com.nus.logicuniversity.db.DBAdapter;
import com.nus.logicuniversity.model.User;
import com.nus.logicuniversity.retrofit.Api;
import com.nus.logicuniversity.retrofit.RetrofitClient;
import com.nus.logicuniversity.utility.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, BiometricCallback {

    private BiometricManager biometricManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initBiometric();

        findViewById(R.id.id_login).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        doLogin();
    }

    private void initBiometric() {
        biometricManager = new BiometricManager.BiometricBuilder(this)
                .setTitle(getString(R.string.title_fingerprint))
                .setSubtitle(getString(R.string.title_sub_fingerprint))
                .setDescription(getString(R.string.msg_fingerprint_login))
                .setNegativeButtonText(getString(R.string.action_cancel))
                .build();
        checkForBiometricLogin();
    }

    private void doLogin() {
        Api api = RetrofitClient.getInstance().getApi();
        api.test().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("Name --- ", response.body());
                Util.showToast(LoginActivity.this, response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Exception --- ", t.getMessage());
                call.cancel();
            }
        });
//        startActivity(new Intent(this, StockClerkActivity.class));
    }

    private void checkForBiometricLogin() {
        User user = new DBAdapter(this).getUser();
        if(user != null && user.isEnrolledFingerprint()) {
            biometricManager.authenticate(this);
        }
    }

    @Override
    public void onSdkVersionNotSupported() {
        Util.showToast(this, getString(R.string.biometric_error_sdk_not_supported));
    }

    @Override
    public void onBiometricAuthenticationNotSupported() {
        Util.showToast(this, getString(R.string.biometric_error_hardware_not_supported));
    }

    @Override
    public void onBiometricAuthenticationNotAvailable() {
        Util.showToast(this, getString(R.string.biometric_error_fingerprint_not_available));
    }

    @Override
    public void onBiometricAuthenticationPermissionNotGranted() {
        Util.showToast(this, getString(R.string.biometric_error_permission_not_granted));
    }

    @Override
    public void onBiometricAuthenticationInternalError(String error) {
        Util.showToast(this, error);
    }

    @Override
    public void onAuthenticationFailed() {
        Util.showToast(this, getString(R.string.biometric_failure));
    }

    @Override
    public void onAuthenticationCancelled() {
        Util.showToast(this, getString(R.string.biometric_cancelled));
    }

    @Override
    public void onAuthenticationSuccessful() {
        Util.showToast(this, getString(R.string.biometric_success));
        doLogin();
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        Util.showToast(this, helpString.toString());
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        Util.showToast(this, errString.toString());
    }

}
