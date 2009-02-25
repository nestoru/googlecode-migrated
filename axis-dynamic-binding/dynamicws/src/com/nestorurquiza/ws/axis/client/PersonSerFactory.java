package com.nestorurquiza.ws.axis.client;

import org.apache.axis.Constants;
import org.apache.axis.encoding.SerializerFactory;

import java.util.Iterator;
import java.util.Vector;

/**
 * SerializerFactory for PersonSer
 *
 * 
 */
public class PersonSerFactory implements SerializerFactory {
    private Vector mechanisms;

    public PersonSerFactory() {
    }
    public javax.xml.rpc.encoding.Serializer getSerializerAs(String mechanismType) {
        return new PersonSer();
    }
    public Iterator getSupportedMechanismTypes() {
        if (mechanisms == null) {
            mechanisms = new Vector();
            mechanisms.add(Constants.AXIS_SAX);
        }
        return mechanisms.iterator();
    }
}
