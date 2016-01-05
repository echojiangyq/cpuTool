package echo.tool.cputool.activity;

import java.util.List;

import jyq.echoapi.utils.ListUtils;
import jyq.echoapi.utils.PreferencesUtils;
import jyq.echoapi.utils.ResourceUtils;
import jyq.echoapi.utils.ShellUtils;
import jyq.echoapi.utils.ShellUtils.CommandResult;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import echo.tool.cputool.R;
import echo.tool.cputool.common.CommonUnit;
import echo.tool.cputool.common.Constant;
import echo.tool.cputool.service.ScreenLisenerService;

public class MainActivity extends Activity {
	private Context	mContext	= this;
	private Button	mbtn_high;
	private Button	mbtn_mid;
	private Button	mbtn_low;
	private Switch	msw_screen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViews();
		setOnClickLisener();
		startService(new Intent(this, ScreenLisenerService.class));
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		stopService(new Intent(this, ScreenLisenerService.class));
		super.onDestroy();
	}

	private void setOnClickLisener() {
		msw_screen.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				PreferencesUtils.putBoolean(mContext, Constant.PREFERENCE_KEY_SCREEN, isChecked);
			}
		});
	}

	private void findViews() {
		mbtn_high = (Button) findViewById(R.id.btn_high);
		mbtn_mid = (Button) findViewById(R.id.btn_mid);
		mbtn_low = (Button) findViewById(R.id.btn_low);
		msw_screen = (Switch) findViewById(R.id.sw_screen);
	}

	public void myOnClick(View v) {
		CommandResult lres = new CommandResult();
		switch (v.getId()) {
		case R.id.btn_high:
			lres = ShellUtils.execCommand(getCmdFromAssert(Constant.FILE_HIGH), true);
			break;

		case R.id.btn_mid:
			lres = ShellUtils.execCommand(getCmdFromAssert(Constant.FILE_MID), true);
			break;

		case R.id.btn_low:
			lres = ShellUtils.execCommand(getCmdFromAssert(Constant.FILE_LOW), true);
			break;
		default:
			break;
		}
		if (lres.result != 0) {
			CommonUnit.toastShow(mContext, lres.errorMsg);
		}
		initViews();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initViews();
	}

	private void initViews() {
		msw_screen.setChecked(PreferencesUtils.getBoolean(mContext, Constant.PREFERENCE_KEY_SCREEN, false));

		CommandResult ret = ShellUtils.execCommand("cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor", false);
		if (ret.result == 0) {
			if (ret.successMsg.contains("msm-dcvs")) {
				mbtn_mid.setBackgroundResource(R.color.blue);
				mbtn_high.setBackgroundResource(R.color.lightblue);
				mbtn_low.setBackgroundResource(R.color.lightblue);
			}
			if (ret.successMsg.contains("performance")) {
				mbtn_high.setBackgroundResource(R.color.blue);
				mbtn_mid.setBackgroundResource(R.color.lightblue);
				mbtn_low.setBackgroundResource(R.color.lightblue);
			}
			if (ret.successMsg.contains("powersave")) {
				mbtn_low.setBackgroundResource(R.color.blue);
				mbtn_high.setBackgroundResource(R.color.lightblue);
				mbtn_mid.setBackgroundResource(R.color.lightblue);
			}
		}
	}

	private String[] getCmdFromAssert(String name) {
		List<String> cmdList = ResourceUtils.geFileToListFromAssets(mContext, name);
		return ListUtils.convert2Array(cmdList);
	}

}
