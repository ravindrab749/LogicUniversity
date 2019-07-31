package com.nus.logicuniversity.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.app.DialogCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.utility.Util;

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
        fpSwitch = (SwitchCompat) view.findViewById(R.id.fp_switch);
        fpSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                compoundButton.setChecked(b);
                showDialog();
            }
        });

//        DrawableCompat.setTintList(DrawableCompat.wrap(fpSwitch.getThumbDrawable()), new ColorStateList(states, thumbColors));
//        DrawableCompat.setTintList(DrawableCompat.wrap(fpSwitch.getTrackDrawable()), new ColorStateList(states, trackColors));

        return view;
    }

    private void showDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(getString(R.string.title_reminder));
        dialogBuilder.setMessage(getString(R.string.msg_fingerprint));
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
