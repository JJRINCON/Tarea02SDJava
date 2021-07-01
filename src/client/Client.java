package client;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    private ArrayList<String> data;
    private Socket socket;
    private Scanner console;

    public Client() throws IOException {
        console = new Scanner(System.in);
        initApp();
    }

    private void initApp() throws IOException {
        int option = 0;
        boolean exit = false;
        while (!exit){
            option = showMenu();
            if(option == 1){
                showPositionInfo();
            }else if(option == 2){
                exit = true;
            }
            System.out.println(option);
        }
    }

    private int showMenu(){
        System.out.println("---------Paises---------");
        System.out.println("1. Ingresar posicion");
        System.out.println("2. Salir");
        return console.nextInt();
    }

    public String getPositionInfo(int position) throws IOException {
        socket = new Socket("localhost", 5000);
        socket.getOutputStream().write(position - 1);
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        return inputStream.readUTF();
    }

    private void showPositionInfo() throws IOException {
        System.out.print("Ingrese la posicion:  ");
        int position = console.nextInt();
        System.out.println(getPositionInfo(position));
    }

    public static void main(String[] args) {
        try {
            new Client();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
