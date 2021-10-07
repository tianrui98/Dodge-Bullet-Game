package com.example.unicorngladiators.network;

import java.io.*;
import java.net.*;
import java.io.DataOutputStream;

public class Client {
    public static void main(String[] args) {
        try
        {
            Socket s = new Socket("172.17.202.176", 6666);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            dout.writeUTF("Hiiiiii");
            dout.flush();
            dout.close();
            s.close();
        }

        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
