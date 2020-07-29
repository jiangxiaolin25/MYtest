package zhepan.com.testcase_reboottrad;

import android.app.Instrumentation;

import android.content.Context;
import android.os.BatteryManager;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;

import android.support.test.uiautomator.UiSelector;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.content.Context.BATTERY_SERVICE;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    UiDevice mDevice;
    private Context mContext;
    private Context appContext;
    Instrumentation mInstrumentation;
    UiObject DL, SY, posQD, QD, XF, JE1, QR, inputaccoun, inputpass, SM, SMss, SMssclick,fail,DJFH;


    @Before
    public void setup() {
        mInstrumentation = InstrumentationRegistry.getInstrumentation();
        mDevice = UiDevice.getInstance(mInstrumentation);
        String accountid = "com.xgd.umsapp:id/et_tellerno";
        String passwordid = "com.xgd.umsapp:id/et_tellerkey";
        String signid = "com.xgd.umsapp:id/login_in";
        String djfhid = "com.xgd.umsapp:id/bt_counttime_transresult";

        String failid = "com.xgd.umsapp:id/tv_responsemsg_error";



//        DL = mDevice.findObject(new UiSelector().resourceId(signid));
        inputaccoun = mDevice.findObject(new UiSelector().resourceId(accountid));
        inputpass = mDevice.findObject(new UiSelector().resourceId(passwordid));
        DL = mDevice.findObject(new UiSelector().resourceId(signid));
        SY = mDevice.findObject(new UiSelector().text("收银"));
        posQD = mDevice.findObject(new UiSelector().text("POS签到"));
        QD = mDevice.findObject(new UiSelector().text("签 到"));
        XF = mDevice.findObject(new UiSelector().text("消 费"));
        JE1 = mDevice.findObject(new UiSelector().text("1"));
        QR = mDevice.findObject(new UiSelector().text("确认"));
        SM = mDevice.findObject(new UiSelector().text("点击扫码"));
        SMss = mDevice.findObject(new UiSelector().text("调用扫码服务测试"));
        SMssclick = mDevice.findObject(new UiSelector().text("点击自动扫码测试"));
        fail = mDevice.findObject(new UiSelector().resourceId(failid));
        DJFH = mDevice.findObject(new UiSelector().resourceId(djfhid));
        mContext = InstrumentationRegistry.getContext();
        appContext = InstrumentationRegistry.getTargetContext().getApplicationContext();


    }

    public int getbattery() {
        BatteryManager batteryManager = (BatteryManager)mContext.getSystemService(BATTERY_SERVICE);
        int battery = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        return battery;
    }


    /**
     * reboot 重启循环交易
     *
     * @throws Exception
     */
    @Test
    public void useAppContext() throws Exception {
        String method="useAppContext";
        assist.watcherdebugnotify();
//        assist.watcherlowbatternotify();
//        assist.watchefailnotify(method);
        mDevice.pressHome();
        mDevice.pressHome();
        assist.waitforUiObject(SY);
        Thread.sleep(1000);
        inputaccoun.setText("01");
        Thread.sleep(1000);
        inputpass.setText("0000");
        Thread.sleep(1000);
        assist.waitforUiObject(DL);
        Thread.sleep(1000);
        assist.waitforUiObject(QD);
        Thread.sleep(1000);
        assist.waitforUiObject(posQD);
        Thread.sleep(3000);
        if (mDevice.isScreenOn()) {
            mDevice.sleep();
            Thread.sleep(3000);
            mDevice.wakeUp();
            Thread.sleep(1000);
        }
        assist.waitforUiObject(XF);
        Thread.sleep(1000);
        assist.waitforUiObject(JE1);
        assist.waitforUiObject(QR);
        Thread.sleep(1000);
//        if (fail.exists()||DJFH.exists()) {
//            String gettime =assist. gettime();
//            try {
//                assist. waitforUiObject(DJFH);
//                Thread.sleep(1000);
//                assist.result(method, gettime + "时间出现一次测试失败");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

    }

    /**
     * N5SRF.开机自动签到
     */
    @Test
    public void N5SRFautosign() throws Exception {
        String method ="N5SRF卡";
        assist.watcherdebugnotify();
//        assist.watcherlowbatternotify();
//        assist.watchefailnotify(method);
        mDevice.pressHome();
        mDevice.pressHome();
        assist.waitforUiObject(SY);
        Thread.sleep(1000);
        if (!XF.exists()) {
            inputaccoun.setText("01");
            Thread.sleep(1000);
            inputpass.setText("0000");
            Thread.sleep(1000);
            assist.waitforUiObject(DL);
            Thread.sleep(1000);
        }
        assist.creattitle(method);
        for (int a = 0; a < 99999; a++) {
            assist.waitforUiObject(QD);
            Thread.sleep(1000);
            assist.waitforUiObject(posQD);
            Thread.sleep(1000);
            assist.waitforUiObject(XF);
            Thread.sleep(2000);
            if(!(JE1.exists()&&QR.exists())){
                assist.clean(method);
                continue;
            }
            assist.waitforUiObject(JE1);
            assist.waitforUiObject(QR);
            Thread.sleep(9000);

//            if (fail.exists()||DJFH.exists()) {
//                String gettime =assist. gettime();
//                try {
//                    assist. waitforUiObject(DJFH);
//                    Thread.sleep(1000);
//                    assist.result(method, gettime + "时间出现一次测试失败");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            int getbattery = getbattery();
            assist.resultaddbatter(method,""+a,getbattery);
        }
    }

    @Test
    public void N86SRFautosign() throws Exception {
        String method ="N86RF卡";
        String ZH ="com.nexgo.payment:id/et_operator_no";
        String MM ="com.nexgo.payment:id/et_password";
        String DLid ="com.nexgo.payment:id/btn_login";
        String QRDYid ="com.nexgo.payment:id/tv_positive";
        String FHZCDid ="com.nexgo.payment:id/btn_back_2_main";
        UiObject   inputaccoun = mDevice.findObject(new UiSelector().resourceId(ZH));
        UiObject   inputpass = mDevice.findObject(new UiSelector().resourceId(MM));
        UiObject   QRDY = mDevice.findObject(new UiSelector().resourceId(QRDYid));
        UiObject   DL = mDevice.findObject(new UiSelector().resourceId(DLid));
        UiObject   FHZCD = mDevice.findObject(new UiSelector().resourceId(FHZCDid));
        UiObject   QD = mDevice.findObject(new UiSelector().text("签到"));
        UiObject   XF = mDevice.findObject(new UiSelector().text("消费"));
        assist.watcherdebugnotify();
//        assist.watcherlowbatternotify();
//        assist.watchefailnotify(method);
        if (!XF.exists()) {
            inputaccoun.setText("01");
            Thread.sleep(1000);
            inputpass.setText("0000");
            Thread.sleep(1000);
            assist.waitforUiObject(DL);
            Thread.sleep(1000);
            if(QR.exists()){
                assist.waitforUiObject(QR);
                inputpass.setText("0000");
                Thread.sleep(1000);
                assist.waitforUiObject(DL);
                Thread.sleep(1000);
            }
        }
        assist.creattitle(method);
        for (int a = 0; a < 99999; a++) {
            assist.waitforUiObject(QD);
            Thread.sleep(1000);
            inputaccoun.setText("01");
            Thread.sleep(1000);
            inputpass.setText("0000");
            Thread.sleep(1000);
            assist.waitforUiObject(DL);
            Thread.sleep(1000);
            if(QR.exists()){
                assist.waitforUiObject(QR);
                inputpass.setText("0000");
                Thread.sleep(1000);
                assist.waitforUiObject(DL);
                Thread.sleep(1000);
            }
            assist.waitforUiObject(XF);
//            Thread.sleep(2000);
            if(!(JE1.exists()&&QR.exists())){
                assist.clean(method);
                continue;
            }
            assist.waitforUiObject(JE1);
            assist.waitforUiObject(QR);
            assist.waitforUiObject(QRDY);
            assist.waitforUiObject(FHZCD);
            Thread.sleep(1000);
//            if (fail.exists()||DJFH.exists()) {
//                String gettime =assist. gettime();
//                try {
//                    assist. waitforUiObject(DJFH);
//                    Thread.sleep(1000);
//                    assist.result(method, gettime + "时间出现一次测试失败");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            int getbattery = getbattery();
            assist.resultaddbatter(method,""+a,getbattery);
        }
    }
    @Test
    public void N86scanautosign() throws Exception {
        String method ="N86扫码";
        String ZH ="com.nexgo.payment:id/et_operator_no";
        String MM ="com.nexgo.payment:id/et_password";
        String DLid ="com.nexgo.payment:id/btn_login";
        String QRDYid ="com.nexgo.payment:id/tv_positive";
        String FHZCDid ="com.nexgo.payment:id/btn_back_2_main";
        UiObject   inputaccoun = mDevice.findObject(new UiSelector().resourceId(ZH));
        UiObject   inputpass = mDevice.findObject(new UiSelector().resourceId(MM));
        UiObject   QRDY = mDevice.findObject(new UiSelector().resourceId(QRDYid));
        UiObject   DL = mDevice.findObject(new UiSelector().resourceId(DLid));
        UiObject   FHZCD = mDevice.findObject(new UiSelector().resourceId(FHZCDid));
        UiObject   QD = mDevice.findObject(new UiSelector().text("签到"));
        UiObject   SMZF = mDevice.findObject(new UiSelector().text("扫码支付"));
        UiObject   XF = mDevice.findObject(new UiSelector().text("消费"));
        assist.watcherdebugnotify();
//        assist.watcherlowbatternotify();
//        assist.watchefailnotify(method);
        if (!XF.exists()) {
            inputaccoun.setText("01");
            Thread.sleep(1000);
            inputpass.setText("0000");
            Thread.sleep(1000);
            assist.waitforUiObject(DL);
            Thread.sleep(1000);
            if(QR.exists()){
                assist.waitforUiObject(QR);
                inputpass.setText("0000");
                Thread.sleep(1000);
                assist.waitforUiObject(DL);
                Thread.sleep(1000);
            }
        }
        assist.creattitle(method);
        for (int a = 0; a < 99999; a++) {
            assist.waitforUiObject(QD);
            Thread.sleep(1000);
            inputaccoun.setText("01");
            Thread.sleep(1000);
            inputpass.setText("0000");
            Thread.sleep(1000);
            assist.waitforUiObject(DL);
            Thread.sleep(1000);
            if(QR.exists()){
                assist.waitforUiObject(QR);
                inputpass.setText("0000");
                Thread.sleep(1000);
                assist.waitforUiObject(DL);
                Thread.sleep(1000);
            }
            assist.waitforUiObject(SMZF);
//            Thread.sleep(2000);
            if(!(JE1.exists()&&QR.exists())){
                assist.clean(method);
                continue;
            }
            assist.waitforUiObject(JE1);
            assist.waitforUiObject(QR);
            assist.waitforUiObject(QRDY);
            assist.waitforUiObject(FHZCD);
            Thread.sleep(1000);
//            if (fail.exists()||DJFH.exists()) {
//                String gettime =assist. gettime();
//                try {
//                    assist. waitforUiObject(DJFH);
//                    Thread.sleep(1000);
//                    assist.result(method, gettime + "时间出现一次测试失败");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            int getbattery = getbattery();
            assist.resultaddbatter(method,""+a,getbattery);
        }
    }

    /**
     * N6RF.开机自动签到
     */
    @Test
    public void N6SRFautosign() throws Exception {
        String method ="N6RF卡";
        assist.watcherdebugnotify();
//        assist.watcherlowbatternotify();

        mDevice.pressHome();
        mDevice.pressHome();
        assist.waitforUiObject(SY);
        Thread.sleep(1000);
        if (!XF.exists()) {
            inputaccoun.setText("01");
            Thread.sleep(1000);
            inputpass.setText("0000");
            Thread.sleep(1000);
            assist.waitforUiObject(DL);
            Thread.sleep(1000);
        }
        assist.creattitle(method);
        for (int a = 0; a < 99999; a++) {
            assist.waitforUiObject(QD);
            Thread.sleep(1000);
            assist.waitforUiObject(posQD);
            Thread.sleep(1000);
            assist.waitforUiObject(XF);
            Thread.sleep(2000);
            if(!(JE1.exists()&&QR.exists())){
                assist.clean(method);
                continue;
            }
            assist.waitforUiObject(JE1);
//            Thread.sleep(1000);
            assist.waitforUiObject(QR);
            Thread.sleep(9000);
//            if (fail.exists()||DJFH.exists()) {
//                String gettime =assist. gettime();
//                try {
//                    assist. waitforUiObject(DJFH);
//                    Thread.sleep(1000);
//                    assist.result(method, gettime + "时间出现一次测试失败");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            int getbattery = getbattery();
            assist.resultaddbatter(method,""+a,getbattery);
        }
    }

    /**
     * N6sm.开机自动签到扫码测试
     */
    @Test
    public void N6smautosign() throws Exception {
        String method ="N6扫码";
        assist.watcherdebugnotify();
//        assist.watcherlowbatternotify();
        mDevice.pressHome();
        mDevice.pressHome();
        assist.waitforUiObject(SY);
        Thread.sleep(1000);
        if (!XF.exists()) {
            inputaccoun.setText("01");
            Thread.sleep(1000);
            inputpass.setText("0000");
            Thread.sleep(1000);
            assist.waitforUiObject(DL);
            Thread.sleep(1000);
        }
        assist.creattitle(method);
        for (int a = 0; a < 99999; a++) {
            assist.waitforUiObject(QD);
            Thread.sleep(1000);
            assist.waitforUiObject(posQD);
            Thread.sleep(2000);
            assist.waitforUiObject(XF);
            Thread.sleep(2000);
            if(!(JE1.exists()&&QR.exists())){
                assist.clean(method);
                continue;
            }
            assist.waitforUiObject(JE1);
//            Thread.sleep(1000);
            assist.waitforUiObject(QR);
            Thread.sleep(1000);
            assist.waitforUiObject(SM);
            Thread.sleep(9000);
//            if (fail.exists()||DJFH.exists()) {
//                String gettime =assist. gettime();
//                try {
//                    assist. waitforUiObject(DJFH);
//                    Thread.sleep(1000);
//                    assist.result(method, gettime + "时间出现一次测试失败");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }

            int getbattery = getbattery();
            assist.resultaddbatter(method,""+a,getbattery);
        }
    }

    /**
     * N5sm.开机自动签到扫码测试
     */
    @Test
    public void N5smautosign() throws Exception {
        String method ="N5S扫码";
        assist.watcherdebugnotify();
//        assist.watcherlowbatternotify();
//        assist.watchefailnotify(method);
        mDevice.pressHome();
        mDevice.pressHome();
        assist.waitforUiObject(SY);
        Thread.sleep(1000);
        if (!XF.exists()) {
            inputaccoun.setText("01");
            Thread.sleep(1000);
            inputpass.setText("0000");
            Thread.sleep(1000);
            assist.waitforUiObject(DL);
            Thread.sleep(1000);
        }
        assist.creattitle(method);
        for (int a = 0; a < 99999; a++) {
            assist.waitforUiObject(QD);
            Thread.sleep(1000);
            assist.waitforUiObject(posQD);
            Thread.sleep(1000);
            assist.waitforUiObject(XF);
            Thread.sleep(2000);
            if(!(JE1.exists()&&QR.exists())){
                assist.clean(method);
                continue;
            }
            assist.waitforUiObject(JE1);
            assist.waitforUiObject(QR);
            assist.waitforUiObject(SM);
            Thread.sleep(9000);
//            if (fail.exists()||DJFH.exists()) {
//                String gettime =assist. gettime();
//                try {
//                    assist. waitforUiObject(DJFH);
//                    Thread.sleep(1000);
//                    assist.result(method, gettime + "时间出现一次测试失败");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            int getbattery = getbattery();
            assist.resultaddbatter(method,""+a,getbattery);
        }
    }

    /**
     *
     * P100 刷卡交易测试
     *
     * @throws Exception
     */
    @Test
    public void P100RFchange() throws Exception {
        String method ="P100RF交易测试";
        assist.watcherdebugnotify();
//        assist.watcherlowbatternotify();
//        mDevice.pressHome();
//        UiObject Demo = mDevice.findObject(new UiSelector().text("Demo"));
        UiObject RFpay = mDevice.findObject(new UiSelector().text("卡类支付"));
        UiObject signed = mDevice.findObject(new UiSelector().text("签好了"));
        assist.creattitle(method);
//        for (int i = 0; i < 99999; i++) {
//            if (Demo.exists()) {
//                assist.waitforUiObject(Demo);
//                break;
//            } else {
//                mDevice.drag(450, 522, 20, 522, 400);
//            }
//        }
        for(int i=0;i<99999;i++){
            assist.waitforUiObject(SY);
            assist.waitforUiObject(JE1);
            assist.waitforUiObject(QR);
            assist.waitforUiObject(RFpay);
            assist.waitforUiObject(signed);
            Thread.sleep(6000);
            int getbattery = getbattery();
            assist.resultaddbatter(method,""+i,getbattery);
        }
    }/**
     *
     * P100 扫码交易测试
     *
     * @throws Exception
     */
    @Test
    public void P100scanchange() throws Exception {
        String method ="P100scan扫码交易测试";
        assist.watcherdebugnotify();
        assist.watcherlowbatternotify();
//        mDevice.pressHome();
//        UiObject Demo = mDevice.findObject(new UiSelector().text("Demo"));
        UiObject RFpay = mDevice.findObject(new UiSelector().text("扫一扫"));
        UiObject signed = mDevice.findObject(new UiSelector().text("签好了"));
        assist.creattitle(method);
//        for (int i = 0; i < 99999; i++) {
//            if (Demo.exists()) {
//                assist.waitforUiObject(Demo);
//                break;
//            } else {
//                mDevice.drag(450, 522, 20, 522, 400);
//            }
//        }
        for(int i=0;i<99999;i++){
            assist.waitforUiObject(SY);
            assist.waitforUiObject(JE1);
            assist.waitforUiObject(QR);
            assist.waitforUiObject(RFpay);
            assist.waitforUiObject(signed);
            Thread.sleep(6000);
            int getbattery = getbattery();
            assist.resultaddbatter(method,""+i,getbattery);
        }
    }

    /**建行招标RF卡测试
     * @throws Exception
     */
    @Test
    public void ccbRFtrade() throws Exception {
        String method ="建行招标RF卡测试";
        assist.watcherdebugnotify();
//        assist.watcherlowbatternotify();
//        assist.watchefailnotify(method);
        mDevice.pressHome();
        mDevice.pressHome();
        String ccbaccountid = "com.ccb.packmessage:id/et_tellerno";
        String ccbpasswordid = "com.ccb.packmessage:id/et_tellerkey";
        String ccbsignid = "com.ccb.packmessage:id/login_in";
        String ccbRF= "com.ccb.packmessage:id/textview_num8";
        UiObject   ccbinputaccoun = mDevice.findObject(new UiSelector().resourceId(ccbaccountid));
        UiObject    ccbinputpass = mDevice.findObject(new UiSelector().resourceId(ccbpasswordid));
        UiObject     ccbDL = mDevice.findObject(new UiSelector().resourceId(ccbsignid));
        UiObject    ccbRFc = mDevice.findObject(new UiSelector().resourceId(ccbRF));
        assist.waitforUiObject(SY);
        Thread.sleep(1000);
        if (!XF.exists()) {
            ccbinputaccoun.setText("01");
            Thread.sleep(1000);
            ccbinputpass.setText("0000");
            Thread.sleep(1000);
            assist.waitforUiObject(ccbDL);
            Thread.sleep(1000);
        }
        assist.creattitle(method);
        for (int a = 0; a < 99999; a++) {
            assist.waitforUiObject(XF);
            Thread.sleep(2000);
            assist.waitforUiObject(JE1);
            assist.waitforUiObject(QR);
//            assist.waitforUiObject(ccbRFc);
            Thread.sleep(9000);
//            if (fail.exists()||DJFH.exists()) {
//                String gettime =assist. gettime();
//                try {
//                    assist. waitforUiObject(DJFH);
//                    Thread.sleep(1000);
//                    assist.result(method, gettime + "时间出现一次测试失败");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            int getbattery = getbattery();
            assist.resultaddbatter(method,""+a,getbattery);
        }
    }

    /**建行招标预授权自动交易
     * @throws Exception
     */
    @Test
    public void ccbysqtrade() throws Exception {
        String method ="建行招标预授权交易测试";
        assist.watcherdebugnotify();
//        assist.watcherlowbatternotify();
//        assist.watchefailnotify(method);
//        assist.watcherccbnotify();

       UiObject YSQ = mDevice.findObject(new UiSelector().text("预授权"));
        mDevice.pressHome();
        mDevice.pressHome();
        UiObject CZYQD = mDevice.findObject(new UiSelector().text("操作员签到"));
        assist.waitforUiObject(SY);
        Thread.sleep(1000);
        if (!XF.exists()) {
            inputaccoun.setText("01");
            Thread.sleep(1000);
            inputpass.setText("0000");
            Thread.sleep(1000);
            assist.waitforUiObject(DL);
            Thread.sleep(1000);
        }
        assist.creattitle(method);
        for (int a = 0; a < 99999; a++) {
            //签到
//            assist.waitforUiObject(QD);
//            Thread.sleep(1000);
//            assist.waitforUiObject(CZYQD);
//            Thread.sleep(1000);
//            inputaccoun.setText("01");
//            Thread.sleep(1000);
//            inputpass.setText("0000");
//            Thread.sleep(1000);
//            assist.waitforUiObject(DL);
//            Thread.sleep(1000);
            assist.waitforUiObject(YSQ);
//            Thread.sleep(1000);
            if(YSQ.exists()){
                assist.waitforUiObject(YSQ);
//                Thread.sleep(1000);
            }
//            if(!(JE1.exists()&&QR.exists())){
//                assist.clean(method);
//                continue;
//            }
            assist.waitforUiObject(JE1);
            assist.waitforUiObject(QR);
            Thread.sleep(9000);
//            if (fail.exists()||DJFH.exists()) {
//                String gettime =assist. gettime();
//                try {
//                    assist. waitforUiObject(DJFH);
//                    Thread.sleep(1000);
//                    assist.result(method, gettime + "时间出现一次测试失败");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            int getbattery = getbattery();
            assist.resultaddbatter(method,""+a,getbattery);
        }
    }

    /**
     * 调用扫码服务进行扫码压力测试
     * @throws Exception
     */
    @Test
    public void SSMssautosign() throws Exception {
        String method ="调用扫码服务";
        assist.watcherdebugnotify();
//        assist.watcherlowbatternotify();
//        assist.watcherscanserviceclosenotify();
//        mDevice.pressHome();
//        mDevice.pressHome();
//        for (int i = 0; i < 99999; i++) {
//            if (SMss.exists()) {
//                assist.waitforUiObject(SMss);
//                break;
////                i = 111110;
//            } else {
//                mDevice.drag(600, 500, 20, 500, 600);
//            }
//        }
        assist.creattitle(method);
        for(int j=0;j<99999;j++){
            assist.waitforUiObject(SMssclick);
            Thread.sleep(2000);
            int getbattery = getbattery();
            assist.resultaddbatter(method,""+j,getbattery);
        }
    }

    /**
     * @throws Exception
     */
    @Test
    public void BP990scan() throws Exception {
        String method ="BP990scan";
        assist.watcherdebugnotify();
        assist.watcherlowbatternotify();
        assist.watcherscanserviceclosenotify();
        UiObject openscan = mDevice.findObject(new UiSelector().text("启用扫码设备"));
        UiObject opencdc = mDevice.findObject(new UiSelector().text("打开串口"));
        UiObject stascan = mDevice.findObject(new UiSelector().text("开始扫码"));
        UiObject closecdc = mDevice.findObject(new UiSelector().text("关闭串口"));
        UiObject closescan = mDevice.findObject(new UiSelector().text("关闭扫码设备"));
        mDevice.pressHome();
        mDevice.pressHome();
        assist.creattitle(method);
        for(int j=0;j<99999;j++){
            assist.waitforUiObject(openscan);
            Thread.sleep(1000);
            assist.waitforUiObject(opencdc);
            Thread.sleep(1000);
            assist.waitforUiObject(stascan);
            Thread.sleep(1000);
            assist.waitforUiObject(closecdc);
            Thread.sleep(1000);
            assist.waitforUiObject(closescan);
            Thread.sleep(1000);
            int getbattery = getbattery();
            assist.resultaddbatter(method,""+j,getbattery);

        }
    }






}
