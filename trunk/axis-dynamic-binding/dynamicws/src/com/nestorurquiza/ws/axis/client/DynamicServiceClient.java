package com.nestorurquiza.ws.axis.client;

import com.nestorurquiza.ws.axis.service.Person;
import com.nestorurquiza.ws.axis.service.DynamicServiceSample;
import com.nestorurquiza.ws.axis.service.DynamicServiceSampleServiceLocator;

public class DynamicServiceClient {

    public static void main(String[] args) {

        try {
          //Use Locator generated from wsdl2java'
          DynamicServiceSample dynamicServiceSample = new DynamicServiceSampleServiceLocator().getDynamicServiceSample();
          Person person = dynamicServiceSample.getPerson("Fred Bar Foo");
          String firstName = person.firstName;         
          System.out.println("Response from DynamicServiceSample#getPerson()#getFirstName(): " + firstName);
          //String lastName = person.getLastName();         
          //System.out.println("Response from DynamicServiceSample#getPerson()#getLastName(): " + lastName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

