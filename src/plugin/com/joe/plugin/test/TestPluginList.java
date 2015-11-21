package com.joe.plugin.test;

// JDK imports
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class represents a natural ordering for which parsing plugin should get
 * called for a particular mimeType. It provides methods to store the
 * parse-plugins.xml data, and methods to retreive the name of the appropriate
 * parsing plugin for a contentType.
 *
 * @author mattmann
 * @version 1.0
 */
class TestPluginList {
  
  /* a map to link mimeType to an ordered list of parsing plugins */
  private Map<String, List<String>> fMimeTypeToPluginMap = null;
  
  /* A list of aliases */
  private Map<String, String> aliases = null;
  
  
  /**
   * Constructs a new ParsePluginList
   */
  TestPluginList() {
    fMimeTypeToPluginMap = new HashMap<String, List<String>>();
    aliases = new HashMap<String, String>();
  }
  
  List<String> getPluginList(String mimeType) {
    return fMimeTypeToPluginMap.get(mimeType);
  }

  void setAliases(Map<String, String> aliases) {
    this.aliases = aliases;
  }
  
  Map<String, String> getAliases() {
    return aliases;
  }
  
  void setPluginList(String mimeType, List<String> l) {
    fMimeTypeToPluginMap.put(mimeType, l);
  }
  
  List<String> getSupportedMimeTypes() {
    return Arrays.asList(fMimeTypeToPluginMap.keySet().toArray(
            new String[] {}));
  }
  
}
