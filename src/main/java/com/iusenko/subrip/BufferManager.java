package com.iusenko.subrip;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author iusenko
 */
public class BufferManager {

    private static final int BUFFER_SIZE = 1024;
    private RandomAccessFile file;
    private String path;
    private long position = 0;
    private byte[] buffer = new byte[BUFFER_SIZE];

    BufferManager(String path, long position) throws FileNotFoundException, IOException {
        if (position < 0) {
            throw new IllegalArgumentException("File: " + path + ", initial position is negative");
        }
        this.path = path;
        this.file = new RandomAccessFile(path, "r");

        this.position = position;
        if (position < this.file.length()) {
            this.file.seek(this.position);
        } else {
            
        }
    }

    BufferManager(String path) throws FileNotFoundException, IOException {
        this(path, 0);
    }

    List<Phrase> getNext() throws Exception {
        if (position < this.file.length()) {

            file.seek(position);
            int read = readIntoBuffer();
            if (read == 0) {
                
            } else {
                position += read;

                return parse(buffer, read);
            }
        }
        return Collections.EMPTY_LIST;
    }

    List<Phrase> getPrev() throws Exception {
        if (position > 0) {
            position = (position - BUFFER_SIZE) > 0 ? (position - BUFFER_SIZE) : 0;
            if (position == 0) {
                // throw event : start reached                
            }

            file.seek(position);
            int read = readIntoBuffer();
            if (read == 0) {
            } else {
                position += read;                
                return parse(buffer, read);
            }
        } else {
        }
        return Collections.EMPTY_LIST;
    }

    protected int readIntoBuffer() throws IOException {
        int read = file.read(buffer);
        if (read == -1) {
            // throw event            

            return 0;
        }
        return read;
    }

    protected List<Phrase> parse(byte[] buffer, int length) throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream(buffer, 0, length);
        SubRipParser subParser = new SubRipParser(in);
        List<Phrase> phrases;
        try {
            phrases = subParser.getPhrases();
        } catch (Exception ex) {
            throw ex;
        }
        return phrases;
    }

    public void close() {
        try {
            file.close();
        } catch (IOException ex) {
            // ignore it
        }
    }

    public String getPath() {
        return path;
    }
}
