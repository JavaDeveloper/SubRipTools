package com.iusenko.subrip.srt;

import com.iusenko.subrip.Utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author iusenko
 */
public class SrtUnitBuilder {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("hh:mm:ss,SSS");
    private static final String TIME_DELIM = "-->";
    private String timeline;
    private String id;
    private String text;

    public SrtUnitBuilder timeline(String timeline) {
        this.timeline = timeline;
        return this;
    }

    public SrtUnitBuilder id(String id) {
        this.id = id;
        return this;
    }

    public SrtUnitBuilder text(String text) {
        this.text = text;
        return this;
    }

    public SrtUnit create() throws SrtParserException {
        SrtUnit unit = new SrtUnit();
        unit.setId(getId(id));
        String[] startEnd = splitTimeline(timeline);
        unit.setStartTime(getTimestamp(startEnd[0]));
        unit.setEndTime(getTimestamp(startEnd[1]));
        unit.setText(text);
        return unit;
    }

    protected String[] splitTimeline(String timeline) throws SrtParserException {
        String[] startAndEnd = timeline.split(TIME_DELIM);
        if (startAndEnd.length != 2) {
            throw new SrtParserException("time line has wrong format:" + timeline);
        }
        for (int i = 0; i < startAndEnd.length; i++) {
            startAndEnd[i] = startAndEnd[i].trim();
            if (Utils.isBlank(startAndEnd[i])) {
                throw new SrtParserException("blank " + (i == 0 ? "start" : "end") + " timestamp value");
            }
        }
        return startAndEnd;
    }

    protected long getTimestamp(String timestamp) throws SrtParserException {
        try {
            Date date = DATE_FORMAT.parse(timestamp);
            return date.getTime();
        } catch (ParseException ex) {
            throw new SrtParserException("timestamp string has wrong format:" + timestamp);
        }
    }

    protected int getId(String id) throws SrtParserException {
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new SrtParserException("id string has wrong format(not a number):" + id);
        }
    }
}
