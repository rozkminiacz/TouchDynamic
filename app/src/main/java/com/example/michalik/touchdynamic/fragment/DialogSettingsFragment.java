package com.example.michalik.touchdynamic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.michalik.touchdynamic.R;
import com.example.michalik.touchdynamic.objects.MeasureSettings;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by michalik on 30.10.16
 */

public class DialogSettingsFragment extends DialogFragment {

    @BindView(R.id.dialog_settings_age_spinner)
            AppCompatSpinner ageSpinner;

    @BindView(R.id.dialog_settings_gender_spinner)
            AppCompatSpinner genderSpinner;
    @BindView(R.id.dialog_settings_position_spinner)
            AppCompatSpinner positionSpinner;
    @BindView(R.id.dialog_settings_rested_spinner)
            AppCompatSpinner restedSpinner;

    SettingsAppliedListener listener;

    public void setListener(SettingsAppliedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_settings_fragment, container, false);

        ButterKnife.bind(this, v);
        populateSpinners();
        return v;
    }

    @OnClick(R.id.dialog_settings_apply)
    public void onApply(){
        MeasureSettings settings = new MeasureSettings();
        settings.setPosition((String)positionSpinner.getSelectedItem());
        listener.onApplied(new MeasureSettings());
    }

    private void populateSpinners(){
        List<String> genderList = new ArrayList<>();
        genderList.add("Man");
        genderList.add("Woman");
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, genderList);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setPrompt(genderList.get(0));

        List<String> tiredList = new ArrayList<>();
        tiredList.add("1 - rested");
        for(int i=2; i<=9; i++){
            tiredList.add(""+i);
        }
        tiredList.add("10 - tired");
        ArrayAdapter<String> tiredAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, tiredList);
        restedSpinner.setAdapter(tiredAdapter);
        restedSpinner.setPrompt(tiredList.get(0));

        List<Integer> ageList = new ArrayList<>();
        for(int i=1; i<100; i++){
            ageList.add(i);
        }
        ArrayAdapter<Integer> ageAdapter = new ArrayAdapter<Integer>(getContext(), R.layout.support_simple_spinner_dropdown_item, ageList);
        ageSpinner.setAdapter(ageAdapter);
        ageSpinner.setPrompt("20");

        List<String> positionList = new ArrayList<>();
        positionList.add("Standing");
        positionList.add("Sitting");
        positionList.add("Laying");

        ArrayAdapter<String> positionAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, positionList);
        positionSpinner.setAdapter(positionAdapter);
        positionSpinner.setPrompt(positionList.get(0));

    }

    public interface SettingsAppliedListener{
        void onApplied(MeasureSettings measureSettings);
        void onDismissed();
    }
}
