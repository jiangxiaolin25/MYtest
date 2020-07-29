package zhepan.com.uitest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import zhepan.com.mytest.R;

/**
 * 作者：jiangxiaolin on 2020/5/28
 * 邮箱：jiangxiaolin@xgd.com
 *
 * ToDo：
 */
public class firstactivity extends AppCompatActivity {
     private Button uitest,btuitest,bt990;
     String inbackcamere="am start -a android.media.action.IMAGE_CAPTURE --ei android.intent.extras.CAMERA_FACING 1";
     String infrontcamere= "am start -a android.media.action.IMAGE_CAPTURE --ei android.intent.extras.CAMERA_FACING 0";
     String back="input keyevent 4";
     String capture="input keyevent 27";
    String endpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera";
    private int mCurrentCamIndex;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondactivity);
        initview();
      final   Intent intent=new Intent(firstactivity.this, MainActivity.class);
        uitest.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        Toast.makeText(firstactivity.this, "开始相机测试", Toast.LENGTH_SHORT).show();
                          Thread thread=new Thread(new Runnable() {
                                          @Override
                                          public void run() {
                                              for(int i=0;i<99999;i++){
                                                 CMDUtils.runCMD1(inbackcamere, true, true);
                                                 SystemClock.sleep(2000);
//                                                  CMDUtils.runCMD1(back, true, true);
//                                                  SystemClock.sleep(1000);
                                                  CMDUtils.runCMD1(capture, true, true);
//                                                  CMDUtils.runCMD1(infrontcamere, true, true);
                                                  SystemClock.sleep(3000);
                                                  CMDUtils.runCMD1(back, true, true);
                                                  SystemClock.sleep(4000);
                                                 Log.v("TAG",""+i);
                                                  int availableExternalMemorySize2 = getAvailableExternalMemorySize2();
                                                   Log.v("TAG","availableExternalMemorySize2"+availableExternalMemorySize2);
                                                  if(availableExternalMemorySize2<20){
                                                      File endfile1 = new File(endpath);
                                                      File[] files = endfile1.listFiles();
                                                      for (File f : files) {
                                                          f.delete();
                                                          Log.v("TAG", "" + f.getName());
                                                      }

                                                  }
                                              }

                                          }
                                      });

                          thread.start();
//










        			}

        		});

        btuitest.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub

                        intent.setAction("android.intent.action.MAIN1");
                        intent.addCategory("android.intent.category.LAUNCHER1");
                        startActivity(intent);
        			}

        		});

        bt990.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub



        			}

        		});



    }

    public int getAvailableExternalMemorySize2() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //sdcard状态是没有挂载的情况
            Toast.makeText(this, "sdcard不存在或未挂载", Toast.LENGTH_SHORT).show();
            return -1;
        }
        File sdcard_filedir = Environment.getExternalStorageDirectory();//得到sdcard的目录作为一个文件对象
        long usableSpace = sdcard_filedir.getUsableSpace();//获取文件目录对象剩余空间
        long l = usableSpace / (1024 * 1024);
        Log.v("TAG", "空间剩余多少M" + l);
        if (usableSpace < 1024 * 1024 * 200) {//判断剩余空间是否小于200M
            return (int) l;
        }
        return (int) l;

    }
    public void initview() {
        uitest=(Button) findViewById(R.id.UItest);
        btuitest=(Button) findViewById(R.id.btuitest1);
        bt990=(Button) findViewById(R.id.BP990);

        }






}


