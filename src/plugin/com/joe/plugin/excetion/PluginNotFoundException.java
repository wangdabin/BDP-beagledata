package com.joe.plugin.excetion;

public class PluginNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PluginNotFoundException() {
		super();
	}

	public PluginNotFoundException(String message) {
		super(message);
	}

	public PluginNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PluginNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 * @param name
	 * @param version
	 * @return
	 */
	public static final PluginNotFoundException getNotFound(String name,String version){
		return new PluginNotFoundException("Name " + name + " version " + version + " plugin not found");
	}
	
	/**
	 * 
	 * @param name
	 * @param version
	 * @return
	 */
	public static final PluginNotFoundException getNotFoundPoint(String name,String version,String xPointId){
		return new PluginNotFoundException("Name " + name + " version " + version + " X_POINT_ID " + xPointId  +" plugin not found");
	}
	
	/**
	 * 
	 * @param name
	 * @param version
	 * @return
	 */
	public static final PluginNotFoundException getNotFoundExtension(String name,String version,String extensionId){
		return new PluginNotFoundException("Name " + name + " version " + version + " extension id " + extensionId  +" plugin not found");
	}
}
