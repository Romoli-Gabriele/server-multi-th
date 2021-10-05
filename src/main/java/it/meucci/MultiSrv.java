package it.meucci;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiSrv {

    public void start() {
        try {
            ServerSocket server = new ServerSocket(6789);
            String ind = InetAddress.getLocalHost().getHostAddress();
            for (;;) {
                System.out.println("Server partito in esecuzione..." + ind);
                Socket socket = server.accept();
                System.out.println("3 Server socket " + server);
                ServerThread serverthread = new ServerThread(socket);
                serverthread.start();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("errore durante l'istanza del server");
            System.exit(1);
        }
    }

}
