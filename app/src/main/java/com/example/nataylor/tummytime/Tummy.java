package com.example.nataylor.tummytime;

/**
 * Created by nataylor on 5/2/17.
 */

public class Tummy {
    private String date;
    private String start;
    private Long time;

    public Tummy(){}

    public Tummy(String date, String start, Long time) {
        this.date=date;
        this.start=start;
        this.time=time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getStart() {
        return start;
    }

    public Long getTime() {
        return time;
    }
}
