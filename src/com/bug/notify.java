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
    private Boolean tls = false;
    private Boolean auth = false;
    private String user = "";
    private String pass = "";
    private Properties prop;

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

    public void setSMTP(String host, int port, Boolean auth, String user, String pass, Boolean tls) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.auth = auth;
        this.tls = tls;
        prop = new Properties();
        prop.put("mail.smtp.auth", String.valueOf(auth));
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", String.valueOf(port));
        prop.put("mail.smtp.starttls.enable", String.valueOf(tls));

    }

    public void setMail(String sendto, String recepient, String subject, String body) {
        this.sendto = sendto;
        this.recepient = recepient;
        this.subject = subject;
        this.body = body;
    }

    public String sendNotify() {
        if ("xml".equals(type)) {

            return "xml";
        } else if ("mail".equals(type)) {
            Session session;
            if (auth) {
                session = Session.getDefaultInstance(prop,
                        new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, pass);
                    }
                });
            } else {
                session = Session.getDefaultInstance(prop);
            }
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(recepient));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendto));
                message.setSubject(subject);
                message.setText(body);
                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return "mail";
        } else {
            return "";
        }
    }

}
