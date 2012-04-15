package com.iusenko.subrip;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.iusenko.subrip.Phrase;
import com.iusenko.subrip.PhraseParserException;
import com.iusenko.subrip.SubripParser;

public class SubripParserTest {

	private static final String FILE_NAME = "/pulp.en.srt";
	private String path = this.getClass().getResource(FILE_NAME).toString()
			.replace("file:", "");

	@Test
	public void testNext() throws FileNotFoundException, IOException,
			PhraseParserException {
		SubripParser parser = new SubripParser(path);

		Phrase phrase = parser.next();
		assertEquals(1, phrase.getId());
		assertEquals("00:00:29,441", phrase.getStartTimeText());
		assertEquals("00:00:33,537", phrase.getEndTimeText());
		assertEquals(
				"Forget it. It's too risky.\nI'm through doing that shit.",
				phrase.getText());

		phrase = parser.next();
		assertEquals(2, phrase.getId());
		assertEquals("00:00:33,612", phrase.getStartTimeText());
		assertEquals("00:00:37,070", phrase.getEndTimeText());
		assertEquals("You always say that.\nThe same thing every time.",
				phrase.getText());
	}

	@Test
	public void testLoad() throws Exception {
		SubripParser parser = new SubripParser(path);
		List<Phrase> phrases = parser.load();

		assertNotNull(phrases);
		assertEquals(2192, phrases.size());
		Phrase first = phrases.get(0);
		assertEquals(1, first.getId());
		assertEquals("00:00:29,441", first.getStartTimeText());
		assertEquals("00:00:33,537", first.getEndTimeText());
		assertEquals(
				"Forget it. It's too risky.\nI'm through doing that shit.",
				first.getText());
		System.out.println("00:00:29,441 = " + first.getStartTime());
		System.out.println("00:00:33,537 = " + first.getEndTime());

		Phrase last = phrases.get(2191);
		assertEquals(2192, last.getId());
		assertEquals("02:29:19,112", last.getStartTimeText());
		assertEquals("02:29:23,048", last.getEndTimeText());
		assertEquals("[ Music Continues ]", last.getText());
		System.out.println("02:29:19,112 = " + last.getStartTime());
		System.out.println("02:29:23,048 = " + last.getEndTime());
	}

}
