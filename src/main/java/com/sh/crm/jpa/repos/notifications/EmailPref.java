package com.sh.crm.jpa.repos.notifications;

import java.util.List;

public class EmailPref {
    private String userID;
    private String emailID;
    private boolean disabled;
    private boolean includeAttatchments;
    private boolean slaEmail;
    private boolean copyCC;
    private boolean copyBBC;
    private List<String> ccList;
    private List<String> bccList;

    public EmailPref() {

    }

    public EmailPref(String userID, String emailID, boolean includeAttatchments) {
        this.userID = userID;
        this.emailID = emailID;
        this.includeAttatchments = includeAttatchments;
    }

    public EmailPref(String userID, String emailID, boolean disabled, boolean includeAttatchments) {
        this.userID = userID;
        this.emailID = emailID;
        this.disabled = disabled;
        this.includeAttatchments = includeAttatchments;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public boolean isIncludeAttatchments() {
        return includeAttatchments;
    }

    public void setIncludeAttatchments(boolean includeAttatchments) {
        this.includeAttatchments = includeAttatchments;
    }

    public boolean isSlaEmail() {
        return slaEmail;
    }

    public void setSlaEmail(boolean slaEmail) {
        this.slaEmail = slaEmail;
    }

    public boolean isCopyCC() {
        return copyCC;
    }

    public void setCopyCC(boolean copyCC) {
        this.copyCC = copyCC;
    }

    public boolean isCopyBBC() {
        return copyBBC;
    }

    public void setCopyBBC(boolean copyBBC) {
        this.copyBBC = copyBBC;
    }

    public List<String> getCcList() {
        return ccList;
    }

    public void setCcList(List<String> ccList) {
        this.ccList = ccList;
    }

    public List<String> getBccList() {
        return bccList;
    }

    public void setBccList(List<String> bccList) {
        this.bccList = bccList;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return "EmailPref{" +
                "userID='" + userID + '\'' +
                ", emailID='" + emailID + '\'' +
                ", disabled=" + disabled +
                ", includeAttatchments=" + includeAttatchments +
                ", slaEmail=" + slaEmail +
                ", copyCC=" + copyCC +
                ", copyBBC=" + copyBBC +
                ", ccList=" + ccList +
                ", bccList=" + bccList +
                '}';
    }
}
