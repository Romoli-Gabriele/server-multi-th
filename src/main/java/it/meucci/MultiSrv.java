package it.meucci;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class MultiSrv {
    Vector<ServerThread> threadList = new Vector<ServerThread>(); //Creazione lista dei gestori dei client

    public void start() {
        try {
            ServerSocket server = new ServerSocket(6789); //Apertura porta
            String ind = InetAddress.getLocalHost().getHostAddress();//IP server
            System.out.println("Server partito in esecuzione..." + ind);
            for (;;) {
                Socket socket = server.accept();//accetta client e libera la porta
                System.out.println("3 Server socket " + server);
                ServerThread serverthread = new ServerThread(socket,server,this);//creazione thread per gestire il client
                threadList.add(serverthread);//aggiungi il gestore appena creato alla lista
                serverthread.start();
            }
        } catch (Exception e) {
            System.out.println("Errore connessione client o creazione thread");
            System.exit(1);
        }
    }
    public void close(){
        for(int i = 0;i < threadList.size(); i++){
            threadList.get(i).close();//chiusura di tutti i socket 
        }
    }

}
