package com.joe.config.plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public abstract class AbstractFileConfig extends BaseConfig implements
		FileConfig {
	public static final int EVENT_RELOAD = 20;

	protected String fileName;

	protected String basePath;

	protected boolean autoSave = false;

	private Object reloadLock = new Object();

	private String encoding;

	private URL sourceURL;

	private int noReload;

	public AbstractFileConfig() {
		setLogger(Logger.getLogger(getClass()));
		addErrorLogListener();
	}

	public AbstractFileConfig(String fileName) throws IOException {
		this();

		// store the file name
		setFileName(fileName);

		// load the file
		load();
	}

	public AbstractFileConfig(File file) throws IOException {
		this();

		// set the file and update the url, the base path and the file name
		setFile(file);

		// load the file
		if (file.exists()) {
			load();
		}
	}

	public AbstractFileConfig(URL url) throws IOException {
		this();

		// set the URL and update the base path and the file name
		setURL(url);

		// load the file
		load();
	}

	public void load() throws IOException {
		if (sourceURL != null) {
			load(sourceURL);
		} else {
			load(getFileName());
		}
	}

	public void load(String fileName) throws IOException {
		try {
			URL url = ConfigUtils.locate(basePath, fileName);

			if (url == null) {
				throw new IOException("Cannot locate configuration source "
						+ fileName);
			}
			load(url);
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new IOException("Unable to load the configuration file "
					+ fileName, e);
		}
	}

	public void load(File file) throws IOException {
		try {
			load(ConfigUtils.toURL(file));
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new IOException("Unable to load the configuration file "
					+ file, e);
		}
	}

	public void load(URL url) throws IOException {
		if (sourceURL == null) {
			if (StringUtils.isEmpty(getBasePath())) {
				// ensure that we have a valid base path
				setBasePath(url.toString());
			}
			sourceURL = url;
		}

		// throw an exception if the target URL is a directory
		File file = ConfigUtils.fileFromURL(url);
		if (file != null && file.isDirectory()) {
			throw new IOException(
					"Cannot load a configuration from a directory");
		}

		InputStream in = null;

		try {
			in = url.openStream();
			load(in);
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new IOException(
					"Unable to load the configuration from the URL " + url, e);
		} finally {
			// close the input stream
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				getLogger().warn("Could not close input stream", e);
			}
		}
	}

	public void load(InputStream in) throws IOException {
		load(in, getEncoding());
	}

	public void load(InputStream in, String encoding) throws IOException {
		Reader reader = null;

		if (encoding != null) {
			try {
				reader = new InputStreamReader(in, encoding);
			} catch (UnsupportedEncodingException e) {
				throw new IOException(
						"The requested encoding is not supported, try the default encoding.",
						e);
			}
		}

		if (reader == null) {
			reader = new InputStreamReader(in);
		}

		load(reader);
	}

	public void save() throws IOException {
		if (getFileName() == null) {
			throw new IOException("No file name has been set!");
		}

		if (sourceURL != null) {
			save(sourceURL);
		} else {
			save(fileName);
		}
	}

	public void save(String fileName) throws IOException {
		try {
			File file = ConfigUtils.getFile(basePath, fileName);
			if (file == null) {
				throw new IOException("Invalid file name for save: " + fileName);
			}
			save(file);
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new IOException(
					"Unable to save the configuration to the file " + fileName,
					e);
		}
	}

	public void save(URL url) throws IOException {
		// file URLs have to be converted to Files since FileURLConnection is
		// read only
		// (http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4191800)
		File file = ConfigUtils.fileFromURL(url);
		if (file != null) {
			save(file);
		} else {
			// for non file URLs save through an URLConnection
			OutputStream out = null;
			try {
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);

				// use the PUT method for http URLs
				if (connection instanceof HttpURLConnection) {
					HttpURLConnection conn = (HttpURLConnection) connection;
					conn.setRequestMethod("PUT");
				}

				out = connection.getOutputStream();
				save(out);

				// check the response code for http URLs and throw an exception
				// if an error occured
				if (connection instanceof HttpURLConnection) {
					HttpURLConnection conn = (HttpURLConnection) connection;
					if (conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
						throw new IOException("HTTP Error "
								+ conn.getResponseCode() + " "
								+ conn.getResponseMessage());
					}
				}
			} catch (IOException e) {
				throw new IOException("Could not save to URL " + url, e);
			} finally {
				closeSilent(out);
			}
		}
	}

	public void save(File file) throws IOException {
		OutputStream out = null;

		try {
			// create the file if necessary
			createPath(file);
			out = new FileOutputStream(file);
			save(out);
		} catch (IOException e) {
			throw new IOException(
					"Unable to save the configuration to the file " + file, e);
		} finally {
			closeSilent(out);
		}
	}

	/**
	 * Save the configuration to the specified stream, using the encoding
	 * returned by {@link #getEncoding()}.
	 * 
	 * @param out
	 *            the output stream
	 * 
	 * @throws IOException
	 *             if an error occurs during the save operation
	 */
	public void save(OutputStream out) throws IOException {
		save(out, getEncoding());
	}

	/**
	 * Save the configuration to the specified stream, using the specified
	 * encoding. If the encoding is null the default encoding is used.
	 * 
	 * @param out
	 *            the output stream
	 * @param encoding
	 *            the encoding to use
	 * @throws IOException
	 *             if an error occurs during the save operation
	 */
	public void save(OutputStream out, String encoding) throws IOException {
		Writer writer = null;

		if (encoding != null) {
			try {
				writer = new OutputStreamWriter(out, encoding);
			} catch (UnsupportedEncodingException e) {
				throw new IOException(
						"The requested encoding is not supported, try the default encoding.",
						e);
			}
		}

		if (writer == null) {
			writer = new OutputStreamWriter(out);
		}

		save(writer);
	}

	/**
	 * Return the name of the file.
	 * 
	 * @return the file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Set the name of the file. The passed in file name can contain a relative
	 * path. It must be used when referring files with relative paths from
	 * classpath. Use <code>{@link AbstractFileConfig#setPath(String)
	 * setPath()}</code> to set a full qualified file name.
	 * 
	 * @param fileName
	 *            the name of the file
	 */
	public void setFileName(String fileName) {
		sourceURL = null;
		this.fileName = fileName;
	}

	/**
	 * Return the base path.
	 * 
	 * @return the base path
	 * @see FileConfiguration#getBasePath()
	 */
	public String getBasePath() {
		return basePath;
	}

	/**
	 * Sets the base path. The base path is typically either a path to a
	 * directory or a URL. Together with the value passed to the
	 * <code>setFileName()</code> method it defines the location of the
	 * configuration file to be loaded. The strategies for locating the file are
	 * quite tolerant. For instance if the file name is already an absolute path
	 * or a fully defined URL, the base path will be ignored. The base path can
	 * also be a URL, in which case the file name is interpreted in this URL's
	 * context. Because the base path is used by some of the derived classes for
	 * resolving relative file names it should contain a meaningful value. If
	 * other methods are used for determining the location of the configuration
	 * file (e.g. <code>setFile()</code> or <code>setURL()</code>), the base
	 * path is automatically set.
	 * 
	 * @param basePath
	 *            the base path.
	 */
	public void setBasePath(String basePath) {
		sourceURL = null;
		this.basePath = basePath;
	}

	/**
	 * Return the file where the configuration is stored. If the base path is a
	 * URL with a protocol different than &quot;file&quot;, or the configuration
	 * file is within a compressed archive, the return value will not point to a
	 * valid file object.
	 * 
	 * @return the file where the configuration is stored; this can be
	 *         <b>null</b>
	 */
	public File getFile() {
		if (getFileName() == null && sourceURL == null) {
			return null;
		} else if (sourceURL != null) {
			return ConfigUtils.fileFromURL(sourceURL);
		} else {
			return ConfigUtils.getFile(getBasePath(), getFileName());
		}
	}

	/**
	 * Set the file where the configuration is stored. The passed in file is
	 * made absolute if it is not yet. Then the file's path component becomes
	 * the base path and its name component becomes the file name.
	 * 
	 * @param file
	 *            the file where the configuration is stored
	 */
	public void setFile(File file) {
		sourceURL = null;
		setFileName(file.getName());
		setBasePath((file.getParentFile() != null) ? file.getParentFile()
				.getAbsolutePath() : null);
	}

	/**
	 * Returns the full path to the file this configuration is based on. The
	 * return value is a valid File path only if this configuration is based on
	 * a file on the local disk. If the configuration was loaded from a packed
	 * archive the returned value is the string form of the URL from which the
	 * configuration was loaded.
	 * 
	 * @return the full path to the configuration file
	 */
	public String getPath() {
		String path = null;
		File file = getFile();
		// if resource was loaded from jar file may be null
		if (file != null) {
			path = file.getAbsolutePath();
		}

		// try to see if file was loaded from a jar
		if (path == null) {
			if (sourceURL != null) {
				path = sourceURL.getPath();
			} else {
				try {
					path = ConfigUtils.getURL(getBasePath(), getFileName())
							.getPath();
				} catch (MalformedURLException e) {
					// simply ignore it and return null
					;
				}
			}
		}

		return path;
	}

	/**
	 * Sets the location of this configuration as a full or relative path name.
	 * The passed in path should represent a valid file name on the file system.
	 * It must not be used to specify relative paths for files that exist in
	 * classpath, either plain file system or compressed archive, because this
	 * method expands any relative path to an absolute one which may end in an
	 * invalid absolute path for classpath references.
	 * 
	 * @param path
	 *            the full path name of the configuration file
	 */
	public void setPath(String path) {
		setFile(new File(path));
	}

	/**
	 * Return the URL where the configuration is stored.
	 * 
	 * @return the configuration's location as URL
	 */
	public URL getURL() {
		return (sourceURL != null) ? sourceURL : ConfigUtils.locate(
				getBasePath(), getFileName());
	}

	/**
	 * Set the location of this configuration as a URL. For loading this can be
	 * an arbitrary URL with a supported protocol. If the configuration is to be
	 * saved, too, a URL with the &quot;file&quot; protocol should be provided.
	 * 
	 * @param url
	 *            the location of this configuration as URL
	 */
	public void setURL(URL url) {
		setBasePath(ConfigUtils.getBasePath(url));
		setFileName(ConfigUtils.getFileName(url));
		sourceURL = url;
	}

	public void setAutoSave(boolean autoSave) {
		this.autoSave = autoSave;
	}

	public boolean isAutoSave() {
		return autoSave;
	}

	/**
	 * Save the configuration if the automatic persistence is enabled and if a
	 * file is specified.
	 */
	protected void possiblySave() {
		if (autoSave && fileName != null) {
			try {
				save();
			} catch (IOException e) {
				throw new RuntimeException("Failed to auto-save", e);
			}
		}
	}


	/**
	 * Sets a new value for the specified property. This implementation checks
	 * if the auto save mode is enabled and saves the configuration if
	 * necessary.
	 * 
	 * @param key
	 *            the key of the affected property
	 * @param value
	 *            the value
	 */
	public void setProperty(String key, Object value) {
		super.setProperty(key, value);
		possiblySave();
	}

	public void clearProperty(String key) {
		super.clearProperty(key);
		possiblySave();
	}

	/**
	 * Performs a reload operation if necessary. This method is called on each
	 * access of this configuration. It asks the associated reloading strategy
	 * whether a reload should be performed. If this is the case, the
	 * configuration is cleared and loaded again from its source. If this
	 * operation causes an exception, the registered error listeners will be
	 * notified. The error event passed to the listeners is of type
	 * <code>EVENT_RELOAD</code> and contains the exception that caused the
	 * event.
	 */
	public void reload() {
		synchronized (reloadLock) {
			if (getLogger().isInfoEnabled()) {
				getLogger().info("Reloading configuration. URL is " + getURL());
			}
			fireEvent(EVENT_RELOAD, null, getURL(), true);
			setDetailEvents(false);
			boolean autoSaveBak = this.isAutoSave(); // save the current state
			this.setAutoSave(false); // deactivate autoSave to prevent
										// information loss
			try {
//				clear();
//				load();
			} catch (Exception e) {
				getLogger().error(e);
			} finally {
				this.setAutoSave(autoSaveBak); // set autoSave to previous value
				setDetailEvents(true);
			}
			fireEvent(EVENT_RELOAD, null, getURL(), false);
		}
	}

	/**
	 * Enters the &quot;No reloading mode&quot;. As long as this mode is active
	 * no reloading will be performed. This is necessary for some
	 * implementations of <code>save()</code> in derived classes, which may
	 * cause a reload while accessing the properties to save. This may cause the
	 * whole configuration to be erased. To avoid this, this method can be
	 * called first. After a call to this method there always must be a
	 * corresponding call of <code>{@link #exitNoReload()}</code> later! (If
	 * necessary, <code>finally</code> blocks must be used to ensure this.
	 */
	protected void enterNoReload() {
		synchronized (reloadLock) {
			noReload++;
		}
	}

	/**
	 * Leaves the &quot;No reloading mode&quot;.
	 * 
	 * @see #enterNoReload()
	 */
	protected void exitNoReload() {
		synchronized (reloadLock) {
			if (noReload > 0) // paranoia check
			{
				noReload--;
			}
		}
	}

	/**
	 * Sends an event to all registered listeners. This implementation ensures
	 * that no reloads are performed while the listeners are invoked. So
	 * infinite loops can be avoided that can be caused by event listeners
	 * accessing the configuration's properties when they are invoked.
	 * 
	 * @param type
	 *            the event type
	 * @param propName
	 *            the name of the property
	 * @param propValue
	 *            the value of the property
	 * @param before
	 *            the before update flag
	 */
	protected void fireEvent(int type, String propName, Object propValue,
			boolean before) {
		enterNoReload();
		try {
			super.fireEvent(type, propName, propValue, before);
		} finally {
			exitNoReload();
		}
	}

	public Object getProperty(String key) {
		synchronized (reloadLock) {
			reload();
			return super.getProperty(key);
		}
	}

	public boolean isEmpty() {
		reload();
		return super.isEmpty();
	}

	public boolean containsKey(String key) {
		reload();
		return super.containsKey(key);
	}

	/**
	 * Returns an <code>Iterator</code> with the keys contained in this
	 * configuration. This implementation performs a reload if necessary before
	 * obtaining the keys. The <code>Iterator</code> returned by this method
	 * points to a snapshot taken when this method was called. Later changes at
	 * the set of keys (including those caused by a reload) won't be visible.
	 * This is because a reload can happen at any time during iteration, and it
	 * is impossible to determine how this reload affects the current iteration.
	 * When using the iterator a client has to be aware that changes of the
	 * configuration are possible at any time. For instance, if after a reload
	 * operation some keys are no longer present, the iterator will still return
	 * those keys because they were found when it was created.
	 * 
	 * @return an <code>Iterator</code> with the keys of this configuration
	 */
	public Iterator getKeys() {
		reload();
		List keyList = new LinkedList();
		enterNoReload();
		try {
			for (Iterator it = super.getKeys(); it.hasNext();) {
				keyList.add(it.next());
			}

			return keyList.iterator();
		} finally {
			exitNoReload();
		}
	}

	/**
	 * Create the path to the specified file.
	 * 
	 * @param file
	 *            the target file
	 */
	private void createPath(File file) {
		if (file != null) {
			// create the path to the file if the file doesn't exist
			if (!file.exists()) {
				File parent = file.getParentFile();
				if (parent != null && !parent.exists()) {
					parent.mkdirs();
				}
			}
		}
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Object clone() {
		AbstractFileConfig copy = (AbstractFileConfig) super
				.clone();
		copy.setBasePath(null);
		copy.setFileName(null);
		return copy;
	}

	private void closeSilent(OutputStream out) {
		try {
			if (out != null) {
				out.close();
			}
		} catch (IOException e) {
			getLogger().warn("Could not close output stream", e);
		}
	}
}
