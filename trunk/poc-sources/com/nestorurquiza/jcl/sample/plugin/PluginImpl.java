package com.nestorurquiza.jcl.sample.plugin;

import com.nestorurquiza.jcl.sample.app.Plugin;

public class PluginImpl implements Plugin{
  private static final int version = 3;
  public String getMessage(){
    return "PluginImpl version=" + version + ". Loaded by: " + this.getClass().getClassLoader();
  }
}

