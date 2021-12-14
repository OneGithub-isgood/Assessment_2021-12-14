package sdfa;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args)
            throws UnknownHostException, IOException {
        int tcpPortNum = 3000;
        String directoriesPath = "";
        for (int argPosition= 0; argPosition < args.length; argPosition++) {
            String arg = args[argPosition];
            int nextArgPosition = argPosition + 1;
            if (arg.equals("--port")) {
                tcpPortNum = Integer.parseInt(args[nextArgPosition]);
            } else if (arg.equals("--docRoot")) {
                String[] docRoot = args[nextArgPosition].split(":");
                System.out.println(docRoot[0]);
                for (int docRootNum = 0; docRootNum < docRoot.length; docRootNum++) {
                    Path docRootPath = Paths.get(docRoot[docRootNum]);
                    if (Files.notExists(docRootPath)) {
                        System.exit(1);
                    }
                }
            }
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        System.out.println("Opening new connection for port " + tcpPortNum + "...");
        ServerSocket serverSocket = new ServerSocket(tcpPortNum);

        try {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                HttpServer worker = new HttpServer(socket);
                threadPool.submit(worker);
            }
        } finally {
            serverSocket.close();
        }
    }
}
