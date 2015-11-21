package com.joe.download.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;


/**
 * @ClassName: StreamUtils
 * @Description: 对文件流的处理的工具
 * @author WDB
 * @date 2014-12-28 下午9:04:03
 * 
 */
public class StreamUtils {

    /**
     * 将对应的输入流转成对应的输出流
     * @param fis
     * @param os
     * @throws IOException
     */
    public static void input2Output(InputStream fis, OutputStream os)
	    throws IOException {

	BufferedInputStream bis = null;
	BufferedOutputStream bos = null;
	try {
	    bis = new BufferedInputStream(fis);
	    bos = new BufferedOutputStream(os);
	    byte[] bytes = new byte[1024 * 256];// 每次传输文件块的大小,先设置为256KB
	    int len = bis.read(bytes);
	    while (len > 0) {
	    	bos.write(bytes, 0,len);
	    	len = bis.read(bytes);
	    }
	} finally {
	    bos.flush();
	    bis.close();
	    bos.close();
	}
    }
    /**
     * @param path  文件路径
     * @param name  文件名称
     * @return 输入流
     * @throws IOException
     */
    public static InputStream getInputStreamByFileName(String path, String name)
	    throws IOException {
	return new FileInputStream(new File(path, name));
    }
    
    public static void main(String[] args) throws IOException {
		File file = new File("deploy/cdh5.1.0/zookeeper-3.4.5-cdh5.1.0.tar.gz");
		System.out.println(file.length());
		File testFile = new File("/Users/qiaolong/test/test/zookeeper-3.4.5-cdh5.1.0.tar.gz");
		System.out.println(testFile.length());
		File testFile1 = new File("/Users/qiaolong/test/test/1/zookeeper-3.4.5-cdh5.1.0.tar.gz");
		System.out.println(testFile1.length());
		File test2 = new File("/Users/qiaolong/test/test/2.tar.gz");
//		InputStream in = new FileInputStream(file);
		InputStream in = new URL("http://192.168.32.59:8098/download/package?serviceName=zookeeper").openStream();
		OutputStream out = new FileOutputStream(test2);
		
		byte[] b = new byte[8196];
		int len = in.read(b);
		while(len > 0){
			out.write(b, 0, len);
			len = in.read(b);
		}
		out.flush();
		out.close();
		in.close();
		System.out.println(test2.length());
	}
}
