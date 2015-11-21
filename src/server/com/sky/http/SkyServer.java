package com.sky.http;

import java.io.IOException;

import org.apache.commons.configuration.Configuration;

import com.joe.core.utils.CoreConfigUtils;
import com.sky.jetty.HttpServer;

/**
 * 服务启动器
 * @author Joe
 *
 */
public class SkyServer {

    private HttpServer server;
    private int port;

    public SkyServer(Configuration conf, int port) throws Exception {
		if (port <= 0 || port >= 65535) {
		    this.port = Integer.parseInt(conf.getString("bdp.service.port", "8098"));
		} else {
		    this.port = port;
		}
		server = new HttpServer(conf,"webapp", "0.0.0.0", this.port, true);
	}

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
		String usage = "Usage: SkyServer  [port]";
		int port = 0;
		if (args.length == 1) {
			try{
				port = Integer.parseInt(args[0]);
			}catch(Exception e){
				System.out.println(usage);
			}
		}
		Configuration conf = CoreConfigUtils.create();
		SkyServer server = new SkyServer(conf, port);
		server.start();
    }

    public void start() throws IOException {
	this.server.start();
    }

    public void stop() throws Exception {
	if (server != null)
	    this.server.stop();
    }
}
