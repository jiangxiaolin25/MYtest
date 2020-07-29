package com.xinguodu.ddiinterface.struct;

public class StrIs23sc1604{

	private int i;
	private int m_addr;
	private int m_le;
	private int m_type;
	private int m_zone_id;

	public int getAddr()
	{
		return m_addr;
	}
	public int getLe()
	{
		return m_le;
	}
	public int setAddr(int Addr)
	{
		m_addr = Addr;
		return 0;
	}
	public int setLe(int Le)
	{
		m_le = Le;
		return 0;
	}

	public int getZoneId() {
		return m_zone_id;
	}

	public void setZoneId(int zoneId) {
		this.m_zone_id = zoneId;
	}

	public int getType() {
		return m_type;
	}

	public void setType(int type) {
		this.m_type = type;
	}
}
