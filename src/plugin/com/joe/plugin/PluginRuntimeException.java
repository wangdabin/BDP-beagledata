package com.joe.plugin;
/**
 * <code>PluginRuntimeException</code> will be thrown until a exception in the
 * plugin managemnt occurs.
 * 
 * @author joe
 */
public class PluginRuntimeException extends Exception {

  private static final long serialVersionUID = 1L;

  public PluginRuntimeException(Throwable cause) {
    super(cause);
  }

  public PluginRuntimeException(String message) {
    super(message);
  }
}
