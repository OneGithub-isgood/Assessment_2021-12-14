package sdfa;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class HttpServer implements Runnable {
    private final Socket socket;
    private int tcpPortNumber;
    private HttpWriter webOut;

    public HttpServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        HttpClientConnection newHttp = new HttpClientConnection();

        try (InputStream iS = socket.getInputStream()) {

                java.io.BufferedReader input = new java.io.BufferedReader(new java.io.InputStreamReader(iS, "UTF-8"));
                String eachLine = input.readLine();
                while (eachLine != null) {
                    newHttp.clientRequest(eachLine);
                    System.out.println(newHttp.clientRequest(eachLine));
                    break;
                }

                OutputStream oS = socket.getOutputStream();
                try {
                    webOut = new HttpWriter(oS);
                    webOut.writeString(eachLine);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                PrintWriter pW = new PrintWriter(oS);
                pW.println("The fox jump over the lazy dog"+newHttp.clientRequest(eachLine));
                iS.close();

        } catch (IOException eIO) {

        }
    }
}
