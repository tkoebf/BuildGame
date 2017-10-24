package com.mygdx.game.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketHelper {
    private Socket socket = null;

    public static SocketHelper client = new SocketHelper();


    public void socketConnect(String ip, int port) throws UnknownHostException, IOException {
        System.out.println("Connecting...");
        this.socket = new Socket(ip, port);
    }

    public void sendMessage(String message) {
        try {
            PrintWriter output = new PrintWriter(getSocket().getOutputStream(), true);
            output.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedReader getInput() {
        try {
            return new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeSocket()  {
        try {
            getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Socket getSocket() {
        return socket;
    }
}
