package com.iusenko.subrip.srt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 * 
 * @author iusenko
 */
public class PhraseBuilderTest {

	private PhraseBuilder parser = new PhraseBuilder();

	@Test
	public void testGetTimestamp() throws Exception {
		long l1 = parser.getTimestamp("00:00:20,000");
		long l2 = parser.getTimestamp("00:00:20,010");
		System.out.println("l1=" + l1 + ", l2=" + l2 + ", l2-l1=" + (l2 - l1));
		assertEquals(10, l2 - l1);

		l1 = parser.getTimestamp("1:2:2,00");
		l2 = parser.getTimestamp("1:2:2,10");
		System.out.println("l1=" + l1 + ", l2=" + l2 + ", l2-l1=" + (l2 - l1));
		assertEquals(10, l2 - l1);
	}

	@Test(expected = PhraseParserException.class)
	public void testGetTimestampError() throws Exception {
		parser.getTimestamp("00:0020,000");
	}

	@Test
	public void testGetId() throws PhraseParserException {
		assertEquals(10, parser.getId("10"));
		assertEquals(24, parser.getId("24"));
		assertEquals(31, parser.getId("31"));
	}

	@Test
	public void testGetIdError() throws PhraseParserException {
		String[] vals = { "a", ",", "0x1" };
		for (String val : vals) {
			try {
				parser.getId(val);
				fail("should never reach this state");
			} catch (Exception e) {
			}
		}
	}

	@Test
	public void testSplitTimeline() throws PhraseParserException {
		String[] startEnd = parser
				.splitTimeline("00:02:31,567 --> 00:02:37,164 ");
		assertEquals("00:02:31,567", startEnd[0]);
		assertEquals("00:02:37,164", startEnd[1]);
	}

	@Test(expected = PhraseParserException.class)
	public void testSplitTimelineErrorStart() throws PhraseParserException {
		parser.splitTimeline(" --> 00:02:37,164 ");
	}

	@Test(expected = PhraseParserException.class)
	public void testSplitTimelineErrorEnd() throws PhraseParserException {
		parser.splitTimeline("00:02:31,567 --> ");
	}
}
