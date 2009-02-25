package com.nestorurquiza.ws.axis.service;

import java.io.Serializable;

public class Address implements Serializable{
  static private final long serialVersionUID = 3L;

  private String country;
  private String zip;
  
  public String getCountry(){
    return country;
  }
  public void setCountry(String country){
    this.country = country;
  }
  
  public String getZip(){
    return zip;
  }
  public void setZip(String zip){
    this.zip = zip;
  }
  
}
