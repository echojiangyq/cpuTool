package echo.tool.cputool.view;

/**************************************************************************************
 * [Project]
 *       MyProgressDialog
 * [Package]
 *       com.broadlink.rmt
 * [FileName]
 *       CustomProgressDialog.java
 * [History]
 *
 **************************************************************************************/

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import echo.tool.cputool.R;

public class MyProgressDialog extends Dialog {
	private static MyProgressDialog	customProgressDialog	= null;

	public MyProgressDialog(Context context) {
		super(context);
	}

	public MyProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public static MyProgressDialog createDialog(Context context) {
		customProgressDialog = new MyProgressDialog(context, R.style.CustomProgressDialog);
		customProgressDialog.setContentView(R.layout.bl_progress);
		LayoutParams layoutParams = customProgressDialog.getWindow().getAttributes();
		layoutParams.gravity = Gravity.CENTER;
		customProgressDialog.setCanceledOnTouchOutside(false);
		return customProgressDialog;
	}

	public static MyProgressDialog createNoContentDialog(Context context) {
		customProgressDialog = new MyProgressDialog(context, R.style.CustomProgressDialog);
		LayoutParams layoutParams = customProgressDialog.getWindow().getAttributes();
		layoutParams.gravity = Gravity.CENTER;
		customProgressDialog.setCanceledOnTouchOutside(false);
		return customProgressDialog;
	}

	public void onWindowFocusChanged(boolean hasFocus) {

		if (customProgressDialog == null) {
			return;
		}

	}

	/**
	 * [Summary] setMessage 提示内容
	 * 
	 * @param strMessage
	 * @return
	 */
	public MyProgressDialog setMessage(String strMessage) {
		TextView message = (TextView) customProgressDialog.findViewById(R.id.message);

		if (message != null) {
			message.setText(strMessage);
		}

		return customProgressDialog;
	}

	/**
	 * [Summary] setMessage 提示内容
	 * 
	 * @param strMessage
	 * @return
	 */
	public MyProgressDialog setMessage(int messageId) {
		TextView message = (TextView) customProgressDialog.findViewById(R.id.message);

		if (message != null) {
			message.setText(messageId);
		}

		return customProgressDialog;
	}
}
