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
import com.nestorurquiza.ews.jaxws.BaseFolderIdType;
import com.nestorurquiza.ews.jaxws.BaseFolderType;
import com.nestorurquiza.ews.jaxws.DefaultShapeNamesType;
import com.nestorurquiza.ews.jaxws.DistinguishedFolderIdNameType;
import com.nestorurquiza.ews.jaxws.DistinguishedFolderIdType;
import com.nestorurquiza.ews.jaxws.ExchangeService;
import com.nestorurquiza.ews.jaxws.ExchangeServicePortType;
import com.nestorurquiza.ews.jaxws.FolderInfoResponseMessageType;
import com.nestorurquiza.ews.jaxws.FolderResponseShapeType;
import com.nestorurquiza.ews.jaxws.FolderType;
import com.nestorurquiza.ews.jaxws.GetFolderResponseType;
import com.nestorurquiza.ews.jaxws.GetFolderType;
import com.nestorurquiza.ews.jaxws.NonEmptyArrayOfBaseFolderIdsType;
import com.nestorurquiza.ews.jaxws.ResponseMessageType;

/**
 * This is just a proof of concept to show how to use Exchange Web Services (EWS) to list total and unread inbox items.
 * @author nestor
 *
 */
public class InboxSummaryExample {
    private static final Logger log = LoggerFactory.getLogger(InboxSummaryExample.class);
    
    public static void main(String[] args) throws MalformedURLException {
        //Parameters
        String mailboxCulture = "en-US";
        
        //This works with the default URL included in the wsdl file from which the Stubs were generated
        //ExchangeService service = new ExchangeService(); 
        
        //This should be used to make the code environment independent
        EwsSettings ewsSettings = EwsSettings.getInstance();
        URL wsdlURL = new URL(ewsSettings.getWsdlPath());
        ExchangeService service = new ExchangeService(wsdlURL, new QName("http://schemas.microsoft.com/exchange/services/2006/messages", ewsSettings.getServiceStubName()));
        
        ExchangeServicePortType port = service.getExchangeServicePort();
        ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, ewsSettings.getUserName());
        ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, ewsSettings.getPassword());
        
        GetFolderType request = new GetFolderType();
        
        FolderResponseShapeType folderShape = new FolderResponseShapeType();
        folderShape.setBaseShape(DefaultShapeNamesType.ALL_PROPERTIES);

        request.setFolderShape(folderShape);
        
        DistinguishedFolderIdType inbox = new DistinguishedFolderIdType();
        inbox.setId(DistinguishedFolderIdNameType.INBOX);
        NonEmptyArrayOfBaseFolderIdsType folderIds = new NonEmptyArrayOfBaseFolderIdsType();
        List<BaseFolderIdType> ids = folderIds.getFolderIdOrDistinguishedFolderId();
        ids.add(inbox);
        
        request.setFolderIds(folderIds);
        
        //We do not use the below and that is why we need to overload the corresponding methods
        //ExchangeImpersonationType impersonation = new ExchangeImpersonationType();
        //SerializedSecurityContextType s2SAuth = new SerializedSecurityContextType();
        
        Holder<GetFolderResponseType> folderResult = new Holder<GetFolderResponseType>(); //Renamed from original getFolderResult to respect Java conventions

        
        //request.setItemIds(value)
        
        //port.getFolder(request, impersonation, s2SAuth, mailboxCulture, requestVersion, getFolderResult, serverVersion);
        port.getFolder(request, mailboxCulture, ewsSettings.getRequestServerVersion(), folderResult, ewsSettings.getServerVersionInfoHolder());
        
        GetFolderResponseType folderResponseType = folderResult.value;
        ArrayOfResponseMessagesType arrayOfResponseMessagesType = folderResponseType.getResponseMessages();
        List<JAXBElement<? extends ResponseMessageType>> responseMessageTypeList = arrayOfResponseMessagesType.getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage();
        for (JAXBElement<? extends ResponseMessageType> jaxBElement : responseMessageTypeList) {
            FolderInfoResponseMessageType response = (FolderInfoResponseMessageType) jaxBElement.getValue();
            ArrayOfFoldersType arrayOfFolderTypes = response.getFolders();
            //List<BaseFolderType> folderTypes = 
            List<BaseFolderType> folderTypes = arrayOfFolderTypes.getFolderOrCalendarFolderOrContactsFolder();
            for( BaseFolderType baseFolderType : folderTypes ) {
                FolderType folderType = (FolderType) baseFolderType;
                String displayName = folderType.getDisplayName();
                int totalCount = folderType.getTotalCount();
                int unread = folderType.getUnreadCount();
                log.debug(displayName + ":");
                log.debug("  Unread: " + unread ); 
                log.debug("  Total: " + totalCount ); 
            }
            //log.debug(folderResult.value.getResponseMessages());
            
        }

        
        
        
        //port.getFolder(request, impersonation, s2SAuth, mailboxCulture, requestVersion, getFolderResult, serverVersion);
        //port.sendItem(request, impersonation, s2SAuth, mailboxCulture, requestVersion, sendItemResult, serverVersion)
    }

}
