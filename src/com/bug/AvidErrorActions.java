/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bug;

import java.io.File;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author bkantor
 */
public class AvidErrorActions {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SOAPquery Q = new SOAPquery();
        command cmd = new command();
        notify ntf = new notify();
        JSONObject config = getGfg();
        JSONObject gen = config.getJSONObject("General");
        System.err.println(gen.getInt("CheckTime"));
        if (!gen.getJSONObject("smtp").getBoolean("auth")) {
            ntf.setSMTP(
                    gen.getJSONObject("smtp").getString("host"),
                    gen.getJSONObject("smtp").getInt("port"),
                    gen.getJSONObject("smtp").getBoolean("auth"), "", "", false);
        } else {
            ntf.setSMTP(
                    gen.getJSONObject("smtp").getString("host"),
                    gen.getJSONObject("smtp").getInt("port"),
                    gen.getJSONObject("smtp").getBoolean("auth"),
                    gen.getJSONObject("smtp").getString("user"),
                    gen.getJSONObject("smtp").getString("pass"),
                    gen.getJSONObject("smtp").getBoolean("tls")
            );
        }
        
        Q.setUrl(gen.getString("AvidWFE_Url"));
        Q.setAcceptEncoding(gen.getString("AvidWFE_AcceptEncoding"));
        Q.setContentType(gen.getString("AvidWFE_ContentType"));
        JSONArray Actions = config.getJSONArray("Action");
        for (int i = 0; i < Actions.length(); i++) {
            JSONObject act = Actions.getJSONObject(i);
            String SOAPVarNameact = "";
            String SOAPVarNames = "";
            String SOAPVarValueact = "";
            String SOAPVarValues = "";
            String SOAPFilteract = "";
            String SOAPFilter = "";
            try {
                SOAPVarNameact = act.getJSONObject("SOAPNames").getString("action");
            } catch (JSONException e) {
            }
            try {
                SOAPVarNames = act.getJSONObject("SOAPNames").getString("content");
            } catch (JSONException e) {
            }
            try {
                SOAPVarValueact = act.getJSONObject("SOAPValues").getString("action");
            } catch (JSONException e) {
            }
            try {
                SOAPVarValues = act.getJSONObject("SOAPValues").getString("content");
            } catch (JSONException e) {
            }
            try {
                SOAPFilteract = act.getJSONObject("SOAPFilter").getString("action");
            } catch (JSONException e) {
            }
            try {
                SOAPFilter = act.getJSONObject("SOAPFilter").getString("content");
            } catch (JSONException e) {
            }

            if (!SOAPFilter.isEmpty() && !SOAPFilteract.isEmpty()) {
                Q.setSOAPAction(SOAPFilteract);
                Q.setFilterBody(SOAPFilter);
                Q.setFilterTag("workflowEngine_GetWorkflowsResult");
                Q.setFilterTagName("WorkflowInfo");
                Q.setFilterPrefix("SOAPFilter");
                try {
                    System.out.println(Q.getAnsver());
                } catch (Exception ex) {
                    Logger.getLogger(AvidErrorActions.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (!SOAPVarNameact.isEmpty() && !SOAPVarValueact.isEmpty() && !SOAPVarNames.isEmpty() && !SOAPVarValues.isEmpty()) {

            }

            //System.out.println("SOAPNames: " + SOAPNM + "\n\n" + SOAPact);
            try {
                for (int ii = 0; ii < act.getJSONArray("command").length(); ii++) {
                    cmd.setType(act.getJSONArray("command").getJSONObject(ii).getString("type"));
                    if(cmd.getType().equals("exec")){
                    }
                    if(cmd.getType().equals("script")){
                    }
                }
            } catch (JSONException e) {
            }
            try {
                for (int ii = 0; ii < act.getJSONArray("notify").length(); ii++) {
                    ntf.setType(act.getJSONArray("notify").getJSONObject(ii).getString("type"));
                    if (ntf.getType().equals("mail")) {
                        ntf.setMail(act.getJSONArray("notify").getJSONObject(ii).getString("sendto"),
                                act.getJSONArray("notify").getJSONObject(ii).getString("recepient"),
                                act.getJSONArray("notify").getJSONObject(ii).getString("subject"),
                                act.getJSONArray("notify").getJSONObject(ii).getString("content")
                        );
                        //ntf.sendNotify();
                    }
                    if (ntf.getType().equals("xml")) {
                    }
                }
            } catch (JSONException e) {
            }
        }
        
    }

    public static JSONObject getGfg() {
        JSONObject out = null;
        File config = new File("config.xml");
        if (config.exists()) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(config);
                DOMSource source = new DOMSource(doc);
                StringWriter strb = new StringWriter();
                StreamResult result = new StreamResult(strb);
                TransformerFactory transFactory = TransformerFactory.newInstance();
                Transformer transformer = transFactory.newTransformer();
                transformer.transform(source, result);
                out = XML.toJSONObject(strb.toString()).getJSONObject("Config");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Not read config file");
        }
        return out;
    }
}
