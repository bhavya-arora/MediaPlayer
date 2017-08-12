package com.bhavya.mediaplayer;

import android.icu.util.TimeUnit;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import java.util.regex.*;

/*String f="";
		float a=157048;
		float b=(a/1000);
		float c=b/60;
		String minute=String.valueOf(c);
		int position =minute.indexOf('.');
		char[] e=minute.toCharArray();
		for(int d=0;d<position;d++){
			f=f+e[d];
		}

		System.out.println(f);
		System.out.println(position);*/

public class MainActivity extends AppCompatActivity {
    Button playButton;
    Button pauseButton;
    TextView time;
    TextView leftTime;
    int a =157;
    int counter=0;
    static int min=0;
    private static int count=0;
    long duration;
    int check = 0;
    boolean isPaused=false;
    boolean isCanceled=false;
    long remainingTime=0;
    String TAG="Bhavya";
    long totalSeconds;
    long second;
    long seconds;
    long minute=0;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(count<10){
                leftTime.setText(min+":"+"0"+(count));
            }
            else if(count>=10){
                leftTime.setText(min+":"+(count));
            }
            if(count==59){
                count=0;
                min++;
            }
            count++;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton=(Button) findViewById(R.id.playButton);
        pauseButton=(Button) findViewById(R.id.pauseButton);
        time=(TextView) findViewById(R.id.time);
        leftTime=(TextView) findViewById(R.id.leftTime);
        pauseButton.setEnabled(false);

        final MediaPlayer mediaPlayer=MediaPlayer.create(this,R.raw.music);

        duration=mediaPlayer.getDuration();
        second=duration/1000;
        remainingTime=duration;
        playButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mediaPlayer.start();
                        Log.i(TAG, "Duration is"+duration);
                        setTime();
                        enabled();
                        isCanceled=false;
                        isPaused=false;
                        new CountDownTimer(remainingTime,1000){
                            @Override
                            public void onTick(long millisUntilFinished) {
                                if(isPaused || isCanceled){
                                    cancel();
                                }
                                else{
                                    String setTime=String.valueOf(millisUntilFinished/1000);
                                    Log.i(TAG, "second is"+second);
                                    long millis=millisUntilFinished/1000;
                                    seconds=second-millis;
                                    Log.i(TAG, "onTick: "+seconds);
                                    if(seconds==60){
                                        minute++;
                                       second=millis;
                                    }
                                    if(seconds==60){
                                        seconds=0;
                                    }
                                    leftTime.setText(minute+":"+(String.valueOf(seconds)));
                                    remainingTime=millisUntilFinished;
                                }
                            }

                            @Override
                            public void onFinish() {
                                leftTime.setText("Finished");
                                remainingTime=duration;
                                playButton.setEnabled(true);
                                pauseButton.setEnabled(false);
                                minute=0;
                                second=duration/1000;
                            }
                        }.start();
                    }
                }
        );

        pauseButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mediaPlayer.pause();
                       enabled();
                        isPaused=true;
                        new CountDownTimer(remainingTime,1000){
                            @Override
                            public void onTick(long millisUntilFinished) {
                                if(isPaused || isCanceled){
                                    cancel();
                                    Log.i(TAG, "remainingTime "+remainingTime);
                                    Log.i(TAG, "MiilisUntilFInished "+millisUntilFinished);
                                }

                            }

                            @Override
                            public void onFinish() {
                                leftTime.setText("Finished");
                                remainingTime=duration;
                                playButton.setEnabled(true);
                            }
                        }.start();
                    }
                }
        );
    }
    /*public void threadStart() {
        Log.i(TAG, "threadStart: Under method");
        ThreadGroup threadGroup=new ThreadGroup("bhavya");
        if (check<= 0) {
            Log.i(TAG, "threadStart: Under loop");
            Thread bhavyaThread = new Thread(threadGroup,
                    new Runnable() {
                        @Override
                        public void run() {
                            synchronized (this) {
                                while (a > counter) {
                                    try {
                                        wait(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    a--;
                                    handler.sendEmptyMessage(0);
                                }
                            }

                        }
                    }
            );
            Log.i(TAG, "Actice count "+bhavyaThread.isInterrupted());
            Log.i(TAG, "ThreadGroup Active Count "+bhavyaThread.activeCount());
            Log.i(TAG, String.valueOf(bhavyaThread.getThreadGroup()));


            if (threadGroup.activeCount()>=2) {
                Log.i(TAG, "threadStart: is alive");
                return;
            } else if(threadGroup.activeCount()==0 || threadGroup.activeCount()==1){
                Log.i(TAG, "threadStart: not alive");
                bhavyaThread.start();
            }
        }
    }*/
    public void setTime(){
       totalSeconds=duration/1000;
        Log.i(TAG, "setTime: "+totalSeconds);
        long finalMInute=totalSeconds/60;
        long approxSeconds=finalMInute*60;
        long finalSecond=totalSeconds-approxSeconds;
        time.setText(finalMInute+":"+finalSecond);
    }

    public void enabled(){
        if(playButton.isEnabled()){
            playButton.setEnabled(false);
            pauseButton.setEnabled(true);
        }
        else if(pauseButton.isEnabled()){
            pauseButton.setEnabled(false);
            playButton.setEnabled(true);
        }
    }

}
