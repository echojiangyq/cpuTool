package echo.tool.cputool.broadcast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jyq.echoapi.utils.ListUtils;
import jyq.echoapi.utils.PreferencesUtils;
import jyq.echoapi.utils.ResourceUtils;
import jyq.echoapi.utils.ShellUtils;
import jyq.echoapi.utils.ShellUtils.CommandResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import echo.tool.cputool.common.CommonUnit;
import echo.tool.cputool.common.Constant;

public class ScreenOnOffBroadcastReceiver extends BroadcastReceiver {
	Timer				mTimer			= null;
	static final int	DELAY			= 5000;
	Context				mContext;
	boolean				mCurrentIsHigh	= false;

	@Override
	public void onReceive(Context context, Intent intent) {
		mContext = context;
		if (!PreferencesUtils.getBoolean(mContext, Constant.PREFERENCE_KEY_SCREEN, false)) {
			Looper.prepare();
			CommonUnit.toastShow(mContext, "cputool Î´¸úËæÆÁÄ»×´Ì¬");
			return;
		}

		if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
			stopTimer();
			startTimer(true);
		} else if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
			stopTimer();
			startTimer(false);
		}
	}

	private String[] getCmdFromAssert(String name) {
		List<String> cmdList = ResourceUtils.geFileToListFromAssets(mContext, name);
		return ListUtils.convert2Array(cmdList);
	}

	void startTimer(final boolean isHigh) {
		if (mTimer == null) {
			mTimer = new Timer();
			mTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					CommandResult ret = ShellUtils.execCommand(
							"cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor", false);
					if (ret.result == 0) {
						if (ret.successMsg.contains("powersave")) {
							mCurrentIsHigh = false;
						} else {
							mCurrentIsHigh = true;
						}
					}

					CommonUnit.logd(String.format("startTimer(%s) -----> cputool timer start", (isHigh ? "on" : "off")));
					CommandResult lres = new CommandResult();
					if (isHigh && !mCurrentIsHigh) {
						lres = ShellUtils.execCommand(getCmdFromAssert(Constant.FILE_HIGH), true);
					} else if (!isHigh && mCurrentIsHigh) {
						lres = ShellUtils.execCommand(getCmdFromAssert(Constant.FILE_LOW), true);
					} else {
						Looper.prepare();
						CommonUnit.toastShow(mContext, String.format("current£º%s, intent to: %s", ret.successMsg,
								isHigh ? "perfermance" : "powersave"));
					}
					if (lres.result != 0) {
						CommonUnit.loge(lres.errorMsg);
						Looper.prepare();
						CommonUnit.toastShow(mContext, lres.errorMsg);
					}
				}
			}, DELAY);

		} else {
			CommonUnit.loge(String.format("startTimer(%s) -----> cputool timer already started",
					(isHigh ? "on" : "off")));
		}
	}

	void stopTimer() {
		if (mTimer != null) {
			CommonUnit.logd("stopTimer() -----> cputool timer stopped");
			mTimer.purge();
			mTimer.cancel();
			mTimer = null;
		} else {
			CommonUnit.loge("stopTimer() -----> cputool timer not running");
		}
	}
}
