package zhepan.com.testcase_reboottrad;

import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class assist {

    String accountid = "com.xgd.umsapp:id/et_tellerno";
    String passwordid = "com.xgd.umsapp:id/et_tellerkey";
    String signid = "com.xgd.umsapp:id/login_in";
    /**
     * Waitfor ui object.
     * object等待找到
     *
     * @throws UiObjectNotFoundException the ui object not found exception
     */
    public static void waitforUiObject(UiObject uiObject) throws Exception {
        for (int i = 0; i < 2000; i++) {
            if (!uiObject.exists()) {
                Thread.sleep(1);
                continue;
            }
            if (uiObject.exists()) {
                Thread.sleep(1);
                uiObject.clickAndWaitForNewWindow();
                Thread.sleep(1000);
                break;
            }
        }
    }

    /**
     * Watcherdebugnotify.监听调试模式
     */
    public static void watcherdebugnotify() {
        final UiDevice mDevice;
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.registerWatcher("debugnotify", new UiWatcher() {
            UiObject object = mDevice.findObject(new UiSelector().text("调试模式请勿商用"));
            UiObject chart = mDevice.findObject(new UiSelector().text("请连接充电器"));
            String djfhid = "com.xgd.umsapp:id/bt_counttime_transresult";
            String failid = "com.xgd.umsapp:id/tv_responsemsg_error";
            String accountid = "com.xgd.umsapp:id/et_tellerno";
            String passwordid = "com.xgd.umsapp:id/et_tellerkey";
            String signid = "com.xgd.umsapp:id/login_in";
            UiObject fail = mDevice.findObject(new UiSelector().resourceId(failid));
            UiObject DJFH = mDevice.findObject(new UiSelector().resourceId(djfhid));
            UiObject DL = mDevice.findObject(new UiSelector().resourceId(signid));
            UiObject inputaccoun = mDevice.findObject(new UiSelector().resourceId(accountid));
            UiObject inputpass = mDevice.findObject(new UiSelector().resourceId(passwordid));
            UiObject XF  = mDevice.findObject(new UiSelector().text("消 费"));
            public boolean checkForCondition() {
                // TODO Auto-generated method stub
                String gettime = gettime();
                if (object.exists()) {
                    mDevice.pressBack();
                } else if (fail.exists()||DJFH.exists()){
                    try {
                        waitforUiObject(DJFH);
                        if (!XF.exists()) {
                            inputaccoun.setText("01");
                            Thread.sleep(1000);
                            inputpass.setText("0000");
                            Thread.sleep(1000);
                            assist.waitforUiObject(DL);
                            Thread.sleep(1000);
                        }
                        Thread.sleep(1000);
                        result("监听", gettime + "时间出现一次测试失败");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if (object.exists()){
                    mDevice.pressBack();
                    try {
                        result("监听", gettime + "时间出现一次");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                return false;
            }
        });

    }

    /**
     * Watcherlowbatternotify.监听电池电量低
     */
    public static void watcherlowbatternotify() {
        final UiDevice mDevice;
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.registerWatcher("lowbatter", new UiWatcher() {
            UiObject object = mDevice.findObject(new UiSelector().text("请连接充电器"));



            public boolean checkForCondition() {
                // TODO Auto-generated method stub
                if (object.exists()) {
                    mDevice.pressBack();
                }
                return false;
            }
        });

    }
    /**
     * Watcherlowbatternotify.监听扫码服务出现失败
     */
    public static void watcherscanserviceclosenotify() {
        final UiDevice mDevice;
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.registerWatcher("closeservice", new UiWatcher() {
            UiObject object = mDevice.findObject(new UiSelector().text("关闭应用"));
            public boolean checkForCondition() {
                // TODO Auto-generated method stub
                if (object.exists()) {
                    try {
                        waitforUiObject(object);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

    }
    /**
     * Watcherlowbatternotify.建行招标应用失败监控测试
     */
    public static void watcherccbnotify() {
        final UiDevice mDevice;
        final String accountid = "com.xgd.umsapp:id/et_tellerno";
        final String passwordid = "com.xgd.umsapp:id/et_tellerkey";
        final String signid = "com.xgd.umsapp:id/login_in";
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.registerWatcher("ccbappclose", new UiWatcher() {
            UiObject object = mDevice.findObject(new UiSelector().text("重新打开应用"));
            UiObject inputaccoun = mDevice.findObject(new UiSelector().resourceId(accountid));
            UiObject inputpass = mDevice.findObject(new UiSelector().resourceId(passwordid));
            UiObject  DL = mDevice.findObject(new UiSelector().resourceId(signid));
            public boolean checkForCondition() {
                // TODO Auto-generated method stub
                if (object.exists()) {
                    try {
                        waitforUiObject(object);
                        inputaccoun.setText("01");
                        Thread.sleep(1000);
                        inputpass.setText("0000");
                        Thread.sleep(1000);
                        assist.waitforUiObject(DL);
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

    }

    /**
     * Watcherlowbatternotify.监听交易失败后清除流水
     */
    public static void watchefailnotify(String name) {
        final UiDevice mDevice;
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.registerWatcher("fail", new UiWatcher() {
            String djfhid = "com.xgd.umsapp:id/bt_counttime_transresult";
            String failid = "com.xgd.umsapp:id/tv_responsemsg_error";
            String accountid = "com.xgd.umsapp:id/et_tellerno";
            String passwordid = "com.xgd.umsapp:id/et_tellerkey";
            String signid = "com.xgd.umsapp:id/login_in";
            UiObject fail = mDevice.findObject(new UiSelector().resourceId(failid));
            UiObject DJFH = mDevice.findObject(new UiSelector().resourceId(djfhid));
            UiObject DL = mDevice.findObject(new UiSelector().resourceId(signid));
            UiObject inputaccoun = mDevice.findObject(new UiSelector().resourceId(accountid));
            UiObject inputpass = mDevice.findObject(new UiSelector().resourceId(passwordid));
            UiObject XF  = mDevice.findObject(new UiSelector().text("消 费"));
            public boolean checkForCondition() {
                // TODO Auto-generated method stub
                watcherdebugnotify();
                if (fail.exists()||DJFH.exists()) {
                    String gettime = gettime();
                    try {
                        waitforUiObject(DJFH);
                        if (!XF.exists()) {
                            inputaccoun.setText("01");
                            Thread.sleep(1000);
                            inputpass.setText("0000");
                            Thread.sleep(1000);
                            assist.waitforUiObject(DL);
                            Thread.sleep(1000);
                        }
                        Thread.sleep(1000);
                        result("监听一次", gettime + "时间出现一次测试失败");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });


    }

    /**
     * Watcherlowbatternotify.监听交易满后清除流水操作
     */
    public static void watchetrailfullnotify(final String methname) {
        final UiDevice mDevice;

        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.registerWatcher("fail", new UiWatcher() {
            UiObject fail = mDevice.findObject(new UiSelector().text("交易失败"));
            UiObject DJFH = mDevice.findObject(new UiSelector().text("点击返回主界面"));
            UiObject QD = mDevice.findObject(new UiSelector().text("签 到"));
            UiObject SY = mDevice.findObject(new UiSelector().text("收银"));
            UiObject QR = mDevice.findObject(new UiSelector().text("确认"));
            UiObject CZYQD = mDevice.findObject(new UiSelector().text("操作员签到"));
            UiObject CZYZH = mDevice.findObject(new UiSelector().text("请输入操作员号码"));
            UiObject CZYMM = mDevice.findObject(new UiSelector().text("请输入密码"));
            UiObject QTGN = mDevice.findObject(new UiSelector().text("其他功能设置"));
            UiObject QCLS = mDevice.findObject(new UiSelector().text("清除流水"));
            UiObject QCCZ = mDevice.findObject(new UiSelector().text("清除冲正"));
            public boolean checkForCondition() {
                // TODO Auto-generated method stub
                if (fail.exists()) {
                    String gettime = gettime();
                    try {
                        result(methname, gettime + "清除了一次流水");
                        waitforUiObject(DJFH);
                        Thread.sleep(1000);
                        waitforUiObject(QD);
                        Thread.sleep(1000);
                        waitforUiObject(CZYQD);
                        Thread.sleep(1000);
                        waitforUiObject(CZYQD);
                        Thread.sleep(1000);
                        CZYZH.setText("99");
                        Thread.sleep(1000);
                        CZYMM.setText("12345678");
                        Thread.sleep(1000);
                        waitforUiObject(QR);
                        Thread.sleep(1000);
                        waitforUiObject(QTGN);
//                        Thread.sleep(1000);
                        waitforUiObject(QCLS);
//                        Thread.sleep(1000);
                        waitforUiObject(QR);
//                        Thread.sleep(1000);
                        waitforUiObject(QCCZ);
                        Thread.sleep(1000);
                        waitforUiObject(QR);
                        mDevice.pressBack();
                        Thread.sleep(1000);
                        mDevice.pressBack();
                        CZYZH.setText("01");
                        Thread.sleep(1000);
                        CZYMM.setText("0000");
                        Thread.sleep(1000);
                        waitforUiObject(QR);
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
                return false;
            }


        });


    }

    /**
     * Watcherlowbatternotify.清除流水操作
     */
    public static void clean(final String methname) {
        final UiDevice mDevice;
        String passwordid = "com.xgd.umsapp:id/et_tellerkey";
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject fail = mDevice.findObject(new UiSelector().text("交易失败"));
        UiObject DJFH = mDevice.findObject(new UiSelector().text("点击返回主界面"));
        UiObject QD = mDevice.findObject(new UiSelector().text("签 到"));
        UiObject SY = mDevice.findObject(new UiSelector().text("收银"));
        UiObject QR = mDevice.findObject(new UiSelector().text("确认"));
        UiObject CZYQD = mDevice.findObject(new UiSelector().text("操作员签到"));
        UiObject CZYZH = mDevice.findObject(new UiSelector().text("请输入操作员号码"));
//        UiObject CZYMM = mDevice.findObject(new UiSelector().text("请输入密码"));
        UiObject CZYMM = mDevice.findObject(new UiSelector().resourceId(passwordid));
        UiObject QTGN = mDevice.findObject(new UiSelector().text("其他功能设置"));
        UiObject QCLS = mDevice.findObject(new UiSelector().text("清除流水"));
        UiObject QCCZ = mDevice.findObject(new UiSelector().text("清除冲正"));
        String gettime = assist.gettime();
        try {
            assist.watcherdebugnotify();
            assist.waitforUiObject(QD);
            Thread.sleep(1000);
            assist.waitforUiObject(CZYQD);
            Thread.sleep(1000);
            CZYZH.setText("99");
            Thread.sleep(1000);
            CZYMM.setText("12345678");
            Thread.sleep(1000);
            assist.waitforUiObject(QR);
            Thread.sleep(1000);
            assist.waitforUiObject(QTGN);
            assist.waitforUiObject(QCLS);
            assist.waitforUiObject(QR);
            assist.waitforUiObject(QCCZ);
            Thread.sleep(1000);
            assist.waitforUiObject(QR);
            mDevice.pressBack();
            Thread.sleep(1000);
            mDevice.pressBack();
            CZYZH.setText("01");
            Thread.sleep(1000);
            CZYMM.setText("0000");
            Thread.sleep(1000);
            assist.waitforUiObject(QR);
            Thread.sleep(1000);
            assist.result(methname, gettime + "清除了一次流水");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Result.LOG的名称
     *
     * @param txt the txt写入的文本
     * @throws Exception the exception
     */
    public static void result(String result, String txt) throws Exception {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(path, result + ".txt");
//        if (file.exists()){
//            file.delete();
//            file.createNewFile();
//        }else {
//            file.createNewFile();
//        }
        BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
        if(txt.contains("一次")){
            output.append(  txt+ "\r\n");
            output.close();
        }
        output.append("第 " + txt + " 次测试成功" + "\r\n");
        output.close();


    }
    public static void resultaddbatter(String result, String txt,int battery) throws Exception {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(path, result + ".txt");
//        if (file.exists()){
//            file.delete();
//            file.createNewFile();
//        }else {
//            file.createNewFile();
//        }
//        BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"GBK"));
        if(txt.contains("一次")){
            output.append(  txt+ "\r\n");
            output.close();
        }
        output.append("第 " + txt + " 次" +","+battery+"%"+"\r\n");
        output.close();


    }

    /**创建一个第一行
     * @param result 传入文档的名字
     * @throws Exception
     */
    public static void creattitle(String result) throws Exception {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(path, result + ".txt");
//        if (file.exists()){
//            file.delete();
//            file.createNewFile();
//        }else {
//            file.createNewFile();
//        }
//        BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"GBK"));
        output.append("测试次数" +","+ "  电池电量"+"\r\n");
        output.close();


    }
//    public static int getbattery() {
//        BatteryManager batteryManager = (BatteryManager)getSystemService(BATTERY_SERVICE);
//        int battery = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
//
//          return battery;
//        }


    /**
     * @return 获取当前时间
     */
    public static String gettime() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
        String hehe = dateFormat.format(now);
        return hehe;
    }


}
