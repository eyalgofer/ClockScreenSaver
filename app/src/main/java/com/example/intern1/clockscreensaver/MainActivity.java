package com.example.intern1.clockscreensaver;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private final long timeInterval = 1000;
    private int progress = 0;

    private Handler handler = new Handler();

    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");


    private DotCircleProgress mDotCircleProgress;
    private TextView mTime;
    private TextView mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDotCircleProgress = (DotCircleProgress)findViewById(R.id.dots);
        mTime = (TextView)findViewById(R.id.time);
        mDate = (TextView)findViewById(R.id.date);

        // on ui thread
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Date currDate = new Date();
                progress = currDate.getSeconds();
                mTime.setText(timeFormat.format(currDate));
                mDate.setText(dateFormat.format(currDate));
                mDotCircleProgress.setProgress(progress);
                handler.postDelayed(this, timeInterval);
            }
        };
        handler.post(runnable);
    }
}
