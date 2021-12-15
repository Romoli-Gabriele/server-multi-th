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
    int index;

    public ServerThread(Socket socket, ServerSocket server, MultiSrv gestore, int index) {
        this.client = socket;
        this.server = server;
        this.allThread = gestore;
        this.index = index;
    }

    public void run() {
        try {
            comunica();
        } catch (Exception e) {
        }

    }
    public void close(boolean D){
        try {
            String rispostaChiusura;
            if(D){
                rispostaChiusura =("Disponibili 0 biglietti\n");
            }else{
                rispostaChiusura =("Biglietti Esauriti\n");
            }
            outVersoClient.writeBytes(rispostaChiusura);// invia segnale al client di chiudersi
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
            if(StringRV.equals("D")){
                StringMD = ("Disponibili "+allThread.nBiglietti+" biglietti\n");
                if(allThread.nBiglietti==0){
                    allThread.close(this.index);
                }
            }else if(StringRV.equals("A")){
               StringMD = allThread.vendi(this.index);
            }
            outVersoClient.writeBytes(StringMD+'\n');
        }
        
    }
}