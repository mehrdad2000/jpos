package org.jpos.iso.channel;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.ISOException;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FixedHeaderASCIIChannel
 * - Outgoing: 4-digit ASCII length + "0000" + packed ISO message
 * - Incoming: 4-digit ASCII length + fixed response header + packed ISO message
 * - Validates the exact inbound header
 * - Logs ISO fields after receive
 */
public class FixedHeaderASCIIChannel extends ASCIIChannel {

    private static final byte[] REQ_HEADER =
            "0000".getBytes(StandardCharsets.US_ASCII);

    private static final byte[] RESP_HEADER =
            "0000000000000000000000000000000000000000000000000000000000000"
                    .getBytes(StandardCharsets.US_ASCII);

    private static final Logger logger = LoggerFactory.getLogger(FixedHeaderASCIIChannel.class);

    public FixedHeaderASCIIChannel() {
        super();
    }

    public FixedHeaderASCIIChannel(ISOPackager p) throws IOException {
        super(p);
    }

    public FixedHeaderASCIIChannel(String host, int port, ISOPackager p) throws IOException {
        super(host, port, p);
    }

    // Outbound length must include the fixed request header
    @Override
    protected int getHeaderLength(ISOMsg m) {
        return REQ_HEADER.length;
    }

    // Inbound: tell BaseChannel how many header bytes to read
    @Override
    protected int getHeaderLength() {
        return RESP_HEADER.length;
    }

    // Write the exact request header
    @Override
    protected void sendMessageHeader(ISOMsg m, int len) throws IOException {
        serverOut.write(REQ_HEADER);
    }

    // Read and enforce the exact response header
    @Override
    protected byte[] readHeader(int hLen) throws IOException {
        if (hLen != RESP_HEADER.length) {
            throw new EOFException("Unexpected header length " + hLen +
                    ", expected " + RESP_HEADER.length);
        }
        byte[] h = new byte[hLen];
        DataInputStream in = this.serverIn;
        in.readFully(h, 0, hLen);

        if (!Arrays.equals(h, RESP_HEADER)) {
            String got = new String(h, StandardCharsets.US_ASCII);
            throw new IOException("Unexpected response header: '" + got + "'");
        }
        return h;
    }

    // Log ISO fields after receive
    @Override
    public ISOMsg receive() throws IOException, ISOException {
        ISOMsg m = super.receive();
        logISOMsgFields(m);
        return m;
    }

    private void logISOMsgFields(ISOMsg m) {
        logger.info("--- ISO8583 Fields ---");
        for (int i = 0; i <= m.getMaxField(); i++) {
            if (m.hasField(i)) {
                String value = m.getString(i);
                logger.info(String.format("Field %d: %s", i, value));
            }
        }
        logger.info("--- End of Fields ---");
    }
}
