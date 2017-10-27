/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bug;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.w3c.dom.NodeList;

/**
 *
 * @author bkantor
 */
public class SOAPquery {

    private String Url;
    private String AcceptEncoding;
    private String ContentType;
    private String filterBody;
    private String SOAPAction;

    public SOAPquery() {
        System.setProperty("javax.xml.soap.MessageFactory","com.sun.xml.internal.messaging.saaj.soap.ver1_1.SOAPMessageFactory1_1Impl");
    }

    public String getSOAPAction() {
        return SOAPAction;
    }

    public void setSOAPAction(String SOAPAction) {
        this.SOAPAction = SOAPAction;
    }

    public String getUrl() {
        return Url;
    }

    public String getAcceptEncoding() {
        return AcceptEncoding;
    }

    public String getContentType() {
        return ContentType;
    }

    public String getFilterBody() {
        return filterBody;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public void setAcceptEncoding(String AcceptEncoding) {
        this.AcceptEncoding = AcceptEncoding;
    }

    public void setContentType(String ContentType) {
        this.ContentType = ContentType;
    }

    public void setFilterBody(String filterBody) {
        this.filterBody = filterBody;
    }

    public String getAnsver() throws UnsupportedEncodingException {
        String out = "";
        URL url;
        try {
            url = new URL(Url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("SOAPAction", SOAPAction);
            conn.setRequestProperty("Content-Type", ContentType);
            conn.setRequestProperty("Accept-Encoding", AcceptEncoding);
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            String bodyF = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wfe=\"http://www.blue-order.com/ma/workflowenginews/wfe\">\n<soapenv:Header/>\n<soapenv:Body>";
            bodyF += filterBody + "</soapenv:Body>\n</soapenv:Envelope>\n";
            wr.write(bodyF);
            wr.close();
            int success;
            String response;
            InputStream responseStream;
            try {
                responseStream = conn.getInputStream();
                success = 1;
            } catch (IOException e) {
                success = 0;
                if (conn.getResponseCode() == 500) {
                    responseStream = conn.getErrorStream();
                } else {
                    throw e;
                }
            }

            try {
                MessageFactory factory = MessageFactory.newInstance();
                SOAPMessage message = factory.createMessage(new MimeHeaders(), responseStream);
//                SOAPPart soapPart = message.getSOAPPart();
//                SOAPEnvelope envelope = soapPart.getEnvelope();
//                envelope.addNamespaceDeclaration("wfe", "http://www.blue-order.com/ma/workflowenginews/wfe");
//                envelope.addNamespaceDeclaration("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
                SOAPBody body = message.getSOAPBody();
                NodeList returnList = body.getElementsByTagName("ISLoginResponse");
                for (int k = 0; k < returnList.getLength(); k++) {
                    NodeList innerResultList = returnList.item(k).getChildNodes();
                    for (int l = 0; l < innerResultList.getLength(); l++) {
                        if (innerResultList.item(l).getNodeName().equalsIgnoreCase("ISLoginResult")) {
                            //uuid = innerResultList.item(l).getTextContent().trim();
                        }
                    }
                }
            }catch (SOAPException e) {}
            
            responseStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

}
