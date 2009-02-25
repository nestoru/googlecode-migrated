/**
 * DynamicServiceSampleSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.nestorurquiza.ws.axis.service;

public class DynamicServiceSampleSoapBindingSkeleton implements com.nestorurquiza.ws.axis.service.DynamicServiceSample, org.apache.axis.wsdl.Skeleton {
    private com.nestorurquiza.ws.axis.service.DynamicServiceSample impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getPerson", _params, new javax.xml.namespace.QName("", "getPersonReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("urn:DynamicServiceSample", "Person"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:DynamicServiceSample", "getPerson"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getPerson") == null) {
            _myOperations.put("getPerson", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getPerson")).add(_oper);
    }

    public DynamicServiceSampleSoapBindingSkeleton() {
        this.impl = new com.nestorurquiza.ws.axis.service.DynamicServiceSampleSoapBindingImpl();
    }

    public DynamicServiceSampleSoapBindingSkeleton(com.nestorurquiza.ws.axis.service.DynamicServiceSample impl) {
        this.impl = impl;
    }
    public com.nestorurquiza.ws.axis.service.Person getPerson(java.lang.String userName) throws java.rmi.RemoteException
    {
        com.nestorurquiza.ws.axis.service.Person ret = impl.getPerson(userName);
        return ret;
    }

}
