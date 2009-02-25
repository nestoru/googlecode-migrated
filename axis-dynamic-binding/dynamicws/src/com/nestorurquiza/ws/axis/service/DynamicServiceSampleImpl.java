package com.nestorurquiza.ws.axis.service;

import java.io.Serializable;

public class DynamicServiceSampleImpl implements DynamicServiceSample, Serializable {
  static private final long serialVersionUID = 2L;

  public Person getPerson( String userName ) {
    Person person = new Person();
    person.firstName = getFirstName(userName);
    //person.lastName(getLastName(userName));
    return person;
  }
  
  private String getFirstName( String userName ) {
    String firstName = "unknown";
    System.out.println("Serving getFirstName(\"" + userName + "\")");
    if(!isEmpty(userName)) {
      firstName = userName.split(" ")[0];
    }
    return firstName;
  }
 
  private String getLastName( String userName ) {
    String lastName = "unknown";
    System.out.println("Serving getLastName(\"" + userName + "\")");
    if(!isEmpty(userName)) {
      String[] tokens = userName.split(" ");
      int length = tokens.length;      
      if( length > 0 ) {
        if( length > 1 ) {
          if( length > 2 ) {
            lastName = tokens[2];
          }else{
            lastName = tokens[1];
          }
        }else{
          //Just firstName provided
        }
      }
    }
    return lastName;
  }
  
  private boolean isEmpty(String myString){
    if(myString==null || myString.trim().length()==0)
      return true;
    else
      return false;
  }
}
