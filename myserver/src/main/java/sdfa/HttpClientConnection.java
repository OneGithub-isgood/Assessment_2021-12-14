package sdfa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;

public class HttpClientConnection {
    private HttpWriter webOut;

    public String clientRequest(String browserMsg)
            throws IOException {

        String serverMsg = "HTTP/1.1 200 OK\r\n\r\n<>resources contents as bytes";
        String[] msgSubstring = browserMsg.split(" ");

        String resourceString = msgSubstring[1].toString().replaceFirst("/","");
        File dbFile = new File(resourceString);

        if (!msgSubstring[0].toString().equals("GET")) {
            serverMsg = "HTTP/1.1 405 Method Not Allowed\r\n\r\n<resource name> not found\r\n";
            return serverMsg;
        } else {
            try (Reader newReader = new FileReader(dbFile)) {
                if (!msgSubstring[2].toString().contains("png")) {
                    serverMsg = "HTTP/1.1 200 OK\r\n\r\n<resource contents as bytes>\r\n";
                    return serverMsg;
                } else {
                    serverMsg = "HTTP/1.1 200 OK\r\nContent-Type: image/png\r\n\r\n<resource contents as bytes>\r\n";
                    return serverMsg;
                }
            } catch (FileNotFoundException eFNF) {
                serverMsg = "HTTP/1.1 404 Not Found\r\n\r\n<method name> not suported\r\n";
                return serverMsg;
            }
        }
    }
}
