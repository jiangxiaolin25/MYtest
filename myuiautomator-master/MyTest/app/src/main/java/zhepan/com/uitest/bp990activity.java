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
 * 作者：jiangxiaolin on 2020/6/10
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class bp990activity  extends AppCompatActivity {
     private Button sacn;
    String pkgName = "zhepan.com.testcase_reboottrad";
    String openactivity = "zhepan.com.testcase_reboottrad";
    //类名clsname
    String clsName = "ExampleInstrumentedTest";
    final Intent intent1 = new Intent(bp990activity.this, myintentservice.class);



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dditest);
        sacn=(Button) findViewById(R.id.scan);

        sacn.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Toast.makeText(bp990activity.this, "BP990scan", Toast.LENGTH_SHORT).show();
                CMDUtils.runCMD1(openactivity, true, true);
                Mystarservice(intent1, "BP990scan", 1);



            }

        });


    }

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

    public String generateCommand(String pkgName, String clsName, String mtdName) {
        String command = "am instrument -w -r -e debug false -e class "
                + pkgName + "." + clsName + "#" + mtdName + " "
                + pkgName + ".test/android.support.test.runner.AndroidJUnitRunner";
        Log.e("test1: ", command);
        return command;
    }
}
