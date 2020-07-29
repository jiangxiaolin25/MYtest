package com.xinguodu.ddiinterface.struct;

public class StrHkey{

	private int m_area;
	private int m_index;
	private int m_half;
	private byte[]  m_indata =  new byte[512];
	private byte[]  m_outdata =  new byte[512];
	private int m_len;

	public int getArea()
	{
		return m_area;
	}
	public int getIndex()
	{
		return m_index;
	}
	public int getHalf()
	{
		return m_half;
	}
	public byte[] getIndata()
	{
		return m_indata;
	}
	public byte[] getOutdata()
	{
		return m_outdata;
	}
	public int getLen()
	{
		return m_len;
	}
	public int setArea(int Area)
	{
		m_area = Area;
		return 0;
	}
	public int setIndex(int Index)
	{
		m_index = Index;
		return 0;
	}
	public int setHalf(int  Half)
	{
		m_half = Half;
		return 0;
	}
	public void setIndata(byte[] m_indata) {
		this.m_indata = m_indata;
	}
	public void setOutdata(byte[] m_outdata) {
		this.m_outdata = m_outdata;
	}
	public int setLen(int len)
	{
		m_len = len;
		return 0;
	}
}