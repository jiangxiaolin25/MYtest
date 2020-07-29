package zhepan.com.uitest;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.annotation.Nullable;
import zhepan.com.mytest.R;

/**
 * Created by jiangxiaolin on 2019/6/21.
 */

public class myintentservice extends IntentService {
    String CHANNEL_ONE_ID = "CHANNEL_ONE_ID";
    String CHANNEL_ONE_NAME= "CHANNEL_ONE_ID";
    private static final String TAG = "TAG";
    private   String sscan = "am start -n com.example.autoscantest/com.example.autoscantest.MainActivity";
    private   String P100 = "am start -n com.xgd.showdemo/com.xgd.showdemo.MainActivity";
    private   String N86 = "am start -n com.nexgo.payment/com.nexgo.payment.trans.login.LoginActivity";


    public myintentservice() {


        super("myintentservice");
        Log.v(TAG, "构造函数");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.v(TAG, "onHandleIntent");
        Bundle extras = intent.getExtras();
        int tag = extras.getInt("TAG");
        String command = extras.getString("command");
        Log.v("TAG", "服务里面获取到的值" + command + "...." + tag);
        CMDUtils.CMD_Result rs1;
        if (intent.getAction().equalsIgnoreCase("com.test.intentservice")) {
            switch (tag) {
                case 1:
                    if(command.contains("SSMssautosign")){
//                        Intent bintent=new Intent("com.m520it.receiver01.action.starscanservice");
//                        sendBroadcast(intent);
                        rs1 = CMDUtils.runCMD1(sscan, true, true);
                    }
                    if(command.contains("P100")){
//                        Intent bintent=new Intent("com.m520it.receiver01.action.starscanservice");
//                        sendBroadcast(intent);
                        rs1 = CMDUtils.runCMD1(P100, true, true);
                    }
                    if(command.contains("N86")){
//                        Intent bintent=new Intent("com.m520it.receiver01.action.starscanservice");
//                        sendBroadcast(intent);
                        rs1 = CMDUtils.runCMD1(N86, true, true);
                    }
                    rs1 = CMDUtils.runCMD1(command, true, true);
                    break;
                case 2:
                    rs1 = CMDUtils.runCMD1(command, true, true);
                    try {
                        rebootmeth(12);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
//                    SDcardtest();
                    break;
                default:
            }
        } else {
            Log.v("TAG", "action错误" + intent.getAction());
        }
    }

    private void SDcardtest() {
        String endpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SDtest";
        Log.v("TAG", "endpath" + endpath);
        File endfile1 = new File(endpath);
        Log.v("TAG", "" + endpath);
        try {
            for (int i = 0; i <= 3000; i++) {
                if (!endfile1.exists()) {
                    endfile1.mkdir();
                }
                InputStream is = getClass().getResourceAsStream("/assets/test.txt");
                long begin = System.currentTimeMillis();
                Log.v("TAG", "" + begin);
                File endfile = new File(endpath, begin + ".txt");
                 Log.v("TAG",""+i);
                copyFileUsingFileStreams(is, endfile);
                if(i==2999){
                    File[] files = endfile1.listFiles();
                    for (File f:files) {
                        f.delete();
                         Log.v("TAG",""+f.getName());
                    }
                    Log.v("TAG","文件被删了开始重新跑");
                    i=0;
                    continue;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void rebootmeth(int sleeptime) throws InterruptedException {
        Thread.sleep(sleeptime * 1000);
        Intent shutdownintent = new Intent();
        shutdownintent.setAction("com.xgd.powermanager.REBOOT");
        sendBroadcast(shutdownintent);
    }

    private static void copyFileUsingFileStreams(InputStream input, File dest)
            throws IOException {
        OutputStream output = null;
        try {
//            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.v(TAG, "onStartCommand");
        startfrontservice();
        return super.onStartCommand(intent, flags, startId);


    }
    public void startfrontservice() {
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext()); //获取一个Notification构造器
        Intent nfIntent = new Intent(this, MainActivity.class);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0))
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.xgd)) // 设置下拉列表中的图标(大图标)
                .setContentTitle("UI自动化测试") // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.xgd) // 设置状态栏内的小图标
                .setContentText("自动化测试中....") // 设置上下文内容
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //修改安卓8.1以上系统报错
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ONE_ID, CHANNEL_ONE_NAME,                    NotificationManager.IMPORTANCE_MIN);
            notificationChannel.enableLights(false);//如果使用中的设备支持通知灯，则说明此通知通道是否应显示灯
            notificationChannel.setShowBadge(false);//是否显示角标
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
            builder.setChannelId(CHANNEL_ONE_ID);
        }
        Notification notification = builder.build(); // 获取构建好的Notification
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
        startForeground(110,notification);

        }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(myintentservice.this, "服务已关闭", Toast.LENGTH_LONG).show();
        Log.v("TAG", "onDestroy");

    }
}
