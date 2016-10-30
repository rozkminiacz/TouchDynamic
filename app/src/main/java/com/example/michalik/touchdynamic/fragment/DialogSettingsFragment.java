package com.example.michalik.touchdynamic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.michalik.touchdynamic.R;
import com.example.michalik.touchdynamic.objects.MeasureSettings;

import butterknife.ButterKnife;

/**
 * Created by michalik on 30.10.16
 */

public class DialogSettingsFragment extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_settings_fragment, container, false);

        ButterKnife.bind(this, v);

        return v;
    }

    private void populateSpinners(){

    }

    public interface SettingsAppliedListener{
        void onApplied(MeasureSettings measureSettings);
        void onDismissed();
    }
}
