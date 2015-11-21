package com.joe.plugin;

import org.apache.commons.configuration.Configuration;

/**
 * 插件类
 * @author qiaolong
 *
 */
public class SkyPlugin {
  private PluginDescriptor fDescriptor;
  protected Configuration conf;

  public SkyPlugin(PluginDescriptor pDescriptor, Configuration conf) {
    setDescriptor(pDescriptor);
    this.conf = conf;
  }

  public void startUp() throws PluginRuntimeException {
  }

  public void shutDown() throws PluginRuntimeException {
  }

  public PluginDescriptor getDescriptor() {
    return fDescriptor;
  }

  private void setDescriptor(PluginDescriptor descriptor) {
    fDescriptor = descriptor;
  }

  protected void finalize() throws Throwable {
    super.finalize();
    shutDown();
  }
}
