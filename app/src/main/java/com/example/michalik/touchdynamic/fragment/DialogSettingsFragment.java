package com.example.michalik.touchdynamic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.michalik.touchdynamic.R;
import com.example.michalik.touchdynamic.objects.MeasureSettings;
import com.example.michalik.touchdynamic.utils.Metronome;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by michalik on 30.10.16
 */

public class DialogSettingsFragment extends DialogFragment {

    public static final String TAG = DialogSettingsFragment.class.getSimpleName();

    @BindView(R.id.dialog_settings_age_spinner)
            AppCompatSpinner ageSpinner;

    @BindView(R.id.dialog_settings_gender_spinner)
            AppCompatSpinner genderSpinner;
    @BindView(R.id.dialog_settings_position_spinner)
            AppCompatSpinner positionSpinner;
    @BindView(R.id.dialog_settings_rested_spinner)
            AppCompatSpinner restedSpinner;

    @BindView(R.id.dialog_settings_finger_spinner)
            AppCompatSpinner fingerSpinner;

    @BindView(R.id.dialog_settings_bpm_spinner)
            AppCompatSpinner bpmSpinner;

    @BindView(R.id.dialog_settings_hand_switch)
            SwitchCompat handSwitch;

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
        populateViews();
        return v;
    }

    @OnClick(R.id.dialog_settings_apply)
    public void onApply(){
        MeasureSettings settings = new MeasureSettings();
        settings.setPosition((String)positionSpinner.getSelectedItem());
        settings.setAge((int)ageSpinner.getSelectedItem()+"");
        settings.setGender((String)genderSpinner.getSelectedItem());
        settings.setFinger((int)fingerSpinner.getSelectedItem()+"");
        settings.setTired((int)restedSpinner.getSelectedItem()+"");
        settings.setHand(handSwitch.isChecked() ? "Right" : "Left");
        settings.setDesiredBPM((String) bpmSpinner.getSelectedItem());
        settings.setDevicePosition("unspecified");
        Log.d(TAG, "onApply: ");
//        settings.setDevicePosition((String));
        listener.onApplied(settings);
    }

    private void populateViews(){

        handSwitch.setShowText(true);
        handSwitch.setTextOn(getString(R.string.settings_dialog_hand_right));
        handSwitch.setTextOff(getString(R.string.settings_dialog_hand_left));
        handSwitch.setChecked(true);

        List<String> genderList = new ArrayList<>();
        genderList.add("Man");
        genderList.add("Woman");
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, genderList);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setPrompt(genderList.get(0));

        List<Integer> tiredList = new ArrayList<>();
        for(int i=1; i<=10; i++){
            tiredList.add(i);
        }
//        tiredList.add("1 - rested");
//        for(int i=2; i<=9; i++){
//            tiredList.add(""+i);
//        }
//        tiredList.add("10 - tired");
        ArrayAdapter<Integer> tiredAdapter = new ArrayAdapter<Integer>(getContext(), R.layout.support_simple_spinner_dropdown_item, tiredList);
        restedSpinner.setAdapter(tiredAdapter);
        restedSpinner.setPrompt(tiredList.get(0).toString());

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

        List<Integer> fingerList = new ArrayList<>();
        for(int i=1; i<=5; i++){
            fingerList.add(i);
        }
        ArrayAdapter<Integer> fingerAdapter = new ArrayAdapter<Integer>(getContext(), R.layout.support_simple_spinner_dropdown_item, fingerList);
        fingerSpinner.setAdapter(fingerAdapter);
        fingerSpinner.setPrompt("1");

        List<String> bpmList = new ArrayList<>();

        bpmList.add(Metronome.bpm80);
        bpmList.add(Metronome.bpm100);
        bpmList.add(Metronome.bpm120);
        bpmList.add(Metronome.bpm140);

        ArrayAdapter<String> bpmAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, bpmList);
        bpmSpinner.setAdapter(bpmAdapter);
        bpmSpinner.setPrompt("100 BPM");


    }

    public interface SettingsAppliedListener{
        void onApplied(MeasureSettings measureSettings);
        void onDismissed();
    }
}
