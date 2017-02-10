package com.mljr.operators.entity;

/**
 * Created by songchi on 17/2/10.
 */
public class LoginResponse {

    /**
     * transactionID : 00210201702101517379601000004160
     * brand : 01
     * artifact : 4ca74c2b0af0406d8645a2724e52e3f8
     * result : 0
     * uid : e69535c2bf484a75898fe0f5da68d7fd
     * message : 成功
     */

    private String transactionID;
    private String brand;
    private String artifact;
    private String result;
    private String uid;
    private String message;

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getArtifact() {
        return artifact;
    }

    public void setArtifact(String artifact) {
        this.artifact = artifact;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
