package com.iusenko.subrip;

import java.util.List;

public class PhraseUtils {

	public static Phrase find(long timestamp, List<Phrase> phrases) {
		for (Phrase phrase : phrases) {
			if (phrase.getStartTime() <= timestamp
					&& timestamp <= phrase.getEndTime()) {
				return phrase;
			}
		}
		return null;
	}

	public static Phrase find(long start, long end, List<Phrase> phrases) {
		StringBuilder sb = new StringBuilder();
		int num = 0;
		Phrase phrase = new Phrase();
		for (Phrase p : phrases) {
			boolean a = p.startTime <= start && start < p.endTime;
			boolean b = start <= p.startTime && p.endTime <= end;
			// boolean c = phrase.startTime < end && end <= phrase.endTime;

			if (b || a) {
				phrase.endTime = p.endTime;
				phrase.endTimeText = p.endTimeText;

				if (num == 0) {
					phrase.startTime = p.startTime;
					phrase.startTimeText = p.startTimeText;
					num++;
				}

				sb.append(p.text).append("\n\n");
			}
		}
		phrase.text = sb.toString().trim();

		return phrase;
	}

}
