package com.iusenko.subrip.srt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.iusenko.subrip.Utils;

public class SubripFileManager {
	private final BufferedReader in;

	public SubripFileManager(InputStream in) {
		this.in = new BufferedReader(new InputStreamReader(in));
	}

	public Phrase next() throws IOException, PhraseParserException {
		String line;
		int lineIndex = 0;
		PhraseBuilder builder = new PhraseBuilder();

		while ((line = in.readLine()) != null) {
			line = line.trim();

			if (Utils.isBlank(line)) {
				return builder.create();
			}

			if (lineIndex == 0) {
				builder.id(line);
			} else if (lineIndex == 1) {
				builder.timeline(line);
			} else {
				builder.appendTextLine(line);
			}

			lineIndex++;
		}
		return Phrase.END;
	}

}
