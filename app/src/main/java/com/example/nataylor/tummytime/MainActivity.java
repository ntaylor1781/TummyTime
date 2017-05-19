package com.example.nataylor.tummytime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.btnCalendar)
    Button btnCalendar;

    @ViewById(R.id.btnTimer)
    Button btnTimer;

    @ViewById(R.id.btnStart)
    Button btnStart;

    @AfterViews
    void afterViews() {
        this.btnTimer.setTag("start");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper db = dbHelper.newInstance(this);
    }

    @Click(R.id.btnCalendar)
    void showCalendar() {
        Intent intent = new Intent(this, Calendar_.class);
        startActivity(intent);
    }

    @Click(R.id.btnTimer)
    public void startTimer(View v) {
        final String status =(String) v.getTag();
        if (status == "start") {
            this.btnTimer.setText("Stop Timer");
            v.setTag("stop");
        } else {
            this.btnTimer.setText("Start Timer");
            v.setTag("start");
        }
    }

    @Click(R.id.btnStart)
    void showTimer(){
        Intent intent = new Intent(this, Timer_.class);
        startActivity(intent);
    }
}
