package zhepan.com.uitest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import zhepan.com.mytest.R;

/**
 * 作者：jiangxiaolin on 2020/3/31
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class CCBActivity extends AppCompatActivity {
    //包名
    String pkgName = "zhepan.com.testcase_reboottrad";
    //类名clsname
    String clsName = "ExampleInstrumentedTest";


    private Button ccbRFButton,ccbysqbutton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ccbtest);
        init();
        final Intent intent1 = new Intent(CCBActivity.this, myintentservice.class);
        intent1.setAction("com.test.intentservice");
        ccbRFButton.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        Toast.makeText(CCBActivity.this, "ccbRFtrade", Toast.LENGTH_SHORT).show();
                            Mystarservice(intent1, "ccbRFtrade", 1);
        			}

        		});
        ccbysqbutton.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        Toast.makeText(CCBActivity.this, "ccbysqtrade", Toast.LENGTH_SHORT).show();
                        Mystarservice(intent1, "ccbRFtrade", 1);
        			}

        		});



    }

    private void init() {
        ccbRFButton=(Button) findViewById(R.id.ccbRFtrade);
        ccbysqbutton=(Button) findViewById(R.id.ccbysqtrade);
    }

    /**
     * 循环启动服务
     *
     * @param intent
     * @param methname
     * @param tag
     */
    private void Mystarservice(Intent intent, String methname, int tag) {
        Log.v("TAG", "BTN5smN5S扫码测试");
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
