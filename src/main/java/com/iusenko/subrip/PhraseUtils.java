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
}
