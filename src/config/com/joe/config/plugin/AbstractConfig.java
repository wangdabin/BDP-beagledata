package com.joe.config.plugin;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.configuration.PropertyConverter;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.joe.config.plugin.event.ConfigErrorEvent;
import com.joe.config.plugin.event.ConfigErrorListener;
import com.joe.config.plugin.event.EventSource;

public abstract class AbstractConfig extends EventSource implements Config {

	public static final int EVENT_ADD_PROPERTY = 1;

	public static final int EVENT_CLEAR_PROPERTY = 2;

	public static final int EVENT_SET_PROPERTY = 3;

	public static final int EVENT_CLEAR = 4;

	public static final int EVENT_READ_PROPERTY = 5;

	private static final char DISABLED_DELIMITER = '\0';

	private static char defaultListDelimiter = ',';

	private char listDelimiter = defaultListDelimiter;

	private boolean delimiterParsingDisabled;

	private boolean throwExceptionOnMissing;
	
	private Logger log;

	public static void setDefaultListDelimiter(char delimiter) {
		AbstractConfig.defaultListDelimiter = delimiter;
	}

	public static char getDefaultListDelimiter() {
		return AbstractConfig.defaultListDelimiter;
	}

	public void setListDelimiter(char listDelimiter) {
		this.listDelimiter = listDelimiter;
	}

	public char getListDelimiter() {
		return listDelimiter;
	}

	public boolean isDelimiterParsingDisabled() {
		return delimiterParsingDisabled;
	}

	public void setDelimiterParsingDisabled(boolean delimiterParsingDisabled) {
		this.delimiterParsingDisabled = delimiterParsingDisabled;
	}

	public void setThrowExceptionOnMissing(boolean throwExceptionOnMissing) {
		this.throwExceptionOnMissing = throwExceptionOnMissing;
	}

	public boolean isThrowExceptionOnMissing() {
		return throwExceptionOnMissing;
	}

	public void addErrorLogListener() {
		addErrorListener(new ConfigErrorListener() {
			public void configError(ConfigErrorEvent event) {
				getLogger().warn("Internal error", event.getCause());
			}
		});
	}

	protected abstract void setPropertyDirect(String key, Object value);

	protected String interpolate(String base) {
		Object result = interpolate((Object) base);
		return (result == null) ? null : result.toString();
	}

	protected Object interpolate(Object value) {
		return value;
		// return PropertyConverter.interpolate(value, this);
	}

	  /** 
	   * Set the array of string values for the <code>name</code> property as 
	   * as comma delimited values.  
	   * 
	   * @param name property name.
	   * @param values The values
	   */
	  public void setStrings(String name, String... values) {
	    setString(name, StringUtils.join(values, this.getListDelimiter()));
	  }
	
	  public void setString(String key,String value){
		  this.setProperty(key, value);
	  }
	  
	public void setProperty(String key, Object value) {
		fireEvent(EVENT_SET_PROPERTY, key, value, true);
		setDetailEvents(false);
		try {
			setPropertyDirect(key, value);
		} finally {
			setDetailEvents(true);
		}
		fireEvent(EVENT_SET_PROPERTY, key, value, false);
	}

	public void clearProperty(String key) {
		fireEvent(EVENT_CLEAR_PROPERTY, key, null, true);
		clearPropertyDirect(key);
		fireEvent(EVENT_CLEAR_PROPERTY, key, null, false);
	}

	protected void clearPropertyDirect(String key) {
		// override in sub classes
	}

	public void clear() {
		fireEvent(EVENT_CLEAR, null, null, true);
		setDetailEvents(false);
		boolean useIterator = true;
		try {
			Iterator it = getKeys();
			while (it.hasNext()) {
				String key = (String) it.next();
				if (useIterator) {
					try {
						it.remove();
					} catch (UnsupportedOperationException usoex) {
						useIterator = false;
					}
				}

				if (useIterator && containsKey(key)) {
					useIterator = false;
				}

				if (!useIterator) {
					// workaround for Iterators that do not remove the property
					// on calling remove() or do not support remove() at all
					clearProperty(key);
				}
			}
		} finally {
			setDetailEvents(true);
		}
		fireEvent(EVENT_CLEAR, null, null, false);
	}

	public boolean getBoolean(String key) {
		Boolean b = getBoolean(key, null);
		if (b != null) {
			return b.booleanValue();
		} else {
			throw new NoSuchElementException('\'' + key
					+ "' doesn't map to an existing object");
		}
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return getBoolean(key, BooleanUtils.toBooleanObject(defaultValue))
				.booleanValue();
	}

	public Boolean getBoolean(String key, Boolean defaultValue) {
		Object value = resolveContainerStore(key);

		if (value == null) {
			return defaultValue;
		} else {
			try {
				return PropertyConverter.toBoolean(interpolate(value));
			} catch (ConversionException e) {
				throw new ConversionException('\'' + key
						+ "' doesn't map to a Boolean object", e);
			}
		}
	}

	public byte getByte(String key) {
		Byte b = getByte(key, null);
		if (b != null) {
			return b.byteValue();
		} else {
			throw new NoSuchElementException('\'' + key
					+ " doesn't map to an existing object");
		}
	}

	public byte getByte(String key, byte defaultValue) {
		return getByte(key, new Byte(defaultValue)).byteValue();
	}

	public Byte getByte(String key, Byte defaultValue) {
		Object value = resolveContainerStore(key);

		if (value == null) {
			return defaultValue;
		} else {
			try {
				return PropertyConverter.toByte(interpolate(value));
			} catch (ConversionException e) {
				throw new ConversionException('\'' + key
						+ "' doesn't map to a Byte object", e);
			}
		}
	}

	public double getDouble(String key) {
		Double d = getDouble(key, null);
		if (d != null) {
			return d.doubleValue();
		} else {
			throw new NoSuchElementException('\'' + key
					+ "' doesn't map to an existing object");
		}
	}

	public double getDouble(String key, double defaultValue) {
		return getDouble(key, new Double(defaultValue)).doubleValue();
	}

	public Double getDouble(String key, Double defaultValue) {
		Object value = resolveContainerStore(key);

		if (value == null) {
			return defaultValue;
		} else {
			try {
				return PropertyConverter.toDouble(interpolate(value));
			} catch (ConversionException e) {
				throw new ConversionException('\'' + key
						+ "' doesn't map to a Double object", e);
			}
		}
	}

	public float getFloat(String key) {
		Float f = getFloat(key, null);
		if (f != null) {
			return f.floatValue();
		} else {
			throw new NoSuchElementException('\'' + key
					+ "' doesn't map to an existing object");
		}
	}

	public float getFloat(String key, float defaultValue) {
		return getFloat(key, new Float(defaultValue)).floatValue();
	}

	public Float getFloat(String key, Float defaultValue) {
		Object value = resolveContainerStore(key);

		if (value == null) {
			return defaultValue;
		} else {
			try {
				return PropertyConverter.toFloat(interpolate(value));
			} catch (ConversionException e) {
				throw new ConversionException('\'' + key
						+ "' doesn't map to a Float object", e);
			}
		}
	}

	public int getInt(String key) {
		Integer i = getInteger(key, null);
		if (i != null) {
			return i.intValue();
		} else {
			throw new NoSuchElementException('\'' + key
					+ "' doesn't map to an existing object");
		}
	}

	public int getInt(String key, int defaultValue) {
		Integer i = getInteger(key, null);

		if (i == null) {
			return defaultValue;
		}

		return i.intValue();
	}

	public Integer getInteger(String key, Integer defaultValue) {
		Object value = resolveContainerStore(key);

		if (value == null) {
			return defaultValue;
		} else {
			try {
				return PropertyConverter.toInteger(interpolate(value));
			} catch (ConversionException e) {
				throw new ConversionException('\'' + key
						+ "' doesn't map to an Integer object", e);
			}
		}
	}

	public long getLong(String key) {
		Long l = getLong(key, null);
		if (l != null) {
			return l.longValue();
		} else {
			throw new NoSuchElementException('\'' + key
					+ "' doesn't map to an existing object");
		}
	}

	public long getLong(String key, long defaultValue) {
		return getLong(key, new Long(defaultValue)).longValue();
	}

	public Long getLong(String key, Long defaultValue) {
		Object value = resolveContainerStore(key);

		if (value == null) {
			return defaultValue;
		} else {
			try {
				return PropertyConverter.toLong(interpolate(value));
			} catch (ConversionException e) {
				throw new ConversionException('\'' + key
						+ "' doesn't map to a Long object", e);
			}
		}
	}

	public short getShort(String key) {
		Short s = getShort(key, null);
		if (s != null) {
			return s.shortValue();
		} else {
			throw new NoSuchElementException('\'' + key
					+ "' doesn't map to an existing object");
		}
	}

	public short getShort(String key, short defaultValue) {
		return getShort(key, new Short(defaultValue)).shortValue();
	}

	public Short getShort(String key, Short defaultValue) {
		Object value = resolveContainerStore(key);

		if (value == null) {
			return defaultValue;
		} else {
			try {
				return PropertyConverter.toShort(interpolate(value));
			} catch (ConversionException e) {
				throw new ConversionException('\'' + key
						+ "' doesn't map to a Short object", e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see #setThrowExceptionOnMissing(boolean)
	 */
	public BigDecimal getBigDecimal(String key) {
		BigDecimal number = getBigDecimal(key, null);
		if (number != null) {
			return number;
		} else if (isThrowExceptionOnMissing()) {
			throw new NoSuchElementException('\'' + key
					+ "' doesn't map to an existing object");
		} else {
			return null;
		}
	}

	public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
		Object value = resolveContainerStore(key);

		if (value == null) {
			return defaultValue;
		} else {
			try {
				return PropertyConverter.toBigDecimal(interpolate(value));
			} catch (ConversionException e) {
				throw new ConversionException('\'' + key
						+ "' doesn't map to a BigDecimal object", e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see #setThrowExceptionOnMissing(boolean)
	 */
	public BigInteger getBigInteger(String key) {
		BigInteger number = getBigInteger(key, null);
		if (number != null) {
			return number;
		} else if (isThrowExceptionOnMissing()) {
			throw new NoSuchElementException('\'' + key
					+ "' doesn't map to an existing object");
		} else {
			return null;
		}
	}

	public BigInteger getBigInteger(String key, BigInteger defaultValue) {
		Object value = resolveContainerStore(key);

		if (value == null) {
			return defaultValue;
		} else {
			try {
				return PropertyConverter.toBigInteger(interpolate(value));
			} catch (ConversionException e) {
				throw new ConversionException('\'' + key
						+ "' doesn't map to a BigDecimal object", e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see #setThrowExceptionOnMissing(boolean)
	 */
	public String getString(String key) {
		String s = getString(key, null);
		if (s != null) {
			return s;
		} else if (isThrowExceptionOnMissing()) {
			throw new NoSuchElementException('\'' + key
					+ "' doesn't map to an existing object");
		} else {
			return null;
		}
	}

	public String getString(String key, String defaultValue) {
		Object value = resolveContainerStore(key);

		if (value instanceof String) {
			return interpolate((String) value);
		} else if (value == null) {
			return interpolate(defaultValue);
		} else {
			throw new ConversionException('\'' + key
					+ "' doesn't map to a String object");
		}
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String[] getStringArray(String key) {
		Object value = getProperty(key);
		String[] array;
		if (value instanceof String) {
			String val = (String)value;
			array = StringUtils.split(val, this.getListDelimiter());
		} else if (value instanceof List) {
			List list = (List) value;
			array = new String[list.size()];
			for (int i = 0; i < array.length; i++) {
				array[i] = interpolate((String) list.get(i));
			}
		} else if (value == null) {
			array = new String[0];
		} else {
			throw new ConversionException('\'' + key
					+ "' doesn't map to a String/List object");
		}
		return array;
	}

	protected Object resolveContainerStore(String key) {
		Object value = getProperty(key);
		if (value != null) {
			if (value instanceof Collection) {
				Collection collection = (Collection) value;
				value = collection.isEmpty() ? null : collection.iterator()
						.next();
			} else if (value.getClass().isArray() && Array.getLength(value) > 0) {
				value = Array.get(value, 0);
			}
		}

		return value;
	}

	public void copy(Config c) {
		if (c != null) {
			for (Iterator it = c.getKeys(); it.hasNext();) {
				String key = (String) it.next();
				Object value = c.getProperty(key);
				fireEvent(EVENT_SET_PROPERTY, key, value, true);
				setDetailEvents(false);
				try {
					setProperty(key, value);
				} finally {
					setDetailEvents(true);
				}
				fireEvent(EVENT_SET_PROPERTY, key, value, false);
			}
		}
	}

	public void append(Config c) {
		if (c != null) {
			for (Iterator it = c.getKeys(); it.hasNext();) {
				String key = (String) it.next();
				Object value = c.getProperty(key);
				fireEvent(EVENT_ADD_PROPERTY, key, value, true);
				setProperty(key, value);
				fireEvent(EVENT_ADD_PROPERTY, key, value, false);
			}
		}
	}
	
    public Logger getLogger()
    {
        return log;
    }

    public void setLogger(Logger log){
        this.log = (log != null) ? log : Logger.getLogger(getClass());
    }

}
