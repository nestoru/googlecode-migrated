package com.nestorurquiza.ws.axis.client;

import org.apache.axis.Constants;
import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.DeserializerImpl;
import org.apache.axis.encoding.FieldTarget;
import org.apache.axis.message.SOAPHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import java.util.Hashtable;

import com.nestorurquiza.ws.axis.service.Person;

public class PersonDeser extends DeserializerImpl
{
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName"; 
    
    private Hashtable typesByMemberName = new Hashtable();  
    
    public PersonDeser()
    {
        typesByMemberName.put(FIRSTNAME, Constants.XSD_STRING);
        typesByMemberName.put(LASTNAME, Constants.XSD_STRING); 
        value = new Person();
    }
    
    /** DESERIALIZER STUFF - event handlers
     */

    /**
     * This method is invoked when an element start tag is encountered.
     * @param namespace is the namespace of the element
     * @param localName is the name of the element
     * @param prefix is the element's prefix
     * @param attributes are the attributes on the element...used to get the type
     * @param context is the DeserializationContext
     */
    public SOAPHandler onStartChild(String namespace,
                                    String localName,
                                    String prefix,
                                    Attributes attributes,
                                    DeserializationContext context)
        throws SAXException
    {
        //System.out.println("[value, namespace, localName,prefix,attributes,context]=[" + value + "," + namespace + "," + localName + "," + prefix + "," + attributes + "," + context);
        QName typeQName = (QName)typesByMemberName.get(localName);
        if (typeQName == null){
            //throw new SAXException("Invalid element in Data struct - " + localName);
            //Not a valid element which is OK as soon as we are expecting it.
            return null;
        }
        // These can come in either order.
        Deserializer dSer = context.getDeserializerForType(typeQName);
        try {
            dSer.registerValueTarget(new FieldTarget(value, localName));
        } catch (NoSuchFieldException e) {
            throw new SAXException(e);
        }
        
        if (dSer == null)
            throw new SAXException("No deserializer for a " + typeQName + "???");
        
        return (SOAPHandler)dSer;
    }
}
