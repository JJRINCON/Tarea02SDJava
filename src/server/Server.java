package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable{

    public static final int PORT = 5000;
    private ServerSocket server;
    private Thread thread;
    private ArrayList<String> data;

    public Server() throws IOException {
        initData();
        server = new ServerSocket(PORT);
        thread = new Thread(this);
        thread.start();
        Logger.getGlobal().log(Level.INFO, "Server init in port 5000");
    }

    private void initData(){
        data = new ArrayList<>();
        data.add("Colombia");
        data.add("Peru");
        data.add("Ecuador");
        data.add("Argentina");
        data.add("Chile");
        data.add("Venezuela");
        data.add("Uruguay");
        data.add("Paraguay");
    }

    @Override
    public void run() {
        while (!server.isClosed()){
            try {
               manageNewConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void manageNewConnection() throws IOException {
        Socket connection = server.accept();
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        sendPositionInfo(connection.getInputStream().read(), outputStream);
        Logger.getGlobal().log(Level.INFO, "New Client Connected");
    }

    private void sendPositionInfo(int position, DataOutputStream outputStream) throws IOException {
        if(position >= 0 && position < data.size()){
            outputStream.writeUTF("El pais que se encuentra en la posicion " + (position + 1) +
                                    " es:  " + data.get(position));
        }else{
            outputStream.writeUTF("Posicion erronea");
        }
    }

    public static void main(String[] args) {
        try {
            new Server();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
