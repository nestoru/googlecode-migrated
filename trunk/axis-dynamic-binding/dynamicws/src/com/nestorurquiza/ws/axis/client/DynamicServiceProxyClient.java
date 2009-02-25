package com.nestorurquiza.ws.axis.client;

import com.nestorurquiza.ws.axis.service.Person;
import com.nestorurquiza.ws.axis.service.DynamicServiceSample;

import java.rmi.RemoteException;

import java.net.MalformedURLException;  
import java.net.URL; 

import javax.xml.namespace.QName; 
 
import javax.xml.rpc.ServiceException; 
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.Service;   
import javax.xml.rpc.ServiceFactory;  

import org.apache.axis.client.Call;

import org.w3c.dom.Element;

public class DynamicServiceProxyClient {

    public static void main(String args[]) {

        try {
            //Use Service Factory to create dynamic stubs            
            URL wsdlDocumentLocation = new URL("http://localhost:8081/axis/services/DynamicServiceSample?wsdl");
            String serviceName = "DynamicServiceSampleService";
            String nameSpaceURI = "urn:DynamicServiceSample";
            String portName = "DynamicServiceSample";
            String userName = "Fred Bar Foo"; 
            //tries ...
            
            //1
            //ServiceFactory serviceFactory = ServiceFactory.newInstance();
            //Service service = serviceFactory.createService(wsdlDocumentLocation, new QName(nameSpaceURI, serviceName));
            //DynamicServiceSample dynamicServiceSample = (DynamicServiceSample) service.getPort(new QName(nameSpaceURI, portName), DynamicServiceSample.class);
            //Person person = dynamicServiceSample.getPerson(userName);
            //String firstName = person.getFirstName();
            
            //2
            //org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            //Call call = (Call)service.createCall();
            //call.setTargetEndpointAddress(wsdlDocumentLocation);
            //call.setOperationName( new QName(nameSpaceURI, "getPerson") );
            //Person person = (Person)call.invoke(new Object [] {userName});
            //String firstName = person.getFirstName();
            
            //3
            //org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            //Call call = (Call) service.createCall();
            //QName personQName = new QName(nameSpaceURI,"Person");
            //call.registerTypeMapping(Person.class, personQName, new org.apache.axis.encoding.ser.BeanSerializerFactory(Person.class,personQName), new org.apache.axis.encoding.ser.BeanDeserializerFactory(Person.class,personQName));
            //call.setTargetEndpointAddress( wsdlDocumentLocation );
            //call.setOperationName( "getPerson");
            //call.setReturnType(org.apache.axis.encoding.XMLType.XSD_ANY);
            //call.addParameter("userName", new QName(nameSpaceURI, portName), ParameterMode.IN);
            //Person person = (Person)call.invoke(new Object[] {userName});
            //String firstName = person.getFirstName();
            
            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            Call call = (Call) service.createCall();
            QName personQName = new QName(nameSpaceURI,"Person");
            call.registerTypeMapping(Person.class, personQName, new PersonSerFactory(), new PersonDeserFactory());
            call.setTargetEndpointAddress( wsdlDocumentLocation );
            call.setOperationName( "getPerson");
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_ANY);
            //call.setReturnClass(Person.class);            
            call.addParameter("userName", new QName(nameSpaceURI, portName), ParameterMode.IN);
            //call.setEncodingStyle(nameSpaceURI);
            Person person = (Person)call.invoke(new Object[] {userName});
            String firstName = person.firstName;
            //call.setReturnType(new QName("http://xml.apache.org/xml-soap", "Element"));
            //Element element = (Element)call.invoke(new Object[] {userName});
            //String firstName = element.toString();
               
            System.out.println("Response from DynamicServiceSample#getPerson()#getFirstName(): " + firstName);            
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

