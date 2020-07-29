package zhepan.com.uitest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import zhepan.com.mytest.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    Button Btreboot,BTN5sm,BTN5RF,BTN6rf,BRN6sm,BTSS,closss,SDfill,isrebootcheck,installAPP,unrebootcheck,P100scan,P100RF,ccbtest;
    Intent staractintent, starbrodintent;
    //包名
    String pkgName = "zhepan.com.testcase_reboottrad";
    //类名clsname
    String clsName = "ExampleInstrumentedTest";
    String sinclsName = "sinExampleInstrumentedTest";
    String model ;
    boolean isreboot = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Log.v(TAG, "开始测试");
        final Intent intent1 = new Intent(MainActivity.this, myintentservice.class);
        intent1.setAction("com.test.intentservice");
        intentinit();
        model = android.os.Build.MODEL;
        isreboot = getbooleamSP();
        if (isreboot) {
            try {
                //获取测试次数
                int sp = getintSP();
                sp = ++sp;
                putintSP(sp);
                result("" + sp);
                Mystarservice(intent1, "useAppContext", 2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Btreboot.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, "重启签到交易测试", Toast.LENGTH_SHORT).show();
                if (isreboot) {
                    SinMystarservice(intent1, "useAppContext", 2);
                } else {
                    Mystarservice(intent1, "useAppContext", 1);
                }

            }
        });
        BTN5sm.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, "N5扫码测试", Toast.LENGTH_SHORT).show();
//                if (model.equalsIgnoreCase("N86")||model.equalsIgnoreCase("N5")) {
//                    Mystarservice(intent1, "N5smautosign", 1);
//                } else {
//                    Mystarservice(intent1, "N86scanautosign", 1);
//                }
                Mystarservice(intent1, "N5smautosign", 1);
            }

        });
        BTN5RF.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, "N5RF测试", Toast.LENGTH_SHORT).show();
//                if (model.equalsIgnoreCase("N86")||model.equalsIgnoreCase("N5")) {
//                    Mystarservice(intent1, "N5SRFautosign", 1);
//                } else {
//                    Mystarservice(intent1, "N86SRFautosign", 1);
//                }
                Mystarservice(intent1, "N5SRFautosign", 1);
            }

        });
        BTN6rf.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, "N6RF测试", Toast.LENGTH_SHORT).show();
                if (isreboot) {
                    SinMystarservice(intent1, "N6SRFautosign", 2);
                } else {
                    Mystarservice(intent1, "N6SRFautosign", 1);

                }


            }

        });
        BRN6sm.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, "N6扫码测试", Toast.LENGTH_SHORT).show();
                if (isreboot) {
                    SinMystarservice(intent1, "N6smautosign", 2);
                } else {
                    Mystarservice(intent1, "N6smautosign", 1);

                }


            }

        });
        //P100扫码测试
        P100scan.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, "P100扫码测试", Toast.LENGTH_SHORT).show();
                if (isreboot) {
                    SinMystarservice(intent1, "P100scanchange", 2);
                } else {
                    Mystarservice(intent1, "P100scanchange", 1);
                }
            }

        });
        //P100RF测试
        P100RF.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, "P100扫码测试", Toast.LENGTH_SHORT).show();
                if (isreboot) {
                    SinMystarservice(intent1, "P100RFchange", 2);
                } else {
                    Mystarservice(intent1, "P100RFchange", 1);
                }
            }

        });
        //建行招标测试
        ccbtest.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        Toast.makeText(MainActivity.this, "建行招标测试", Toast.LENGTH_SHORT).show();
                     Intent intent=new Intent(MainActivity.this, CCBActivity.class);
                     intent.setAction("android.intent.action.ccbtest");
                     intent.addCategory("android.intent.category.LAUNCHER");
                     startActivity(intent);



        			}

        		});
        //调用扫码服务
        BTSS.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, "测试扫码服务", Toast.LENGTH_SHORT).show();

                Mystarservice(intent1, "SSMssautosign", 1);
                Log.v("TAG","启动广播");
                starbrodintent.setAction("com.m520it.receiver01.action.starscanservice");
                sendBroadcast(starbrodintent);
            }
        });

        //SD卡fill
        SDfill.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                staractintent.setAction("android.intent.action.sdtest");
                startActivity(staractintent);
            }

        });
        //进入装载APP界面
        installAPP.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                       Intent intent=new Intent(MainActivity.this, installappactivity.class);
                       intent.setAction("android.intent.action.installapp");
                       intent.addCategory("android.intent.category.installapp");
                       startActivity(intent);


        			}

        		});
        //机器重启
        closss.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, "机器重启...", Toast.LENGTH_SHORT).show();
                rebootmeth();

            }
        });



        //循环测试时需要重启
        isrebootcheck.setOnClickListener(new CheckBox.OnClickListener() {

            @Override
            public void onClick(View v) {
                isreboot = true;
                putbooleanSP(isreboot);
            }
        });
        //循环测试时取消重启
        unrebootcheck.setOnClickListener(new CheckBox.OnClickListener() {

            @Override
            public void onClick(View v) {
                isreboot = false;
                putbooleanSP(isreboot);
            }
        });
    }

    private void intentinit() {
        staractintent = new Intent(this, SDtestActivity.class);
        starbrodintent = new Intent();
    }

    /**
     * 循环启动服务
     *
     * @param intent
     * @param methname
     * @param tag
     */
    private void Mystarservice(Intent intent, String methname, int tag) {
        Log.v(TAG, "BTN5smN5S扫码测试");
        //方法名n5SRFautosign
        String mthName = methname;
        String s = generateCommand(pkgName, clsName, mthName);
        Log.v("TAG", "" + s);
        Bundle mbd = new Bundle();
        mbd.putString("command", s);
        mbd.putInt("TAG", tag);
        intent.putExtras(mbd);
        startService(intent);
    }

    /**
     * 单次重启服务
     *
     * @param intent
     * @param methname
     * @param tag
     */
    private void SinMystarservice(Intent intent, String methname, int tag) {
//        Log.v(TAG, "BTN5smN5S扫码测试");
        //方法名n5SRFautosign
        String mthName = methname;
        String s = generateCommand(pkgName, sinclsName, mthName);
        Log.v("TAG", "" + s);
        Bundle mbd = new Bundle();
        mbd.putString("command", s);
        mbd.putInt("TAG", tag);
        intent.putExtras(mbd);
        startService(intent);
    }

    /**
     * 机器重启
     *
     * @throws InterruptedException
     */
    private void rebootmeth() {
//        Thread.sleep(sleeptime * 1000);
        Intent shutdownintent = new Intent();
        shutdownintent.setAction("com.xgd.powermanager.REBOOT");
        sendBroadcast(shutdownintent);
    }

    private void init() {
        //重启自动交易
        Btreboot = (Button) findViewById(R.id.Btreboot);
        BTN5sm = (Button) findViewById(R.id.BtN5sm);
        BTN5RF = (Button) findViewById(R.id.BTNS5RF);
        SDfill = (Button) findViewById(R.id.BTSDfill2);
        BTN6rf = (Button) findViewById(R.id.BtN6rf);
        BRN6sm = (Button) findViewById(R.id.BtN6sm);
        P100RF = (Button) findViewById(R.id.btP100rf);
        P100scan = (Button) findViewById(R.id.btP100scan);
        ccbtest = (Button) findViewById(R.id.ccbtest);
        //调用扫码服务
        BTSS = (Button) findViewById(R.id.BTss);
        installAPP = (Button) findViewById(R.id.ccbRFtrade);
        //停止测试
        closss = (Button) findViewById(R.id.closerservice);
        isrebootcheck = (CheckBox) findViewById(R.id.CKisreboot);
        unrebootcheck = (CheckBox) findViewById(R.id.unrebootcheckBox);

    }


    /**
     * @param i put int数据
     */
    private void putintSP(int i) {
//		int i=1;
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        // 存数据
        editor.putInt("key", i);
//		editor.putString("psw", p);
//		editor.putBoolean("remember", true);
//提交
        editor.commit();
    }

    /**
     * @param isreboot put boolean 数据
     */
    private void putbooleanSP(boolean isreboot) {
//		int i=1;
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        // 存数据
//        editor.putInt("key", i);
//		editor.putString("psw", p);
        editor.putBoolean("reboot", isreboot);
//提交
        editor.commit();
    }

    /**
     * @return 获取boolean类型
     */
    private boolean getbooleamSP() {
        SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
        boolean u = preferences.getBoolean("reboot", false);
        return u;
//		String p = preferences.getString("psw","");
    }

    private void putSP(Class c) {
        try {
            Object o = c.newInstance();
            if (o instanceof Integer) {
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putInt("key", (Integer) o);
                editor.commit();
            } else if (o instanceof String) {
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putString("psw", (String) o);
                editor.commit();
            } else if (o instanceof Boolean) {
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putBoolean("isreboot", true);
                editor.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @return 获取int类型
     */
    private int getintSP() {
        SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
        int u = preferences.getInt("key", 0);
        return u;
//		String p = preferences.getString("psw","");
    }


    /**
     * 生成命令
     *
     * @param pkgName uiautomator包名
     * @param clsName uiautomator类名
     * @param mtdName uiautomator方法名
     * @return
     */
    public String generateCommand(String pkgName, String clsName, String mtdName) {
        String command = "am instrument -w -r -e debug false -e class "
                + pkgName + "." + clsName + "#" + mtdName + " "
                + pkgName + ".test/android.support.test.runner.AndroidJUnitRunner";
        Log.e("test1: ", command);
        return command;
    }

    public static void result(String txt) throws IOException {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(path, "重启签到交易.txt");
//        if (file.exists()){
//            file.delete();
//            file.createNewFile();
//        }else {
//            file.createNewFile();
//        }
        BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
        output.append("第 " + txt + " 次重启测试成功" + "\r\n");
        output.close();


    }


    /**
     * 点击按钮对应的方法
     *
     * @param v
     */
    public void runMyUiautomator(View v) {
        Log.i(TAG, "runMyUiautomator: ");
        new UiautomatorThread().start();
        Toast.makeText(this, "start run", Toast.LENGTH_SHORT).show();
    }

    /**
     * 运行uiautomator是个费时的操作，不应该放在主线程，因此另起一个线程运行
     */
    class UiautomatorThread extends Thread {
        @Override
        public void run() {
            super.run();
            String command = generateCommand("zhepan.com.mytestcase", "ExampleInstrumentedTest", "useAppContext");
            CMDUtils.CMD_Result rs = CMDUtils.runCMD1(command, true, true);
//            Log.e(TAG, "run: " + rs.error + "-------" + rs.success);
        }

        /**
         * 生成命令
         *
         * @param pkgName uiautomator包名
         * @param clsName uiautomator类名
         * @param mtdName uiautomator方法名
         * @return
         */
        public String generateCommand(String pkgName, String clsName, String mtdName) {
            String command = "am instrument -w -r -e debug false -e class "
                    + pkgName + "." + clsName + "#" + mtdName + " "
                    + pkgName + ".test/android.support.test.runner.AndroidJUnitRunner";
            Log.e("test1: ", command);
            return command;
        }

    }
}
