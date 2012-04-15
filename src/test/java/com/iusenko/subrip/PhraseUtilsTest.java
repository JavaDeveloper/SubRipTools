package com.iusenko.subrip;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.iusenko.subrip.Phrase;
import com.iusenko.subrip.PhraseParserException;
import com.iusenko.subrip.PhraseUtils;
import com.iusenko.subrip.SubripParser;

public class PhraseUtilsTest {
	private static final String FILE_NAME = "/pulp.en.srt";
	private String path = this.getClass().getResource(FILE_NAME).toString()
			.replace("file:", "");

	@Test
	public void testFind() throws IOException, PhraseParserException {
		SubripParser parser = new SubripParser(path);
		List<Phrase> phrases = parser.load();

		// 00:00:29,441 = -10770559 search by start time
		Phrase first = PhraseUtils.find(-10770559l, phrases);
		assertNotNull(first);
		assertEquals(1, first.getId());

		// 00:00:33,537 = -10766463 search by stop time
		first = PhraseUtils.find(-10766463l, phrases);
		assertNotNull(first);
		assertEquals(1, first.getId());

		// 02:29:19,112 = -1840888 search by start time
		Phrase last = PhraseUtils.find(-1840888, phrases);
		assertNotNull(last);
		assertEquals(2192, last.getId());

		// 02:29:23,048 = -1836952 search by stop time
		last = PhraseUtils.find(-1836952, phrases);
		assertNotNull(last);
		assertEquals(2192, last.getId());

	}
}
