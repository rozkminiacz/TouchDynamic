package com.example.michalik.touchdynamic.fragment;

import com.example.michalik.touchdynamic.objects.MeasureSettings;

/**
 * Created by michalik on 23.10.16
 */

public class MeasureSettingsDialog{

    public interface SettingsDone{
        void defaultSettings();
        void newSettings(MeasureSettings settings);
    }
}
