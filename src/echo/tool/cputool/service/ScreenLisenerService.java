package echo.tool.cputool.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import echo.tool.cputool.broadcast.ScreenOnOffBroadcastReceiver;
import echo.tool.cputool.common.CommonUnit;

public class ScreenLisenerService extends Service {
	private ScreenOnOffBroadcastReceiver	mReceiver	= null;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (mReceiver == null) {
			mReceiver = new ScreenOnOffBroadcastReceiver();
			IntentFilter recevierFilter = new IntentFilter();
			recevierFilter.addAction(Intent.ACTION_SCREEN_ON);
			recevierFilter.addAction(Intent.ACTION_SCREEN_OFF);
			registerReceiver(mReceiver, recevierFilter);
		} else {
			CommonUnit.loge("ScreenLisenerService already reged");
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		if (mReceiver != null) {
			unregisterReceiver(mReceiver);
		}
		super.onDestroy();
	}

}
