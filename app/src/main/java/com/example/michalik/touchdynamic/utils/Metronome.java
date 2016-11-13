package com.example.michalik.touchdynamic.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;

import com.example.michalik.touchdynamic.R;

/**
 * Created by michalik on 12.11.16
 */

public class Metronome {
    public static final String bpm80 = "80";
    public static final String bpm100 = "100";
    public static final String bpm120 = "120";
    public static final String bpm140 = "140";
    public static final String TAG = Metronome.class.getSimpleName();

    public static void playSound(String BPM, final Context context){

        final String resourceId = "t_"+BPM+"_bpm.wav";

        new Runnable() {
            @Override
            public void run() {
                try{
                    AssetFileDescriptor descriptor = context.getAssets().openFd(resourceId);
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
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
        }.run();
    }
}
