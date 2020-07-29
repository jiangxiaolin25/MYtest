package com.xinguodu.ddiinterface2;
import android.graphics.Bitmap;

public final class Ddi_BT{
  
	static {  
	System.loadLibrary("xgd_bluetooth_jni");  
	}  


	public native int ddi_E_POS_data(byte[] Epos_Data, int DataLen);
	public native int drawIntoBitmapDouble(Bitmap image , byte[] words,int font_size, int xpos, int ypos ,int double_x, int double_y, int blod);//新增
	public native int drawIntoBitmap(Bitmap image , byte[] words,int font_size, int xpos, int ypos , int blod);
	public native int drawdottext(Bitmap dstBitmap , byte[] words ,int chinese_type, int eng_type, int xpos, int ypos, int double_x, int double_y );
	public native int drawdottextblod(Bitmap dstBitmap , byte[] words ,int chinese_type, int eng_type, int xpos, int ypos, int double_x, int double_y , int blod);//新增
	public native int setfont(String font, int len);

}
