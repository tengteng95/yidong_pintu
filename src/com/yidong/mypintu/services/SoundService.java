package com.yidong.mypintu.services;


import com.yidong.mypintu.R;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;

public class SoundService extends Service implements OnCompletionListener{

	private MediaPlayer mp;
	 
    @Override
    public void onCreate() {
        super.onCreate();
        mp = MediaPlayer.create(this, R.raw.bgground);
        mp.setOnCompletionListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.release();
        stopSelf();
    }
 
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	//用来startService
//        boolean playing = intent.getBooleanExtra("playing", false);
//        if (!playing) {
//            mp.start();
//        } else {
//            mp.pause();
//        }
    	mp.start();
        return super.onStartCommand(intent, flags, startId);
    }
 
    @Override
    public IBinder onBind(Intent intent) {
    	//用来bind service
        return null;
    }

	@Override
	public void onCompletion(MediaPlayer mp) {
		mp.start();
		
	}


}
