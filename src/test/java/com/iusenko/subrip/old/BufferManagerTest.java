package com.iusenko.subrip.old;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Test;

import com.iusenko.subrip.old.BufferManager;

import static org.junit.Assert.*;

/**
 *
 * @author iusenko
 */
public class BufferManagerTest {

    private static final String FILE_NAME = "/pulp.en.srt";
    private String path = this.getClass().getResource(FILE_NAME).toString().replace("file:", "");

    @Test(expected = FileNotFoundException.class)
    public void testWrongPath() throws FileNotFoundException, IOException {
        new BufferManager("/abcd");
    }

    @Test(expected = IOException.class)
    public void testWrongSeek_Overflow() throws FileNotFoundException, IOException {
        new BufferManager(path, Long.MAX_VALUE);
    }

    @Test(expected = IOException.class)
    public void testWrongSeek_Negative() throws FileNotFoundException, IOException {
        new BufferManager(path, -1);
    }

    @Test
    public void testBufferManager() throws FileNotFoundException, IOException {
        BufferManager bm = new BufferManager(path);
        bm.close();
        assertTrue("Open and close buffer manager without exceptions", true);

        bm = new BufferManager(path, 5l);
        bm.close();
        assertTrue("Open(seek) and close buffer manager without exceptions", true);
    }

    @Test
    public void testUpdateWithNext() throws FileNotFoundException, IOException {
        BufferManager bm = new BufferManager(path);

        int read = bm.updateWithNext();
        assertEquals(BufferManager.BUFFER_SIZE, read);
        assertEquals(bm.getBuffer()[0], 0x31);
        assertEquals(bm.getBuffer()[BufferManager.BUFFER_SIZE - 1], 0x38);

        read = bm.updateWithNext();
        assertEquals(BufferManager.BUFFER_SIZE, read);
        assertEquals(bm.getBuffer()[0], 0xd);
        assertEquals(bm.getBuffer()[BufferManager.BUFFER_SIZE - 1], 0x6e);

        read = bm.updateWithNext();
        assertEquals(BufferManager.BUFFER_SIZE, read);
        assertEquals(bm.getBuffer()[0], 0x61);
        assertEquals(bm.getBuffer()[BufferManager.BUFFER_SIZE - 1], 0xd);

        bm.close();
    }

    @Test
    public void testUpdateWithPrev() throws FileNotFoundException, IOException {
        byte[] firstByte = new byte[2];
        byte[] lastByte = new byte[2];

        BufferManager bm = new BufferManager(path);

        bm.updateWithNext();
        firstByte[0] = bm.getBuffer()[0];
        lastByte[0] = bm.getBuffer()[BufferManager.BUFFER_SIZE - 1];

        bm.updateWithNext();
        firstByte[1] = bm.getBuffer()[0];
        lastByte[1] = bm.getBuffer()[BufferManager.BUFFER_SIZE - 1];

        bm.updateWithNext();


        bm.updateWithPrev();
        assertEquals(bm.getBuffer()[0], firstByte[1]);
        assertEquals(bm.getBuffer()[BufferManager.BUFFER_SIZE - 1], lastByte[1]);

        bm.updateWithPrev();
        assertEquals(bm.getBuffer()[0], firstByte[0]);
        assertEquals(bm.getBuffer()[BufferManager.BUFFER_SIZE - 1], lastByte[0]);

        // get 'prev' from start will return first buffer
        bm.updateWithPrev();
        assertEquals(bm.getBuffer()[0], firstByte[0]);
        assertEquals(bm.getBuffer()[BufferManager.BUFFER_SIZE - 1], lastByte[0]);

        bm.close();
    }
}
