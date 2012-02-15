
package com.nestorurquiza.ews.jaxws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PermissionReadAccessType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PermissionReadAccessType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="FullDetails"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PermissionReadAccessType")
@XmlEnum
public enum PermissionReadAccessType {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("FullDetails")
    FULL_DETAILS("FullDetails");
    private final String value;

    PermissionReadAccessType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PermissionReadAccessType fromValue(String v) {
        for (PermissionReadAccessType c: PermissionReadAccessType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}