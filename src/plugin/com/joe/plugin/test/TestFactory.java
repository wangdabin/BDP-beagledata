package com.joe.plugin.test;

// JDK imports
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.joe.core.utils.ObjectCache;
import com.joe.plugin.Extension;
import com.joe.plugin.ExtensionPoint;
import com.joe.plugin.PluginRepository;
import com.joe.plugin.PluginRuntimeException;



/** Creates and caches {@link Parser} plugins.*/
public final class TestFactory {
  
  public static final Logger LOG = Logger.getLogger(TestFactory.class);
  
  /** Wildcard for default plugins. */
  public static final String DEFAULT_PLUGIN = "*";
  
  /** Empty extension list for caching purposes. */
  private final List EMPTY_EXTENSION_LIST = Collections.EMPTY_LIST;
  
  private Configuration conf;
  private ExtensionPoint extensionPoint;
  private TestPluginList parsePluginList;

  public TestFactory(Configuration conf) {
    this.conf = conf;
    ObjectCache objectCache = ObjectCache.get(conf);
    this.extensionPoint = PluginRepository.get(conf).getExtensionPoint(
    		TestPlugin.X_POINT_ID);
    this.parsePluginList = (TestPluginList)objectCache.getObject(TestPluginList.class.getName());
    if (this.parsePluginList == null) {
      this.parsePluginList = new ParsePluginsReader().parse(conf);
      objectCache.setObject(TestPluginList.class.getName(), this.parsePluginList);
    }

    if (this.extensionPoint == null) {
      throw new RuntimeException("x point " + TestPlugin.X_POINT_ID + " not found.");
    }
    if (this.parsePluginList == null) {
      throw new RuntimeException(
          "Parse Plugins preferences could not be loaded.");
    }
  }                      
  
   
  /**
   * Function returns an array of {@link Parser}s for a given content type.
   *
   * The function consults the internal list of parse plugins for the
   * ParserFactory to determine the list of pluginIds, then gets the
   * appropriate extension points to instantiate as {@link Parser}s.
   *
   * @param contentType The contentType to return the <code>Array</code>
   *                    of {@link Parser}s for.
   * @param url The url for the content that may allow us to get the type from
   *            the file suffix.
   * @return An <code>Array</code> of {@link Parser}s for the given contentType.
   *         If there were plugins mapped to a contentType via the
   *         <code>parse-plugins.xml</code> file, but never enabled via
   *         the <code>plugin.includes</code> Nutch conf, then those plugins
   *         won't be part of this array, i.e., they will be skipped.
   *         So, if the ordered list of parsing plugins for
   *         <code>text/plain</code> was <code>[parse-text,parse-html,
   *         parse-rtf]</code>, and only <code>parse-html</code> and
   *         <code>parse-rtf</code> were enabled via
   *         <code>plugin.includes</code>, then this ordered Array would
   *         consist of two {@link Parser} interfaces,
   *         <code>[parse-html, parse-rtf]</code>.
   */
  public TestPlugin[] getParsers(String content)
  throws TestNotFound {
    
    List<TestPlugin> parsers = null;
    List<Extension> parserExts = null;
    
    ObjectCache objectCache = ObjectCache.get(conf);
    
    // TODO once the MimeTypes is available
    // parsers = getExtensions(MimeUtils.map(contentType));
    // if (parsers != null) {
    //   return parsers;
    // }
    // Last Chance: Guess content-type from file url...
    // parsers = getExtensions(MimeUtils.getMimeType(url));

    parserExts = getExtensions(content);
    if (parserExts == null) {
      throw new TestNotFound(content);
    }

    parsers = new Vector<TestPlugin>(parserExts.size());
    for (Iterator i=parserExts.iterator(); i.hasNext(); ){
      Extension ext = (Extension) i.next();
      TestPlugin p = null;
      try {
        //check to see if we've cached this parser instance yet
        p = (TestPlugin) objectCache.getObject(ext.getId());
        if (p == null) {
          // go ahead and instantiate it and then cache it
          p = (TestPlugin) ext.getExtensionInstance();
          objectCache.setObject(ext.getId(),p);
        }
        parsers.add(p);
      } catch (PluginRuntimeException e) {
          LOG.warn("ParserFactory:PluginRuntimeException when "
                 + "initializing parser plugin "
                 + ext.getDescriptor().getPluginId()
                 + " instance in getParsers "
                 + "function: attempting to continue instantiating parsers");
      } 
    }
    return parsers.toArray(new TestPlugin[]{});
  }
    
  /**
   * Function returns a {@link Parser} instance with the specified
   * <code>extId</code>, representing its extension ID. If the Parser
   * instance isn't found, then the function throws a
   * <code>ParserNotFound</code> exception. If the function is able to find
   * the {@link Parser} in the internal <code>PARSER_CACHE</code> then it
   * will return the already instantiated Parser. Otherwise, if it has to
   * instantiate the Parser itself , then this function will cache that Parser
   * in the internal <code>PARSER_CACHE</code>.
   * 
   * @param id The string extension ID (e.g.,
   *        "org.apache.nutch.parse.rss.RSSParser",
   *        "org.apache.nutch.parse.rtf.RTFParseFactory") of the {@link Parser}
   *        implementation to return.
   * @return A {@link Parser} implementation specified by the parameter
   *         <code>id</code>.
   * @throws TestNotFound If the Parser is not found (i.e., registered with
   *         the extension point), or if the there a
   *         {@link PluginRuntimeException} instantiating the {@link Parser}.
   */
  public TestPlugin getParserById(String id) throws TestNotFound {

    Extension[] extensions = this.extensionPoint.getExtensions();
    Extension parserExt = null;

    ObjectCache objectCache = ObjectCache.get(conf);
    
    if (id != null) {
      parserExt = getExtension(extensions, id);
    }
    if (parserExt == null) {
      parserExt = getExtensionFromAlias(extensions, id);
    }

    if (parserExt == null) {
      throw new TestNotFound("No Parser Found for id [" + id + "]");
    }
    
    // first check the cache	    	   
    if (objectCache.getObject(parserExt.getId()) != null) {
      return (TestPlugin) objectCache.getObject(parserExt.getId());

    // if not found in cache, instantiate the Parser    
    } else {
      try {
    	  TestPlugin p = (TestPlugin) parserExt.getExtensionInstance();
        objectCache.setObject(parserExt.getId(), p);
        return p;
      } catch (PluginRuntimeException e) {
          LOG.warn("Canno initialize parser " +
                   parserExt.getDescriptor().getPluginId() +
                   " (cause: " + e.toString());
        throw new TestNotFound("Cannot init parser for id [" + id + "]");
      }
    }
  }
  
  /**
   * Finds the best-suited parse plugin for a given contentType.
   * 
   * @param contentType Content-Type for which we seek a parse plugin.
   * @return a list of extensions to be used for this contentType.
   *         If none, returns <code>null</code>.
   */
  protected List<Extension> getExtensions(String contentType) {
    
    ObjectCache objectCache = ObjectCache.get(conf);
    // First of all, tries to clean the content-type
    String type = contentType;


    List<Extension> extensions = (List<Extension>) objectCache.getObject(type);

    // Just compare the reference:
    // if this is the empty list, we know we will find no extension.
    if (extensions == EMPTY_EXTENSION_LIST) {
      return null;
    }
    
    if (extensions == null) {
      extensions = findExtensions(type);
      if (extensions != null) {
        objectCache.setObject(type, extensions);
      } else {
      	// Put the empty extension list into cache
      	// to remember we don't know any related extension.
        objectCache.setObject(type, EMPTY_EXTENSION_LIST);
      }
    }
    return extensions;
  }
  
  /**
   * searches a list of suitable parse plugins for the given contentType.
   * <p>It first looks for a preferred plugin defined in the parse-plugin
   * file.  If none is found, it returns a list of default plugins.
   * 
   * @param contentType Content-Type for which we seek a parse plugin.
   * @return List - List of extensions to be used for this contentType.
   *                If none, returns null.
   */
  private List<Extension> findExtensions(String contentType) {
    
    Extension[] extensions = this.extensionPoint.getExtensions();
    
    // Look for a preferred plugin.
    List<String> parsePluginList =
      this.parsePluginList.getPluginList(contentType);
    List<Extension> extensionList =
      matchExtensions(parsePluginList, extensions, contentType);
    if (extensionList != null) {
      return extensionList;
    }
    
    // If none found, look for a default plugin.
    parsePluginList = this.parsePluginList.getPluginList(DEFAULT_PLUGIN);
    return matchExtensions(parsePluginList, extensions, DEFAULT_PLUGIN);
  }
  
  /**
   * Tries to find a suitable parser for the given contentType.
   * <ol>
   * <li>It checks if a parser which accepts the contentType
   * can be found in the <code>plugins</code> list;</li>
   * <li>If this list is empty, it tries to find amongst the loaded
   * extensions whether some of them might suit and warns the user.</li>
   * </ol>
   * @param plugins List of candidate plugins.
   * @param extensions Array of loaded extensions.
   * @param contentType Content-Type for which we seek a parse plugin.
   * @return List - List of extensions to be used for this contentType.
   *                If none, returns null.
   */
  private List<Extension> matchExtensions(List<String> plugins,
                               Extension[] extensions,
                               String contentType) {
    
    List<Extension> extList = new ArrayList<Extension>();
    if (plugins != null) {
      
      for (String parsePluginId : plugins) {
        
        Extension ext = getExtension(extensions, parsePluginId, contentType);
        // the extension returned may be null
        // that means that it was not enabled in the plugin.includes
        // nutch conf property, but it was mapped in the
        // parse-plugins.xml
        // file. 
        // OR it was enabled in plugin.includes, but the plugin's plugin.xml
        // file does not claim that the plugin supports the specified mimeType
        // in either case, LOG the appropriate error message to WARN level
        
        if (ext == null) {
          //try to get it just by its pluginId
          ext = getExtension(extensions, parsePluginId);
          
            if (ext != null) {
              // plugin was enabled via plugin.includes
              // its plugin.xml just doesn't claim to support that
              // particular mimeType
              LOG.warn("ParserFactory:Plugin: " + parsePluginId +
                       " mapped to contentType " + contentType +
                       " via parse-plugins.xml, but " + "its plugin.xml " +
                       "file does not claim to support contentType: " +
                       contentType);
            } else {
              // plugin wasn't enabled via plugin.includes
              LOG.warn("ParserFactory: Plugin: " + parsePluginId + 
                       " mapped to contentType " + contentType +
                       " via parse-plugins.xml, but not enabled via " +
                       "plugin.includes in nutch-default.xml");                     
            }
        }

        if (ext != null) {
          // add it to the list
          extList.add(ext);
        }
      }
      
    } else {
      // okay, there were no list of plugins defined for
      // this mimeType, however, there may be plugins registered
      // via the plugin.includes nutch conf property that claim
      // via their plugin.xml file to support this contentType
      // so, iterate through the list of extensions and if you find
      // any extensions where this is the case, throw a
      // NotMappedParserException
      
      for (int i=0; i<extensions.length; i++) {
      	if ("*".equals(extensions[i].getAttribute("contentType"))){
          extList.add(0, extensions[i]);
        }
        else if (extensions[i].getAttribute("contentType") != null
            && contentType.matches(escapeContentType(extensions[i].getAttribute("contentType")))) {
          extList.add(extensions[i]);
        }
      }
      
      if (extList.size() > 0) {
        if (LOG.isInfoEnabled()) {
          StringBuffer extensionsIDs = new StringBuffer("[");
          boolean isFirst = true;
          for (Extension ext : extList){
        	  if (!isFirst) extensionsIDs.append(" - ");
        	  else isFirst=false;
        	  extensionsIDs.append(ext.getId());
          }
    	  extensionsIDs.append("]");
          LOG.info("The parsing plugins: " + extensionsIDs.toString() +
                   " are enabled via the plugin.includes system " +
                   "property, and all claim to support the content type " +
                   contentType + ", but they are not mapped to it  in the " +
                   "parse-plugins.xml file");
        }
      } else if (LOG.isDebugEnabled()) {
        LOG.debug("ParserFactory:No parse plugins mapped or enabled for " +
                  "contentType " + contentType);
      }
    }
    
    return (extList.size() > 0) ? extList : null;
  }
  
  private String escapeContentType(String contentType) {
  	// Escapes contentType in order to use as a regex 
  	// (and keep backwards compatibility).
  	// This enables to accept multiple types for a single parser. 
  	return contentType.replace("+", "\\+").replace(".", "\\.");
	}

  private boolean match(Extension extension, String id, String type) {
    return ((id.equals(extension.getId())) &&
            (extension.getAttribute("contentType").equals("*") || 
             type.matches(escapeContentType(extension.getAttribute("contentType"))) ||
             type.equals(DEFAULT_PLUGIN)));
  }
  
  /** Get an extension from its id and supported content-type. */
  private Extension getExtension(Extension[] list, String id, String type) {
    for (int i=0; i<list.length; i++) {
      if (match(list[i], id, type)) {
        return list[i];
      }
    }
    return null;
  }
    
  private Extension getExtension(Extension[] list, String id) {
    for (int i=0; i<list.length; i++) {
      if (id.equals(list[i].getId())) {
        return list[i];
      }
    }
    return null;
  }
  
  private Extension getExtensionFromAlias(Extension[] list, String id) {
    return getExtension(list, parsePluginList.getAliases().get(id));
  }

}
