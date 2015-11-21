package com.joe.plugin.test;

import com.joe.plugin.Pluggable;
import com.sky.config.ConfigAble;

public interface TestPlugin extends Pluggable, ConfigAble {
  public final static String X_POINT_ID = TestPlugin.class.getName();

   String doTest(String content);
}
