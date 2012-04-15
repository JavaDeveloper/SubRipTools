package com.iusenko.subrip;

/**
 * 
 * @author iusenko
 */
public class Phrase {
	public static final Phrase END = new Phrase();

	private int id;
	private long startTime;
	private long endTime;
	private String startTimeText;
	private String endTimeText;
	private String text;

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getStartTimeText() {
		return startTimeText;
	}

	public void setStartTimeText(String startTimeText) {
		this.startTimeText = startTimeText;
	}

	public String getEndTimeText() {
		return endTimeText;
	}

	public void setEndTimeText(String endTimeText) {
		this.endTimeText = endTimeText;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(id);
		sb.append(" ");
		sb.append(startTimeText).append("(").append(startTime).append(")");
		sb.append(" --> ");
		sb.append(endTimeText).append("(").append(endTime).append(")");
		sb.append(" ");
		sb.append(text);
		return sb.toString();
	}
}
