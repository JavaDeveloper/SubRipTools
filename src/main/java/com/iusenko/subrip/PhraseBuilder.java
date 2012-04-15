package com.iusenko.subrip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.iusenko.subrip.old.Utils;

/**
 * 
 * @author iusenko
 */
public class PhraseBuilder {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"hh:mm:ss,SSS");
	private static final String TIME_DELIM = "-->";
	private String timeline;
	private String id;
	private StringBuilder text = new StringBuilder();

	public PhraseBuilder timeline(String timeline) {
		this.timeline = timeline;
		return this;
	}

	public PhraseBuilder id(String id) {
		this.id = id;
		return this;
	}

	public PhraseBuilder appendTextLine(String text) {
		this.text.append(text).append("\n");
		return this;
	}

	public Phrase create() throws PhraseParserException {
		Phrase phrase = new Phrase();
		phrase.setId(getId(id));
		String[] startEnd = splitTimeline(timeline);
		phrase.setStartTimeText(startEnd[0]);
		phrase.setEndTimeText(startEnd[1]);
		phrase.setStartTime(getTimestamp(startEnd[0]));
		phrase.setEndTime(getTimestamp(startEnd[1]));
		phrase.setText(text.toString().trim());
		return phrase;
	}

	protected String[] splitTimeline(String timeline)
			throws PhraseParserException {
		String[] startAndEnd = timeline.split(TIME_DELIM);
		if (startAndEnd.length != 2) {
			throw new PhraseParserException("time line has wrong format:"
					+ timeline);
		}
		for (int i = 0; i < startAndEnd.length; i++) {
			startAndEnd[i] = startAndEnd[i].trim();
			if (Utils.isBlank(startAndEnd[i])) {
				throw new PhraseParserException("blank "
						+ (i == 0 ? "start" : "end") + " timestamp value");
			}
		}
		return startAndEnd;
	}

	protected long getTimestamp(String timestamp) throws PhraseParserException {
		try {
			Date date = DATE_FORMAT.parse(timestamp);
			return date.getTime();
		} catch (ParseException ex) {
			throw new PhraseParserException(
					"timestamp string has wrong format:" + timestamp);
		}
	}

	protected int getId(String id) throws PhraseParserException {
		try {
			return Integer.parseInt(id);
		} catch (NumberFormatException e) {
			throw new PhraseParserException(
					"id string has wrong format(not a number):" + id);
		}
	}
}
