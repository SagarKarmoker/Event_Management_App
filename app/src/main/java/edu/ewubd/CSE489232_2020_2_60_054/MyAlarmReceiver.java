package edu.ewubd.CSE489232_2020_2_60_054;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;

public class MyAlarmReceiver extends BroadcastReceiver {

    MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    private PowerManager.WakeLock wakeLock;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Acquire a WakeLock to ensure the device stays awake while handling the alarm.
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyAlarmReceiver:WakeLock");
        wakeLock.acquire(2* 60 * 1000L /*10 minutes*/);

        mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stop();
            }
        }, 10000);
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        // Release the WakeLock once the task is complete.
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
            wakeLock = null;
        }
    }
}
