package  com.fomina;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Scanner;

import static java.net.URLDecoder.decode;

/**
 * Created by Vika on 13.03.2018.
 */
public class Sender {
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length > 0 && (args[0].compareToIgnoreCase("test") == 0)) {
            final PipedInputStream inputStream = new PipedInputStream();
            PipedOutputStream toTestedClass = new PipedOutputStream(inputStream);

            final PipedOutputStream outputStream = new PipedOutputStream();
            PipedInputStream fromTestedClass = new PipedInputStream(outputStream);

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            Thread testRunner = new Thread(); //

            requestHeder(br, "GET / HTTP/1.1"
            );

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


            ServerSocket serverSocket = new ServerSocket(8888);
            while (true) {
                Socket socket = serverSocket.accept(); // при команде accept()готов слушать все что происходит на этом порту
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String nextLine = br.readLine();
                HashMap<String, String> requestHeders = requestHeder(br, nextLine);


                if (requestHeders.get("method").equals("POST")) { //тут просмотр ещё и body, т.к. формы приходят именно туда
                    String form = getFormText(br, Integer.parseInt(requestHeders.get("Content-Length")));
                    System.out.println("POST");
                    //изменение файла

                    String response = String.join("\n", Files.readAllLines(FileSystems.getDefault().getPath("index.html"), StandardCharsets.UTF_8));
                    String newFileContent = response.replace("<!--new_msg-->", "<li>" + URLDecoder.decode(form, StandardCharsets.UTF_8.name()).split("=")[1] + "</li>" + "\n<!--new_msg-->");
                    PrintWriter printWriter = new PrintWriter("index.html", "UTF-8");
                    printWriter.write(newFileContent);
                    printWriter.flush();
                    printWriter.close();

                }

                String response = String.join("\n", Files.readAllLines(FileSystems.getDefault().getPath("index.html"), StandardCharsets.UTF_8));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufferedWriter.write("HTTP/1.1 200 OK\n");
                bufferedWriter.write("Content-Type: text/html\n");
                bufferedWriter.write("Content-Length: " + response.length() + "\n");
                int size = response.length();
                bufferedWriter.write("\n");
                bufferedWriter.write(response);
                bufferedWriter.flush(); //покажи, выведи, после флаша он пустой.
                Thread.sleep(2000);

                socket.close();
                br.close();
                bufferedWriter.close();

            }

        }
    }

    static String getFormText(BufferedReader br, Integer contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        String formText = new String(body);
        formText = decode(formText, StandardCharsets.UTF_8.name());
        System.out.println("FORM: " + formText);
        return formText;
    }

     static HashMap<String, String> requestHeder (BufferedReader br, String nextLine) throws IOException {
        HashMap<String, String> requestHeders = new HashMap<>();
        while (!nextLine.equals("")) {
            if (nextLine.indexOf(":") > -1) {
                requestHeders.put(nextLine.split(":")[0].trim(), nextLine.split(":")[1].trim());
            } else {
                requestHeders.put("method", nextLine.split(" ")[0].trim());
                requestHeders.put("request-target", nextLine.split(" ")[1].trim());
                requestHeders.put("http-version", nextLine.split(" ")[2].trim());
            }
            System.out.println(nextLine);
            nextLine = br.readLine();
        }
        return requestHeders;
    }

}
