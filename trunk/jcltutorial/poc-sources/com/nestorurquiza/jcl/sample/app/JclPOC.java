package com.nestorurquiza.jcl.sample.app;

import xeus.jcl.JarClassLoader;
import xeus.jcl.JclObjectFactory;
import xeus.jcl.JclUtils;

public class JclPOC{
  public static void main(String[] args) throws Exception{
    while(true) {
      printMessage();
      Thread.currentThread().sleep(10000);
    }
  }
  
  private static void printMessage() throws Exception{
    JarClassLoader jcl = new JarClassLoader(); 
    
    //Load jar file 
    jcl.add("plugin.jar");   
    JclObjectFactory factory = JclObjectFactory.getInstance();  

    //Create object of loaded class  
    Object obj = factory.create(jcl,"com.nestorurquiza.jcl.sample.plugin.PluginImpl");
    
    //Obtain interface reference in the current classloader  
    Plugin pluginInterfaceReference = JclUtils.cast(obj, Plugin.class);   

    System.out.println(pluginInterfaceReference.getMessage());
  }  
}


