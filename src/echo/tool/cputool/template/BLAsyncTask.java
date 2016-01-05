package echo.tool.cputool.template;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import echo.tool.cputool.view.MyProgressDialog;

public class BLAsyncTask extends AsyncTask<Void, Void, Void> {
	public interface MyAsyncCallBack {
		public Void doInBackground();

		public Void onPostExecute();
	}

	MyProgressDialog	mMyProgressDialog;
	Context				mContext;
	MyAsyncCallBack		mCallBack;
	OnCancelListener	mCancelListener;

	public BLAsyncTask(Context mContext, String tipString, MyAsyncCallBack mCallBack) {
		this.mContext = mContext;
		this.mMyProgressDialog = MyProgressDialog.createDialog(mContext);
		this.mCallBack = mCallBack;
		mMyProgressDialog.setMessage(tipString);
	}

	public BLAsyncTask(Context mContext, String tipString, MyAsyncCallBack mCallBack, OnCancelListener mCancelListener) {
		this.mContext = mContext;
		this.mMyProgressDialog = MyProgressDialog.createDialog(mContext);
		this.mCallBack = mCallBack;
		this.mCancelListener = mCancelListener;
		mMyProgressDialog.setMessage(tipString);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mMyProgressDialog.show();
		if (mCancelListener != null) {
			mMyProgressDialog.setOnCancelListener(mCancelListener);
		}

	}

	@Override
	protected Void doInBackground(Void... params) {
		if (mCallBack != null) {
			mCallBack.doInBackground();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if (mCallBack != null) {
			mCallBack.onPostExecute();
		}
		mMyProgressDialog.dismiss();

	}

}
