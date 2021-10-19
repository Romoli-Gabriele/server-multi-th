package it.meucci;

import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
    ServerSocket server = null;
    Socket client = null;
    String StringRV = null;
    String StringMD = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    MultiSrv allThread;

    public ServerThread(Socket socket, ServerSocket server, MultiSrv gestore) {
        this.client = socket;
        this.server = server;
        this.allThread = gestore;
    }

    public void run() {
        try {
            comunica();
        } catch (Exception e) {
        }

    }
    public void close(){
        try {
            outVersoClient.writeBytes("close");// invia segnale al client di chiudersi
            outVersoClient.close();
            inDalClient.close();
            client.close();
        } catch (IOException e) {
            System.out.println("Errore invio messaggio di chiusura");
        }
    }

    public void comunica() throws Exception {
        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoClient = new DataOutputStream(client.getOutputStream());
        for (;;) {
            StringRV = inDalClient.readLine();//Lettura dal client
            StringMD = StringRV.toUpperCase();//modifica stringa
            if (StringRV == null || StringMD.equals("FINE") || StringMD.equals("STOP")) { //chiusura thread 
                outVersoClient.writeBytes(StringRV + " (=>server dedicato in chiusura..)" + '\n');
                System.out.println("Echo sul server in chiusura : " + StringRV);
                outVersoClient.close();
                inDalClient.close();
                client.close();
                break;
            } else {
                outVersoClient.writeBytes(StringMD + "(ricevuta e ritrasmessa)" + '\n');
                System.out.println("6 Echo sul server: " + StringRV);
            }
        }
        outVersoClient.close();
        inDalClient.close();
        System.out.println("9 Chiusura socket ..." + client);
        client.close();
        if (StringRV.equals("STOP")) {
            allThread.close();//chiama la chiusura di tutti i thread
            server.close();
            System.out.println("Server in chiusura");
            System.exit(1);
        }
    }
}