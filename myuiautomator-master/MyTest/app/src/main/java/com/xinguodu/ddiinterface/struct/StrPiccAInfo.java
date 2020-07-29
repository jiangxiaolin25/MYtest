package com.xinguodu.ddiinterface.struct;

public class StrPiccAInfo {
    private byte[] atqa = new byte[2];  
    private byte sak; 
    private byte[] uid = new byte[10]; 
    private byte uid_len;  
    private byte[] ats = new byte[24]; 
    private byte ats_len;  

    public byte[] getAtqa() {
        return atqa;
    }

    public void setAtqa(byte[] atqa) {
        this.atqa = atqa;
    }

    public byte getSak() {
        return sak;
    }

    public void setSak(byte sak) {
        this.sak = sak;
    }

    public byte[] getUid() {
        return uid;
    }

    public void setUid(byte[] uid) {
        this.uid = uid;
    }

    public byte getUid_len() {
        return uid_len;
    }

    public void setUid_len(byte uid_len) {
        this.uid_len = uid_len;
    }

    public byte[] getAts() {
        return ats;
    }

    public void setAts(byte[] ats) {
        this.ats = ats;
    }

    public byte getAts_len() {
        return ats_len;
    }

    public void setAts_len(byte ats_len) {
        this.ats_len = ats_len;
    }
}