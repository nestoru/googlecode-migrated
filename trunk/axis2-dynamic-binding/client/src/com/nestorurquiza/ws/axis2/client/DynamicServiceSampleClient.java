package com.nestorurquiza.ws.axis2.client;

import com.nestorurquiza.ws.axis2.service.Person;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

import org.apache.axis2.databinding.utils.BeanUtil;

import org.apache.axis2.engine.DefaultObjectSupplier;

public class DynamicServiceSampleClient {

    private static EndpointReference targetEPR = 
        new EndpointReference("http://localhost:8085/axis2/services/DynamicServiceSample");

    public static OMElement getPersonPayload(String symbol) {
        OMFactory fac = OMAbstractFactory.getOMFactory();
        OMNamespace omNs = fac.createOMNamespace("http://dynamicws.nestorurquiza.com", "tns");

        OMElement method = fac.createOMElement("getPerson", omNs);
        OMElement value = fac.createOMElement("userName", omNs);
        value.addChild(fac.createOMText(value, symbol));
        method.addChild(value);
        return method;
    }
    
    public static void main(String[] args) {
        try {
            OMElement getPersonPayload = getPersonPayload("Right Said Fred");
            Options options = new Options();
            options.setTo(targetEPR);
            options.setTransportInProtocol(Constants.TRANSPORT_HTTP);

            ServiceClient sender = new ServiceClient();
            sender.setOptions(options);

            OMElement result = sender.sendReceive(getPersonPayload);

            OMElement firstElement = result.getFirstElement();
            Person person = (Person)BeanUtil.deserialize(Person.class, firstElement,  new DefaultObjectSupplier(), "person");
            System.err.print("Result: " + result + "\n\n");
            System.err.println("Extracting first Name ... " + person.getFirstName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

