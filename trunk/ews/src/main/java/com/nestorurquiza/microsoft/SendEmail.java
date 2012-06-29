package com.nestorurquiza.microsoft;

import java.net.URI;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import microsoft.exchange.webservices.data.EmailAddress;
import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.ExchangeCredentials;
import microsoft.exchange.webservices.data.ExchangeService;
import microsoft.exchange.webservices.data.ExpandGroupResults;
import microsoft.exchange.webservices.data.MessageBody;
import microsoft.exchange.webservices.data.WebCredentials;


/**
 * Hello world!
 * 
 */
public class SendEmail {
    private static final String USER = "myuser";
    private static final String PASSWORD = "mypassword";
    private static final String SERVICE_URL = "https://red001.mail.microsoftonline.com/EWS/Exchange.asmx";
    private static final String DISTRIBUTION_LIST =  "mydistro@sample.com";
    private static final String MAIL_TO= "nestor.urquiza@sample.com";
    
    public static void main(String[] args) throws Exception {
        // Create a trust manager that does not validate certificate chains
        X509TrustManager[] trustAllCerts = new X509TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
            }
        } };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
        }

        ExchangeService service = new ExchangeService();

        // Setting credentials is unnecessary when you connect from a computer
        // that is
        // logged on to the domain.
        
        ExchangeCredentials credentials = new WebCredentials(USER, PASSWORD);
        service.setCredentials(credentials);
        service.setUrl( new URI(SERVICE_URL) );
        service.setTraceEnabled(true);
        
        String group = DISTRIBUTION_LIST;
        String allMembers = "";
        ExpandGroupResults myGroupMembers = service.expandGroup(group);
        for (EmailAddress address : myGroupMembers.getMembers()) {
            String email = address.getAddress();
            allMembers += allMembers.length() > 0 ? "; " +  email : email;
        }
        
        EmailMessage msg= new EmailMessage(service);
        msg.setSubject("From EWS"); 
        String body = "Using the Microsoft EWS Managed API";
        if(allMembers.length() > 0) {
            body += " Members of " + group + ": " + allMembers;
        }
        msg.setBody(MessageBody.getMessageBodyFromText(body));
        msg.getToRecipients().add(MAIL_TO);
        msg.send();

    }
}
