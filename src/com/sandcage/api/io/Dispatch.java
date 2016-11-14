
package com.sandcage.api.io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sandcage.api.service.put.ScheduledPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sandcage.api.SandCage;
import com.sandcage.api.service.Payload;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 *  This class dispatches a request to the SandCage API/endpoint of interest.
 *  
 *  @date       03/11/2016
 *  @version    0.2
 *  @see        <a href="https://www.sandcage.com/docs/0.2/">SandCage API v0.2</a>
 */
public class Dispatch {

    private static final String ENCODING_UTF8 = "UTF-8";

    private static final String HTTP_METHOD_POST = "POST";
    private static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HTTP_HEADER_CONTENT_LENGTH = "Content-Length";
    
    private static final String CONTENT_TYPE_JSON = "application/json; charset=UTF-8";

    private static final int TIMEOUT_CONNECT = 5000;                            //  5000ms
    private static final int TIMEOUT_READ = 10000;                              // 10000ms

    private String service;
    private Payload payload;

    /*  IMPORTANT NOTICE:
     *  
     *  Requests can be sent over HTTPS only. You may therefore require the JDK 
     *  JCE files or some crypto API, like Bouncy Castle. It is assumed that you 
     *  have the required crypto setup to allow you communicate over HTTPS, with 
     *  the sole exception that you may not have the SandCage SSL certificate 
     *  installed in your cacerts. It is important, therefore, that you install
     *  the SandCage API endpoint SSL certificate to your cacerts before invoking
     *  an instance of this class. 
     *  
     *  Nonetheless, to allow you to hit the ground running, static method 
     *  {@link #bypassSslVerification()} has been put in place to allow you to 
     *  bypass JVM checks which assure that the SandCage API certificate is 
     *  present in your cacerts (by default this features is commented-out, and 
     *  therefore disabled, so your JVM cacerts repo will be checked).
     *
     *  You can use this option to disable verification as a mechanism to exclude 
     *  other issues which you may experience during development. 
     *  
     *  NOTE: do not disable/bypass verification in a production environment.
     */
    static {
         // bypassVerification();                                                // Uncomment to disable SSL verification (you MUST COMMENT THIS OUT IN A PRODUCTION ENVIRONMENT)
    }


    /**
     *  Creates a {@link Dispatch}.
     * 
     *  @param  payload     the (specialized) {@link Payload} to dispatch to the 
     *                      SandCage API
     *  @param  service     the SandCage API service at which to dispatch the
     *                      (specialized) {@link Payload}
     */
    public Dispatch(Payload payload, String service) {
        this.payload = payload;
        this.service = service;
    }

    /**
     *  Dispatches, via HTTPS, a given (specialized) {@link Payload} to the 
     *  relevant SandCage API service.
     */
    public void post() {

        boolean dispatchSuccess = false;

        URL url = null;
        HttpsURLConnection connection = null;

        try {

            // Converts your specialized payload into a JSON payload
            String jsonPayload = jsonParser();

            // Prints the JSON request to the SandCage API to output
            System.out.println("Your JSON request to the SandCage API is:");
            System.out.println(jsonPayload);

            String endpoint = SandCage.getEndpointBase()+SandCage.getEndpointVersion()+"/"+this.service;

            // Prints out the url/endpoint
            System.out.println("Dispatching payload to:");
            System.out.println(endpoint);

            url = new URL(endpoint);
            connection = (HttpsURLConnection) url.openConnection();             // You must install the SandCage SSL cert in your cacerts
            connection.setRequestMethod(HTTP_METHOD_POST);
            connection.setInstanceFollowRedirects(Boolean.TRUE);
            connection.setConnectTimeout(TIMEOUT_CONNECT);
            connection.setReadTimeout(TIMEOUT_READ);
            connection.setUseCaches(false);
            connection.setRequestProperty(HTTP_HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
            connection.setRequestProperty(HTTP_HEADER_CONTENT_LENGTH, String.valueOf(jsonPayload.length()));
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            // Send JSON payload to the SandCage API
            OutputStream os = connection.getOutputStream();
            os.write(URLEncoder.encode(jsonPayload, ENCODING_UTF8).getBytes());
            os.close();
            os = null;

            int responseCode = connection.getResponseCode();

            if(responseCode==HttpsURLConnection.HTTP_OK)
                dispatchSuccess = true;

            // Prints the response code to output 
            System.out.println("Response code: "+responseCode);

            // Reads the response
            InputStream is = connection.getInputStream();
            ByteArrayOutputStream str = new ByteArrayOutputStream();
            int read = 0;
            byte[] buffer = new byte[8192];                                     // Increase the buffer size if you expect responses to be larger
            while((read=is.read(buffer))!=-1)
                str.write(buffer, 0, read);

            is.close();
            is = null;

            // Prints the response to your output channel
            System.out.println(new String(str.toByteArray(), "UTF-8"));

            str.close();
            str = null;

        } catch(JsonProcessingException jpex) {
            jpex.printStackTrace();
        } catch(MalformedURLException murlex) {
            murlex.printStackTrace();
        } catch(ProtocolException pex) {
            pex.printStackTrace();
        } catch(SocketTimeoutException stex) {
            stex.printStackTrace();
        } catch(IOException ioex) {
            ioex.printStackTrace();
        } finally {
            if(connection!=null)
                connection.disconnect();
            connection = null;
            url = null;

            // Prints the final commeont to output 
            System.out.println(dispatchSuccess?"Your request was successfully sent to the API":"An error occured with your request");
        }
    }

    /**
     *  Analyzes the {@link Payload} associated with the request, generating its
     *  JSON representation.
     * 
     *  @return     the JSON representation of the associated {@link ScheduledPayload}
     * 
     *  @throws     JsonProcessingException     if a parsing issue occurs
     */
    private String jsonParser() 
            throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        String json = mapper.writeValueAsString(this.payload);

        return json;
    }

    /**
     *  Sets {@link HttpsURLConnection} instances to bypass verification of the 
     *  SandCage API SSL certificate vis-a-vis your cacerts repo.
     *  <p>
     *  This option can be invoked as a mechanism to exclude other issues which 
     *  you may experience during the development process. 
     *  <p>
     *  <b>NOTE: do not disable verification in a production environment.</b>
     */
    private static void bypassVerification() {

        try {

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(
                null, 
                new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] xcs, String string) 
                            throws CertificateException {
                        }
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] xcs, String string) 
                            throws CertificateException {
                        }
                    }
                }, 
                new java.security.SecureRandom()
            );

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(
                new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                }
            );

        } catch(KeyManagementException kmex) {
            kmex.printStackTrace();
        } catch(NoSuchAlgorithmException nsaex) {
            nsaex.printStackTrace();
        }
    }
}