package com.nus.logicuniversity.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.db.DBAdapter;
import com.nus.logicuniversity.model.User;
import com.nus.logicuniversity.utility.Util;

import java.util.Objects;

public class FingerprintFragment extends Fragment {

    private SwitchCompat fpSwitch;

    private int[][] states = new int[][] {
            new int[] {-android.R.attr.state_checked},
            new int[] {android.R.attr.state_checked},
    };

    private int[] thumbColors = new int[] {
            Color.WHITE,
            Color.GREEN
    };

    private int[] trackColors = new int[] {
            Color.GRAY,
            Color.MAGENTA
    };

    private boolean isAutoCheck = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fingerprint, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        updateToolbarTitle();
        fpSwitch = view.findViewById(R.id.fp_switch);
        fpSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!isAutoCheck)
                    checkFingerPrintAccount(b);
                else isAutoCheck = false;
            }
        });

        isUserFingerPrintEnabled();

//        DrawableCompat.setTintList(DrawableCompat.wrap(fpSwitch.getThumbDrawable()), new ColorStateList(states, thumbColors));
//        DrawableCompat.setTintList(DrawableCompat.wrap(fpSwitch.getTrackDrawable()), new ColorStateList(states, trackColors));
    }

    private void updateToolbarTitle() {
        Util.updateTitle(getString(R.string.title_fragment_finger_print), Objects.requireNonNull(getActivity()));
    }

    private void checkFingerPrintAccount(boolean b) {
        String username = Util.getValueFromSharedPreferences("username", getActivity());
        User user = new DBAdapter(getActivity()).getUserByUsername(username);
        if(user == null) {
            user = new DBAdapter(getActivity()).getUser();
            if(user.isEnrolledFingerprint()) {
                enableFP(false);
                showDialog(false);
            } else {
                deleteAndInsertUser(username);
                if(b) showDialog(true);
                enableFP(b);
            }
        } else {
            if(b) showDialog(true);
            enableFP(b);
            updateUserFingerPrint(b);
        }
    }

    private void deleteAndInsertUser(String username) {
        String password = Util.getValueFromSharedPreferences("password", getActivity());
        DBAdapter adapter = new DBAdapter(getActivity());
        adapter.deleteAll();
        adapter.insertLogin(username, password, true);
    }

    private void enableFP(boolean enable) {
        fpSwitch.setChecked(enable);
    }

    private void updateUserFingerPrint(boolean isEnabled) {
        DBAdapter adapter = new DBAdapter(getActivity());
        adapter.updateFingerprint(isEnabled, Util.getValueFromSharedPreferences("username", getActivity()));
        Util.addToSharedPref(getActivity(), Util.FP_KEY, String.valueOf(isEnabled));
    }

    private void isUserFingerPrintEnabled() {
        String val = Util.getValueFromSharedPreferences(Util.FP_KEY, getActivity());
        boolean isEnabled = Boolean.valueOf(val);
        isAutoCheck = isEnabled;
        enableFP(isEnabled);
    }

    private void showDialog(boolean success) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        dialogBuilder.setTitle(getString(R.string.title_reminder));
        dialogBuilder.setMessage(getString(success ? R.string.msg_fingerprint : R.string.msg_fingerprint_err));
        dialogBuilder.setCancelable(true);
        dialogBuilder.setPositiveButton(getString(R.string.action_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogBuilder.create().show();
    }

}
