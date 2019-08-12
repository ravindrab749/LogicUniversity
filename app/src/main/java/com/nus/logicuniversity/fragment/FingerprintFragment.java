package com.nus.logicuniversity.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fingerprint, container, false);
        updateToolbarTitle();
        fpSwitch = view.findViewById(R.id.fp_switch);
        fpSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkFingerPrintAccount(b);
            }
        });

        isUserFingerPrintEnabled();

//        DrawableCompat.setTintList(DrawableCompat.wrap(fpSwitch.getThumbDrawable()), new ColorStateList(states, thumbColors));
//        DrawableCompat.setTintList(DrawableCompat.wrap(fpSwitch.getTrackDrawable()), new ColorStateList(states, trackColors));

        return view;
    }

    private void updateToolbarTitle() {
        Util.updateTitle(getString(R.string.title_fragment_finger_print), Objects.requireNonNull(getActivity()));
    }

    private void checkFingerPrintAccount(boolean b) {
        User user = new DBAdapter(getActivity()).getUserByUsername(Util.getValueFromSharedPreferences("username", getActivity()));
        if(user == null) {
            enableFP(false);
            showDialog(false);
        } else {
            if(b) showDialog(true);
            enableFP(b);
            updateUserFingerPrint(b);
        }
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
        enableFP(Boolean.valueOf(val));
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
