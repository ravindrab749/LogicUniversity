package com.nus.logicuniversity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.biometric.BiometricCallback;
import com.nus.logicuniversity.biometric.BiometricManager;
import com.nus.logicuniversity.db.DBAdapter;
import com.nus.logicuniversity.model.Employee;
import com.nus.logicuniversity.model.LoginResponse;
import com.nus.logicuniversity.model.Roles;
import com.nus.logicuniversity.model.User;
import com.nus.logicuniversity.retrofit.Api;
import com.nus.logicuniversity.retrofit.RetrofitClient;
import com.nus.logicuniversity.utility.Util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, BiometricCallback {

    private BiometricManager biometricManager;
    private ProgressBar progressBar;
    private User user;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

    }

    private  void initView() {

        initBiometric();

        progressBar = findViewById(R.id.progress_bar);
        loginBtn = findViewById(R.id.id_login);
        loginBtn.setOnClickListener(this);
    }

    private void showProgressBar(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        loginBtn.setEnabled(!show);
        loginBtn.setClickable(!show);
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

    private boolean isInRole(String role) {

        for(Roles r : Roles.values()) {
            if(r.name().equalsIgnoreCase(role)) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void onResume() {
        reset();
        super.onResume();
    }

    private void reset() {
        EditText unEt = findViewById(R.id.id_username);
        if(unEt != null) {
            unEt.setText(null);
            unEt.clearFocus();
        }
        EditText pwEt = findViewById(R.id.id_password);
        if(pwEt != null) {
            pwEt.setText(null);
            pwEt.clearFocus();
        }
    }

    private void doLogin() {

        EditText unEt = findViewById(R.id.id_username);
        EditText pwEt = findViewById(R.id.id_password);
        String username = unEt.getText().toString();
        String password = pwEt.getText().toString();
        if(TextUtils.isEmpty(username)) {
            unEt.setError("Username should not be empty");
            unEt.requestFocus();
            return;
        } else {
            unEt.setError(null);
        }

        if(TextUtils.isEmpty(password)) {
            pwEt.setError("Password should not be empty");
            pwEt.requestFocus();
            return;
        } else {
            pwEt.setError(null);
        }

        login(username, password);

    }

    private void login(final String username, final String password){
        showProgressBar(true);
        Api api = RetrofitClient.getInstance().getApi();
        api.login(username, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {

                showProgressBar(false);

                LoginResponse res = response.body();
                assert res != null;
                if(!res.isLogin()) {
                    Util.showToast(getApplicationContext(), res.getMsg());
                } else {

                    String headerVal = response.headers().get(Util.AUTH_HEADER);
                    if(headerVal == null || TextUtils.isEmpty(headerVal)) {
                        Util.showToast(getApplicationContext(), "Missing authorized data");
                        return;
                    }

                    Employee emp = res.getEmp();
                    if(emp == null) {
                        Util.showToast(getApplicationContext(), "Missed information");
                        return;
                    }
                    if(!isInRole(emp.getEmpRole())) {
                        Util.showToast(getApplicationContext(), "Un-authorized");
                        return;
                    }

                    DBAdapter adapter = new DBAdapter(LoginActivity.this);
                    User user = adapter.getUser();
                    boolean isFpEnabled = false;
                    if(user == null) {
                        adapter.insertLogin(username, password);
                    } else if(username.equals(user.getUsername())) {
                        isFpEnabled = user.isEnrolledFingerprint();
                    }

                    Util.addUserToSharedPref(LoginActivity.this, emp);
                    Util.addHeaderToSharedPref(LoginActivity.this, headerVal);
                    Util.addToSharedPref(LoginActivity.this, Util.FP_KEY, String.valueOf(isFpEnabled));

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                showProgressBar(false);
            }
        });
    }

    private void checkForBiometricLogin() {
        user = new DBAdapter(this).getUser();
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
//        Util.showToast(this, getString(R.string.biometric_success));
//        doLogin();
        login(user.getUsername(), user.getPassword());
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
