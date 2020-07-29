package zhepan.com.testcase_reboottrad;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


@RunWith(AndroidJUnit4.class)
public class sinExampleInstrumentedTest {
    UiDevice mDevice;
    Instrumentation mInstrumentation;
    UiObject DL, SY, posQD, QD, XF, JE1, QR, inputaccoun, inputpass, SM, SMss, SMssclick;


    @Before
    public void setup() {
        mInstrumentation = InstrumentationRegistry.getInstrumentation();
        mDevice = UiDevice.getInstance(mInstrumentation);
        String accountid = "com.xgd.umsapp:id/et_tellerno";
        String passwordid = "com.xgd.umsapp:id/et_tellerkey";
        String signid = "com.xgd.umsapp:id/login_in";
        DL = mDevice.findObject(new UiSelector().resourceId(signid));
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
        assist.watcherlowbatternotify();
        assist.watchefailnotify(method);
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
        Thread.sleep(2000);
        if(!(JE1.exists()&&QR.exists())){
            assist.clean(method);
        }else {
            assist.waitforUiObject(JE1);
            assist.waitforUiObject(QR);
            Thread.sleep(1000);
        }

    }
    /**
     * N5SRF.开机自动签到
     */
    @Test
    public void N5SRFautosign() throws Exception {
        String method ="N5SRF卡";
        assist.watcherdebugnotify();
        assist.watcherlowbatternotify();
        assist.watchefailnotify(method);
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
            assist.waitforUiObject(QD);
            Thread.sleep(1000);
            assist.waitforUiObject(posQD);
            Thread.sleep(1000);
            assist.waitforUiObject(XF);
            Thread.sleep(2000);
            if(!(JE1.exists()&&QR.exists())){
                assist.clean(method);
            }else {
                assist.waitforUiObject(JE1);
                assist.waitforUiObject(QR);
                Thread.sleep(1000);

            }


    }

    /**
     * N6RF.开机自动签到
     */
    @Test
    public void N6SRFautosign() throws Exception {
        String method ="N6RF卡";
        assist.watcherdebugnotify();
        assist.watcherlowbatternotify();
        assist.watchefailnotify(method);
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

            assist.waitforUiObject(QD);
            Thread.sleep(1000);
            assist.waitforUiObject(posQD);
            Thread.sleep(1000);
            assist.waitforUiObject(XF);
            Thread.sleep(2000);
            if(!(JE1.exists()&&QR.exists())){
                assist.clean(method);

            }else {
                assist.waitforUiObject(JE1);
//            Thread.sleep(1000);
                assist.waitforUiObject(QR);
                Thread.sleep(1000);
            }


    }

    /**
     * N6sm.开机自动签到扫码测试
     */
    @Test
    public void N6smautosign() throws Exception {
        String method ="N6扫码";
        assist.watcherdebugnotify();
        assist.watcherlowbatternotify();
        assist.watchefailnotify(method);
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

//            assist.waitforUiObject(QD);
//            Thread.sleep(1000);
//            assist.waitforUiObject(posQD);
            Thread.sleep(2000);
            assist.waitforUiObject(XF);
            Thread.sleep(2000);
            if(!(JE1.exists()&&QR.exists())){
                assist.clean(method);

            }else {
                assist.waitforUiObject(JE1);
//            Thread.sleep(1000);
                assist.waitforUiObject(QR);
                Thread.sleep(1000);
                assist.waitforUiObject(SM);
                Thread.sleep(3000);

            }


    }

    /**
     * N5sm.开机自动签到扫码测试
     */
    @Test
    public void N5smautosign() throws Exception {
        String method ="N5S扫码";
        assist.watcherdebugnotify();
        assist.watcherlowbatternotify();
        assist.watchefailnotify(method);
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

            assist.waitforUiObject(QD);
            Thread.sleep(1000);
            assist.waitforUiObject(posQD);
            Thread.sleep(1000);
            assist.waitforUiObject(XF);
            Thread.sleep(2000);
            if(!(JE1.exists()&&QR.exists())){
                assist.clean(method);
            }else {
                assist.waitforUiObject(JE1);
                assist.waitforUiObject(QR);
                assist.waitforUiObject(SM);

            }


    }

    /**
     *
     * 调用图库图片进行切换
     *
     * @throws Exception
     */
    @Test
    public void PCchange() throws Exception {


    }

    /**
     * 调用扫码服务进行扫码压力测试
     * @throws Exception
     */
    @Test
    public void SSMssautosign() throws Exception {
        String method ="调用扫码服务";
        assist.watcherdebugnotify();
        assist.watcherlowbatternotify();
        assist.watcherscanserviceclosenotify();
        mDevice.pressHome();
        mDevice.pressHome();
        for (int i = 0; i < 99999; i++) {
            if (SMss.exists()) {
                assist.waitforUiObject(SMss);
                i = 111110;
            } else {
                mDevice.drag(600, 500, 20, 500, 500);
            }
        }
        for(int j=0;j<=9;j++){
            assist.waitforUiObject(SMssclick);
            Thread.sleep(2000);
            assist.result(method,""+j);
        }
    }




}
