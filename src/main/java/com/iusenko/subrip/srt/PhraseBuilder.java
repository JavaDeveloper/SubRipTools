package com.iusenko.subrip.srt;

import com.iusenko.subrip.Utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author iusenko
 */
public class PhraseBuilder {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("hh:mm:ss,SSS");
    private static final String TIME_DELIM = "-->";
    private String timeline;
    private String id;
    private String text;

    public PhraseBuilder timeline(String timeline) {
        this.timeline = timeline;
        return this;
    }

    public PhraseBuilder id(String id) {
        this.id = id;
        return this;
    }

    public PhraseBuilder text(String text) {
        this.text = text;
        return this;
    }

    public Phrase create() throws PhraseParserException {
        Phrase unit = new Phrase();
        unit.setId(getId(id));
        String[] startEnd = splitTimeline(timeline);
        unit.setStartTime(getTimestamp(startEnd[0]));
        unit.setEndTime(getTimestamp(startEnd[1]));
        unit.setText(text);
        return unit;
    }

    protected String[] splitTimeline(String timeline) throws PhraseParserException {
        String[] startAndEnd = timeline.split(TIME_DELIM);
        if (startAndEnd.length != 2) {
            throw new PhraseParserException("time line has wrong format:" + timeline);
        }
        for (int i = 0; i < startAndEnd.length; i++) {
            startAndEnd[i] = startAndEnd[i].trim();
            if (Utils.isBlank(startAndEnd[i])) {
                throw new PhraseParserException("blank " + (i == 0 ? "start" : "end") + " timestamp value");
            }
        }
        return startAndEnd;
    }

    protected long getTimestamp(String timestamp) throws PhraseParserException {
        try {
            Date date = DATE_FORMAT.parse(timestamp);
            return date.getTime();
        } catch (ParseException ex) {
            throw new PhraseParserException("timestamp string has wrong format:" + timestamp);
        }
    }

    protected int getId(String id) throws PhraseParserException {
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new PhraseParserException("id string has wrong format(not a number):" + id);
        }
    }
}
