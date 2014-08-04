/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author korena
 */
public class Users implements Serializable{

    private UUID uid;
    private List<String> emails;
    private String ualias;
    private String password;
    private Map<String,String> questions;
    private String fname;
    private String lname;
    private String address;

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map getQuestions() {
        return questions;
    }

    public void setQuestions(Map questions) {
        this.questions = questions;
    }

    
    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUalias() {
        return ualias;
    }

    public void setUalias(String ualias) {
        this.ualias = ualias;
    }    
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    
}
