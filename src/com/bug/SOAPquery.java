/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bug;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author bkantor
 */
public class SOAPquery {

    private String Url;
    private String AcceptEncoding;
    private String ContentType;
    private String filterBody;

    public SOAPquery() {
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

    public String getAnsver() {
       String out = "";
//        URL url;
//        try {
//            url = new URL(Url);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setDoOutput(true);
//            //conn.setRequestProperty("SOAPAction", " http://www.blue-order.com/ma/integrationservicews/api/ISLogin");
//            conn.setRequestProperty("Content-Type", ContentType);
//            conn.setRequestProperty("Accept-Encoding", AcceptEncoding);
//            conn.setRequestProperty("Accept-Charset", "UTF-8");
//            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
//
//            wr.write();
//            wr.close();
//            int success;
//            String response;
//            InputStream responseStream;
//            try {
//                responseStream = conn.getInputStream();
//                success = 1;
//            } catch (IOException e) {
//                success = 0;
//                if (conn.getResponseCode() == 500) {
//                    responseStream = conn.getErrorStream();
//                } else {
//                    throw e;
//                }
//            }}
//            catch(Exception e){
//                e.printStackTrace();
//            }
        
            return out;
        }

    }
