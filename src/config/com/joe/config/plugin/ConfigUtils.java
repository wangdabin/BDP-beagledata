package com.joe.config.plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.joe.config.plugin.event.ConfigErrorEvent;
import com.joe.config.plugin.event.ConfigErrorListener;
import com.joe.config.plugin.event.EventSource;

/**
 * 
 * @author qiaolong
 *
 */
public final class ConfigUtils {
	
	static final String PROTOCOL_FILE = "file";

	static final String RESOURCE_PATH_SEPARATOR = "/";

	private static final String METHOD_CLONE = "clone";

	private static Logger log = Logger.getLogger(ConfigUtils.class);

	private ConfigUtils() {
		// to prevent instantiation...
	}

	public static void dump(Config config, PrintStream out) {
		dump(config, new PrintWriter(out));
	}

	public static void dump(Config config, PrintWriter out) {
		Iterator keys = config.getKeys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			Object value = config.getProperty(key);
			out.print(key);
			out.print("=");
			out.print(value);

			if (keys.hasNext()) {
				out.println();
			}
		}

		out.flush();
	}

	public static String toString(Config config) {
		StringWriter writer = new StringWriter();
		dump(config, new PrintWriter(writer));
		return writer.toString();
	}

	public static void copy(Config source, Config target) {
		Iterator keys = source.getKeys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			target.setProperty(key, source.getProperty(key));
		}
	}

	public static void append(Config source, Config target) {
		Iterator keys = source.getKeys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			target.setProperty(key, source.getProperty(key));
		}
	}

	public static Config cloneConfig(Config config)
			throws RuntimeException {
		if (config == null) {
			return null;
		} else {
			try {
				return (Config) clone(config);
			} catch (CloneNotSupportedException cnex) {
				throw new RuntimeException(cnex);
			}
		}
	}

	static Object clone(Object obj) throws CloneNotSupportedException {
		if (obj instanceof Cloneable) {
			try {
				Method m = obj.getClass().getMethod(METHOD_CLONE, null);
				return m.invoke(obj, null);
			} catch (NoSuchMethodException nmex) {
				throw new CloneNotSupportedException(
						"No clone() method found for class"
								+ obj.getClass().getName());
			} catch (IllegalAccessException iaex) {
				throw new RuntimeException(iaex);
			} catch (InvocationTargetException itex) {
				throw new RuntimeException(itex);
			}
		} else {
			throw new CloneNotSupportedException(obj.getClass().getName()
					+ " does not implement Cloneable");
		}
	}

	public static URL getURL(String basePath, String file)
			throws MalformedURLException {
		File f = new File(file);
		if (f.isAbsolute()) // already absolute?
		{
			return toURL(f);
		}

		try {
			if (basePath == null) {
				return new URL(file);
			} else {
				URL base = new URL(basePath);
				return new URL(base, file);
			}
		} catch (MalformedURLException uex) {
			return toURL(constructFile(basePath, file));
		}
	}

	static File constructFile(String basePath, String fileName) {
		File file = null;

		File absolute = null;
		if (fileName != null) {
			absolute = new File(fileName);
		}

		if (StringUtils.isEmpty(basePath)
				|| (absolute != null && absolute.isAbsolute())) {
			file = new File(fileName);
		} else {
			StringBuffer fName = new StringBuffer();
			fName.append(basePath);

			// My best friend. Paranoia.
			if (!basePath.endsWith(File.separator)) {
				fName.append(File.separator);
			}

			//
			// We have a relative path, and we have
			// two possible forms here. If we have the
			// "./" form then just strip that off first
			// before continuing.
			//
			if (fileName.startsWith("." + File.separator)) {
				fName.append(fileName.substring(2));
			} else {
				fName.append(fileName);
			}

			file = new File(fName.toString());
		}

		return file;
	}

	public static URL locate(String name) {
		return locate(null, name);
	}

	public static URL locate(String base, String name) {
		if (log.isDebugEnabled()) {
			StringBuffer buf = new StringBuffer();
			buf.append("ConfigurationUtils.locate(): base is ").append(base);
			buf.append(", name is ").append(name);
			log.debug(buf.toString());
		}

		if (name == null) {
			// undefined, always return null
			return null;
		}

		URL url = null;

		// attempt to create an URL directly
		try {
			if (base == null) {
				url = new URL(name);
			} else {
				URL baseURL = new URL(base);
				url = new URL(baseURL, name);

				// check if the file exists
				InputStream in = null;
				try {
					in = url.openStream();
				} finally {
					if (in != null) {
						in.close();
					}
				}
			}

			log.debug("Loading configuration from the URL " + url);
		} catch (IOException e) {
			url = null;
		}

		// attempt to load from an absolute path
		if (url == null) {
			File file = new File(name);
			if (file.isAbsolute() && file.exists()) // already absolute?
			{
				try {
					url = toURL(file);
					log.debug("Loading configuration from the absolute path "
							+ name);
				} catch (MalformedURLException e) {
					log.warn("Could not obtain URL from file", e);
				}
			}
		}

		// attempt to load from the base directory
		if (url == null) {
			try {
				File file = constructFile(base, name);
				if (file != null && file.exists()) {
					url = toURL(file);
				}

				if (url != null) {
					log.debug("Loading configuration from the path " + file);
				}
			} catch (MalformedURLException e) {
				log.warn("Could not obtain URL from file", e);
			}
		}

		// attempt to load from the user home directory
		if (url == null) {
			try {
				File file = constructFile(System.getProperty("user.home"), name);
				if (file != null && file.exists()) {
					url = toURL(file);
				}

				if (url != null) {
					log.debug("Loading configuration from the home path "
							+ file);
				}

			} catch (MalformedURLException e) {
				log.warn("Could not obtain URL from file", e);
			}
		}

		// attempt to load from classpath
		if (url == null) {
			url = locateFromClasspath(name);
		}
		return url;
	}

	static URL locateFromClasspath(String resourceName) {
		URL url = null;
		// attempt to load from the context classpath
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader != null) {
			url = loader.getResource(resourceName);

			if (url != null) {
				log.debug("Loading configuration from the context classpath ("
						+ resourceName + ")");
			}
		}

		// attempt to load from the system classpath
		if (url == null) {
			url = ClassLoader.getSystemResource(resourceName);

			if (url != null) {
				log.debug("Loading configuration from the system classpath ("
						+ resourceName + ")");
			}
		}
		return url;
	}

	static String getBasePath(URL url) {
		if (url == null) {
			return null;
		}

		String s = url.toString();

		if (s.endsWith("/") || StringUtils.isEmpty(url.getPath())) {
			return s;
		} else {
			return s.substring(0, s.lastIndexOf("/") + 1);
		}
	}

	static String getFileName(URL url) {
		if (url == null) {
			return null;
		}

		String path = url.getPath();

		if (path.endsWith("/") || StringUtils.isEmpty(path)) {
			return null;
		} else {
			return path.substring(path.lastIndexOf("/") + 1);
		}
	}

	public static File getFile(String basePath, String fileName) {
		// Check if the file name is absolute
		File f = new File(fileName);
		if (f.isAbsolute()) {
			return f;
		}

		// Check if URLs are involved
		URL url;
		try {
			url = new URL(new URL(basePath), fileName);
		} catch (MalformedURLException mex1) {
			try {
				url = new URL(fileName);
			} catch (MalformedURLException mex2) {
				url = null;
			}
		}

		if (url != null) {
			return fileFromURL(url);
		}

		return constructFile(basePath, fileName);
	}

	public static File fileFromURL(URL url) {
		if (PROTOCOL_FILE.equals(url.getProtocol())) {
			return new File(URLDecoder.decode(url.getPath()));
		} else {
			return null;
		}
	}

	static URL toURL(File file) throws MalformedURLException {
		try {
			Method toURI = file.getClass().getMethod("toURI", (Class[]) null);
			Object uri = toURI.invoke(file, (Class[]) null);
			Method toURL = uri.getClass().getMethod("toURL", (Class[]) null);
			URL url = (URL) toURL.invoke(uri, (Class[]) null);

			return url;
		} catch (Exception e) {
			throw new MalformedURLException(e.getMessage());
		}
	}

	public static void enableRuntimeExceptions(Config src) {
		if (!(src instanceof EventSource)) {
			throw new IllegalArgumentException(
					"Config must be derived from EventSource!");
		}
		((EventSource) src).addErrorListener(new ConfigErrorListener() {
			public void configError(ConfigErrorEvent event) {
				throw new RuntimeException(event.getCause());
			}
		});
	}
}
