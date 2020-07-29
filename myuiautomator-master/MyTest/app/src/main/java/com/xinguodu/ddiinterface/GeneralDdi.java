package com.xinguodu.ddiinterface;

import java.util.Arrays;
import android.os.Build;
import android.util.Log;
import com.xinguodu.ddiinterface.Ddi;


public final class GeneralDdi {  
  
	private static final String TAG = "GeneralDdi";

	private static final int DDI_OK 				=  0;	//成功
	private static final int DDI_ERR 				= -1;	//错误
	private static final int DDI_ETIMEOUT 			= -2;	//超时
	private static final int DDI_EBUSY 				= -3;	//设备繁忙
	private static final int DDI_ENODEV 			= -4;	// 设备不存在
	private static final int DDI_EACCES 			= -5;	// 无权限
	private static final int DDI_EINVAL 			= -6;	// 参数无效
	private static final int DDI_EIO 				= -7;	//设备未打开或设备操作出错
	private static final int DDI_EDATA 				= -8;	// 数据错误
	private static final int DDI_EPROTOCOL 			= -9;	// 协议错误
	private static final int DDI_ETRANSPORT 		= -10;	// 传输错误


	private static final int GENERAL_DDI_SET_TIMEOUT 	= 0x0001;      //设置超时时间，单次有效

	private static final int SRED_SET_INPUT_MODE 		= 0x7200;      //打开卡号输入
	private static final int SRED_INPUT_DATA 			= 0x7201;      //卡号输入回显
	private static final int SRED_INPUT_CANCEL 			= 0x7202;      //取消卡号输入
	private static final int SRED_GET_INPUT_LAYOUT 		= 0x7203;      //获取随机键盘序列
	private static final int SRED_SET_INPUT_INDEX 		= 0x7204;      //设置输入卡号索引
	private static final int SRED_CARDNO_CLEARMODE 		= 0x7205;      //设置清除模式
	private static final int SRED_SET_CARDNO_MASKMODE 	= 0x7206;      //设置主账号掩码方式
	private static final int SRED_GET_CARDNO 			= 0x7207;      //获取卡类（磁卡、IC卡、RF卡）数据
	private static final int PIN_SET_INPUT_LAYOUT       = 0x650A;      //设置密码键盘tp布局

	private static final int EMV_RF_INTEROPERATOR 		= 0x6615;      //emv测试使用，不用于量产
	private static final int EMV_RF_TRANSA 				= 0x6616;      //emv测试使用，不用于量产
	private static final int EMV_RF_TRANSB  			= 0x6617;      //emv测试使用，不用于量产

    private static final int SEC_DATA_DEAL  			= 0x6C41;      //安全防切机基础模块

    
	private Ddi ddi = new Ddi();

	/*public int ddi_get_model()
	{
		Build bd = new Build();     
		String s1 = "Octopus A83 F1";
		String s2 = "msm8909";
		String s2_2 = SystemProperties.get(PROPETY_PRODUCT_MODEL, "N5");
    		String model = bd.MODEL;
		System.err.println("ddi_get_version:"+model);	
		if(s1.equals(model)==true)
			return 1;
		else if(s2.equals(model)==true)
			return 2;
		else if(s2_2.equals(model)==true)
			return 2;
		else
			return 0;
	}*/
	//net->host
	int b_ntohl(byte[] buff)
	{
		int data = 0;
		data += ((buff[0]<<24)&0xff000000);
		data += ((buff[1]<<16)&0x00ff0000);
		data += ((buff[2]<<8 )&0x0000ff00);
		data += ((buff[3]<<0 )&0x000000ff);
		return data;
	}
	
	//host->net
	byte[] b_htonl(int data)
	{
		byte[]  buff =  new byte[4];
		buff[0] = (byte)((data>>24)&0x000000ff);
		buff[1] = (byte)((data>>16)&0x000000ff);
		buff[2] = (byte)((data>>8 ) &0x000000ff);
		buff[3] = (byte)((data>>0 ) &0x000000ff);
		return buff;
	}

	byte[] IntToByte(int data)
	{
    	byte[]  buf =  new byte[4];
		buf[0] = (byte)(data&0xff);
		buf[1] = (byte)(data>>8&0xff);
		buf[2] = (byte)(data>>16&0xff);
		buf[3] = (byte)(data>>24&0xff);
		return buf;
	}
	int ByteToInt(byte[] data)
	{
		int temp = 0;
		temp+=((data[0]<<0 )&0x000000ff);
		temp+=((data[1]<<8 )&0x0000ff00);
		temp+=((data[2]<<16 )&0x00ff0000);
		temp+=((data[3]<<24 )&0xff000000);

		return temp;
	}
	//设置spi超时时间，单位s，单次有效
	public int ddi_set_spi_blocktime(int second){
		int nCmd,wlen;
		int ret;
		
		byte[] bufTemp = new byte[4];
		byte[] buf = new byte[100];

		int[] rLen = new int[1];
		byte[] p_outdata = new byte[100];

		int[] rStatus = new int[1];
		
		nCmd = GENERAL_DDI_SET_TIMEOUT;

		if(second < 0 ){
			Log.e(TAG, Integer.toHexString(nCmd)+" input parameter err");	
			return DDI_EINVAL;
		}
		
		bufTemp = b_htonl(second);
		System.arraycopy(bufTemp, 0, buf, 0, 4);
		wlen = 4;
		ret = ddi.ddi_general_interface(nCmd,wlen,buf,rLen,p_outdata,rStatus);
		return ret;
	}


	
	public int ddi_get_hwc_info(byte[] name,int name_len,byte[] p_outdata){
		int nCmd,wlen;
		int ret;
		int index;
		byte[] bufTemp = new byte[4];
		byte[] wbuf = new byte[100];

		int[] rLen = new int[1];
		byte[] rbuf = new byte[1024];
		int[] SpRet = new int[1];
		
		nCmd = 0x6C31;
		
		if((name_len < 0) || (name == null ) ||(p_outdata == null)){
			Log.e(TAG, Integer.toHexString(nCmd)+" input parameter err");	
			return DDI_EINVAL;
		}
		
		index = 0x01;
		bufTemp = b_htonl(index);
		System.arraycopy(bufTemp, 0, wbuf, 0, 4);
		bufTemp = b_htonl(name_len);
		System.arraycopy(bufTemp, 0, wbuf, 4, 4);
		System.arraycopy(name, 0, wbuf, 8, name_len);
		wlen = name_len +4+4;
		ret = ddi.ddi_general_interface(nCmd,wlen,wbuf,rLen,rbuf,SpRet);
		if(ret != 0){
			Log.e(TAG, Integer.toHexString(nCmd)+" cmd err,ret = "+ret+" spRet = "+SpRet[0]);
			return ret;
		}
		if(p_outdata.length < rLen[0]){
			Log.e(TAG, Integer.toHexString(nCmd)+" input parameter err");	
			return DDI_EINVAL;
		}
		System.arraycopy(rbuf, 0, p_outdata, 0, rLen[0]);
		return ret;
	}


	//打开卡号输入
	public int ddi_sred_cardno_set_inputmode(int mode,int cardnolenmin,int cardnolenmax,int timeout){

	    int nCmd;
		int ret;
		byte[] intTmp = new byte[4];
		
		int wlen = 0;
		byte[] wbuf = new byte[100];

		int[] rLen = new int[1];
		byte[] rbuf = new byte[100];
		int[] SpRet = new int[1];
		
		nCmd = SRED_SET_INPUT_MODE;

		if((mode < 0) || (cardnolenmin < 0) ||(cardnolenmax < 0) ||(timeout < 0)){
			Log.e(TAG, Integer.toHexString(nCmd)+" input parameter err");	
			return DDI_EINVAL;
		}
		
		intTmp = b_htonl(mode);
		System.arraycopy(intTmp, 0, wbuf,wlen, 4);
		wlen += 4;
		
		intTmp = b_htonl(cardnolenmin);
		System.arraycopy(intTmp, 0, wbuf, wlen, 4);
		wlen += 4;
		
		intTmp = b_htonl(cardnolenmax);
		System.arraycopy(intTmp, 0, wbuf, wlen, 4);
		wlen += 4;
		
		intTmp = b_htonl(timeout);
		System.arraycopy(intTmp, 0, wbuf, wlen, 4);
		wlen += 4;

		ret = ddi.ddi_general_interface(nCmd,wlen,wbuf,rLen,rbuf,SpRet);
		if(ret != 0){
			Log.e(TAG, Integer.toHexString(nCmd)+" cmd err,ret = "+ret+" spRet = "+SpRet[0]);
			return ret;
		}
		return ret;
	}
	//卡号输入回显
	public int ddi_sred_cardno_inputdata(int mode,  byte[] keycode){
	    int nCmd;
		int ret;
		byte[] intTmp = new byte[4];
		
		int wlen = 0;
		byte[] wbuf = new byte[100];

		int[] rLen = new int[1];
		byte[] rbuf = new byte[100];
		int[] SpRet = new int[1];
		
		nCmd = SRED_INPUT_DATA;

		if((mode < 0) || (keycode == null)){
			Log.e(TAG, Integer.toHexString(nCmd)+" input parameter err");	
			return DDI_EINVAL;
		}
		
		intTmp = b_htonl(mode);
		System.arraycopy(intTmp, 0, wbuf,wlen, 4);
		wlen += 4;

		ret = ddi.ddi_general_interface(nCmd,wlen,wbuf,rLen,rbuf,SpRet);
		if(ret != 0){
			Log.e(TAG, Integer.toHexString(nCmd)+" cmd err,ret = "+ret+" spRet = "+SpRet[0]);
			return ret;
		}
		keycode[0] = rbuf[4];
		return ret;
	}
	//取消卡号输入
	public int ddi_sred_cardno_inputcancel(){
	    int nCmd;
		int ret;
		byte[] intTmp = new byte[4];
		
		int wlen = 0;
		byte[] wbuf = new byte[100];

		int[] rLen = new int[1];
		byte[] rbuf = new byte[100];
		int[] SpRet = new int[1];
		
		nCmd = SRED_INPUT_CANCEL;

		ret = ddi.ddi_general_interface(nCmd,wlen,wbuf,rLen,rbuf,SpRet);
		if(ret != 0){
			Log.e(TAG, Integer.toHexString(nCmd)+" cmd err,ret = "+ret+" spRet = "+SpRet[0]);
			return ret;
		}
		return ret;
	}
	//获取随机键盘序列
	public int ddi_sred_cardno_getinputlayout(int mode, int len, byte[] layoutbuf){
	    int nCmd;
		int ret;
		byte[] intTmp = new byte[4];
		
		int wlen = 0;
		byte[] wbuf = new byte[100];

		int[] rLen = new int[1];
		byte[] rbuf = new byte[100];
		int[] SpRet = new int[1];
		
		nCmd = SRED_GET_INPUT_LAYOUT;

		if((mode < 0) || (len <= 0) ||(layoutbuf == null) || (layoutbuf.length < len)){
			Log.e(TAG, Integer.toHexString(nCmd)+" input parameter err");	
			return DDI_EINVAL;
		}
		
		intTmp = b_htonl(mode);
		System.arraycopy(intTmp, 0, wbuf,wlen, 4);
		wlen += 4;

		intTmp = b_htonl(len);
		System.arraycopy(intTmp, 0, wbuf,wlen, 4);
		wlen += 4;

		ret = ddi.ddi_general_interface(nCmd,wlen,wbuf,rLen,rbuf,SpRet);
		if(ret != 0){
			Log.e(TAG, Integer.toHexString(nCmd)+" cmd err,ret = "+ret+" spRet = "+SpRet[0]);
			return ret;
		}
		System.arraycopy(rbuf, 4, layoutbuf,0,len);
		return ret;
	}
	//设置输入卡号索引
	public int ddi_sred_cardno_setinputindex(int mode, int len, byte[] indexbuf){
	    int nCmd;
		int ret;
		byte[] intTmp = new byte[4];
		
		int wlen = 0;
		byte[] wbuf = new byte[100];

		int[] rLen = new int[1];
		byte[] rbuf = new byte[100];
		int[] SpRet = new int[1];
		
		nCmd = SRED_SET_INPUT_INDEX;

		if((mode < 0) || (len < 0) ||(indexbuf.length < len)){
			Log.e(TAG, Integer.toHexString(nCmd)+" input parameter err");	
			return DDI_EINVAL;
		}
		
		intTmp = b_htonl(mode);
		System.arraycopy(intTmp, 0, wbuf,wlen, 4);
		wlen += 4;

		intTmp = b_htonl(len);
		System.arraycopy(intTmp, 0, wbuf,wlen, 4);
		wlen += 4;

		System.arraycopy(indexbuf, 0, wbuf,wlen, len);
		wlen += len;

		
		ret = ddi.ddi_general_interface(nCmd,wlen,wbuf,rLen,rbuf,SpRet);
		if(ret != 0){
			Log.e(TAG, Integer.toHexString(nCmd)+" cmd err,ret = "+ret+" spRet = "+SpRet[0]);
			return ret;
		}
		return ret;
	}
	public int ddi_sred_cardno_setclearmode(int mode){

		int nCmd;
		int ret;
		byte[] intTmp = new byte[4];
		
		int wlen = 0;
		byte[] wbuf = new byte[100];

		int[] rLen = new int[1];
		byte[] rbuf = new byte[100];
		int[] SpRet = new int[1];

		nCmd = SRED_CARDNO_CLEARMODE;
		
		if(mode < 0){
			Log.e(TAG, Integer.toHexString(nCmd)+" input parameter err");	
			return DDI_EINVAL;
		}
		
		intTmp = b_htonl(mode);
		System.arraycopy(intTmp, 0, wbuf,wlen, 4);
		wlen += 4;

		ret = ddi.ddi_general_interface(nCmd,wlen,wbuf,rLen,rbuf,SpRet);
		if(ret != 0){
			Log.e(TAG, Integer.toHexString(nCmd)+" cmd err,ret = "+ret+" spRet = "+SpRet[0]);
			return ret;
		}
		return ret;

	}

	//设置主账号掩码方式，若不设置，默认支持前6后4进行显示，其余*号
	public int ddi_sred_getcardno_maskmode(int mode, int cardno_prelen, int cardno_suflen){
	    int nCmd;
		int ret;
		byte[] intTmp = new byte[4];
		
		int wlen = 0;
		byte[] wbuf = new byte[100];

		int[] rLen = new int[1];
		byte[] rbuf = new byte[100];
		int[] SpRet = new int[1];
		
		nCmd = SRED_SET_CARDNO_MASKMODE;

		if((mode < 0) || (cardno_prelen < 0) ||(cardno_suflen < 0)){
			Log.e(TAG, Integer.toHexString(nCmd)+" input parameter err");	
			return DDI_EINVAL;
		}
		
		intTmp = b_htonl(mode);
		System.arraycopy(intTmp, 0, wbuf,wlen, 4);
		wlen += 4;

		intTmp = b_htonl(cardno_prelen);
		System.arraycopy(intTmp, 0, wbuf,wlen, 4);
		wlen += 4;

		intTmp = b_htonl(cardno_suflen);
		System.arraycopy(intTmp, 0, wbuf,wlen, 4);
		wlen += 4;

		ret = ddi.ddi_general_interface(nCmd,wlen,wbuf,rLen,rbuf,SpRet);
		if(ret != 0){
			Log.e(TAG, Integer.toHexString(nCmd)+" cmd err,ret = "+ret+" spRet = "+SpRet[0]);
			return ret;
		}
		return ret;
	}
	//获取卡类（磁卡、IC卡、RF卡）数据
	public int ddi_sred_getcardno(int mode, byte cardtype, int inlen, byte[] p_indata,int[] p_outlen, byte[] p_outdata){
	    int nCmd;
		int ret;
		byte[] intTmp = new byte[4];
		
		int wlen = 0;
		byte[] wbuf = new byte[100];

		int[] rLen = new int[1];
		byte[] rbuf = new byte[1024];
		int[] SpRet = new int[1];
		
		nCmd = SRED_GET_CARDNO;

		if((mode < 0) || (cardtype < 0) || (inlen < 0)||(p_outlen == null)||(p_outdata == null)||(p_indata == null)){
			Log.e(TAG, Integer.toHexString(nCmd)+" input parameter err");	
			return DDI_EINVAL;
		}
		
		intTmp = b_htonl(mode);
		System.arraycopy(intTmp, 0, wbuf,wlen, 4);
		wlen += 4;

		wbuf[wlen] = cardtype;
		wlen += 1;

        intTmp = b_htonl(inlen);
		System.arraycopy(intTmp, 0, wbuf,wlen, 4);
		wlen += 4;

        System.arraycopy(p_indata, 0, wbuf,wlen, inlen);
		wlen += inlen;

		ret = ddi.ddi_general_interface(nCmd,wlen,wbuf,rLen,rbuf,SpRet);
		if(ret != 0){
			Log.e(TAG, Integer.toHexString(nCmd)+" cmd err,ret = "+ret+" spRet = "+SpRet[0]);
			return ret;
		}
		if(p_outdata.length < b_ntohl(rbuf)){
			Log.e(TAG, Integer.toHexString(nCmd)+" input parameter err");	
			return DDI_EINVAL;
		}
		p_outlen[0] = b_ntohl(rbuf);
		System.arraycopy(rbuf, 4, p_outdata,0,p_outlen[0]);
		return ret;
	}

	//设置随机密码键盘布局
	public int ddi_pin_set_input_layout(int mode, int datalen,  int[] pdata){
		int nCmd;
		int ret;
		byte[] intTmp = new byte[4];
		
		int wlen = 0;
		byte[] wbuf = new byte[512];

		int[] rLen = new int[1];
		byte[] rbuf = new byte[1024];
		int[] SpRet = new int[1];
		
		nCmd = PIN_SET_INPUT_LAYOUT;

		if((mode < 0) || (datalen < 0) || (datalen > 120) ||(pdata.length < datalen)){
			Log.e(TAG, Integer.toHexString(nCmd)+" input parameter err");	
			return DDI_EINVAL;
		}
		
		intTmp = b_htonl(mode);
		System.arraycopy(intTmp, 0, wbuf,wlen, 4);
		wlen += 4;

		intTmp = b_htonl(datalen*2);
		System.arraycopy(intTmp, 0, wbuf,wlen, 4);
		wlen += 4;

        for(int i = 0;i<datalen;i++){
            wbuf[wlen + 2*i +0] = (byte)((pdata[i]>>8 ) &0x000000ff);
		    wbuf[wlen + 2*i +1] = (byte)((pdata[i]>>0 ) &0x000000ff);
        }
		wlen += 2*datalen;

		ret = ddi.ddi_general_interface(nCmd,wlen,wbuf,rLen,rbuf,SpRet);
		if(ret != 0){
			Log.e(TAG, Integer.toHexString(nCmd)+" cmd err,ret = "+ret+" spRet = "+SpRet[0]);
			return ret;
		}
		return ret;
	}
	
	//以下3个接口用于EMV测试，不用于量产
	//安全芯片不返回数据，所以超时时间设置尽量小
	public int ddi_rf_emv_send_interoperator(){
		int nCmd;
		int ret;
		byte[] intTmp = new byte[4];
		
		int wlen = 0;
		byte[] wbuf = new byte[100];

		int[] rLen = new int[1];
		byte[] rbuf = new byte[1024];
		int[] SpRet = new int[1];

		ddi_set_spi_blocktime(1);
			
		nCmd = EMV_RF_INTEROPERATOR;
		
		wlen = 0;
		
		ret = ddi.ddi_general_interface(nCmd,wlen,wbuf,rLen,rbuf,SpRet);
		if(ret != 0){
			Log.e(TAG, Integer.toHexString(nCmd)+" cmd err,ret = "+ret+" spRet = "+SpRet[0]);
			return ret;
		}
		return ret;
	}

	public int ddi_rf_emv_send_transa(){
		int nCmd;
		int ret;
		byte[] intTmp = new byte[4];
		
		int wlen = 0;
		byte[] wbuf = new byte[100];

		int[] rLen = new int[1];
		byte[] rbuf = new byte[1024];
		int[] SpRet = new int[1];

		ddi_set_spi_blocktime(1);
			
		nCmd = EMV_RF_TRANSA;
		
		wlen = 0;
		
		ret = ddi.ddi_general_interface(nCmd,wlen,wbuf,rLen,rbuf,SpRet);
		if(ret != 0){
			Log.e(TAG, Integer.toHexString(nCmd)+" cmd err,ret = "+ret+" spRet = "+SpRet[0]);
			return ret;
		}
		return ret;
	}

	public int ddi_rf_emv_send_transb(){
		int nCmd;
		int ret;
		byte[] intTmp = new byte[4];
		
		int wlen = 0;
		byte[] wbuf = new byte[100];

		int[] rLen = new int[1];
		byte[] rbuf = new byte[1024];
		int[] SpRet = new int[1];

		ddi_set_spi_blocktime(1);
			
		nCmd = EMV_RF_TRANSB;
		
		wlen = 0;
		
		ret = ddi.ddi_general_interface(nCmd,wlen,wbuf,rLen,rbuf,SpRet);
		if(ret != 0){
			Log.e(TAG, Integer.toHexString(nCmd)+" cmd err,ret = "+ret+" spRet = "+SpRet[0]);
			return ret;
		}
		return ret;
	}
	
	public int ddi_secdata_dealcmd(int mode, int cmd, int inlen, byte[] p_indata, int[] p_outlen, byte[] p_outdata){
		int nCmd;
		int ret;
		byte[] intTmp = new byte[4];
		
		int wlen = 0;
		byte[] wbuf = new byte[4096];

		int[] rLen = new int[1];
		byte[] rbuf = new byte[4096];
		int[] SpRet = new int[1];
		
		nCmd = SEC_DATA_DEAL;

		if((mode < 0) || (cmd < 0) || (inlen < 0)||(inlen > 4096) 
            ||(p_indata == null)||(p_outlen == null)||(p_outdata == null)){
			Log.e(TAG, Integer.toHexString(nCmd)+" input parameter err");	
			return DDI_EINVAL;
		}
		
		intTmp = b_htonl(mode);
		System.arraycopy(intTmp, 0, wbuf,wlen, 4);
		wlen += 4;

		intTmp = b_htonl(cmd);
		System.arraycopy(intTmp, 0, wbuf,wlen, 4);
		wlen += 4;

        intTmp = b_htonl(inlen);
		System.arraycopy(intTmp, 0, wbuf,wlen, 4);
		wlen += 4;

        System.arraycopy(p_indata, 0, wbuf,wlen,inlen);
        wlen += inlen;

		ret = ddi.ddi_general_interface(nCmd,wlen,wbuf,rLen,rbuf,SpRet);
		if(ret != 0){
			Log.e(TAG, Integer.toHexString(nCmd)+" cmd err,ret = "+ret+" spRet = "+SpRet[0]);
			return ret;
		}
        if(p_outdata.length < b_ntohl(rbuf)){
			Log.e(TAG, Integer.toHexString(nCmd)+" input parameter err");	
			return DDI_EINVAL;
		}
		p_outlen[0] = b_ntohl(rbuf);
		System.arraycopy(rbuf, 4, p_outdata,0,p_outlen[0]);
		return ret;
	}

}
