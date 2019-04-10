package ir.iconish.sanjinehsub.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class InternetAccess {


    public static boolean isInternetAvailable() {


        String host = "www.google.com";
        int port = 80;
        Socket socket = new Socket();

        try {
            socket.connect(new InetSocketAddress(host, port), 10000);
            socket.close();
            return true;
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException es) {
            }
            return false;
        }

    }

}
