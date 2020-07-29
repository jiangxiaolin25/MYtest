package com.example.filetsystemtest;

import android.util.Log;

import com.xinguodu.ddiinterface.Ddi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 作者：jiangxiaolin on 2020/4/1
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class checkinfo {
    Ddi ddi;
    //机身号
    private String SN = "G310000003";
    private String TUSN = "0000402G310000003";


    String key1 = "12345678abcdEFBFABCDefBCabcdEFBF";
    byte[] innerkey1 = ByteUtils.hexString2ByteArray(key1);
    String key2 = "56248AAAAAAAAAAA9999efBCabcdEFBF";
    byte[] innerkey2 = ByteUtils.hexString2ByteArray(key2);
    byte[] DataOut = new byte[16];
    byte[] ipIn = ByteUtils.hexString2ByteArray("31313131313131313131313131313131");
    byte[] ipOut = new byte[200];
    private byte[] msg_summery;
    String data3 = "23b4e1818650c0f39baab6669e063956";
    int i;
    String data6 = "8b18c930601f4ad94573f487b9406d95";
    String m_cipherauk = "12345678abcdefbfabcdefbcabcdefbf";
    byte[] cipherauk = ByteUtils.hexString2ByteArray(m_cipherauk);
    byte[] DataOut3 = new byte[16];
    byte[] DataOut4 = new byte[16];
    String m_cipher = "12345678abcdefbf";
    byte[] cipher = ByteUtils.hexString2ByteArray(m_cipher);
    byte[] mk_cipher = new byte[8];


    public checkinfo(Ddi ddi, int i) {
        this.ddi = ddi;
        this.i = i;
        Ddi.ddi_ddi_sys_init();
    }

    public int testsecinfo() {
        int getcerthash = getcerthash();
        int sn = getSN();
        int testmainkey = testmainkey();
        int testtusn = testtusn();
        int tektest = Tektest();
        int i = AUKtest();
        return getcerthash+sn+testmainkey+testtusn+tektest+i;
    }

    /**
     * 获取证书HASH值
     *
     * @return
     */
    public int getcerthash() {
        int ret = ddi.ddi_sys_getCertHash(new byte[512]);
        return ret;
    }

    /**
     * 获取机身号
     *
     * @return
     */
    public int getSN() {
        byte[] lpOut = new byte[256];
        int ret1 = ddi.ddi_sys_read_dsn(lpOut);
        String data = ByteUtils.asciiByteArray2String(lpOut);
        if (ret1 == 0 && data.equalsIgnoreCase(SN)) {
            return ret1;
        } else {
            return -99;
        }
    }

    /**
     * 测试主秘钥
     *
     * @throws Exception
     */
    private int testmainkey()  {
        String data0 = "4e168484e5c1c1d6380012839d2d766f";
        Log.v("TAG", "开始秘钥测试");
        Ddi.ddi_innerkey_open();
        Log.v("TAG", " 密钥接口打开");
        if (i == 0) {
            int res0 = Ddi.ddi_innerkey_update_mk((byte) 0x01, 149, innerkey1, 16);
            Log.v("TAG", " 更新主密钥成功" + res0);

            int res1 = Ddi.ddi_innerkey_update_wk((byte) 0x01, 149, 449, 0, ipIn, innerkey2, 16, ipOut, 16);
            Log.v("TAG", " 更新工作密钥成功" + res1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int res2 = Ddi.ddi_innerkey_des_encrypt(0, 149, 0, ipIn, msg_summery, 16, DataOut);
        // 主密钥加密
        String data = ByteUtils.byteArray2HexString(DataOut);
//        Log.v("TAG", " DES加密成功" + data);
        if (res2 == 0 && data.equalsIgnoreCase(data0)) {
//            name.writeExcel3("" + sp, "主密钥加密测试", "主密钥加密成功");
            result("主密钥加密成功：\r\n");
//            Log.v("TAG", "开始获取TUSN");
            return res2;
        } else {
//            name.writeExcel3("" + sp, "主密钥加密测试", "主密钥加密失败");
            result("主密钥加密失败：" + data + "\r\n");
            return -99;
        }
    }

    /**
     * 获取TUSN
     *
     * @return
     * @throws IOException
     */
    private int testtusn()  {
        byte[] data55 = new byte[19];
        byte[] datalen = new byte[4];
        int res3 = Ddi.ddi_read_tusn_sn(data55, datalen);
        String str = ByteUtils.asciiByteArray2String(data55);
        Log.v("TAG", "机器tusn为" + str);
        if (res3 == 0 && str.equalsIgnoreCase(TUSN)) {
//            name.writeExcel3("" + sp, "机器TUSN获取测试", "机器TUSN获取成功");
            result("机器TUSN获取成功\r\n");
            return res3;
        } else {
//            name.writeExcel3("" + sp, "机器TUSN获取测试", "机器TUSN获取失败" + str);
            result("机器TUSN获取失败" + str +res3+ "\r\n");
            return -99;
        }
    }


    /**
     * TEK测试，需要提前下好秘钥
     *
     * @return
     */
    public int Tektest() {
        Ddi.ddi_innerkey_open();

        int res4 = 0;
        if (i == 0) {
            res4 = Ddi.ddi_innerkey_update_mk_cipher((byte) 0, (byte) 0, (byte) 2, (byte) 0, ipIn, cipher, cipher.length, (byte) 0, mk_cipher);
            if (res4 == 0) {
                Log.v("TAG", "TEk更新主密钥成功");

            } else {
                Log.v("TAG", "TEk更新主密钥失败");
                //            name.writeExcel3("" + sp, "TEk更新主密钥测试", "TEk更新主密钥失败");
                                result("TEk更新主密钥失败" + res4 + "\r\n");
            }
        }
        int res5 = Ddi.ddi_innerkey_des_encrypt(0, 2, 0, ipIn, msg_summery, 16, DataOut);
        String data4 = ByteUtils.byteArray2HexString(DataOut);
        if (res5 == 0 && data4.equalsIgnoreCase(data3)) {
            Log.v("TAG", "TEk校验成功");
//                name.writeExcel3("" + sp, "TEK校验测试", "TEK校验成功");
                    result("TEK校验成功\r\n");


            return res4 + res5;
        } else {
//                name.writeExcel3("" + sp, "TEK校验测试", "TEK校验失败");
            Log.v("TAG", "TEk校验失败");
                    result("TEK校验失败" + "\r\n");
            return -99;
        }


    }

    /**
     * AUK校验测试，需要提前下载好秘钥信息
     *
     * @return
     */
    public int AUKtest() {
        int res6 = Ddi.ddi_auk_data_process((byte) 1, (byte) 0, (byte) 0, 2, (byte) 0, ipIn, cipherauk, cipherauk.length, DataOut3);
        String data1 = ByteUtils.byteArray2HexString(DataOut3);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int res7 = Ddi.ddi_auk_data_process((byte) 0, (byte) 0, (byte) 0, 2, (byte) 0, ipIn, DataOut3, DataOut3.length, DataOut4);
        String data7 = ByteUtils.byteArray2HexString(DataOut4);
        if (res7 == 0 && data7.equalsIgnoreCase(m_cipherauk) && res6 == 0 && data1.equalsIgnoreCase(data6)) {
//            Log.v("TAG", "AUK解密成功");
//                name.writeExcel3("" + sp, "AUK解密测试", "AUK解密成功");
                    result("AUK解密成功" + "\r\n");
            return res6 + res7;

        } else {
//            Log.v("TAG", "AUK解密失败");
//                name.writeExcel3("" + sp, "AUK解密测试", "AUK解密失败");
                    result("AUK解密失败" +data7+ "\r\n");

            return i;

        }


    }

        public static void result(String txt) {
        File file = new File("/mnt/sdcard/filetestlog.txt");
            BufferedWriter output = null;
            try {
                output = new BufferedWriter(new FileWriter(file, true));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                output.append(txt + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


}
