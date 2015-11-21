package com.joe.core.version;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.joe.core.utils.ClassUtil;


public class VersionInfo {
  private static final Log LOG = LogFactory.getLog(VersionInfo.class);

  private Properties info;

  protected VersionInfo(String component) {
    info = new Properties(); 
    String versionInfoFile = component + "-version-info.properties";
    try {
      InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("version/" + versionInfoFile);
      if (is == null) {
        throw new IOException("Resource not found");
      }
      info.load(is);
    } catch (IOException ex) {
      LogFactory.getLog(getClass()).warn("Could not read '" + 
        versionInfoFile + "', " + ex.toString(), ex);
    }
  }

  protected String _getVersion() {
    return info.getProperty("version", "Unknown");
  }

  protected String _getRevision() {
    return info.getProperty("revision", "Unknown");
  }

  protected String _getBranch() {
    return info.getProperty("branch", "Unknown");
  }

  protected String _getDate() {
    return info.getProperty("date", "Unknown");
  }

  protected String _getUser() {
    return info.getProperty("user", "Unknown");
  }

  protected String _getUrl() {
    return info.getProperty("url", "Unknown");
  }

  protected String _getSrcChecksum() {
    return info.getProperty("srcChecksum", "Unknown");
  }

  protected String _getBuildVersion(){
    return getVersion() +
      " from " + _getRevision() +
      " by " + _getUser() +
      " source checksum " + _getSrcChecksum();
  }

  protected String _getProtocVersion() {
    return info.getProperty("protocVersion", "Unknown");
  }

  private static VersionInfo COMMON_VERSION_INFO = new VersionInfo("bdp");

  public static String getVersion() {
    return COMMON_VERSION_INFO._getVersion();
  }
  
  
  public static String getRevision() {
    return COMMON_VERSION_INFO._getRevision();
  }

  public static String getBranch() {
    return COMMON_VERSION_INFO._getBranch();
  }

  public static String getDate() {
    return COMMON_VERSION_INFO._getDate();
  }
  
  public static String getUser() {
    return COMMON_VERSION_INFO._getUser();
  }
  
  public static String getUrl() {
    return COMMON_VERSION_INFO._getUrl();
  }

  public static String getSrcChecksum() {
    return COMMON_VERSION_INFO._getSrcChecksum();
  }

  public static String getBuildVersion(){
    return COMMON_VERSION_INFO._getBuildVersion();
  }

  public static String getProtocVersion(){
    return COMMON_VERSION_INFO._getProtocVersion();
  }

  public static void main(String[] args) {
    LOG.debug("version: "+ getVersion());
    System.out.println(BDPVersion.getProductName() + " " + getVersion());
//    System.out.println("Subversion " + getUrl() + " -r " + getRevision());
    System.out.println("Compiled by " + getUser() + " on " + getDate());
//    System.out.println("Compiled with protoc " + getProtocVersion());
//    System.out.println("From source with checksum " + getSrcChecksum());
//    System.out.println("This command was run using " + ClassUtil.findContainingJar(VersionInfo.class));
  }
}
