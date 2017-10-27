/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bug;

import java.util.Properties;
import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.*;
import javax.mail.Provider;
import javax.mail.internet.*;


/**
 * <notify type="xml" outpath="./" xslt="" />
 * <notify type="mail" sendto="" recepient="" subject="">HI!</notify>
 *
 * @author bkantor
 */
public class notify {

    private String type = "";
    private String outpath = "";
    private String xslt = "";
    private String sendto = "";
    private String recepient = "";
    private String subject = "";
    private String body = "";
    private String host = "";
    private int port = 25;
    private String user = "";
    private String pass = "";

    public notify() {
    }

    public String getType() {
        return type;
    }

    public String getOutpath() {
        return outpath;
    }

    public String getXslt() {
        return xslt;
    }

    public String getSendto() {
        return sendto;
    }

    public String getRecepient() {
        return recepient;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOutpath(String outpath) {
        this.outpath = outpath;
    }

    public void setXslt(String xslt) {
        this.xslt = xslt;
    }

    public void setSMTP(String host, int port, String user, String pass) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
    }

    public void setMail(String sendto, String recepient, String subject , String body) {
        this.sendto = sendto;
        this.recepient = recepient;
        this.subject = subject;
        this.body = body;
   }
   
   public String sendNotify(){
        if("xml".equals(type)){
                
            return "xml";
        }else if("mail".equals(type)){
            final String username="your@gmail.com";
            final String password="password";
            Properties prop=new Properties();
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getDefaultInstance(prop,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
          }
        });
          try {
                 String body="Dear Renish Khunt Welcome";
                 String htmlBody = "<strong>This is an HTML Message</strong>";
                 String textBody = "This is a Text Message.";
         Message message = new MimeMessage(session);
         message.setFrom(new InternetAddress("your@gmail.com"));
                 message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("receiver@gmail.com"));
        message.setSubject("Testing Subject");
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
            message.setText(htmlBody);
                        message.setContent(textBody, "text/html");
            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

            return "mail";
        }else{
            return "";
        }
   }

}
