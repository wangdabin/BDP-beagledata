package com.joe.plugin;


/**
 * <code>CircularDependencyException</code> will be thrown if a circular
 * dependency is detected.
 * 
 * @author joe
 */
public class CircularDependencyException extends Exception {

  private static final long serialVersionUID = 1L;

  public CircularDependencyException(Throwable cause) {
    super(cause);
  }

  public CircularDependencyException(String message) {
    super(message);
  }
}
