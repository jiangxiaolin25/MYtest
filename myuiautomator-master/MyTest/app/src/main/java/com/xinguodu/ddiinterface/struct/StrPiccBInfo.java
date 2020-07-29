 package com.xinguodu.ddiinterface.struct;
 public class StrPiccBInfo {
    private byte[] atqb = new byte[12];  
    private byte atqb_len;  
    private byte[] attr_rsp = new byte[32];  
    private byte attr_rsp_len;  

    public byte[] getAtqb() {
        return atqb;
    }

    public void setAtqb(byte[] atqb) {
        this.atqb = atqb;
    }

    public byte getAtqb_len() {
        return atqb_len;
    }

    public void setAtqb_len(byte atqb_len) {
        this.atqb_len = atqb_len;
    }

    public byte[] getAttr_rsp() {
        return attr_rsp;
    }

    public void setAttr_rsp(byte[] attr_rsp) {
        this.attr_rsp = attr_rsp;
    }

    public byte getAttr_rsp_len() {
        return attr_rsp_len;
    }

    public void setAttr_rsp_len(byte attr_rsp_len) {
        this.attr_rsp_len = attr_rsp_len;
    }
}