package com.example.michalik.touchdynamic.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.michalik.touchdynamic.R;

/**
 * Created by michalik on 12.11.16
 */

public class Metronome {
    public static final String bpm80 = "80";
    public static final String bpm100 = "100";
    public static final String bpm120 = "120";
    public static final String TAG = Metronome.class.getSimpleName();
    private static void playSound(String BPM, Context context){

        String resourceId;

        switch (BPM){
            case bpm80:
                resourceId = R.id.;
                break;
            case bpm100:
                break;
            case bpm120:
                break;
        }

        try{
            AssetFileDescriptor descriptor = context.getAssets().openFd(resourceId);
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            mediaPlayer.prepareAsync();
        }
        catch (Exception e){
            Log.d(TAG, "playSound: ", e);
        }
    }
}
