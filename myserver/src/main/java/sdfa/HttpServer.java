package sdfa;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class HttpServer implements Runnable {
    private final Socket socket;
    private String directoriesPath;

    public HttpServer(Socket socket, String directoriesPath) {
        this.socket = socket;
        this.directoriesPath = directoriesPath;
    }

    @Override
    public void run() {
        try (InputStream iS = socket.getInputStream()) {
            while (true) {
                //DataInputStream diS = new DataInputStream(new BufferedInputStream(iS));
                java.io.BufferedReader input = new java.io.BufferedReader(new java.io.InputStreamReader(iS));
                String eachLine = input.readLine();
                System.out.println(eachLine.toString());
                OutputStream oS = socket.getOutputStream();
                PrintWriter pW = new PrintWriter(oS, true);
                while (true) {
                //    String readData = diS.readUTF();
                //    System.out.println("Client: " + readData);
                //    if (readData.equals("get-cookie")) {
                //        String webString = "";
                //        pW.println(webString);
                //    } else if (readData.equals("close")) {
                //        break;
                //    } else {
                //        pW.println("Type 'get-cookie' to receive fortune cookies");
                //    }
                }
            }
        } catch (IOException eIO) {
            System.out.println(eIO);
        }
    }
}
