package com.nestorurquiza.ews.jaxws.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nestorurquiza.ews.jaxws.ArrayOfFoldersType;
import com.nestorurquiza.ews.jaxws.ArrayOfResponseMessagesType;
import com.nestorurquiza.ews.jaxws.BaseFolderType;
import com.nestorurquiza.ews.jaxws.CreateFolderResponseType;
import com.nestorurquiza.ews.jaxws.CreateFolderType;
import com.nestorurquiza.ews.jaxws.DistinguishedFolderIdNameType;
import com.nestorurquiza.ews.jaxws.DistinguishedFolderIdType;
import com.nestorurquiza.ews.jaxws.ExchangeService;
import com.nestorurquiza.ews.jaxws.ExchangeServicePortType;
import com.nestorurquiza.ews.jaxws.FolderInfoResponseMessageType;
import com.nestorurquiza.ews.jaxws.FolderType;
import com.nestorurquiza.ews.jaxws.NonEmptyArrayOfFoldersType;
import com.nestorurquiza.ews.jaxws.ResponseClassType;
import com.nestorurquiza.ews.jaxws.ResponseMessageType;
import com.nestorurquiza.ews.jaxws.TargetFolderIdType;

/**
 * This is just a proof of concept to show how to use Exchange Web Services
 * (EWS) to create a child folder.
 * 
 * @author nestor
 * 
 */
public class CreateFolderExample {
    private static final Logger log = LoggerFactory
            .getLogger(CreateFolderExample.class);

    public static void main(String[] args) throws MalformedURLException {

        // Parameters
        DistinguishedFolderIdNameType rootDistinguishedFolderIdNameType = DistinguishedFolderIdNameType.SENTITEMS;
        String subFolderName = "1006";
        String mailboxCulture = "en-US";

        // This should be used to make the code environment independent
        EwsSettings ewsSettings = EwsSettings.getInstance();
        URL wsdlURL = new URL(ewsSettings.getWsdlPath());
        ExchangeService service = new ExchangeService(wsdlURL, new QName(
                "http://schemas.microsoft.com/exchange/services/2006/messages",
                ewsSettings.getServiceStubName()));

        ExchangeServicePortType port = service.getExchangeServicePort();
        ((BindingProvider) port).getRequestContext().put(
                BindingProvider.USERNAME_PROPERTY, ewsSettings.getUserName());
        ((BindingProvider) port).getRequestContext().put(
                BindingProvider.PASSWORD_PROPERTY, ewsSettings.getPassword());

        // The request type
        CreateFolderType createFolderType = new CreateFolderType();
        createFolderType.setParentFolderId(new TargetFolderIdType());

        // Setup parent/root folder
        DistinguishedFolderIdType distinguishedFolderIdType = new DistinguishedFolderIdType();
        distinguishedFolderIdType.setId(rootDistinguishedFolderIdNameType);
        TargetFolderIdType targetFolderIdType = new TargetFolderIdType();
        targetFolderIdType.setDistinguishedFolderId(distinguishedFolderIdType);
        createFolderType.setParentFolderId(targetFolderIdType);

        // Setup child folder.
        FolderType folderType = new FolderType();
        folderType.setDisplayName(subFolderName);

        // Include the folder
        DistinguishedFolderIdType inbox = new DistinguishedFolderIdType();
        inbox.setId(DistinguishedFolderIdNameType.SENTITEMS);
        NonEmptyArrayOfFoldersType nonEmptyArrayOfFoldersType = new NonEmptyArrayOfFoldersType();
        List<BaseFolderType> baseFolderTypes = nonEmptyArrayOfFoldersType
                .getFolderOrCalendarFolderOrContactsFolder();
        baseFolderTypes.add(folderType);
        createFolderType.setFolders(nonEmptyArrayOfFoldersType);

        // Call the service
        Holder<CreateFolderResponseType> createFolderResponseType = new Holder<CreateFolderResponseType>();
        port.createFolder(createFolderType, mailboxCulture,
                ewsSettings.getRequestServerVersion(),
                createFolderResponseType,
                ewsSettings.getServerVersionInfoHolder());
        
        ArrayOfResponseMessagesType arrayOfResponseMessagesType = createFolderResponseType.value
                .getResponseMessages();
        List<JAXBElement<? extends ResponseMessageType>> responseMessageTypeList = arrayOfResponseMessagesType
                .getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage();
        for (JAXBElement<? extends ResponseMessageType> jaxBElement : responseMessageTypeList) {
            FolderInfoResponseMessageType response = (FolderInfoResponseMessageType) jaxBElement
                    .getValue();
            ResponseClassType responseClassType = response.getResponseClass();
            if( ResponseClassType.SUCCESS == responseClassType ) {
                ArrayOfFoldersType arrayOfFolderTypes = response.getFolders();
                List<BaseFolderType> folderTypes = arrayOfFolderTypes
                        .getFolderOrCalendarFolderOrContactsFolder();
                // Only one folder is created
                log.debug("Folder created, Id: " + folderTypes.get(0).getFolderId().getId());
            } else {
                throw new RuntimeException("Fatal Error. Folder could not be created. Cause: " + response.getMessageText());
            }
            
        }
    }
}
