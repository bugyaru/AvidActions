/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bug;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Node;
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
    private String filterTag;
    private String filterTagName;
    private String FilterPrefix;
    private String SOAPAction;
    public ArrayList<HashMap> wfData = new ArrayList<HashMap>();
    
    public SOAPquery() {
//            System.setProperty("javax.xml.soap.MessageFactory", "com.sun.xml.internal.messaging.saaj.soap.ver1_2.SOAPMessageFactory1_2Impl");
//            System.setProperty("javax.xml.bind.JAXBContext", "com.sun.xml.internal.bind.v2.ContextFactory");
    }

    public void setFilterPrefix(String FilterPrefix) {
        this.FilterPrefix = FilterPrefix;
    }

    public String getFilterPrefix() {
        return FilterPrefix;
    }

    public void setFilterTag(String filterTag) {
        this.filterTag = filterTag;
    }

    public void setFilterTagName(String filterTagName) {
        this.filterTagName = filterTagName;
    }

    public String getFilterTag() {
        return filterTag;
    }

    public String getFilterTagName() {
        return filterTagName;
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
            conn.setDoInput(true);
            conn.setRequestProperty("SOAPAction", SOAPAction);
            conn.setRequestProperty("Content-Type", ContentType);
            conn.setRequestProperty("Accept-Encoding", AcceptEncoding);
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            String bodyF = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wfe=\"http://www.blue-order.com/ma/workflowenginews/wfe\">\n<soapenv:Header/>\n<soapenv:Body>";
            bodyF += filterBody + "</soapenv:Body>\n</soapenv:Envelope>\n";
            wr.getEncoding();
            wr.write(bodyF);
            wr.close();
            int success;
            String response;
            InputStream responseStream = null;
            try {
                if (conn.getResponseCode() == 200) {
                    responseStream = conn.getInputStream();
                    success = 1;
                }
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
                SOAPMessage message = factory.createMessage(new MimeHeaders(), new GZIPInputStream(responseStream));
                SOAPBody body = message.getSOAPBody();
                NodeList ResultList;
                NodeList returnList = body.getElementsByTagName(filterTag);
                for (int k = 0; k < returnList.getLength(); k++) {
                    NodeList innerResultList = returnList.item(k).getChildNodes();
                    for (int l = 0; l < innerResultList.getLength(); l++) {
                        if (innerResultList.item(l).getNodeName().equalsIgnoreCase(filterTagName)) {
                            System.out.println(innerResultList.item(l).getNodeName());
                            ResultList = innerResultList.item(l).getChildNodes();
                            HashMap<String, String> hmap = new HashMap<String, String>();
                            for (int m = 0; m < ResultList.getLength(); m++) {
                                if (ResultList.item(m).getNodeType()==Node.ELEMENT_NODE) {
                                    hmap.put(FilterPrefix+ResultList.item(m).getNodeName(), ResultList.item(m).getTextContent());
                                    System.out.println(FilterPrefix+"_"+ResultList.item(m).getNodeName()+":"+ResultList.item(m).getTextContent());
                                }
                            }
                            wfData.add(hmap);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            responseStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

}