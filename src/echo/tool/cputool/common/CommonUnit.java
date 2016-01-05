package echo.tool.cputool.common;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class CommonUnit {

	public static boolean checkNetwork(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			return true;
		}
		return false;
	}

	/************
	 * Mac转换带：
	 */
	public static String toMacFormat(String mac) {
		if (mac.length() == 12) {
			StringBuffer sb = new StringBuffer(mac);
			sb.insert(10, ':');
			sb.insert(8, ':');
			sb.insert(6, ':');
			sb.insert(4, ':');
			sb.insert(2, ':');
			return sb.toString();
		}
		return "";
	}

	public static boolean isMacEqual(String mac1, String mac2) {
		if (!TextUtils.isEmpty(mac1) && !TextUtils.isEmpty(mac2)) {
			return mac1.replace(":", "").toUpperCase().equals(mac2.replace(":", "").toUpperCase());
		}
		return false;
	}

	public static String getFormatedMac(String mac1) {
		if (!TextUtils.isEmpty(mac1)) {
			return mac1.replace(":", "").toUpperCase();
		}
		return "";
	}

	public static boolean isWifiConnect(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return mWifi.isConnected();
	}

	/**
	 * 根据手机的分辨率�??dp 的单�??转成�??px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率�??px(像素) 的单�??转成�??dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static String getPhoneMac(Context context) {
		String macAddress = null;
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = (null == wifiManager ? null : wifiManager.getConnectionInfo());

		if (!wifiManager.isWifiEnabled() && TextUtils.isEmpty(info.getMacAddress())) {
			wifiManager.setWifiEnabled(true);
			for (int i = 0; i < 300; i++) {
				info = (null == wifiManager ? null : wifiManager.getConnectionInfo());
				if (!TextUtils.isEmpty(info.getMacAddress()))
					break;
				SystemClock.sleep(100);
				System.out.println("wait for wlan_mac accessable..." + i);
			}
			wifiManager.setWifiEnabled(false);
		}
		if (null != info) {
			macAddress = info.getMacAddress();
		}
		return macAddress.replace(":", "");
	}

	/**
	 * �??��2个数组是否相�??
	 * 
	 * @param bt1
	 * @param bt2
	 * @return
	 */
	public static boolean checkByteArrayEqual(byte[] bt1, byte[] bt2) {
		int length = bt1.length;
		boolean b = true;
		for (int i = 0; i < length; i++) {
			if (bt1[i] != bt2[i]) {
				b = false;
			}
		}
		return b;
	}

	/**
	 * 计算经纬度距�??
	 * 
	 * @param startLat
	 * @param startLng
	 * @param endLat
	 * @param endLng
	 * @return
	 */
	public static double getLocationDistance(double startLat, double startLng, double endLat, double endLng) {
		double EARTH_RADIUS = 6378137.0;
		double radLat1 = (startLat * Math.PI / 180.0);
		double radLat2 = (endLat * Math.PI / 180.0);

		double a = radLat1 - radLat2;
		double b = (startLng - endLng) * Math.PI / 180.0;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));

		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	public static List<String> stringsToList(String[] items) {
		List<String> list = new ArrayList<String>();
		if (items != null) {
			for (String item : items) {
				list.add(item);
			}
		}
		return list;
	}

	/**************************************************************************
	 * Toast Show
	 * 
	 * @param context
	 * @param message
	 *            **************************************************************
	 *            ***********
	 */
	public static void toastShow(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/*************************************************************************
	 * Toast Show
	 * 
	 * @param context
	 * @param message
	 *            **************************************************************
	 *            ***********
	 */
	public static void toastShow(Context context, int messageId) {
		Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show();
	}

	public static final int getPhoneHour() {
		Date curDate = new Date(System.currentTimeMillis());
		return curDate.getHours();
	}

	public static final int getPhoneMin() {
		Date curDate = new Date(System.currentTimeMillis());
		return curDate.getMinutes();
	}

	public static final String MD5(String data) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] sha1hash = new byte[40];

			md.update(data.getBytes("iso-8859-1"), 0, data.length());
			sha1hash = md.digest();
			return convertToHex(sha1hash);
		} catch (Exception e) {
		}

		return "";
	}

	private static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		int length = data.length;

		for (int i = 0; i < length; ++i) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;

			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));

				halfbyte = data[i] & 0x0F;
			}

			while (++two_halfs < 1);
		}

		return buf.toString();
	}

	/**
	 * 将解释到的数据转为String
	 * 
	 * @param receiverDate
	 * @param receiverLength
	 * @return
	 */
	public static String parseData(byte[] receiverDate) {
		StringBuffer re = new StringBuffer();
		for (int i = 0; i < receiverDate.length; i++) {
			re.append(to16(receiverDate[i]));
		}

		return re.toString();
	}

	/**
	 * 字符串转�? btye 数组
	 * 
	 * @param dataString�
	 *            �化�?16进制字符�?
	 * @return byte数组
	 */
	public static byte[] parseStringToByte(String dataString) {
		int subPosition = 0;
		int byteLenght = dataString.length() / 2;

		byte[] result = new byte[byteLenght];

		for (int i = 0; i < byteLenght; i++) {
			String s = dataString.substring(subPosition, subPosition + 2);
			result[i] = (byte) Integer.parseInt(s, 16);
			subPosition = subPosition + 2;
		}

		return result;
	}

	// 2进制 �? 16进制
	public static String to16(int b) {
		String s = Integer.toHexString(b);
		int lenth = s.length();
		if (lenth == 1) {
			s = "0" + s;
		}
		if (lenth > 2) {
			s = s.substring(lenth - 2, lenth);
		}
		return s.toString();
	}

	// 2进制 �? 16进制
	public static String to16(int b, int len) {
		String s = Integer.toHexString(b);
		int lenth = s.length();
		for (int i = 0; i < len * 2 - lenth; i++) {
			s = "0" + s;
		}
		return s;
	}

	// 2进制 �? 16进制
	public static String to16(long b, int len) {
		String s = Long.toHexString(b);
		int lenth = s.length();
		for (int i = 0; i < len * 2 - lenth; i++) {
			s = "0" + s;
		}
		return s;
	}

	// 2进制 �? 16进制
	public static String to16(short b, int len) {
		String s = Long.toHexString(b);
		int lenth = s.length();
		for (int i = 0; i < len * 2 - lenth; i++) {
			s = "0" + s;
		}
		return s;
	}

	/**
	 * 根据日期取得星期�?
	 * 
	 * @param date
	 * @return 0星期�? 1星期�? 2星期�? 3星期�?
	 */
	public static int getWeekByDate() {
		int[] weeks = { 0, 1, 2, 3, 4, 5, 6 };
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week_index < 0) {
			week_index = 0;
		}
		return weeks[week_index];
	}

	/**
	 * 将时间分秒转为HH : NN
	 * 
	 * @param hour
	 * @param min
	 * @return
	 */
	public static String toTime(int hour, int min) {
		return String.format("%02d:%02d", hour, min);
	}

	/**
	 * hex字符串转byte数组<br/>
	 * 2个hex转为�?个byte
	 * 
	 * @param src
	 * @return
	 */
	public static byte[] hex2Bytes(String src) {
		byte[] res = new byte[src.length() / 2];
		char[] chs = src.toCharArray();
		for (int i = 0, c = 0; i < chs.length; i += 2, c++) {
			res[c] = (byte) (Integer.parseInt(new String(chs, i, 2), 16));
		}

		return res;
	}

	/**
	 * byte数组转hex字符�?<br/>
	 * �?个byte转为2个hex字符
	 * 
	 * @param src
	 * @return
	 */
	public static String bytes2Hex(byte[] src) {
		char[] res = new char[src.length * 2];
		final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		for (int i = 0, j = 0; i < src.length; i++) {
			res[j++] = hexDigits[src[i] >>> 4 & 0x0f];
			res[j++] = hexDigits[src[i] & 0x0f];
		}

		return new String(res);
	}

	public static String bytes2ShowHex(byte[] src) {
		char[] res = new char[src.length * 3 + src.length / 10 + 1];
		final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		int i, j;
		for (i = 0, j = 0; i < src.length; i++) {
			if (i != 0)
				res[j++] = ',';
			res[j++] = hexDigits[src[i] >>> 4 & 0x0f];
			res[j++] = hexDigits[src[i] & 0x0f];
			if ((i + 1) % 10 == 0) {
				res[j++] = '\n';
			}
		}
		return new String(res, 0, j);
	}

	public static String getAppInfo(Context mContext) {
		try {
			String pkName = mContext.getPackageName();
			String versionName = mContext.getPackageManager().getPackageInfo(pkName, 0).versionName;

			int versionCode = mContext.getPackageManager().getPackageInfo(pkName, 0).versionCode;

			return pkName + "   " + versionName + "  " + versionCode;
		} catch (Exception e) {

		}
		return null;
	}

	public static String getVersionName(Context mContext) {
		try {
			String pkName = mContext.getPackageName();
			String versionName = mContext.getPackageManager().getPackageInfo(pkName, 0).versionName;
			return versionName;
		} catch (Exception e) {

		}
		return null;
	}

	public static int getVersionCode(Context mContext) {
		try {
			String pkName = mContext.getPackageName();
			int versionCode = mContext.getPackageManager().getPackageInfo(pkName, 0).versionCode;
			return versionCode;
		} catch (Exception e) {
		}
		return 0;
	}

	public static boolean isZh(Context context) {
		Locale locale = context.getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		if (language.endsWith("zh"))
			return true;
		else
			return false;
	}

	public static void toActivity(Context mContext, Class T) {
		Intent intent = new Intent(mContext, T);
		mContext.startActivity(intent);
	}

	public static void execCommand(String command) {
		Process process = null;
		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec("su");
			//

			InputStream inputstream = process.getInputStream();
			InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
			BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

			//
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(command + "\n");
			os.writeBytes("exit\n");
			os.flush();
			//
			// read the ls output

			String line = "";

			StringBuilder sb = new StringBuilder(line);
			while ((line = bufferedreader.readLine()) != null) {

				sb.append(line);

				sb.append('\n');

			}

			// ////////////
			process.waitFor();
		} catch (Exception e) {
			Log.d("*** DEBUG ***", "Unexpected error - Here is what I know: " + e.getMessage());
			// return false;
		}

	}

	public static int writeFile2(String fileName, String content, Boolean cover) {
		try {
			File file = new File(fileName);
			try {
				if (!file.exists()) {
					file.createNewFile();
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}

			// 打开�?个写文件器，构�?�函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, cover);
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public static void writeBin(String fileName, byte[] data) {
		try {
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
			for (byte b : data) {
				out.writeByte(b);
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setTextViewLeftDrawable(Context mContext, TextView v, Drawable drawable) {
		drawable.setBounds(0, 0, dip2px(mContext, 17f), dip2px(mContext, 17f));
		v.setCompoundDrawables(drawable, null, null, null);
	}

	public static boolean checkUrlValid(String urlString) {
		try {
			URL url = new URL(urlString);
			InputStream in = url.openStream();
			return true;
		} catch (Exception e1) {
			return false;
		}
	}

	public static int getWeekDayByMill(long mill) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(mill);
		return c.get(Calendar.DAY_OF_WEEK) - 1;
	}

	public static String getCurrentZoneHour(int hour, int min) {
		int newHour = 0;
		int newMin = 0;

		TimeZone tz = TimeZone.getDefault();
		int zoneHour = tz.getRawOffset() / 1000 / 3600;
		int zoneMin = tz.getRawOffset() / 1000 / 60 % 60;
		int dif = hour - (8 - zoneHour);

		if (dif < 0) {
			if (zoneMin == 0) {
				newHour = 24 + dif;
				newMin = min;
			} else {
				if (min >= 30) {
					newHour = 24 + dif;
					newMin = min - 30;
				} else {
					newHour = 24 + dif - 1;
					newMin = min + 60 - 30;
				}
			}
		} else if (dif < 24) {
			if (zoneMin == 0) {
				newHour = dif;
				newMin = min;
			} else if (zoneMin == 45) {
				if (min >= 15) {
					newHour = dif + 1;
					newMin = min - 15;
				} else {
					newHour = dif;
					newMin = min + 45;
				}
			} else {
				if (min >= 30) {
					newHour = dif + 1;
					newMin = min - 30;
				} else {
					newHour = dif;
					newMin = min + 30;
				}
			}
		} else {
			if (zoneMin == 0) {
				newHour = dif - 24;
				newMin = min;
			} else if (zoneMin == 45) {
				if (min >= 15) {
					newHour = dif - 24 + 1;
					newMin = min - 15;
				} else {
					newHour = dif - 24;
					newMin = min + 45;
				}
			} else {
				if (min >= 30) {
					newHour = dif - 24 + 1;
					newMin = min - 30;
				} else {
					newHour = dif - 24;
					newMin = min + 30;
				}
			}
		}

		return toTime(newHour, newMin);
	}

	/**
	 * 计算某个时间与现在的时间是否是同�?�? 即星期几是否�?�?
	 * 
	 * @param time
	 * @return
	 */
	public static int getDiffDay(long time1, long time2) {
		int mDiffDay = 0;
		int time1WeekDay = CommonUnit.getWeekDayByMill(time1);
		int time2WeekDay = CommonUnit.getWeekDayByMill(time2);
		if (time1WeekDay > time2WeekDay) {
			mDiffDay = 1;
		} else if (time1WeekDay < time2WeekDay) {
			if (time1WeekDay == 0 && time2WeekDay == 6) {
				mDiffDay = 1;
			} else {
				mDiffDay = -1;
			}
		} else {
			mDiffDay = 0;
		}
		return mDiffDay;
	}

	/** 将时间转为long�? **/
	public static final long changeDataToMill(int yr, int month, int day, int hour, int min) {
		try {
			String data = String.format("%d-%d-%d %d:%d:%d", yr, month, day, hour, min, 30);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.parse(data).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return System.currentTimeMillis();
		}
	}

	public static final long changeDataToMill(int yr, int month, int day, int hour, int min, int second) {
		try {
			String data = String.format("%d-%d-%d %d:%d:%d", yr, month, day, hour, min, second);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.parse(data).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return System.currentTimeMillis();
		}
	}

	public static final int getYearByMill(long mill) {
		Date curDate = new Date(mill);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(curDate);
		return calendar.get(java.util.Calendar.YEAR);
	}

	public static final int getMonthByMill(long mill) {
		Date curDate = new Date(mill);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(curDate);
		return calendar.get(java.util.Calendar.MONTH) + 1;
	}

	public static final int getDayByMill(long mill) {
		Date curDate = new Date(mill);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(curDate);
		return calendar.get(java.util.Calendar.DAY_OF_MONTH);
	}

	public static final int getHourByMill(long mill) {
		Date curDate = new Date(mill);
		return curDate.getHours();
	}

	public static final int getMinByMill(long mill) {
		Date curDate = new Date(mill);
		return curDate.getMinutes();
	}

	public static final int getSecondByMill(long mill) {
		Date curDate = new Date(mill);
		return curDate.getSeconds();
	}

	/** 将时间转为long�? **/
	public static final long changeDataToMill(int hour, int min) {

		try {
			Date curDate = new Date(System.currentTimeMillis());
			String data = String.format("%d-%d-%d %d:%d:%d", curDate.getYear() + 1900, curDate.getMonth() + 1,
					curDate.getDate(), hour, min, 30);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.parse(data).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return System.currentTimeMillis();
		}
	}

	/** 将时间转为long�? **/
	public static final long changeDataToMill(int hour, int min, int sec) {

		try {
			Date curDate = new Date(System.currentTimeMillis());
			String data = String.format("%d-%d-%d %d:%d:%d", curDate.getYear() + 1900, curDate.getMonth() + 1,
					curDate.getDate(), hour, min, sec);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.parse(data).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return System.currentTimeMillis();
		}
	}

	public static double convertCel2Fah(double cel) {
		return 32.0 + cel * 1.8;
	}

	public static void setTextViewUpDrawable(Context mContext, TextView v, Drawable drawable, int width, int height) {
		drawable.setBounds(0, 0, dip2px(mContext, width), dip2px(mContext, height));
		v.setCompoundDrawables(null, drawable, null, null);
	}

	public static int getTimeZone() {
		return TimeZone.getDefault().getRawOffset() / (1000 * 3600);
	}

	public static void loge(String msg) {
		Log.e("echo", msg);
	}

	public static void logd(String msg) {
		Log.d("echo", msg);
	}
}
