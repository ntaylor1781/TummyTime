package com.example.nataylor.tummytime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by nataylor on 5/4/17.
 */

@EActivity(R.layout.date_view)
public class DateView extends AppCompatActivity {
    private int year;
    private int month;
    private int date;
    private String startDate;
    private Long totalTime;

    @ViewById(R.id.tableView)
    TableLayout tableView;

    @ViewById(android.R.id.empty)
    TextView empty;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        year = getIntent().getIntExtra("YEAR", 0000);
        month = getIntent().getIntExtra("MONTH", 00);
        date = getIntent().getIntExtra("DATE", 00);

        Calendar c = Calendar.getInstance();
        c.set(year, month, date, 0, 0);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        startDate = sdf.format(c.getTime());
    }

    @AfterViews
    void update() {
        setTitle(startDate);

        dbHelper db = dbHelper.newInstance(this);
        List<Tummy> tummyList = db.getTime(startDate);

        if (tummyList.isEmpty()) {
            empty.setText("There has been no tummy time on this day.");
            empty.setVisibility(View.VISIBLE);
            tableView.setVisibility(View.INVISIBLE);
        } else {
            int count = tummyList.size();
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            lp.setMargins(10, 2, 10, 2);
            TableRow head = new TableRow(this);
            head.setPadding(10, 2, 10, 0);
            head.setLayoutParams(lp);
            head.setBackground(getResources().getDrawable(R.drawable.border));
            TextView headStart = new TextView(this);
            headStart.setText("Start Time");
            headStart.setBackground(getResources().getDrawable(R.drawable.border));
            head.addView(headStart);
            TextView headElapsed = new TextView(this);
            headElapsed.setText("Total Time");
            headElapsed.setBackground(getResources().getDrawable(R.drawable.border));
            head.addView(headElapsed);
            tableView.addView(head);

            for (int i=0; i < count; i++) {
                TableRow row = new TableRow(this);
                row.setPadding(10, 2, 10, 0);
                row.setLayoutParams(lp);
                row.setBackground(getResources().getDrawable(R.drawable.border));
                TextView startTime = new TextView(this);
                TextView elapsedTime = new TextView(this);
                startTime.setText(tummyList.get(i).getStart());
                startTime.setBackground(getResources().getDrawable(R.drawable.border));
                Long holderTime = tummyList.get(i).getTime();
                String convElapsedTime = Util.convertTimeToString(holderTime);
                elapsedTime.setText(convElapsedTime);
                elapsedTime.setBackground(getResources().getDrawable(R.drawable.border));
                row.addView(startTime);
                row.addView(elapsedTime);
                tableView.addView(row);
                if (totalTime == null) {
                    totalTime = holderTime;
                } else {
                    totalTime = totalTime + holderTime;
                }
            }

            TableRow total = new TableRow(this);
            total.setPadding(10, 2, 10, 2);
            total.setLayoutParams(lp);
            total.setBackground(getResources().getDrawable(R.drawable.border));
            TextView totalStart = new TextView(this);
            totalStart.setText("Total time for the day");
            totalStart.setBackground(getResources().getDrawable(R.drawable.border));
            total.addView(totalStart);
            TextView totalElapsed = new TextView(this);
            String convTotal = Util.convertTimeToString(totalTime);
            totalElapsed.setText(convTotal);
            totalElapsed.setBackground(getResources().getDrawable(R.drawable.border));
            total.addView(totalElapsed);
            tableView.addView(total);
        }
    }
}
