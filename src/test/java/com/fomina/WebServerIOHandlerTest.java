package  com.fomina;

import java.io.*;
import java.net.Socket;

/**
 * Created by Vika on 27.03.2018.
 */
public class WebServerIOHandlerTest {
    public static void main(String[] args) throws Exception{
        final PipedInputStream inputStream = new PipedInputStream();
        PipedOutputStream toTestedClass = new PipedOutputStream(inputStream);

        final PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream fromTestedClass = new PipedInputStream(outputStream);


        /*WebServerIOHandler testedClass = new WebServerIOHandler(new Socket() {

            public OutputStream getOutputStream() {
                return outputStream;
            }

            public InputStream getInputStream() {
                return inputStream;
            }

            public void close() {
                System.out.println("call close()");
            }
        });
        testedClass.start();
        System.out.println("Init completed, lets go tests!");
        testIf404Returned(testedClass, fromTestedClass, toTestedClass);*/
    }

    public static void testIf404Returned (
           //WebServerIOHandler testedClass,
            InputStream fromTestedClass,
            OutputStream toTestedClass) throws Exception {

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(toTestedClass));
        BufferedReader br = new BufferedReader(new InputStreamReader(fromTestedClass));
        String request = "GET /notExistedFile HTTP/1.1";
        System.out.println("Send http request to class \"" + request + "\"");
        bw.write(request);
        bw.newLine();
        bw.newLine();
        bw.flush();
        System.out.println("Try to get response");
        String response = br.readLine();
        assert response.compareToIgnoreCase("HTTP/1.1 404 Not Found") == 0;
    }
}

//написать unit тесты для своей программы (чата):
//1) на запрос файла корректно отдается содержимое файла (html, css и т.д)
//2) на запрос на несуществующий ресурс отдается правильная ошибка (404)
