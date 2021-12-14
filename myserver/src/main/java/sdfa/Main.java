package sdfa;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args)
            throws UnknownHostException, IOException {
        int tcpPortNum = 8080;
        String directoriesPath = "";

        for (int argPosition= 0; argPosition < args.length; argPosition++) {
            String arg = "";
            int nextArgPosition = argPosition + 1;
            if (arg.equals("--port")) {
                tcpPortNum = Integer.parseInt(args[argPosition]);
            } else if (arg.equals("docRoot")) {
                directoriesPath = args[argPosition];
            }
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        System.out.println("Opening new connection for port " + tcpPortNum + "...");
        ServerSocket serverSocket = new ServerSocket(tcpPortNum);

        
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                //HttpServer worker = new HttpServer(socket, directoriesPath);
                //threadPool.submit(worker);
                try (InputStream iS = socket.getInputStream();) {
                    while (true) {
                        //DataInputStream diS = new DataInputStream(new BufferedInputStream(iS));
                        java.io.BufferedReader input = new java.io.BufferedReader(new java.io.InputStreamReader(iS, "UTF-8"));
                        String eachLine = input.readLine();
                        //String readData = diS.readUTF();
                        while (eachLine != null) {
                            System.out.println(eachLine);
                        }
                        
                        //System.out.println(readData);
                        
                        OutputStream oS = socket.getOutputStream();
                        PrintWriter pW = new PrintWriter(oS, true);
                        //while (true) {
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
                        //}
                        //iS.close();
                    } 
                } //catch (IOException eIO) {
                    //System.out.println(eIO);
                //}
            }
        } finally {
            serverSocket.close();
        }
    }
}
