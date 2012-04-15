package com.iusenko.subrip.old;

import java.io.Serializable;

public class Phrase implements Serializable {

    private int number;
    private String text;
    private String fromTime;
    private String toTime;

    public Phrase(String text) {
        this.text = text;
        this.fromTime = "00:00:00.00";
        this.toTime = "00:00:00.00";
    }

    public Phrase() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("number=").append(number);
        sb.append(", time=").append(fromTime).append(":").append(toTime);
        sb.append(", text=").append(text);
        return sb.toString();
    }
}
