package zhepan.com.uitest;




import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBCReveiver extends BroadcastReceiver {
	private static final String TAG = "MainActivity123";

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		//启动服务

		//启动应用
		Log.v(TAG,"开始打开应用");
		Intent intent = new Intent(arg0, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		arg0.startActivity(intent);
	}

}
