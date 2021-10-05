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

    public ServerThread(Socket socket) {
        this.client = socket;
    }

    public void run() {
        try {
            comunica();
        } catch (Exception e) {

        }

    }

    public void comunica() throws Exception {
        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoClient = new DataOutputStream(client.getOutputStream());
        for (;;) {
            StringRV = inDalClient.readLine();
            if (StringRV == null || StringRV.equals("FINE")) {
                outVersoClient.writeBytes(StringRV + " (=>server in chiusura..)" + '\n');
                System.out.println("Echo sul server in chiusura : " + StringRV);
                break;
            }else{
                outVersoClient.writeBytes(StringMD +"(ricevuta e ritrasmessa)"+'\n');
                System.out.println("6 Echo sul server: " + StringRV);
            }
            outVersoClient.close();
            inDalClient.close();
            System.out.println("9 Chiusura socket ..."+client);
            client.close();
        }
    }
}