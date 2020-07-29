package com.xinguodu.ddiinterface.struct;

public class StrIs23sc1604ReadRc{

	private int i;
	private int m_zone;
	private int m_mode;
	private int m_type;
	public int getZone()
	{
		return m_zone;
	}
	public int getMode()
	{
		return m_mode;
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

	public int getType() {
		return m_type;
	}

	public void setType(int type) {
		this.m_type = type;
	}
}