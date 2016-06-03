package com.xyz.coolweather.fastDevAndroid;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class fastDevIo {
    private static final int EOF = -1;
    public static byte[] InputStreamReadAll(final InputStream input) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }
    public static byte[] InputStreamReadAtEndSize(final InputStream input,int size) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        byte[] out =  output.toByteArray();
        if (out.length>size){
            return fastDevBytes.Slice(out, out.length - size, out.length);
        }
        return out;

        //int currentBufIndex = 1;
        /*
        byte[] buf1 = new byte[size];
        byte[] buf2 = new byte[8192];
        int n = InputStreamReadAllToBuf(input,buf1);
        if (n==EOF){
            return buf1;
        }
        InputStreamReadAllToNull(input,buf2);
        return buf1;
        */
    }
    public static int InputStreamReadAllToBuf(final InputStream input,byte[] buffer) throws IOException {
        int pos = 0;
        int n = 0;
        while (true) {
            if (pos>=buffer.length){
                return pos;
            }
            n = input.read(buffer,pos,buffer.length-pos);
            if (n==EOF){
                return EOF;
            }
            pos+=n;
        }
    }
    public static int InputStreamReadAllToNull(final InputStream input,byte[] tmpbuf) throws IOException {
        int n = 0;
        while (true) {
            n = input.read(tmpbuf);
            if (n==EOF){
                return EOF;
            }
        }
    }
    public static int copy(final InputStream input, final OutputStream output) throws IOException {
        final long count = copyLarge(input, output,new byte[8192]);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }
    public static long copyLarge(final InputStream input, final OutputStream output, final byte[] buffer)
            throws IOException {
        long count = 0;
        int n = 0;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
}
