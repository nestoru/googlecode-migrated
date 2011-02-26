package com.nestorurquiza.microsoft;


/**
 * Hello world!
 * 
 */
public class App {
/*
    public static void main(String[] args) throws Exception {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
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
        
        ExchangeCredentials credentials = new WebCredentials("nurquiza@mycompany.microsoftonline.com", "mypassword");
        service.setCredentials(credentials);
        service.setUrl( new URI("https://red001.mail.microsoftonline.com/EWS/Exchange.asmx") );
        //service.autodiscoverUrl("nurquiza@mycompany.microsoftonline.com"); //Exception in thread "main" microsoft.exchange.webservices.data.AutodiscoverLocalException: The Autodiscover service couldn't be located.
        
        
        service.setTraceEnabled(true);
        
        EmailMessage msg= new EmailMessage(service);
        msg.setSubject("From EWS"); 
        msg.setBody(MessageBody.getMessageBodyFromText("Using the Microsoft EWS Managed API"));
        msg.getToRecipients().add("nestor.urquiza@gmail.com");
        msg.send();

    }
    */
}
