package com.teamswag.com;

import java.awt.event.InputEvent;
import java.net.*;
import java.util.Enumeration;
import java.awt.Robot;

/**
 * Created by john on 10/18/14.
 */
public class UDPServer extends Thread{
    private int port;
    private String ipaddress;
    private DatagramSocket socket;
    private Robot robot;
    private boolean serverCanRun;
    private DDRDriver ddrpad;

    public UDPServer(int port, String adapter, DDRDriver ddrpad){
        this.port = port;
        this.ddrpad = ddrpad;
        try {
            socket = new DatagramSocket(port);
            socket.setBroadcast(true);
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while(e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                if (n.getName().equals(adapter)) {
                    ipaddress = ((InetAddress) ee.nextElement()).getHostAddress();
                }
            }
            robot = new Robot();
        }catch(Exception e){}
        serverCanRun = true;
    }

    public String getIPAddress(){
        return ipaddress;
    }

    public int getPort(){
        return port;
    }

    public void stopServer(){
        serverCanRun = false;
    }

    public void run(){
        while(serverCanRun){
            try {
                byte[] buf = new byte[1];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                String pressedButton = new String(packet.getData());
                System.out.println(pressedButton);
                if (pressedButton.equals("1")) {
                    robot.mousePress(InputEvent.BUTTON1_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
                }
                if (pressedButton.equals("6")) {
                    ddrpad.orientation=1;
                }
                if (pressedButton.equals("7")) {
                    ddrpad.orientation=2;
                }
                if (pressedButton.equals("8")) {
                    ddrpad.orientation=3;
                }
                if (pressedButton.equals("9")) {
                    ddrpad.orientation=4;
                }
                System.out.println(pressedButton + "\n");
            }catch(Exception e){}

        }
    }
}
