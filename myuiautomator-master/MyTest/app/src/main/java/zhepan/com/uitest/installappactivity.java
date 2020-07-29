package zhepan.com.uitest;


import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xgd.smartpos.manager.ICloudService;

import com.xgd.smartpos.manager.app.IAppInstallObserver;
import com.xgd.smartpos.manager.app.IAppManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import zhepan.com.mytest.R;

public class installappactivity extends AppCompatActivity implements ServiceConnection {
     private Button installUIapp;
     private Intent mIntent;
    private ICloudService mICloudService;
    private int APP_MANAGER = 1;
    private IBinder binder;
    private IAppManager mIAppManager = null;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyStoragePermissions(this);
        setContentView(R.layout.install);
        installUIapp=(Button) findViewById(R.id.ccbRFtrade);
        installUIapp.setOnClickListener(new Button.OnClickListener() {
        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        mIntent = new Intent();
                        mIntent.setAction("com.xgd.smartpos.service.SYSTEM_APIMANAGER");
                        // com.ccb.smartpos.service.CLOUD_MANAGER
                        mIntent.setPackage("com.xgd.possystemservice");
                        bindService(mIntent, installappactivity.this, Context.BIND_AUTO_CREATE);
                         Log.v("TAG","绑定服务完成");


        			}

        		});

    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};


    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }
    @Override
    public void onServiceConnected(ComponentName name1, IBinder service) {
        {
            // TODO Auto-generated method stub

            Log.v("TAG", "onServiceConnected");
            // mBinder = DataBackup.Stub.asInterface(service);
            mICloudService = ICloudService.Stub.asInterface(service);
            if (mICloudService != null) {
                try {
                    Log.v("TAG", "mICloudService");
                    binder = mICloudService.getManager(APP_MANAGER);

                    mIAppManager = IAppManager.Stub.asInterface(binder);
//、Autoscantest.apk
                    String[] appname = {"Autoscantest.apk", "mytestcase-debug-androidTest.apk", "mytestcase-debug.apk"};
                    // 安装APP
                    for (int j = 0; j < appname.length; j++) {
                        File file = new File("/sdcard/" + appname[j]);
                        if (file.exists()) {
                            file.delete();
                        }
                        Log.v("TAG", "file进行中..." + j);
                        InputStream is = getAssets().open(appname[j]);
                        FileOutputStream fos = new FileOutputStream(file);
                        byte[] temp = new byte[1024];
                        int i = 0;
                        while ((i = is.read(temp)) > 0) {
                            fos.write(temp, 0, i);
                        }
                        fos.close();
                        is.close();
                        Log.v("TAG", "file完成" + j);
                    }
                    final IAppInstallObserver.Stub observer1 = new IAppInstallObserver.Stub() {
                        @Override
                        public void onInstallFinished(String packageName, int returnCode, String msg)
                                throws RemoteException {
                            Log.v("TAG", "IAppDeleteObserver basePackageName:" + packageName);
                            Log.v("TAG", "IAppDeleteObserver returnCode:" + returnCode);
                            Log.v("TAG", "IAppDeleteObserver msg:" + msg);
                            try {
                                Log.v("TAG", "应用安装完成");
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        ;
                    };
                    IAppInstallObserver.Stub observer2 = new IAppInstallObserver.Stub() {
                        @Override
                        public void onInstallFinished(String packageName, int returnCode, String msg)
                                throws RemoteException {
                            Log.v("TAG", "IAppDeleteObserver basePackageName:" + packageName);
                            Log.v("TAG", "IAppDeleteObserver returnCode:" + returnCode);
                            Log.v("TAG", "IAppDeleteObserver msg:" + msg);
                            try {
                                Log.v("TAG", "file2应用安装完成");
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }

                        ;
                    };

                    Log.v("TAG", "file2observer2完成");

                    mIAppManager.installApp("/sdcard/mytestcase-debug.apk", observer1, "zhepan.com.mytest");
                    Log.v("TAG", "mytestcase-debug安装完成");
                    mIAppManager.installApp("/sdcard/mytestcase-debug-androidTest.apk", observer2, "zhepan.com.mytest");
                    Log.v("TAG", "file2mytestcase-debug-androidTest.apk安装完成");
                    //安装扫码服务应用
                    mIAppManager.installApp("/sdcard/Autoscantest.apk", observer2, "zhepan.com.mytest");
                    Log.v("TAG", "file2mytestcase-debug-androidTest.apk安装完成");

                    Toast.makeText(installappactivity.this, "全部应用安装完成", Toast.LENGTH_LONG).show();
                    installUIapp.setBackgroundColor(Color.parseColor("#999B9B"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.v("TAG","服务断开连接");

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
