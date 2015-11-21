package com.joe.plugin;

import java.util.HashMap;

import org.apache.commons.configuration.Configuration;

import com.joe.core.version.Name;
import com.sky.config.ConfigAble;


/**
 * An <code>Extension</code> is a kind of listener descriptor that will be
 * installed on a concrete <code>ExtensionPoint</code> that acts as kind of
 * Publisher.
 * 
 * @author joe
 */
public class Extension {
  private PluginDescriptor fDescriptor;
  private String fId;
  private String fName;
  private String fTargetPoint;
  private String fClazz;
  private HashMap<String, String> fAttributes;
  private Configuration conf;
  private PluginRepository pluginRepository;

  public Extension(PluginDescriptor pDescriptor, String pExtensionPoint,
	      String pId,String name, String pExtensionClass, Configuration conf,
	      PluginRepository pluginRepository) {
	    fAttributes = new HashMap<String, String>();
	    setDescriptor(pDescriptor);
	    setExtensionPoint(pExtensionPoint);
	    setId(pId);
	    setName(name);
	    setClazz(pExtensionClass);
	    this.conf = conf;
	    this.pluginRepository = pluginRepository;
	  }

  /**
   * @param point
   */
  private void setExtensionPoint(String point) {
    fTargetPoint = point;
  }

  /**
   * Returns a attribute value, that is setuped in the manifest file and is
   * definied by the extension point xml schema.
   * 
   * @param pKey
   *          a key
   * @return String a value
   */
  public String getAttribute(String pKey) {
    return fAttributes.get(pKey);
  }

  /**
   * Returns the full class name of the extension point implementation
   * 
   * @return String
   */
  public String getClazz() {
    return fClazz;
  }

  /**
   * Return the unique id of the extension.
   * 
   * @return String
   */
  public String getId() {
    return fId;
  }

  /**
   * Adds a attribute and is only used until model creation at plugin system
   * start up.
   * 
   * @param pKey a key
   * @param pValue a value
   */
  public void addAttribute(String pKey, String pValue) {
    fAttributes.put(pKey, pValue);
  }

  /**
   * Sets the Class that implement the concret extension and is only used until
   * model creation at system start up.
   * 
   * @param extensionClazz The extensionClasname to set
   */
  public void setClazz(String extensionClazz) {
    fClazz = extensionClazz;
  }

  /**
   * Sets the unique extension Id and is only used until model creation at
   * system start up.
   * 
   * @param extensionID The extensionID to set
   */
  public void setId(String extensionID) {
    fId = extensionID;
  }

  /**
   * Returns the Id of the extension point, that is implemented by this
   * extension.
   */
  public String getTargetPoint() {
    return fTargetPoint;
  }
  
  /**
   * 
   * @param name
   */
  public void setName(String name){
	  this.fName = name;
  }

  /**
   * 
   * @return
   */
  public String getName(){
	  return this.fName;
  }
  
  /**
   * Return an instance of the extension implementatio. Before we create a
   * extension instance we startup the plugin if it is not already done. The
   * plugin instance and the extension instance use the same
   * <code>PluginClassLoader</code>. Each Plugin use its own classloader. The
   * PluginClassLoader knows only own <i>Plugin runtime libraries </i> setuped
   * in the plugin manifest file and exported libraries of the depenedend
   * plugins.
   * 
   * @return Object An instance of the extension implementation
   */
  public Object getExtensionInstance() throws PluginRuntimeException {
    // Must synchronize here to make sure creation and initialization
    // of a plugin instance and it extension instance are done by
    // one and only one thread.
    // The same is in PluginRepository.getPluginInstance().
    synchronized (getId()) {
      try {
        PluginClassLoader loader = fDescriptor.getClassLoader();
        Class extensionClazz = loader.loadClass(getClazz());
        // lazy loading of Plugin in case there is no instance of the plugin
        // already.
        this.pluginRepository.getPluginInstance(getDescriptor());
        Object object = extensionClazz.newInstance();
        if (object instanceof ConfigAble) {
          ((ConfigAble) object).setConf(this.conf);
        }
        if(object instanceof Name){
        	((Name) object).setName(getId());
//        	((Name) object).setName(this.getDescriptor().getName());
        	((Name) object).setVersion(this.getDescriptor().getVersion());
        }
        return object;
      } catch (ClassNotFoundException e) {
        throw new PluginRuntimeException(e);
      } catch (InstantiationException e) {
        throw new PluginRuntimeException(e);
      } catch (IllegalAccessException e) {
        throw new PluginRuntimeException(e);
      }
    }
  }

  /**
   * return the plugin descriptor.
   * 
   * @return PluginDescriptor
   */
  public PluginDescriptor getDescriptor() {
    return fDescriptor;
  }

  /**
   * Sets the plugin descriptor and is only used until model creation at system
   * start up.
   * 
   * @param pDescriptor
   */
  public void setDescriptor(PluginDescriptor pDescriptor) {
    fDescriptor = pDescriptor;
  }
}
