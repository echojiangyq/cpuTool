package echo.tool.cputool.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import echo.tool.cputool.R;

public class BLAlert {

	public static final int	YES	= 0;

	public static final int	NO	= 1;

	public interface OnAlertSelectId {
		void onClick(int whichButton);
	}

	private BLAlert() {
	}

	public static Dialog showAlert(Context context, String title, String messageId, String confimButtonText,
			String cancleButtonText, final OnAlertSelectId alertDo) {
		return showAlert(context, title, Gravity.CENTER | Gravity.TOP, messageId, confimButtonText, cancleButtonText,
				alertDo);
	}

	public static Dialog showAlert(Context context, int gravity, String messageId, final OnAlertSelectId alertDo) {
		return showAlert(context, null, gravity, messageId, null, null, alertDo);
	}

	public static Dialog showAlert(Context context, String title, int messageGravity, String messageId,
			String confimButtonText, String cancleButtonText, final OnAlertSelectId alertDo) {
		final Dialog dlg = new Dialog(context, R.style.BLTheme_Dialog);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.bl_alert_layout, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);

		TextView titleView = (TextView) layout.findViewById(R.id.dialog_title);
		TextView messTextView = (TextView) layout.findViewById(R.id.dialog_msg);
		messTextView.setGravity(messageGravity);
		Button confimButton = (Button) layout.findViewById(R.id.dialog_yes);
		Button cacelButton = (Button) layout.findViewById(R.id.dialog_no);

		if (confimButtonText != null) {
			confimButton.setText(confimButtonText);
		}

		if (cancleButtonText != null) {
			cacelButton.setText(cancleButtonText);
		}

		if (!TextUtils.isEmpty(title)) {
			titleView.setText(title);
		}

		if (messageId != null) {
			messTextView.setText(messageId);
		}

		confimButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (alertDo != null)
					alertDo.onClick(YES);
				dlg.dismiss();
			}
		});

		cacelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (alertDo != null)
					alertDo.onClick(NO);
				dlg.dismiss();
			}
		});

		Window w = dlg.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.x = 0;
		lp.gravity = Gravity.CENTER;
		dlg.onWindowAttributesChanged(lp);
		dlg.setCanceledOnTouchOutside(true);
		dlg.setCancelable(false);
		dlg.setContentView(layout);
		dlg.show();
		return dlg;
	}

	/**
	 * @param context
	 *            Context
	 * @param messageId
	 *            show message
	 * @param alertDo
	 *            dialog listener
	 * @return
	 */
	public static Dialog showAlert(Context context, int messageId, final OnAlertSelectId alertDo) {
		return showAlert(context, null, context.getString(messageId), null, null, alertDo);
	}

	public static Dialog showAlert(Context context, String messageId, final OnAlertSelectId alertDo) {
		return showAlert(context, null, messageId, null, null, alertDo);
	}

	public static Dialog showAlert(Context context, int titleId, int messageId, final OnAlertSelectId alertDo) {
		return showAlert(context, context.getString(titleId), context.getString(messageId), null, null, alertDo);
	}

	public static Dialog showAlert(Context context, int titleId, int messageId, int confimButtonText,
			int cancleButtonText, final OnAlertSelectId alertDo) {
		return showAlert(context, context.getString(titleId), context.getString(messageId),
				context.getString(confimButtonText), context.getString(cancleButtonText), alertDo);
	}

	public static Dialog showEditAlert(final Context context, int hintResid, String value, final OnAlertClick alertDo) {
		final Dialog dlg = new Dialog(context, R.style.BLTheme_Dialog);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.bl_alert_edit_layout, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);

		TextView hint = (TextView) layout.findViewById(R.id.hint);
		final EditText messTextView = (EditText) layout.findViewById(R.id.dialog_value);
		Button confimButton = (Button) layout.findViewById(R.id.dialog_yes);
		Button cacelButton = (Button) layout.findViewById(R.id.dialog_no);

		hint.setText(hintResid);
		messTextView.setText(value);
		confimButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alertDo.onClick(messTextView.getText().toString());
				dlg.dismiss();
			}
		});

		cacelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dlg.dismiss();
			}
		});

		Window w = dlg.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.x = 0;
		lp.gravity = Gravity.CENTER;
		dlg.onWindowAttributesChanged(lp);
		dlg.setCanceledOnTouchOutside(true);
		dlg.setCancelable(false);
		dlg.setContentView(layout);
		dlg.show();
		return dlg;
	}

	public static Dialog showEditAlert(final Context context, String hintResid, String value, final OnAlertClick alertDo) {
		final Dialog dlg = new Dialog(context, R.style.BLTheme_Dialog);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.bl_alert_edit_layout, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);

		TextView hint = (TextView) layout.findViewById(R.id.hint);
		final EditText messTextView = (EditText) layout.findViewById(R.id.dialog_value);
		Button confimButton = (Button) layout.findViewById(R.id.dialog_yes);
		Button cacelButton = (Button) layout.findViewById(R.id.dialog_no);

		hint.setText(hintResid);
		messTextView.setText(value);
		messTextView.setSelection(messTextView.getText().length());
		confimButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alertDo.onClick(messTextView.getText().toString());
				dlg.dismiss();
			}
		});

		cacelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dlg.dismiss();
			}
		});

		Window w = dlg.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.x = 0;
		lp.gravity = Gravity.CENTER;
		dlg.onWindowAttributesChanged(lp);
		dlg.setCanceledOnTouchOutside(true);
		dlg.setCancelable(false);
		dlg.setContentView(layout);
		dlg.show();
		return dlg;
	}

	public interface OnAlertClick {
		void onClick(String value);
	}

	public static Dialog showAlert(Context context, int title, View content, final OnAlertSelectId alertDo,
			String yesText, String noText) {
		return showAlert(context, title, content, alertDo, false, true, yesText, noText);
	}

	public static Dialog showAlert(Context context, String title, View content, final OnAlertSelectId alertDo,
			String yesText, String noText) {
		return showAlert(context, title, content, alertDo, false, true, yesText, noText);
	}

	/**
	 * 显示自定义内容的对话�?
	 * 
	 * @param context
	 * @param messageId
	 * @param content
	 * @param alertDo
	 * @return
	 */
	public static Dialog showAlert(Context context, int title, View content, final OnAlertSelectId alertDo,
			boolean showYesButton, boolean showNoButton, String yesButtonText, String noButtonText) {
		final Dialog dlg = new Dialog(context, R.style.BLTheme_Dialog);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.bl_alert_no_content_layout, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);

		Button confimButton = (Button) layout.findViewById(R.id.dialog_yes);
		Button cacelButton = (Button) layout.findViewById(R.id.dialog_no);
		TextView titleView = (TextView) layout.findViewById(R.id.dialog_title);
		LinearLayout bodyLayout = (LinearLayout) layout.findViewById(R.id.body_layout);
		View hLine = (View) layout.findViewById(R.id.h_line);

		titleView.setText(title);
		bodyLayout.addView(content);

		if (showYesButton) {
			confimButton.setVisibility(View.VISIBLE);
		} else {
			confimButton.setVisibility(View.GONE);
		}

		if (!TextUtils.isEmpty(yesButtonText)) {
			confimButton.setText(yesButtonText);
		}

		if (!TextUtils.isEmpty(noButtonText)) {
			cacelButton.setText(noButtonText);
		}

		if (showNoButton) {
			cacelButton.setVisibility(View.VISIBLE);
		} else {
			cacelButton.setVisibility(View.GONE);
		}

		if (!showYesButton && !showNoButton) {
			hLine.setVisibility(View.GONE);
		}

		confimButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (alertDo != null) {
					alertDo.onClick(YES);
				}
				dlg.dismiss();
			}
		});

		cacelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (alertDo != null) {
					alertDo.onClick(NO);
				}
				dlg.dismiss();
			}
		});

		Window w = dlg.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.x = 0;
		lp.gravity = Gravity.CENTER;
		dlg.onWindowAttributesChanged(lp);
		dlg.setCanceledOnTouchOutside(true);
		dlg.setContentView(layout);
		dlg.setCancelable(false);
		dlg.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		dlg.show();
		return dlg;
	}

	public static Dialog showAlert(Context context, String title, View content, final OnAlertSelectId alertDo,
			boolean showYesButton, boolean showNoButton, String yesButtonText, String noButtonText) {
		final Dialog dlg = new Dialog(context, R.style.BLTheme_Dialog);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.bl_alert_no_content_layout, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);

		Button confimButton = (Button) layout.findViewById(R.id.dialog_yes);
		Button cacelButton = (Button) layout.findViewById(R.id.dialog_no);
		TextView titleView = (TextView) layout.findViewById(R.id.dialog_title);
		LinearLayout bodyLayout = (LinearLayout) layout.findViewById(R.id.body_layout);
		View hLine = (View) layout.findViewById(R.id.h_line);

		titleView.setText(title);
		bodyLayout.addView(content);

		if (showYesButton) {
			confimButton.setVisibility(View.VISIBLE);
		} else {
			confimButton.setVisibility(View.GONE);
		}

		if (!TextUtils.isEmpty(yesButtonText)) {
			confimButton.setText(yesButtonText);
		}

		if (!TextUtils.isEmpty(noButtonText)) {
			cacelButton.setText(noButtonText);
		}

		if (showNoButton) {
			cacelButton.setVisibility(View.VISIBLE);
		} else {
			cacelButton.setVisibility(View.GONE);
		}

		if (!showYesButton && !showNoButton) {
			hLine.setVisibility(View.GONE);
		}

		confimButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (alertDo != null) {
					alertDo.onClick(YES);
				}
				dlg.dismiss();
			}
		});

		cacelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (alertDo != null) {
					alertDo.onClick(NO);
				}
				dlg.dismiss();
			}
		});

		Window w = dlg.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.x = 0;
		lp.gravity = Gravity.CENTER;
		dlg.onWindowAttributesChanged(lp);
		dlg.setCanceledOnTouchOutside(true);
		dlg.setContentView(layout);
		dlg.setCancelable(false);
		dlg.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		dlg.show();
		return dlg;
	}

}
