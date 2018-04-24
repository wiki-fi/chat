package com.fomina;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class SenderTest {

    @Test
    public void main() throws IOException {
            String[] args = new String[0];
            if (args.length > 0 && (args[0].compareToIgnoreCase("test") == 0)) {
            final PipedInputStream inputStream = new PipedInputStream();
            PipedOutputStream toTestedClass = new PipedOutputStream(inputStream);

            final PipedOutputStream outputStream = new PipedOutputStream();
            PipedInputStream fromTestedClass = new PipedInputStream(outputStream);

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            Thread testRunner = new Thread(); //

//            getRequestHeaders(br, "GET / HTTP/1.1");

            testRunner.start();
            BufferedWriter toTestedClassBufferedWritter = new BufferedWriter(
                    new OutputStreamWriter(toTestedClass)
            );
            toTestedClassBufferedWritter.newLine();
            toTestedClassBufferedWritter.flush();
            BufferedReader fromTestedClassBufferedReader = new BufferedReader(
                    new InputStreamReader(fromTestedClass)
            );
            String line;
            while((line = fromTestedClassBufferedReader.readLine()).compareTo(" ") != 0){
        //        response.add(line);
            }
        }
    }
}