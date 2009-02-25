package com.nestorurquiza.ws.axis.client;

import org.apache.axis.Constants;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.wsdl.fromJava.Types;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

import javax.xml.namespace.QName;
import java.io.IOException;

import com.nestorurquiza.ws.axis.service.Person;

public class PersonSer implements Serializer
{
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";

    /** SERIALIZER STUFF
     */
    /**
     * Serialize an element named name, with the indicated attributes
     * and value.
     * @param name is the element name
     * @param attributes are the attributes...serialize is free to add more.
     * @param value is the value
     * @param context is the SerializationContext
     */
    public void serialize(QName name, Attributes attributes,
                          Object value, SerializationContext context)
        throws IOException
    {
        if (!(value instanceof Person))
            throw new IOException("Can't serialize a " + value.getClass().getName() + " with a DataSerializer.");
        Person person = (Person)value;

        context.startElement(name, attributes);
        context.serialize(new QName("", FIRSTNAME), null, person.firstName);
        context.serialize(new QName("", LASTNAME), null, person.lastName);
        context.endElement();
    }
    public String getMechanismType() { return Constants.AXIS_SAX; }

    public Element writeSchema(Class javaType, Types types) throws Exception {
        return null;
    }
}
