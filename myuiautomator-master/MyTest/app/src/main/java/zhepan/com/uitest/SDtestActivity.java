package zhepan.com.uitest;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import zhepan.com.mytest.R;
public class SDtestActivity extends AppCompatActivity {
    public TextView mpr;
    public TextView mtitle;
    public Button mbtstartest;
    public ProgressBar mProgressBar;
    private Button mbtendtest;
    int avalible;
    mAsyncTask mMAsyncTask;
    boolean isrun = true;
    String endpath;


    class mAsyncTask extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            Log.v("TAG", "onPreExecute");
            avalible = getAvailableExternalMemorySize2();
            endpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SDtest";
            mProgressBar.setMax(avalible);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.v("TAG", "doInBackground");
            int count = 0;
            int length = 1;
            for (int i = 0; i <= avalible - 2; i++) {
                SDcardtest(endpath);
                if (i == avalible - 2) {
                    File endfile1 = new File(endpath);
                    File[] files = endfile1.listFiles();
                    for (File f : files) {
                        f.delete();
                        Log.v("TAG", "" + f.getName());
                    }
//                    Toast.makeText(SDtestActivity.this, "文件被删除从新开始测试", Toast.LENGTH_SHORT).show();
                    Log.v("TAG", "文件被删了开始重新跑");
                    avalible = getAvailableExternalMemorySize2();
                    i = 0;
                    count = 0;
                    continue;
                }
                count += length;
                // 可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
                publishProgress(count);
            }
//            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.v("TAG", "onProgressUpdate");
            mProgressBar.setProgress(values[0]);
            mpr.setText("测试进度：" + values[0] + "/" + avalible);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.v("TAG", "onPostExecute");
            super.onPostExecute(s);
        }

        @Override
        protected void onCancelled() {
            Log.v("TAG", "onCancelled");
            super.onCancelled();
        }

        @Override
        protected void onCancelled(String s) {
            Log.v("TAG", "onCancelled");

            super.onCancelled(s);
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdtestactivity);
        init();
        mMAsyncTask = new mAsyncTask();
        mbtstartest.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mbtstartest.isClickable()) {
                    mMAsyncTask.execute();
                    mbtstartest.setClickable(false);
                    mbtstartest.setBackgroundResource((R.color.colorAccent3));
                }
            }

        });
        mbtendtest.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mMAsyncTask.cancel(true);
                mbtstartest.setClickable(true);
                mbtstartest.setBackgroundResource((R.color.colorAccent2));


            }

        });

    }

    /**
     * 获取可用内存大小
     *
     * @param context
     * @return
     */
    public long getFreeMem(Context context) {
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Activity.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(info);
        // 单位Byte
        return info.availMem;
    }


    /**
     * 获取外部存储可用内存，单位是M
     *
     * @return
     */
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


    /**
     * 文件拷贝和删除
     */
    private void SDcardtest(String endpath) {
//        String endpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SDtest";
        Log.v("TAG", "endpath" + endpath);
        File endfile1 = new File(endpath);
        Log.v("TAG", "" + endpath);
        try {
            if (!endfile1.exists()) {
                endfile1.mkdir();
            }
            InputStream is = getClass().getResourceAsStream("/assets/test.txt");
            long begin = System.currentTimeMillis();
            Log.v("TAG", "" + begin);
            File endfile = new File(endpath, begin + ".txt");
            Log.v("TAG", "");
            copyFileUsingFileStreams(is, endfile);
//                if(i==2999){
//                    File[] files = endfile1.listFiles();
//                    for (File f:files) {
//                        f.delete();
//                        Log.v("TAG",""+f.getName());
//                    }
//                    Log.v("TAG","文件被删了开始重新跑");
//                    i=0;
//                    continue;
//                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SDcardtest2() {
        String endpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SDtest";
        Log.v("TAG", "endpath" + endpath);
        File endfile1 = new File(endpath);
        Log.v("TAG", "" + endpath);
        try {
            for (int i = 0; i <= 1000; i++) {
                if (!endfile1.exists()) {
                    endfile1.mkdir();
                }
                InputStream is = getClass().getResourceAsStream("/assets/test.txt");
                long begin = System.currentTimeMillis();
                Log.v("TAG", "" + begin);
                File endfile = new File(endpath, begin + ".txt");
                Log.v("TAG", "" + i);
                copyFileUsingFileStreams(is, endfile);
                if (i == 2999) {
                    File[] files = endfile1.listFiles();
                    for (File f : files) {
                        f.delete();
                        Log.v("TAG", "" + f.getName());
                    }
                    Log.v("TAG", "文件被删了开始重新跑");
                    i = 0;
                    continue;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void init() {
        mpr = (TextView) findViewById(R.id.progress);
        mtitle = (TextView) findViewById(R.id.txttitle);
        mbtstartest = (Button) findViewById(R.id.btnsatr);
        mbtendtest = (Button) findViewById(R.id.btnend);
        mProgressBar = (ProgressBar) findViewById(R.id.pgbar);
    }

    public SDtestActivity() {
        super();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
