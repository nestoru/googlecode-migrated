package com.nestorurquiza.ws.axis.client;

import org.apache.axis.Constants;
import org.apache.axis.encoding.DeserializerFactory;

import java.util.Iterator;
import java.util.Vector;

/**
 * DeserializerFactory for PersonDeser
 *
 * 
 */
public class PersonDeserFactory implements DeserializerFactory {
    private Vector mechanisms;

    public PersonDeserFactory() {
    }
    public javax.xml.rpc.encoding.Deserializer getDeserializerAs(String mechanismType) {
        return new PersonDeser();
    }
    public Iterator getSupportedMechanismTypes() {
        if (mechanisms == null) {
            mechanisms = new Vector();
            mechanisms.add(Constants.AXIS_SAX);
        }
        return mechanisms.iterator();
    }
}
