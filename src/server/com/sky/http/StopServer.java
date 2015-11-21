package com.sky.http;

import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

public class StopServer {

    /**
     * @param args
     */
    public static void main(String[] args) {
	stop();
    }

    /**
     * Stop a running jetty instance.
     */
    public static void stop()
    {
        int _port=Integer.getInteger("STOP.PORT",-1).intValue();
        String _key=System.getProperty("STOP.KEY",null);

        try
        {
            if (_port<=0)
                System.err.println("STOP.PORT system property must be specified");
            if (_key==null)
            {
                _key="";
                System.err.println("STOP.KEY system property must be specified");
                System.err.println("Using empty key");
            }

            Socket s=new Socket(InetAddress.getByName("127.0.0.1"),_port);
            OutputStream out=s.getOutputStream();
            out.write((_key+"\r\nstop\r\n").getBytes());
            out.flush();
            s.close();
        }
        catch (ConnectException e)
        {
            System.err.println("ERROR: Not running!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
