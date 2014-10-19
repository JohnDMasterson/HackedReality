package com.teamswag.com;

/**
 * Created by john on 10/18/14.
 */
public class VRServer {
    public static void main(String[] args){
        DDRDriver ddrpad = new DDRDriver();
        ddrpad.start();
        UDPServer serverThread = new UDPServer(7777, "en1", ddrpad);
        serverThread.start();
        System.out.println("Server IP: " + serverThread.getIPAddress());
    }
}
