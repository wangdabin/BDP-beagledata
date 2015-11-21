package com.joe.plugin;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * The <code>PluginClassLoader</code> contains only classes of the runtime
 * libraries setuped in the plugin manifest file and exported libraries of
 * plugins that are required pluguin. Libraries can be exported or not. Not
 * exported libraries are only used in the plugin own
 * <code>PluginClassLoader</code>. Exported libraries are available for
 * <code>PluginClassLoader</code> of plugins that depends on these plugins.
 * 
 * @author joe
 */
public class PluginClassLoader extends URLClassLoader {
  /**
   * Construtor
   * 
   * @param urls
   *          Array of urls with own libraries and all exported libraries of
   *          plugins that are required to this plugin
   * @param parent
   */
  public PluginClassLoader(URL[] urls, ClassLoader parent) {
    super(urls, parent);
  }
}
