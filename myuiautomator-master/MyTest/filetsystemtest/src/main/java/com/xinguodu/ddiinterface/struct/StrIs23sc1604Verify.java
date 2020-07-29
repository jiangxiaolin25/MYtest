package com.xinguodu.ddiinterface.struct;

public class StrIs23sc1604Verify{

	private int i;
	private int m_zone;
	private int m_mode;
	private int m_type;
	private byte[]  m_key =  new byte[2];
	public int getZone()
	{
		return m_zone;
	}
	public int getMode()
	{
		return m_mode;
	}
	public byte[] getKey()
	{
		return m_key;
	}
	public int setZone(int Zone)
	{
		m_zone = Zone;
		return 0;
	}
	public int setMode(int Mode)
	{
		m_mode = Mode;
		return 0;
	}
	public int setKey(byte[] Key)
	{
		for(i=0;i<2&&i<Key.length;i++)
		{
			m_key[i] = Key[i];
		}
		return 0;
	}

	public int getType() {
		return m_type;
	}

	public void setType(int type) {
		this.m_type = type;
	}
}