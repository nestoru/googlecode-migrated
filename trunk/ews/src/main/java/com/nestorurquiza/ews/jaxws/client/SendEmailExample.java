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

import com.nestorurquiza.ews.jaxws.ArrayOfRealItemsType;
import com.nestorurquiza.ews.jaxws.ArrayOfRecipientsType;
import com.nestorurquiza.ews.jaxws.ArrayOfResponseMessagesType;
import com.nestorurquiza.ews.jaxws.BaseFolderIdType;
import com.nestorurquiza.ews.jaxws.BaseFolderType;
import com.nestorurquiza.ews.jaxws.BasePathToElementType;
import com.nestorurquiza.ews.jaxws.BodyType;
import com.nestorurquiza.ews.jaxws.BodyTypeType;
import com.nestorurquiza.ews.jaxws.ConstantValueType;
import com.nestorurquiza.ews.jaxws.CreateItemResponseType;
import com.nestorurquiza.ews.jaxws.CreateItemType;
import com.nestorurquiza.ews.jaxws.DefaultShapeNamesType;
import com.nestorurquiza.ews.jaxws.DistinguishedFolderIdNameType;
import com.nestorurquiza.ews.jaxws.DistinguishedFolderIdType;
import com.nestorurquiza.ews.jaxws.EmailAddressType;
import com.nestorurquiza.ews.jaxws.ExchangeService;
import com.nestorurquiza.ews.jaxws.ExchangeServicePortType;
import com.nestorurquiza.ews.jaxws.FieldURIOrConstantType;
import com.nestorurquiza.ews.jaxws.FindFolderResponseMessageType;
import com.nestorurquiza.ews.jaxws.FindFolderResponseType;
import com.nestorurquiza.ews.jaxws.FindFolderType;
import com.nestorurquiza.ews.jaxws.FolderQueryTraversalType;
import com.nestorurquiza.ews.jaxws.FolderResponseShapeType;
import com.nestorurquiza.ews.jaxws.IsEqualToType;
import com.nestorurquiza.ews.jaxws.ItemInfoResponseMessageType;
import com.nestorurquiza.ews.jaxws.ItemType;
import com.nestorurquiza.ews.jaxws.MessageDispositionType;
import com.nestorurquiza.ews.jaxws.MessageType;
import com.nestorurquiza.ews.jaxws.NonEmptyArrayOfAllItemsType;
import com.nestorurquiza.ews.jaxws.NonEmptyArrayOfBaseFolderIdsType;
import com.nestorurquiza.ews.jaxws.ObjectFactory;
import com.nestorurquiza.ews.jaxws.PathToUnindexedFieldType;
import com.nestorurquiza.ews.jaxws.ResponseClassType;
import com.nestorurquiza.ews.jaxws.ResponseMessageType;
import com.nestorurquiza.ews.jaxws.RestrictionType;
import com.nestorurquiza.ews.jaxws.SensitivityChoicesType;
import com.nestorurquiza.ews.jaxws.SingleRecipientType;
import com.nestorurquiza.ews.jaxws.TargetFolderIdType;
import com.nestorurquiza.ews.jaxws.UnindexedFieldURIType;


/**
 * This is just a proof of concept to show how to use Exchange Web Services (EWS) to send an email and store it in a specific Sent Items child folder.
 * 
 * @author nestor
 *
 */
public class SendEmailExample {
    private static final Logger log = LoggerFactory.getLogger(SendEmailExample.class);
    
    public static void main(String[] args) throws MalformedURLException {
        EwsSettings ewsSettings = EwsSettings.getInstance();
        
        //Parameters
        String sentItemsSubFolderName = "1006";
        String from = ewsSettings.getUserName();
        String to = from;
        String subject = "A test from  ews-jaxws";
        String body = "Sending from java ews-jaxws and storing in folder SentItems/1006";
        String mailboxCulture = "en-US";
        
        
        //This works with the default URL included in the wsdl file from which the Stubs were generated
        //ExchangeService service = new ExchangeService(); 
        
        //This should be used to make the code environment independent
        
        URL wsdlURL = new URL(ewsSettings.getWsdlPath());
        ExchangeService service = new ExchangeService(wsdlURL, new QName("http://schemas.microsoft.com/exchange/services/2006/messages", ewsSettings.getServiceStubName()));
        
        ExchangeServicePortType port = service.getExchangeServicePort();
        ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, ewsSettings.getUserName());
        ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, ewsSettings.getPassword());
        
        //
        //Find Sent Items subfolder id
        //
        
        //Configure basic find folder operation
        FolderResponseShapeType folderResponseShapeType = new FolderResponseShapeType();
        folderResponseShapeType.setBaseShape(DefaultShapeNamesType.DEFAULT);
        FindFolderType findFolderType = new FindFolderType();
        findFolderType.setFolderShape(folderResponseShapeType);
        findFolderType.setTraversal(FolderQueryTraversalType.SHALLOW);
        
        //Search by folder name
        RestrictionType restrictionType = new RestrictionType();
        IsEqualToType isEqualToType = new IsEqualToType();
        FieldURIOrConstantType fieldURI = new FieldURIOrConstantType();
        ConstantValueType constant = new ConstantValueType();
        constant.setValue(sentItemsSubFolderName);
        fieldURI.setConstant(constant);
        isEqualToType.setFieldURIOrConstant(fieldURI);
        PathToUnindexedFieldType pathToUnindexedField = new PathToUnindexedFieldType();
        pathToUnindexedField.setFieldURI(UnindexedFieldURIType.FOLDER_DISPLAY_NAME);
        ObjectFactory objectFactory = new ObjectFactory();
        JAXBElement<? extends BasePathToElementType> fieldURIExpression = objectFactory.createFieldURI(pathToUnindexedField);
        isEqualToType.setPath(fieldURIExpression);
        JAXBElement<IsEqualToType> isEqualToTypeExpression = objectFactory.createIsEqualTo(isEqualToType);
        restrictionType.setSearchExpression(isEqualToTypeExpression);
        findFolderType.setRestriction(restrictionType);
        

        //Set parent folder
        NonEmptyArrayOfBaseFolderIdsType nonEmptyArrayOfBaseFolderIdsType = new NonEmptyArrayOfBaseFolderIdsType();
        List<BaseFolderIdType> baseFolderIdTypes = nonEmptyArrayOfBaseFolderIdsType.getFolderIdOrDistinguishedFolderId();
        DistinguishedFolderIdType distinguishedFolderIdType = new DistinguishedFolderIdType();
        distinguishedFolderIdType.setId(DistinguishedFolderIdNameType.SENTITEMS);
        baseFolderIdTypes.add(distinguishedFolderIdType);
        findFolderType.setParentFolderIds(nonEmptyArrayOfBaseFolderIdsType);

        //Response objects definition
        FindFolderResponseType findFolderResponse = new FindFolderResponseType();
        Holder<FindFolderResponseType> findFolderResponseTypeHolder = new Holder<FindFolderResponseType>(findFolderResponse);
        
        

        //Folder to store sent items
        BaseFolderType baseFolderType = null;
        
        //Request
        port.findFolder(findFolderType, ewsSettings.getRequestServerVersion(), findFolderResponseTypeHolder, ewsSettings.getServerVersionInfoHolder());
        
        //Response will contain just the folder we search so there is no need for a loop. I am leaving the loop just for educational purposes
        List<JAXBElement <? extends ResponseMessageType>> findFolderResponses = findFolderResponseTypeHolder.value.getResponseMessages().getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage();
        for(JAXBElement <? extends ResponseMessageType> jaxResponse : findFolderResponses){
            ResponseMessageType response = jaxResponse.getValue();
            if(response.getResponseClass().equals(ResponseClassType.ERROR)){
                    throw new RuntimeException("Failed to find folder: " + response.getMessageText());
            } else if(response.getResponseClass().equals(ResponseClassType.WARNING)){
                    throw new RuntimeException("Find folder Warning: " + response.getMessageText());
            } else if(response.getResponseClass().equals(ResponseClassType.SUCCESS)){
                    FindFolderResponseMessageType findFolderResponseMessageType = (FindFolderResponseMessageType)response;
                    List<BaseFolderType> allFolders =  findFolderResponseMessageType.getRootFolder().getFolders().getFolderOrCalendarFolderOrContactsFolder();
                    for(BaseFolderType folder : allFolders){
                            log.debug("Found folder " + folder.getDisplayName());
                            baseFolderType = folder;
                            
                    }
            }
        }


        
        //
        //Send Email and store it in the Sent Items subfolder
        //
        
        //Create email request
        CreateItemType emailRequest = new CreateItemType();
        emailRequest.setMessageDisposition(MessageDispositionType.SEND_AND_SAVE_COPY);
        
        
        //Location for sent items subfolder
        //distinguishedFolderIdType = new DistinguishedFolderIdType();
        //distinguishedFolderIdType.setId(DistinguishedFolderIdNameType.SENTITEMS);
        //targetFolderIdType.setDistinguishedFolderId(distinguishedFolderIdType);
        TargetFolderIdType targetFolderIdType = new TargetFolderIdType(); 
        targetFolderIdType.setFolderId(baseFolderType.getFolderId());
        emailRequest.setSavedItemFolderId(targetFolderIdType);
        
        
        //Email message.
        MessageType messageType = new MessageType();
        messageType.setSubject(subject);
        BodyType bodyType = new BodyType();
        bodyType.setBodyType(BodyTypeType.TEXT);
        bodyType.setValue(body);
        messageType.setBody(bodyType);
        EmailAddressType senderEmailAddressType = new EmailAddressType();
        senderEmailAddressType.setEmailAddress(from);
        SingleRecipientType sender = new SingleRecipientType();
        sender.setMailbox(senderEmailAddressType);
        messageType.setSender(sender);
        
        ArrayOfRecipientsType recipientsArrayOfRecipientsType = new ArrayOfRecipientsType();
        EmailAddressType toEmailAddressType = new EmailAddressType();
        toEmailAddressType.setEmailAddress(to);
        recipientsArrayOfRecipientsType.getMailbox().add(toEmailAddressType);
        messageType.setToRecipients(recipientsArrayOfRecipientsType);
        messageType.setSensitivity(SensitivityChoicesType.NORMAL);
       
        //message.setCcRecipients(arrayOfRecipientsType);
        //message.setBccRecipients(arrayOfRecipientsType);

        // Add message to email request.
        NonEmptyArrayOfAllItemsType nonEmptyArrayOfAllItemsType = new NonEmptyArrayOfAllItemsType();
        nonEmptyArrayOfAllItemsType.getItemOrMessageOrCalendarItem().add(messageType);
        emailRequest.setItems(nonEmptyArrayOfAllItemsType);
       

        //We do not use the below and that is why we need to overload the corresponding methods
        //ExchangeImpersonationType impersonation = new ExchangeImpersonationType();
        //SerializedSecurityContextType s2SAuth = new SerializedSecurityContextType();
        
        CreateItemResponseType itemResponseType = new CreateItemResponseType();
        Holder<CreateItemResponseType> itemResult = new Holder(itemResponseType);

        //send email
        port.createItem(emailRequest,
                mailboxCulture,
                ewsSettings.getRequestServerVersion() ,
                itemResult,
                ewsSettings.getServerVersionInfoHolder());
        
        //get response
        ArrayOfResponseMessagesType arrayOfResponseMessagesType = itemResult.value.getResponseMessages();
        List<JAXBElement<? extends ResponseMessageType>> responseMessageTypeList = arrayOfResponseMessagesType.getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage();
        for (JAXBElement<? extends ResponseMessageType> jaxBElement : responseMessageTypeList) {
            ItemInfoResponseMessageType itemInfoResponseMessageType = (ItemInfoResponseMessageType) jaxBElement.getValue();
            ResponseClassType responseClassType = itemInfoResponseMessageType.getResponseClass();
            if( ResponseClassType.SUCCESS == responseClassType ) {
                log.debug("Email was sent and stored in folder " + baseFolderType.getDisplayName()); 
            } else {
                throw new RuntimeException("Fatal Error. Email could not be sent. Cause: " + itemInfoResponseMessageType.getMessageText());
            }
            ArrayOfRealItemsType arrayOfRealItemsType = (ArrayOfRealItemsType) itemInfoResponseMessageType.getItems();
            //List<BaseFolderType> folderTypes = 
            List<ItemType> itemTypes = arrayOfRealItemsType.getItemOrMessageOrCalendarItem();
            for( ItemType itemType : itemTypes ) {
                MessageType createdMessageType = (MessageType) itemType;
                log.debug("Item created: " + createdMessageType.getItemId().getId() ); 
            }
        }
    }

}
