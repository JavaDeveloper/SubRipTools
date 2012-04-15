package com.iusenko.subrip;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.iusenko.subrip.old.Utils;

public class SubripParser {
	private final BufferedReader in;
	private List<Phrase> phrases;

	public SubripParser(InputStream in) {
		this.in = new BufferedReader(new InputStreamReader(in));
	}

	public SubripParser(String path) throws FileNotFoundException {
		this(new FileInputStream(path));
	}

	protected Phrase next() throws IOException, PhraseParserException {
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
		return null;
	}

	public List<Phrase> load() throws IOException, PhraseParserException {
		phrases = new ArrayList<Phrase>();
		Phrase phrase = null;
		while ((phrase = next()) != null) {
			phrases.add(phrase);
		}
		return phrases;
	}
}
