package com.joe.plugin;

/**
 * <code>MissingDependencyException</code> will be thrown if a plugin
 * dependency cannot be found.
 * 
 * @author joe
 */
public class MissingDependencyException extends Exception {

  private static final long serialVersionUID = 1L;

  public MissingDependencyException(Throwable cause) {
    super(cause);
  }

  public MissingDependencyException(String message) {
    super(message);
  }
}
