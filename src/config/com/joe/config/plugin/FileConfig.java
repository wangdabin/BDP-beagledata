package com.joe.config.plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

/**
 * 
 * @author qiaolong
 *
 */
public interface FileConfig extends Config {
	
	/**
	 * 
	 * @throws IOException
	 */
	void load() throws IOException;

	/**
	 * 
	 * @param file
	 * @throws IOException
	 */
	void load(File file) throws IOException;

	/**
	 * 
	 * @param url
	 * @throws IOException
	 */
	void load(URL url) throws IOException;

	/**
	 * 
	 * @param in
	 * @param encoding
	 * @throws IOException
	 */
	void load(InputStream in, String encoding) throws IOException;

	/**
	 * 
	 * @param in
	 * @throws IOException
	 */
	void load(Reader in) throws IOException;

	/**
	 * 
	 * @throws IOException
	 */
	void save() throws IOException;

	/**
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	void save(String fileName) throws IOException;

	/**
	 * 
	 * @param file
	 * @throws IOException
	 */
	void save(File file) throws IOException;

	/**
	 * 
	 * @param url
	 * @throws IOException
	 */
	void save(URL url) throws IOException;

	/**
	 * 
	 * @param out
	 * @throws IOException
	 */
	void save(OutputStream out) throws IOException;

	/**
	 * 
	 * @param out
	 * @param encoding
	 * @throws IOException
	 */
	void save(OutputStream out, String encoding) throws IOException;

	/**
	 * 
	 * @param out
	 * @throws IOException
	 */
	void save(Writer out) throws IOException;

	/**
	 * 
	 * @return
	 */
	String getFileName();

	/**
	 * 
	 * @param fileName
	 */
	void setFileName(String fileName);

	/**
	 * 
	 * @return
	 */
	String getBasePath();

	/**
	 * 
	 * @param basePath
	 */
	void setBasePath(String basePath);

	/**
	 * 
	 * @return
	 */
	File getFile();

	/**
	 * 
	 * @param file
	 */
	void setFile(File file);

	/**
	 * 
	 * @return
	 */
	URL getURL();

	/**
	 * 
	 * @param url
	 */
	void setURL(URL url);

	/**
	 * 
	 * @param autoSave
	 */
	void setAutoSave(boolean autoSave);

	/**
	 * 
	 * @return
	 */
	boolean isAutoSave();

	/**
	 * 
	 */
	void reload();

	/**
	 * 
	 * @return
	 */
	String getEncoding();

	/**
	 * 
	 * @param encoding
	 */
	void setEncoding(String encoding);
}
