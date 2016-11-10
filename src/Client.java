import print.Print;
import print.PrintInWeb;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;

public class Client {
    static String header = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<title>Page Title</title>\n" +
            "</head>\n" +
            "<body>\n";
    static String headerForHelloWorld =
            " <form action=\"\">\n" +
                    "  <p><b>Введите вариант</b></p>\n" +
                    "  <input type=\"text\" name=\"name\" \n<Br>\n" +
                    "  <p><input type=\"submit\"value=\"Let's go!\"></p>\n" +
                    " </form>\n";

    static String footer =

            "<form method=\"LINK\" action=\"http://greater.local:8080/calendar\">\n" +
                    "    <input type=\"submit\" value=\"Calendar!\">\n" +
                    "</form>" +
                    "<form method=\"LINK\" action=\"http://greater.local:8080/hello_world\">\n" +
                    "<input type=\"submit\" value=\"HelloWorld!\">\n" +
                    "</form>" +
                    "</body>\n" +
                    "</html>";


    public static void main(String args[]) throws IOException, URISyntaxException {
        while (true) {
            try (ServerSocket server = new ServerSocket(8080)) {
                System.out.println("Listening for connection on port 8080 ....");
                StringBuilder builder = new StringBuilder();
                Socket socket = server.accept();
                BufferedReader reader = getBufferedReader(builder, socket);
                socket.getOutputStream().write(builder.toString().getBytes("UTF-8"));
                reader.close();
            }
        }
    }

    private static BufferedReader getBufferedReader(StringBuilder builder, Socket socket) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String parameters = reader.readLine().split(" ")[1];
        if (parameters.contains("/calendar")) {
            Print calendar = new PrintInWeb();
            builder.append(calendar.print() + footer);
        } else if (parameters.contains("/hello_world")) {
            builder.append(header);
            if (parameters.contains("name")) {
                builder.append("Hello" + parseLink(parameters));
            }
            builder.append(headerForHelloWorld);
            builder.append(footer);
        } else {
            builder.append(header);
            builder.append(footer);
        }
        return reader;
    }

    private static String parseLink(String parameters) {
        if (parameters.contains("name")) {
            return parseName(parameters);
        } else if (parameters.contains("calendar")) {
            return getCalendar();
        } else {
            return "World!";
        }
    }

    private static String getCalendar() {
        Print calendar = new PrintInWeb();
        return calendar.print();
    }

    private static String parseName(String parameters) {
        int index = parameters.indexOf("=");
        index += 1;
        String result = parameters.substring(index);
        return result.isEmpty() ? " world!" : ", "+result+"=)";
    }

}

