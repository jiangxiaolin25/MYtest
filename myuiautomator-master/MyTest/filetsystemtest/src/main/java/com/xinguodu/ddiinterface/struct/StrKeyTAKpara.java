package com.xinguodu.ddiinterface.struct;

public class StrKeyTAKpara{
	private int m_area;
	private int m_target_index;
	private int m_source_index;
	private int m_save_index;

	public int getArea()
	{
		return m_area;
	}
	public int getTargetIndex()
	{
		return m_target_index;
	}
	public int getSourceIndex()
	{
		return m_source_index;
	}
	public int getSaveIndex()
	{
		return m_save_index;
	}

	public int setArea(int Area)
	{
		m_area = Area;
		return 0;
	}
	public byte setTargetIndex(int Index)
	{
		m_target_index = Index;
		return 0;
	}
	public byte setSourceIndex(int Index)
	{
		m_source_index = Index;
		return 0;
	}
	public byte setSaveIndex(int Index)
	{
		m_save_index = Index;
		return 0;
	}
}