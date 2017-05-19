package com.example.nataylor.tummytime;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by nataylor on 5/3/17.
 */

@EActivity(R.layout.timer)
public class Timer extends AppCompatActivity {

    private long startTime;
    private long elapsedTime;
    private final int REFRESH_RATE = 1000;
    private boolean stopped = false;
    private Handler mHandler = new Handler();
    private Handler vHandler = new Handler();
    private SimpleExoPlayer player;
    private TrackSelection.Factory trackSelectionFactory;
    private TrackSelector tSelector;
    private DataSource.Factory mDataSourceFactory;
    private BandwidthMeter bMeter = new DefaultBandwidthMeter();
    private Context context = this;
    import com.google.android.exoplayer2.util.Util exUtil;

    Calendar now = Calendar.getInstance();

    @ViewById(R.id.txtTimer)
    TextView txtTimer;

    @ViewById(R.id.btnStart)
    Button btnStart;

    @ViewById(R.id.btnStop)
    Button btnStop;

    @ViewById(R.id.btnDone)
    Button btnDone;

    @ViewById(R.id.pView)
    SimpleExoPlayerView pView;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        // Setup exoplayer
        trackSelectionFactory = new AdaptiveTrackSelection.Factory(bMeter);
        tSelector = new DefaultTrackSelector(trackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(context, tSelector);
    }

    @AfterViews
    void preparePlayer() {

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                exUtil.getUserAgent(context, "TummyTime"), bMeter);
    }


    @Click(R.id.btnStart)
    public void startClick() {
        if(stopped){
            startTime = System.currentTimeMillis() - elapsedTime;
        }
        else{
            startTime = System.currentTimeMillis();

        }

        //btnStart.setVisibility(View.INVISIBLE);
        //btnDone.setVisibility(View.INVISIBLE);
        //txtTimer.setVisibility(View.INVISIBLE);
        mHandler.removeCallbacks(startTimer);
        mHandler.postDelayed(startTimer, 0);
    }

    @Click(R.id.btnStop)
    public void stopClick() {
        mHandler.removeCallbacks(startTimer);
        stopped = true;
    }

    @Click(R.id.btnDone)
    public void doneClick() {
        String convStartTime = Util.convertStartTimeToString(startTime);
        Log.d("StartTime", convStartTime);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String date = sdf.format(now.getTime());

        dbHelper db = dbHelper.newInstance(this);

        db.addTime(new Tummy(date, convStartTime, elapsedTime));

        stopped = false;
        txtTimer.setText("00:00");
    }

    private Runnable startTimer = new Runnable() {
        @Override
        public void run() {
            elapsedTime = System.currentTimeMillis() - startTime;
            updateTimer(elapsedTime);
            mHandler.postDelayed(this,REFRESH_RATE);
        }
    };

    private void updateTimer (float time) {
        String convertedTime = Util.convertTimeToString(time);
        txtTimer.setText(convertedTime);
    }
}
