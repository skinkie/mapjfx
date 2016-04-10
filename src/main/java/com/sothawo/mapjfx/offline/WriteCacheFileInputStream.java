/**
 * Copyright (c) 2016 sothawo
 *
 * http://www.sothawo.com
 */
package com.sothawo.mapjfx.offline;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

/**
 * FilterInputStream that dumps all data passed through it to a cache file before passing the data on.
 *
 * todo: implement cache writing.
 *
 * @author P.J. Meisch (pj.meisch@sothawo.com).
 */
public class WriteCacheFileInputStream extends FilterInputStream {

    private static final Logger logger = Logger.getLogger(WriteCacheFileInputStream.class.getCanonicalName());

    /** the output stream where th data is stored. */
    private final OutputStream out;

    /**
     * Creates a <code>FilterInputStream</code> by assigning the  argument <code>in</code> to the field
     * <code>this.in</code> so as to remember it for later use.
     *
     * @param in
     *         the underlying input stream, or <code>null</code> if this instance is to be created without an underlying
     *         stream.
     * @param out
     *         the stream where the data read from in should be written to.
     */
    protected WriteCacheFileInputStream(InputStream in, OutputStream out) {
        super(in);
        this.out = out;
    }

    @Override
    public void close() throws IOException {
        logger.finer("closing stream");
        super.close();
        if (null != out) {
            out.flush();
            out.close();
        }
    }

    @Override
    public int read() throws IOException {
        final int i = super.read();
        if (null != out) {
            out.write(i);
        }
        return i;
    }

    @Override
    public int read(byte[] b) throws IOException {
        final int numBytes = super.read(b);
        if (null != out) {
            out.write(b, 0, numBytes);
        }
        return numBytes;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        final int numBytes = super.read(b, off, len);
        if (null != out) {
            out.write(b, off, numBytes);
        }
        return numBytes;
    }
}