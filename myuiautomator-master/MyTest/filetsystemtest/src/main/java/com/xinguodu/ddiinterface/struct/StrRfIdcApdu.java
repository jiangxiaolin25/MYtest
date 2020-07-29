package com.xinguodu.ddiinterface.struct;

public class StrRfIdcApdu{
	private int m_len;//APDU len
	private byte m_apdu[]=new byte[300];  //APDU data
	public int getLen()
	{
		return m_len;
	}
	public byte[] getApdu()
	{
		return m_apdu;
	}
	public void setLen(int len)
	{
		m_len=len;
	}
	public void setApdu(byte apdu[])
	{
		m_apdu=apdu;
	}

}