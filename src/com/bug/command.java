/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bug;

/**
 *
 * @author bkantor
 */
public class command {
    private String type="";
    private String cmd="";
    private String param="";
    private String name="";

    public command() {
    }
    
    public command(String type, String cmd, String param) {
        super();
        this.type = type;
        this.cmd = cmd;
        this.param = param;
    }
    
    public command(String type, String name, String cmd, String param) {
        super();
        this.type = type;
        this.cmd = cmd;
        this.param = param;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getCmd() {
        return cmd;
    }

    public String getParam() {
        return param;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String runCmd(){
        if("exec".equals(type)){
                
            return "exec";
        }else if("script".equals(type)){
            
            return "script";
        }else{
            return "";
        }
    }
    
}
