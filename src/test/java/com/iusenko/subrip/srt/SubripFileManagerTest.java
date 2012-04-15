package com.iusenko.subrip.srt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import static org.junit.Assert.*;

import org.junit.Test;

public class SubripFileManagerTest {

	private static final String FILE_NAME = "/pulp.en.srt";
	private String path = this.getClass().getResource(FILE_NAME).toString()
			.replace("file:", "");

	@Test
	public void testNext() throws FileNotFoundException, IOException,
			PhraseParserException {
		InputStream in = new FileInputStream(path);
		SubripFileManager manager = new SubripFileManager(in);

		Phrase phrase = manager.next();
		assertEquals(1, phrase.getId());
		assertEquals("00:00:29,441", phrase.getStartTimeText());
		assertEquals("00:00:33,537", phrase.getEndTimeText());
		assertEquals(
				"Forget it. It's too risky.\nI'm through doing that shit.",
				phrase.getText());

		phrase = manager.next();
		assertEquals(2, phrase.getId());
		assertEquals("00:00:33,612", phrase.getStartTimeText());
		assertEquals("00:00:37,070", phrase.getEndTimeText());
		assertEquals("You always say that.\nThe same thing every time.",
				phrase.getText());
	}

}
