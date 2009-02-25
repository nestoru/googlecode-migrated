/**
 * DynamicServiceSampleService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.nestorurquiza.ws.axis.service;

public interface DynamicServiceSampleService extends javax.xml.rpc.Service {
    public java.lang.String getDynamicServiceSampleAddress();

    public com.nestorurquiza.ws.axis.service.DynamicServiceSample getDynamicServiceSample() throws javax.xml.rpc.ServiceException;

    public com.nestorurquiza.ws.axis.service.DynamicServiceSample getDynamicServiceSample(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
