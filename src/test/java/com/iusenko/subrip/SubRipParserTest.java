package com.iusenko.subrip;

import java.io.ByteArrayInputStream;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author iusneko
 */
public class SubRipParserTest {

    @Test
    public void testIsSentence() {
        SubRipParser p = new SubRipParser();
        assertFalse(p.isSentence("00:00:07,700 --> 00:00:09,100"));
        assertFalse(p.isSentence("12"));
        assertTrue(p.isSentence("Hello world!"));
    }

    @Test
    public void testIsTime() {
        SubRipParser p = new SubRipParser();
        assertTrue(p.isTime("00:00:07,700 --> 00:00:09,100"));
        assertFalse(p.isTime("00:00:03,600 -> 00:00:07,630"));
        assertFalse(p.isTime("00:00:03,600 -> 00:00:07"));
        assertFalse(p.isTime(""));
    }

    @Test
    public void testIsPhraseNumber() {
        SubRipParser p = new SubRipParser();
        assertTrue(p.isPhraseNumber("100"));
        assertFalse(p.isPhraseNumber(""));
    }

    @Test
    public void testGetStartTime() {
        SubRipParser p = new SubRipParser();
        assertEquals("00:00:07.700", p.getStartTime("00:00:07,700 --> 00:00:09,100"));
    }

    @Test
    public void testGetEndTime() {
        SubRipParser p = new SubRipParser();
        assertEquals("00:00:09.100", p.getEndTime("00:00:07,700 --> 00:00:09,100"));
    }

    @Test
    public void testGetPhrases() throws Exception {
        String text = "too dangerous.\" \n"
                + "\n"
                + "4\n"
                + "00:00:39,151 --> 00:00:41,642 \n"
                + "I know that's what I always say. \n"
                + "I'm always right too. \n"
                + "\n"
                + "5\n"
                + "00:00:41,720 --> 00:00:45,121\n"
                + "- You forget about it in a day or two.\n"
                + "- The days of me forgetting are over.\n"
                + "\n"
                + "6\n"
                + "00:00:45,190 --> 00:00:47,283\n"
                + "The days of me remembering  \"\n"
                + "\n";
        ByteArrayInputStream in = new ByteArrayInputStream(text.getBytes());
        SubRipParser p = new SubRipParser(in);
        List<Phrase> phrases = p.getPhrases();
        assertNotNull(phrases);
        assertSame(3, phrases.size());
        Phrase ph1 = phrases.get(0);
        assertEquals("00:00:39.151", ph1.getFromTime());
        assertEquals("00:00:41.642", ph1.getToTime());
        assertSame(4, ph1.getNumber());
        
        Phrase ph3 = phrases.get(2);
        assertEquals("00:00:45.190", ph3.getFromTime());
        assertEquals("00:00:47.283", ph3.getToTime());
        assertSame(6, ph3.getNumber());
        assertEquals("The days of me remembering  \"", ph3.getText());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateSubRipParser() {
        new SubRipParser(null);
    }
}
