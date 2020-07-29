package com.example.filetsystemtest;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xinguodu.ddiinterface.Ddi;
import com.xinguodu.ddiinterface.GeneralDdi;

import java.lang.ref.WeakReference;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static TextView txsuc;
    private TextView txtoal;
    private static TextView txfail;
    private static TextView txsuclv;
    private Button btstar,btcheck,btstop;

    private EditText mEditText;
    private MyHandler mMyHandler=new MyHandler(this);
    int fail,i,first=0;
    long     suclv;
    private String name,trim;
    private GeneralDdi mGeneralDdi;
    private String file;
//    byte[] outfile1024=new byte[128];
//    byte[] outfile128=new byte[128];
    byte[] outfile16=new byte[16];
    byte[] outfile23=new byte[23];
//    byte[] outfile128=new byte[128];
    int[] outfilelen=new int[16];
    private static Message mes1,mes2,mes3;
    boolean isthread=true;
    
    Thread star;
    Ddi mDdi;
    checkinfo mCheckinfo;

    private final Object lock = new Object();

    private boolean pause = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGeneralDdi=new GeneralDdi();
//        mDdi=new Ddi();
        viewinit();



        //开始测试
        btstar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                resumeThread();
                Log.v("TAG", "开始测试");
                Toast.makeText(MainActivity.this, "开始文件系统测试", Toast.LENGTH_SHORT).show();
                trim = mEditText.getText().toString().trim();
                txtoal.setText("总次数：" + trim);
                isthread=true;
                mCheckinfo=new checkinfo(mDdi,first);
                 star=new Thread(new Runnable() {
                    @Override
                    public void run() {

                            for ( i=1 ; i < Integer.parseInt(trim); i++) {
                                if(isthread){
                                    byte[] namebytes = ByteUtils.hexString2ByteArray("12345" + i);
                                    //返回13个数字
                                    long l = System.currentTimeMillis();
//                                    byte[] file = ByteUtils.hexString2ByteArray("1" + l + ByteUtils.string16);
                                    byte[] file = ByteUtils.hexString2ByteArray( ByteUtils.string16);

                                    Log.v("TAG",""+l);

                                    int res1 = mGeneralDdi.ddi_sys_write_file(namebytes, namebytes.length, file, file.length);
                                    SystemClock.sleep(100);
                                    int res2 = mGeneralDdi.ddi_sys_read_file(namebytes, namebytes.length, outfile16, outfilelen);
//                                    first=i;
                                     Log.v("TAG","reS1："+res1+"...RES2"+res2);


//&& Arrays.equals(file, outfile16)
                                    if (res1 == 0 && res2 == 0 && Arrays.equals(file, outfile16)) {
                                         Log.v("TAG","校验成功");
                                        mes1 = Message.obtain();
                                        mes1.what = 1;
                                        mes1.obj = "成功数：" + (i-fail);
                                        mMyHandler.sendMessage(mes1);
                                        mes2 = Message.obtain();
//                                        suclv = 100 - (Math.round(fail / Integer.parseInt(trim)));
                                        suclv = 100-(Math.round(fail / i))*100;
                                        mes2.what = 2;
                                         Log.v("TAG","成功suclv"+Math.round(fail / i)*100);
                                        mes2.obj = "成功率：" + suclv + "%";
                                        mMyHandler.sendMessage(mes2);
                                        Log.v("TAG", "获取的数据成功");
                                        Log.v("TAG", "写入的数据：" + ByteUtils.byteArray2HexString(file));
                                        Log.v("TAG", "读出的数据：" + ByteUtils.byteArray2HexString(outfile16));
                                        for(int i=0;i<outfile16.length;i++){
                                            outfile16[i]=0;
                                        }
                                    } else {
                                        mes3 = Message.obtain();
                                        mes3.what = 3;
                                        mes3.obj = "失败数：" +fail;
                                        mMyHandler.sendMessage(mes3);
                                        fail += 1;
                                        Log.v("TAG", "获取的数据失败"+fail);
                                        Log.v("TAG", "写入的数据：" + ByteUtils.byteArray2HexString(file));
                                        Log.v("TAG", "读出的数据：" + ByteUtils.byteArray2HexString(outfile16));
                                    }
                                }else{
                                    break;
                                }




                            }




                    }
                });
                
                     star.start();
                 



            }

        });



        btcheck.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                         Log.v("TAG","检测信息");




        			}

        		});
        btstop.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                         Log.v("TAG","停止运行");

//                         pauseThread();
                        Toast.makeText(MainActivity.this, "暂停测试", Toast.LENGTH_SHORT).show();
                        isthread=false;

//                        star.interrupt();



                        



        			}

        		});


    }
    static class MyHandler extends Handler {
        WeakReference<MainActivity> mactivity;

        public MyHandler(MainActivity activity) {
            mactivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String s1 = msg.obj.toString();
                    if (s1.contains("成功数")) {
                        txsuc.setText(s1);
                    } else {
                        txsuc.setText("成功数:失败");
                        txsuc.setTextColor(Color.RED);
                    }
                    break;
                    case 2:
                    String s2 = msg.obj.toString();
                    if (s2.contains("%")) {
                        txsuclv.setText(s2);
                    } else {
                        txsuclv.setText("成功率:失败");
                        txsuclv.setTextColor(Color.RED);
                    }
                    break;
                    case 3:
                    String s3 = msg.obj.toString();
                    if (s3.contains("失败")) {
                        txfail.setText(s3);
                    } else {
                        txsuclv.setText("成率:失败");
                        txsuclv.setTextColor(Color.RED);
                    }
                    break;
                default:
                    break;
            }

        }
    }



    private void viewinit() {
        btstar=(Button) findViewById(R.id.btstar);
        btstop=(Button) findViewById(R.id.btstop);
        btcheck=(Button) findViewById(R.id.btck);
        txsuc=(TextView) findViewById(R.id.success);
        txtoal=(TextView) findViewById(R.id.total);
        txfail=(TextView) findViewById(R.id.fail);
        txsuclv=(TextView) findViewById(R.id.successlv);
        mEditText=(EditText) findViewById(R.id.edtesttime);

    }

    /**
     * 调用该方法实现线程的暂停
     */
    void pauseThread(){
        pause = true;
    }


    /*
    调用该方法实现恢复线程的运行
     */
    void resumeThread(){
        pause =false;
        synchronized (this){
            star.notify();
        }
    }

    /**
     * 这个方法只能在run 方法中实现，不然会阻塞主线程，导致页面无响应
     */
    void onPause1() {
//        synchronized (this) {
            try {
                star.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
//            }
        }
    }
}


