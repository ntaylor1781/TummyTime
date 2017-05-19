package com.example.nataylor.tummytime;

/**
 * Created by nataylor on 5/3/17.
 */

public class Stopwatch {
    private String hour;
    private String minute;
    private String second;
    private String startTime;
    private String elapsedTime;

    public Stopwatch(){}

    public Stopwatch(String hour, String minute, String second, String startTime,
                     String elapsedTime) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.startTime = startTime;
        this.elapsedTime = elapsedTime;
    }

    public void setHour(String hour) {this.hour = hour;}

    public void setMinute(String minute) {this.minute = minute;}

    public void setSecond(String second) {this.second = second;}

    public void setStartTime(String startTime) {this.startTime = startTime;}

    public void setElapsedTime(String elapsedTime) {this.elapsedTime = elapsedTime;}

    public String getHour() {return hour;}

    public String getMinute() {return minute;}

    public String getSecond() {return second;}

    public String getStartTime() {return startTime;}

    public String getElapsedTime() {return elapsedTime;}
}
