/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcontrol;

import ipaddress.GetMyIpAddress;
import server.Server;

/**
 *
 * @author varun
 */
public class SmartControl {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GetMyIpAddress getMyIpAddress = new GetMyIpAddress();
        int portNumber;
        String ipAddresses[] = getMyIpAddress.ipAddress();
        if(args.length != 0 && getMyIpAddress.validatePort(args[0]) == true) {
            portNumber = Integer.parseInt(args[0]); 
        }
        else {
            portNumber = 4500;
        }
        if(! ipAddresses[0].equals("127.0.0.1")){
            System.out.println("Connect to IP Address:" + ipAddresses[0]);
            System.out.println("Connect to port Number: " + portNumber);
            new Server().connect(portNumber);
        }
        else {
            System.out.println("You are not connected to any Network.");
        }
    }
    
}
