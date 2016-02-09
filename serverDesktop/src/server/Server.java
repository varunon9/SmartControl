/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.MouseInfo;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import mousecontrol.MouseControl;

/**
 *
 * @author varun
 */
public class Server {
    public Server() {
        
    }
    /**
     * Code for different Actions
     * 1. Left click
     * 2. Right Click
     * 3. Mouse scroll
     * 4. Mouse move
     * 5. Keyboard enter
     */
    public void connect(int portNumber) {
        final MouseControl mouseControl = new MouseControl();
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Waiting for a connection...");
            Socket clientSocket = serverSocket.accept();
            System.out.println(clientSocket.getRemoteSocketAddress());
            final BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            new Thread() {
                public void run() {
                    String message = "";
                    while (true) {
                        try {
                            message = in.readLine();
                            if (message != null && !message.isEmpty()) {
                                int code = Integer.parseInt(message);
                                switch(code) {
                                    case 1: 
                                        mouseControl.leftClick();
                                        break;
                                    case 2:
                                        mouseControl.rightClick();
                                        break;
                                    case 3:
                                        int scrollAmount = Integer.parseInt(in.readLine());
                                        mouseControl.mouseWheel(scrollAmount);
                                        break;
                                    case 4: 
                                        float x = Float.parseFloat(in.readLine());
                                        float y = Float.parseFloat(in.readLine());
                                        Point point = MouseInfo.getPointerInfo().getLocation(); //Get current mouse position
                                        float nowx = point.x;
                                        float nowy = point.y;
                                        mouseControl.mouseMove((int)(nowx + x), (int)(nowy + y));
                                        break;
                                    case 5:
                                        break;
                                }
                            }
                            else {
                                break;
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }.start();
        }
        catch(Exception e) {
            
        }
    }
    public static void main(String args[]) {
        new Server().connect(4500);
    }
}
