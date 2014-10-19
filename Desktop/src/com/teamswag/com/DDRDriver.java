package com.teamswag.com;

/**
 * Created by john on 10/18/14.
 */
import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDManager;

import java.awt.Robot;
import java.awt.event.KeyEvent;

public class DDRDriver extends Thread {
    static {
        com.codeminders.hidapi.ClassPathLibraryLoader.loadNativeHIDLibrary();
    }

    static final int VENDOR_ID = 0x0E8F;
    static final int PRODUCT_ID = 0x0003;
    static final int BUFSIZE = 2048;
    static final int W = KeyEvent.VK_W;
    static final int A = KeyEvent.VK_A;
    static final int S = KeyEvent.VK_S;
    static final int D = KeyEvent.VK_D;
    /* 1 = up arrow -> up   default
       2 = left arrow -> up
       3 = down arrow -> up
       4 = right arrow -> up
    */
    public volatile int orientation = 1;

//    final static String origUp = "00 00 00 01 ";
//    final static String origLeft = "00 06 00 08 ";
//    final static String origDown = "00 04 00 04 ";
//    final static String origRight = "00 02 00 02 ";
//    final static String origUpLeft = "00 07 00 09 ";
//    final static String origUpRight = "00 01 00 03";
//    final static String origDownLeft = "00 05 00 0c ";
//    final static String origDownRight = "00 03 00 06 ";
//    final static String origUpDown = "00 0f 00 05 ";
//    final static String origLeftRight = "00 0f 00 0a ";

    //    String up = "";
//    String left = "";
//    String down = "";
//    String right = "";
//    String upLeft = "";
//    String upRight = "";
//    String downLeft = "";
//    String downRight = "";
//    String upDown = "";
//    String leftRight = "";
    final String emptyPress = "00 0f 00 00 ";

    public void run() {
        HIDDevice dev;
        try {
            HIDManager hid_mgr = HIDManager.getInstance();
            Robot r = new Robot();

            dev = hid_mgr.openById(VENDOR_ID, PRODUCT_ID, null);

            System.err.print("Manufacturer: " + dev.getManufacturerString() + "\n");
            System.err.print("Product: " + dev.getProductString() + "\n");
            System.err.print("Serial Number: " + dev.getSerialNumberString() + "kek\n");

            try {
                byte[] buf = new byte[BUFSIZE];
                String str;
                dev.enableBlocking();

                // while (this.updateOrientation()) {
                while (true) {
                    str = "";
                    int n = dev.read(buf);

                    for (int i = 0; i < n; i++) {
                        int v = buf[i];

                        if (v < 0) {
                            v = v + 256;
                        }

                        String hs = Integer.toHexString(v);

                        if (v < 16) {
                            str += "0";
                            // System.err.print("0");
                        }

                        if (v == 128) {
                            continue;
                        }

                        // System.err.print(hs + " ");
                        str += hs + " ";
                    }

                if(str.equals("00 00 00 01 ")) {
                    switch (orientation) {
                        // up arrow -> up
                        case 1:
                            r.keyPress(W);
                            r.keyRelease(A);
                            r.keyRelease(S);
                            r.keyRelease(D);
                            break;

                        // left arrow -> up
                        case 2:
                            r.keyPress(D);
                            r.keyRelease(W);
                            r.keyRelease(S);
                            r.keyRelease(A);
                            break;

                        // down arrow -> up
                        case 3:
                            r.keyPress(S);
                            r.keyRelease(W);
                            r.keyRelease(A);
                            r.keyRelease(D);
                            break;

                        // right arrow -> up
                        case 4:
                            r.keyPress(A);
                            r.keyRelease(W);
                            r.keyRelease(D);
                            r.keyRelease(S);
                            break;
                    }
                    // left arrow
                }else if(str.equals("00 06 00 08 ")) {
                    switch (orientation) {
                        // up arrow -> up
                        case 1:
                            r.keyPress(A);
                            r.keyRelease(W);
                            r.keyRelease(S);
                            r.keyRelease(D);
                            break;

                        // left arrow -> up
                        case 2:
                            r.keyPress(W);
                            r.keyRelease(A);
                            r.keyRelease(S);
                            r.keyRelease(D);
                            break;

                        // down arrow -> up
                        case 3:
                            r.keyPress(D);
                            r.keyRelease(W);
                            r.keyRelease(A);
                            r.keyRelease(S);
                            break;

                        // right arrow -> up
                        case 4:
                            r.keyPress(S);
                            r.keyRelease(W);
                            r.keyRelease(A);
                            r.keyRelease(D);
                            break;
                    }

                    // down arrow
                }else if(str.equals("00 04 00 04 ")) {
                    switch (orientation) {
                        // up arrow -> up
                        case 1:
                            r.keyPress(S);
                            r.keyRelease(A);
                            r.keyRelease(W);
                            r.keyRelease(D);
                            break;

                        // left arrow -> up
                        case 2:
                            r.keyPress(A);
                            r.keyRelease(W);
                            r.keyRelease(S);
                            r.keyRelease(D);
                            break;

                        // down arrow -> up
                        case 3:
                            r.keyPress(W);
                            r.keyRelease(S);
                            r.keyRelease(A);
                            r.keyRelease(D);
                            break;

                        // right arrow -> up
                        case 4:
                            r.keyPress(D);
                            r.keyRelease(W);
                            r.keyRelease(A);
                            r.keyRelease(S);
                            break;
                    }

                    // right arrow
                }else if(str.equals("00 02 00 02 ")) {
                    switch (orientation) {
                        // up arrow -> up
                        case 1:
                            r.keyPress(D);
                            r.keyRelease(A);
                            r.keyRelease(S);
                            r.keyRelease(W);
                            break;

                        // left arrow -> up
                        case 2:
                            r.keyPress(S);
                            r.keyRelease(W);
                            r.keyRelease(A);
                            r.keyRelease(D);
                            break;

                        // down arrow -> up
                        case 3:
                            r.keyPress(A);
                            r.keyRelease(W);
                            r.keyRelease(S);
                            r.keyRelease(D);
                            break;

                        // right arrow -> up
                        case 4:
                            r.keyPress(W);
                            r.keyRelease(D);
                            r.keyRelease(A);
                            r.keyRelease(S);
                            break;
                    }

                    //up + left
                }else if(str.equals("00 07 00 09 ")){
                        switch (orientation) {
                            // up arrow -> up
                            case 1:
                                r.keyPress(W);
                                r.keyPress(A);
                                r.keyRelease(S);
                                r.keyRelease(D);
                                break;

                            // left arrow -> up
                            case 2:
                                r.keyPress(W);
                                r.keyPress(D);
                                r.keyRelease(A);
                                r.keyRelease(S);
                                break;

                            // down arrow -> up
                            case 3:
                                r.keyPress(S);
                                r.keyPress(D);
                                r.keyRelease(A);
                                r.keyRelease(W);
                                break;

                            // right arrow -> up
                            case 4:
                                r.keyPress(A);
                                r.keyPress(S);
                                r.keyRelease(W);
                                r.keyRelease(D);
                        }
                        break;

                    //right + down
                }if(str.equals("00 03 00 06 ")) {
                        switch (orientation) {
                            // up arrow -> up
                            case 1:
                                r.keyPress(D);
                                r.keyPress(S);
                                r.keyRelease(W);
                                r.keyRelease(A);
                                break;

                            // left arrow -> up
                            case 2:
                                r.keyPress(A);
                                r.keyPress(S);
                                r.keyRelease(W);
                                r.keyRelease(D);
                                break;

                            // down arrow -> up
                            case 3:
                                r.keyPress(W);
                                r.keyPress(A);
                                r.keyRelease(S);
                                r.keyRelease(D);
                                break;

                            // right arrow -> up
                            case 4:
                                r.keyPress(W);
                                r.keyPress(D);
                                r.keyRelease(A);
                                r.keyRelease(S);
                                break;
                        }

                        //up + down
                    }else if(str.equals("00 0f 00 05 ")) {
                        switch (orientation) {
                            // up/down arrow -> up
                            case 1:
                            case 3:
                                r.keyPress(W);
                                r.keyPress(S);
                                r.keyRelease(A);
                                r.keyRelease(D);
                                break;

                            // left/right arrow -> up
                            case 2:
                            case 4:
                                r.keyPress(A);
                                r.keyPress(D);
                                r.keyRelease(W);
                                r.keyRelease(S);
                                break;
                        }

                        //up + right
                    }else if(str.equals("00 01 00 03 ")) {
                        switch (orientation) {
                            // up arrow -> up
                            case 1:
                                r.keyPress(W);
                                r.keyPress(D);
                                r.keyRelease(S);
                                r.keyRelease(A);
                                break;

                            // left arrow -> up
                            case 2:
                                r.keyPress(S);
                                r.keyPress(D);
                                r.keyRelease(W);
                                r.keyRelease(A);
                                break;

                            // down arrow -> up
                            case 3:
                                r.keyPress(A);
                                r.keyPress(S);
                                r.keyRelease(W);
                                r.keyRelease(D);
                                break;

                            // right arrow -> up
                            case 4:
                                r.keyPress(A);
                                r.keyPress(W);
                                r.keyRelease(S);
                                r.keyRelease(D);
                                break;
                        }

                        //down + left
                    }else if(str.equals("00 05 00 0c ")) {
                        switch (orientation) {
                            // up arrow -> up
                            case 1:
                                r.keyPress(A);
                                r.keyPress(S);
                                r.keyRelease(W);
                                r.keyRelease(D);
                                break;

                            // left arrow -> up
                            case 2:
                                r.keyPress(W);
                                r.keyPress(A);
                                r.keyRelease(D);
                                r.keyRelease(S);
                                break;

                            // down arrow -> up
                            case 3:
                                r.keyPress(W);
                                r.keyPress(D);
                                r.keyRelease(S);
                                r.keyRelease(A);
                                break;

                            // right arrow -> up
                            case 4:
                                r.keyPress(S);
                                r.keyPress(D);
                                r.keyRelease(W);
                                r.keyRelease(A);
                                break;
                        }

                        //left + right
                    }else if(str.equals("00 0f 00 0a ")) {
                        switch (orientation) {
                            // up/down arrow -> up
                            case 1:
                            case 3:
                                r.keyPress(A);
                                r.keyPress(D);
                                r.keyRelease(W);
                                r.keyRelease(S);
                                break;

                            // left/right arrow -> up
                            case 2:
                            case 4:
                                r.keyPress(W);
                                r.keyPress(S);
                                r.keyRelease(A);
                                r.keyRelease(D);
                                break;
                        }
                        break;

                    }else if(str.equals("00 0f 00 00 ")) {
                        r.keyRelease(W);
                        r.keyRelease(A);
                        r.keyRelease(S);
                        r.keyRelease(D);
                        break;

                    }else {
                        System.err.println(str + " was printed.");
                        r.keyRelease(W);
                        r.keyRelease(A);
                        r.keyRelease(S);
                        r.keyRelease(D);
                        r.keyPress(KeyEvent.VK_ESCAPE);
                        r.keyRelease(KeyEvent.VK_ESCAPE);
                        throw new Exception();
                    }
                }
            } finally {
                dev.close();
                hid_mgr.release();
                System.gc();
            }} catch (Exception e) {
            e.printStackTrace();
        }

    }

    // private boolean updateOrientation() throws Exception{
    //     switch (this.orientation) {
    //         //default
    //         case 1:
    //             up = DDRDriver.origUp;
    //             left = DDRDriver.origLeft;
    //             down = DDRDriver.origDown;
    //             right = DDRDriver.origRight;
    //             upLeft = DDRDriver.origUpLeft;
    //             upRight = DDRDriver.origUpRight;
    //             downLeft = DDRDriver.origDownLeft;
    //             downRight = DDRDriver.origDownRight;
    //             upDown = DDRDriver.origUpDown;
    //             leftRight = DDRDriver.origLeftRight;
    //             break;

    //         // left -> up
    //         case 2:
    //             up = DDRDriver.origLeft;
    //             left = DDRDriver.origDown;
    //             down = DDRDriver.origRight;
    //             right = DDRDriver.origUp;
    //             upLeft = DDRDriver.origDownLeft;
    //             upRight = DDRDriver.origUpLeft;
    //             downLeft = DDRDriver.origDownRight;
    //             downRight = DDRDriver.origUpRight;
    //             upDown = DDRDriver.origLeftRight;
    //             leftRight = DDRDriver.origUpDown;
    //             break;

    //         // down -> up
    //         case 3:
    //             up = DDRDriver.origDown;
    //             left = DDRDriver.origRight;
    //             down = DDRDriver.origUp;
    //             right = DDRDriver.origLeft;
    //             upLeft = DDRDriver.origDownRight;
    //             upRight = DDRDriver.origDownLeft;
    //             downLeft = DDRDriver.origUpRight;
    //             downRight = DDRDriver.origUpLeft;
    //             upDown = DDRDriver.origUpDown;
    //             leftRight = DDRDriver.origLeftRight;
    //             break;

    //         // right -> up
    //         case 4:
    //             up = DDRDriver.origRight;
    //             left = DDRDriver.origUp;
    //             down = DDRDriver.origLeft;
    //             right = DDRDriver.origDown;
    //             upLeft = DDRDriver.origUpRight;
    //             upRight = DDRDriver.origDownRight;
    //             downLeft = DDRDriver.origUpLeft;
    //             downRight = DDRDriver.origDownLeft;
    //             upDown = DDRDriver.origLeftRight;
    //             leftRight = DDRDriver.origUpDown;
    //             break;

    //         default:
    //             throw new Exception("orientation not between 1-4\n");
    //     }

    //     return true;
    // }
}
