package com.pain.publics.utils;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.pm.ApplicationInfo.FLAG_LARGE_HEAP;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.HONEYCOMB;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONException;
import org.json.JSONObject;

import com.pain.simple.R;
import com.pain.ui.activity.home.SplashActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Utils {

	private static final String SCHEME = "package";
	private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
	private static final String APP_PKG_NAME_22 = "pkg";
	private static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";
	private static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";
	private static List<Activity> activityList = new LinkedList<Activity>();
	private static List<Activity> actList = new LinkedList<Activity>();
	private static List<Activity> finishList = new LinkedList<Activity>();

	/**
	 * 获取MAC地址
	 */

	public static String getMAC(Context context) {
		String mac = null;// MAC地址
		if (mac == null) {
			WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			if (info != null) {
				mac = info.getMacAddress();
			}
		}
		return mac;
	}

	/**
	 * 获取手机唯一编号
	 */
	private static String imei;// 手机设备唯一编号

	public static String getIMEI(Context context) {
		if (imei == null && context != null) {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			imei = tm.getDeviceId();// 手机串号
			if (imei == null || imei.length() == 0) {
				imei = "000000000000000";
			}
		}
		return imei;
	}

	/**
	 * 获取SIM卡唯一编号
	 */
	private static String imsi;// SIM卡唯一编号

	public static String getIMSI(Context context) {
		if (imsi == null && context != null) {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			imsi = tm.getSubscriberId();
			if (imsi == null || imsi.length() == 0) {
				imsi = "000000000000000";
			}
		}
		return imsi;
	}

	/**
	 * 获取手机型号
	 */
	private static String deviceModel;// 手机型号

	public static String getDeviceModel() {
		if (deviceModel == null) {
			deviceModel = android.os.Build.MODEL;
		}
		return deviceModel;
	}

	/**
	 * 获取手机型号
	 */
	private static String manufacturer;// 设备厂商型号

	public static String getDeviceManufacturer() {
		if (manufacturer == null) {
			manufacturer = android.os.Build.MANUFACTURER;
		}
		return manufacturer;
	}

	/**
	 * 获取Android系统版本
	 */
	private static String androidRelease;// Android系统版本

	public static String getAndroidRelease() {
		if (androidRelease == null) {
			androidRelease = android.os.Build.VERSION.RELEASE;
		}
		return androidRelease;
	}

	/**
	 * 获取Android系统API版本号
	 */
	private static int androidSDKINT;// Android系统API版本号

	public static int getAndroidSDKINT() {
		if (androidSDKINT == 0) {
			androidSDKINT = android.os.Build.VERSION.SDK_INT;
		}
		return androidSDKINT;
	}

	/**
	 * 获取零流量分享用户名
	 */
	public static String getZeroShareNickName() {
		String nickName = android.os.Build.MODEL;
		return nickName;
	}

	/**
	 * 获取手机号码
	 */
	private static String phoneNumber;// 手机号码

	public static String getPhoneNumber(Context context) {
		if (phoneNumber == null) {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			phoneNumber = tm.getLine1Number();
		}
		return phoneNumber;
	}

	/**
	 * @Title: showInstalledAppDetails
	 * @Description: 打开系统应用信息页面
	 * @param @param context
	 * @param @param packageName
	 * @return void
	 * @throws
	 */

	public static void showInstalledAppDetails(Context context, String packageName) {
		Intent intent = new Intent();
		final int apiLevel = Build.VERSION.SDK_INT;
		if (apiLevel >= 9) {
			intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
			Uri uri = Uri.fromParts(SCHEME, packageName, null);
			intent.setData(uri);
		} else {
			final String appPkgName = (apiLevel == 8 ? APP_PKG_NAME_22 : APP_PKG_NAME_21);
			intent.setAction(Intent.ACTION_VIEW);
			intent.setClassName(APP_DETAILS_PACKAGE_NAME, APP_DETAILS_CLASS_NAME);
			intent.putExtra(appPkgName, packageName);
		}
		context.startActivity(intent);
	}

	private static final int CPU_COUNT = 9;
	private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
	private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
	private static final int KEEP_ALIVE = 1;
	private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(128);
	private static final ThreadFactory sThreadFactory = new ThreadFactory() {
		private final AtomicInteger mCount = new AtomicInteger(1);

		public Thread newThread(Runnable r) {
			return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
		}
	};

	public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
			KEEP_ALIVE, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);

	/**
	 * 使用{@link AsyncTask.THREAD_POOL_EXECUTOR} 执行AsyncTask 这样可以避免android
	 * 4.0以上系统 每次只执行一个 asyncTask
	 * 
	 * @param task
	 * @param params
	 */
	public static <Params, Progress, Result> void executeAsyncTask(AsyncTask<Params, Progress, Result> task,
			Params... params) {
		if (VERSION.SDK_INT >= 11) {
			task.executeOnExecutor(THREAD_POOL_EXECUTOR, params);
		} else {
			task.execute(params);
		}
	}

	public static int calculateMemoryCacheSize(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
		boolean largeHeap = (context.getApplicationInfo().flags & FLAG_LARGE_HEAP) != 0;
		int memoryClass = am.getMemoryClass();
		if (largeHeap && SDK_INT >= HONEYCOMB) {
			memoryClass = ActivityManagerHoneycomb.getLargeMemoryClass(am);
		}
		// Target ~15% of the available heap.
		LogUtil.getLogger().d("LruCache size ======" + (memoryClass / 7) + "M");
		return 1024 * 1024 * memoryClass / 7;
	}

	@TargetApi(HONEYCOMB)
	private static class ActivityManagerHoneycomb {
		static int getLargeMemoryClass(ActivityManager activityManager) {
			return activityManager.getLargeMemoryClass();
		}
	}

	public static Drawable getPictureDraw(byte[] b) {
		if (b == null)
			return null;
		Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length, null);
		BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
		return bitmapDrawable;
	}

	public static String sizeFormat(long size) {
		// DecimalFormat df = new DecimalFormat("###.##");
		// float f;
		// if (size < 1024 * 1024) {
		// f = (float) ((float) size / (float) 1024);
		// return (df.format(Float.valueOf(f).doubleValue() + 0) + "KB");
		// } else {
		// f = (float) ((float) size / (float) (1024 * 1024));
		// return (df.format(Float.valueOf(f).doubleValue()) + "MB");
		// }
		if (size / (1024 * 1024 * 1024) > 0) {
			float tmpSize = (float) (size) / (float) (1024 * 1024 * 1024);
			DecimalFormat df = new DecimalFormat("#.##");
			return "" + df.format(tmpSize) + "G";
		} else if (size / (1024 * 1024) > 0) {
			float tmpSize = (float) (size) / (float) (1024 * 1024);
			DecimalFormat df = new DecimalFormat("#.##");
			return "" + df.format(tmpSize) + "MB";
		} else if (size / 1024 > 0) {
			return "" + (size / (1024)) + "KB";
		} else
			return "" + size + "B";

	}

	public static String sizeFormat2(long size) {
		DecimalFormat df = new DecimalFormat("###");
		float f;
		if (size < 1024 * 1024) {
			f = (float) ((float) size / (float) 1024);
			return (df.format(Float.valueOf(f).doubleValue()) + "KB");
		} else {
			f = (float) ((float) size / (float) (1024 * 1024));
			return (df.format(Float.valueOf(f).doubleValue()) + "MB");
		}
	}

	public static String formatData(long time) {
		String data = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			data = sdf.format(new Date(time));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return data;
	}

	public static String getString(Context context, int id) {
		return context.getResources().getString(id);
	}

	public static void launchAnotherApp(Context context, String packageName) {
		PackageManager packageManager = context.getPackageManager();
		Intent it = new Intent();
		try {
			it = packageManager.getLaunchIntentForPackage(packageName);
			context.startActivity(it);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean launchApp(Context context, String packageName) {
		PackageManager packageManager = context.getPackageManager();
		Intent it = new Intent();
		try {
			it = packageManager.getLaunchIntentForPackage(packageName);
			context.startActivity(it);
			return true;
		} catch (Exception e) {
		}
		return false;
	}


	/**
	 * 获取当前应用包名
	 */
	public static String getPackageName(Context context) {
		String packageName = "";
		if (context != null) {
			packageName = context.getPackageName();
		}
		return packageName;
	}

	/**
	 * 获取本应用版本名
	 * 
	 * @return
	 */
	public static String getVersionName(Context context) {
		String version = "";
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			version = packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}

	/**
	 * 获取本应用版本号
	 * 
	 * @return
	 */
	public static int getVersionCode(Context context) {
		int versionCode = 0;
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			versionCode = packInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * @Title: getAppIcon
	 * @Description: 根据包名获取icon
	 * @param @param context
	 * @param @param packageName
	 * @param @return
	 * @return Drawable
	 * @throws
	 */

	public static Drawable getAppIcon(Context context, String packageName) {
		Drawable drawable = null;
		try {
			drawable = context.getPackageManager().getApplicationIcon(packageName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return drawable;
	}

	public static Drawable getAppIconByFile(Context context, String localPath) {
		Drawable drawable = null;
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pkgInfo = pm.getPackageArchiveInfo(localPath, PackageManager.GET_ACTIVITIES);
			if (pkgInfo != null) {
				ApplicationInfo appInfo = pkgInfo.applicationInfo;
				appInfo.sourceDir = localPath;
				appInfo.publicSourceDir = localPath;
				drawable = pm.getApplicationIcon(appInfo);// 得到图标信息
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return drawable;
	}

	/**
	 * @Title: hasInstallShortcut
	 * @Description:
	 * @param @param context
	 * @param @return
	 * @return boolean
	 * @throws
	 */

	public static boolean hasInstallShortcut(Context context) {
		boolean hasInstall = false;
		try {
			final String AUTHORITY = "com.android.launcher.settings";
			Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/favorites?notify=true");
			Cursor cursor = context.getContentResolver().query(CONTENT_URI, new String[] { "title", "iconResource" },
					"title=?", new String[] { context.getString(R.string.app_name) }, null);
			if (cursor != null && cursor.getCount() > 0) {
				hasInstall = true;
			}
			if (cursor != null) {
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return hasInstall;
	}

	/**
	 * @Title: addShortcutToDesktop
	 * @Description: 添加桌面快捷方式
	 * @param @param context
	 * @return void
	 * @throws
	 */

	public static void addShortcutToDesktop(Context context) {
		Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
		shortcut.putExtra("duplicate", false);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.app_name));
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
				Intent.ShortcutIconResource.fromContext(context, R.drawable.ic_launcher));
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
				new Intent(context, SplashActivity.class).setAction(Intent.ACTION_MAIN));
		context.sendBroadcast(shortcut);

	}

	/**
	 * @Title: deleteFile
	 * @Description: 删除文件
	 * @param @param filePath
	 * @return void
	 * @throws
	 */
	public static void deleteFile(String filePath) {
		try {
			File file = new File(filePath);
			if (file.exists())
				file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 转换下载数量，显示规则：1,000+ 5,000+ 10,000+ 50,000+ 100,000+ 500,000+ 1,000,000+
	 * 5,000,000+ 10,000,000+ 50,000,000+ 100,000,000+ 500,000,000+
	 * 
	 * @param count
	 * @return
	 */
	public static String changeDownloads(int count) {
		if (count < 5001) {
			return "1,000+";
		} else if (count > 5000 && count < 10001) {
			return "5,000+";
		} else if (count > 10000 && count < 50001) {
			return "10,000+";
		} else if (count > 50000 && count < 100001) {
			return "50,000+";
		} else if (count > 100000 && count < 500001) {
			return "100,000+";
		} else if (count > 500000 && count < 1000001) {
			return "500,000+";
		} else if (count > 1000000 && count < 5000001) {
			return "1,000,000+";
		} else if (count > 5000000 && count < 10000001) {
			return "5,000,000+";
		} else if (count > 10000000 && count < 50000001) {
			return "10,000,000+";
		} else if (count > 50000000 && count < 100000001) {
			return "50,000,000+";
		} else if (count > 100000000 && count < 500000001) {
			return "100,000,000+";
		} else if (count > 500000000 && count < 1000000000) {
			return "500,000,000+";
		} else {
			return "1,000,000,000+";
		}
	}

	/**
	 * 字符串去空格，去除前后空格，保留中间空格
	 * 
	 * @param str
	 * @return
	 */
	public static String removeSpace(String str) {
		str = str.trim();
		while (str.startsWith(" ")) {
			str = str.substring(1, str.length()).trim();
		}
		while (str.endsWith(" ")) {
			str = str.substring(0, str.length() - 1).trim();
		}
		return str;
	}

	/**
	 * 获取mannifest中metaDate的值
	 * 
	 * @param context
	 * @param packageName
	 * @param metaDateKey
	 * @return
	 */
	public static int getMetaDataIntValue(Context context, String metaDateKey) {
		int metaDateValue = 0;
		if (!TextUtils.isEmpty(metaDateKey)) {
			ApplicationInfo appInfo;
			try {
				appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
						PackageManager.GET_META_DATA);
				if (appInfo != null) {
					Bundle metaData = appInfo.metaData;
					if (metaData != null) {
						if (metaData.containsKey(metaDateKey)) {
							int metaDateInt = metaData.getInt(metaDateKey, -1);
							if (metaDateInt != -1) {
								metaDateValue = metaDateInt;
							}
						}
					}
				}
			} catch (NameNotFoundException e) {
			} catch (Exception e) {
			}
		}
		return metaDateValue;
	}

	/**
	 * 获取mannifest中metaDate的值
	 * 
	 * @param context
	 * @param packageName
	 * @param metaDateKey
	 * @return
	 */
	public static String getMetaDataStringValue(Context context, String metaDateKey) {
		String metaDateValueStr = null;
		if (!TextUtils.isEmpty(metaDateKey)) {
			ApplicationInfo appInfo;
			try {
				appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
						PackageManager.GET_META_DATA);
				if (appInfo != null) {
					Bundle metaData = appInfo.metaData;
					if (metaData != null) {
						if (metaData.containsKey(metaDateKey)) {
							metaDateValueStr = metaData.getString(metaDateKey);
						}
					}
				}
			} catch (NameNotFoundException e) {
			} catch (Exception e) {
			}
		}
		return metaDateValueStr;
	}

	/**
	 * 添加activity 到集合中，当子元素超过10个时，去除第一个元素
	 * 
	 * @param context
	 * @param activity
	 * @return
	 */
	public static void addActivity(Activity activity) {
		activityList.add(activity);
		if (activityList.size() > 10) {
			activityList.get(0).finish();
			activityList.remove(0);
			System.gc();
		}
	}

	public static void addAct(Activity activity) {
		actList.add(activity);
	}

	public static void finishAct() {
		for (int i = 0; i < actList.size(); i++) {
			actList.get(i).finish();
		}
	}

	/***
	 * 合并图片
	 * 
	 * @param src
	 * @param watermark
	 * @return
	 */
	public static Bitmap createBitmap(Bitmap src, Bitmap watermark) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		int ww = watermark.getWidth();
		int wh = watermark.getHeight();

		// create the new blank bitmap
		Bitmap newBitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newBitmap);

		// draw src into
		cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src

		// 在src的中间画watermark
		cv.drawBitmap(watermark, w / 2 - ww / 2, h / 2 - wh / 2, null);// 设置ic_launcher的位置

		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存

		// store
		cv.restore();// 存储

		return newBitmap;

	}

	/***
	 * 缩放图片
	 * 
	 * @param src
	 * @param destWidth
	 * @param destHeigth
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap src, int destWidth, int destHeigth) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();// 源文件的大小
		int h = src.getHeight();
		// calculate the scale - in this case = 0.4f
		float scaleWidth = ((float) destWidth) / w;// 宽度缩小比例
		float scaleHeight = ((float) destHeigth) / h;// 高度缩小比例

		Matrix m = new Matrix();// 矩阵
		m.postScale(scaleWidth, scaleHeight);// 设置矩阵比例
		Bitmap resizedBitmap = Bitmap.createBitmap(src, 0, 0, w, h, m, true);// 直接按照矩阵的比例把源文件画入进行
		return resizedBitmap;
	}

	/**
	 * 递归删除文件
	 * 
	 * @param file
	 
	 */
	public static void deleteFile(File file) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete();
			} else if (file.isDirectory()) { // 如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					deleteFile(files[i]); // 把每个文件 用这个方法进行迭代，>>> 递归
				}
			}
			file.delete();
		}
	}

	/**
	 * 截取文件名
	 * 
	 * @param filepath
	 * @return
	 
	 */
	public static String getNameFromFilepath(String filepath) {
		int pos = filepath.lastIndexOf('/');
		if (pos != -1) {
			return filepath.substring(pos + 1);
		}
		return "";
	}

	/**
	 * 截取文件名，不显示后缀
	 * 
	 * @param filepath
	 * @return
	 
	 */
	public static String getNameNOFromFilepath(String filepath) {
		int pos = filepath.lastIndexOf('/');
		if (pos != -1) {
			int last = filepath.lastIndexOf('.');
			if (last > (pos + 1)) {
				return filepath.substring(pos + 1, filepath.lastIndexOf("."));
			} else {
				return getNameFromFilepath(filepath);
			}
		}
		return "";
	}

	/**
	 * 截取文件后缀
	 * 
	 * @param filepath
	 * @return
	 
	 */
	public static String getTypeFromFilepath(String filepath) {
		int dotPosition = filepath.lastIndexOf('.');
		String ext = filepath.substring(dotPosition + 1, filepath.length()).toLowerCase();
		return ext;
	}

	/**
	 * 获取Doc具体类型
	 * 
	 * @param filePath
	 * @return
	 */
//	public static int getFileType(String filePath) {
//		String type = getTypeFromFilepath(filePath);
//		if (type.equals("txt") || type.equals("text")) {
//			return FileType.TXT;
//		} else if (type.equals("doc") || type.equals("docx")) {
//			return FileType.DOC;
//		} else if (type.equals("pdf")) {
//			return FileType.PDF;
//		} else if (type.equals("ppt")) {
//			return FileType.PPT;
//		} else if (type.equals("xls") || type.equals("xlsx")) {
//			return FileType.XLS;
//		} else if (type.equals("apk")) {
//			return FileType.APK;
//		}
//		String mimeType = MimeUtils.guessMimeTypeFromExtension(type);
//		if (mimeType != null) {
//			int dotPosition = mimeType.indexOf("/");
//			String mediaType = mimeType.substring(0, dotPosition).toLowerCase();
//			if (mediaType.equals("audio")) {
//				return FileType.MUSIC;
//			} else if (mediaType.equals("image")) {
//				return FileType.PICTURE;
//			} else if (mediaType.equals("video")) {
//				return FileType.VIDEO;
//			}
//		}
//		return FileType.UNKNOWN;
//	}

	/**
	 * 时间格式转换
	 * 
	 * @param ms
	 * @return MM : SS
	 
	 */
	public static String millisTimeToDotFormat(long ms) {
		long sec = 1000;
		long min = sec * 60;
		long hour = min * 60;
		long day = hour * 24;

		long days = ms / day;
		long hours = (ms - days * day) / hour;
		long mins = (ms - days * day - hours * hour) / min;
		long secs = (ms - days * day - hours * hour - mins * min) / sec;

		// String strDay = days < 10 ? "0"+days : ""+days;
		// String strHour = hours < 10 ? "0"+hours : ""+hours;
		String strMin = mins < 10 ? "0" + mins : "" + mins;
		String strSec = secs < 10 ? "0" + secs : "" + secs;

		return (strMin + ":" + strSec);
	}

	/**
	 * 设置壁纸
	 * 
	 
	 */
	public static void setWallpaper(Context context, String path) {
//		DataEyeManager.getInstance().module(ModuleName.WALLPAPER_SETWALLPAPAER, true);
		WallpaperManager wallpaperManager = null;
		FileInputStream data = null;
		try {
			data = new FileInputStream(new File(path));
			wallpaperManager = WallpaperManager.getInstance(context);
			wallpaperManager.setStream(data);
			data.close();
//			ToastUtil.show(context, context.getResources().getString(R.string.picture_set_wallpaper_succ),
//					Toast.LENGTH_SHORT);
//			DataEyeManager.getInstance().module(ModuleName.WALLPAPER_SETWALLPAPAER, false);
		} catch (Exception e) {
			e.printStackTrace();
//			ToastUtil.show(context, context.getResources().getString(R.string.picture_set_wallpaper_fail),
//					Toast.LENGTH_SHORT);
		}
	}

	public static String getCurrenTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(new Date(System.currentTimeMillis()));
	}

	/**
	 * 获取时间 小时:分 HH:mm
	 * 
	 * @return
	 */
	public static String getTimeShort() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(new Date(System.currentTimeMillis()));
	}

	/**
	 *把时间由long类型转为String类型
	 */
	public static String converCurrentTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date(time));
	}


	/**
	 * 用于打开各种文件
	 * 
	 * @param filePath文件路径
	 */
	public static Intent openFile(String filePath) {

		File file = new File(filePath);
		if ((file == null) || !file.exists() || file.isDirectory())
			return null;

		/* 取得扩展名 */
		String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length())
				.toLowerCase();
		/* 依扩展名的类型决定MimeType */
		if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg")
				|| end.equals("wav")) {
			return getAudioFileIntent(filePath);
		} else if (end.equals("3gp") || end.equals("mp4")) {
			return getVideoFileIntent(filePath);
		} else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg")
				|| end.equals("bmp")) {
			return getImageFileIntent(filePath);
		} else if (end.equals("apk")) {
			return getApkFileIntent(filePath);
		} else if (end.equals("ppt")) {
			return getPptFileIntent(filePath);
		} else if (end.equals("xls")) {
			return getExcelFileIntent(filePath);
		} else if (end.equals("doc")) {
			return getWordFileIntent(filePath);
		} else if (end.equals("pdf")) {
			return getPdfFileIntent(filePath);
		} else if (end.equals("chm")) {
			return getChmFileIntent(filePath);
		} else if (end.equals("txt")) {
			return getTextFileIntent(filePath, false);
		} else if (end.equals(".html")) {
			return getHtmlFileIntent(filePath);
		} else {
			return getAllIntent(filePath);
		}
	}

	// Android获取一个用于打开APK文件的intent
	private static Intent getAllIntent(String param) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "*/*");
		return intent;
	}

	// Android获取一个用于打开APK文件的intent
	private static Intent getApkFileIntent(String fullPath) {
		fullPath = fullPath.replaceAll(".tmp", "");
		if (fullPath.startsWith("/data/data/")) {
			try {
				Runtime.getRuntime().exec("chmod 644 " + fullPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(fullPath));
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		return intent;
	}

	// Android获取一个用于打开VIDEO文件的intent
	private static Intent getVideoFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "video/*");
		return intent;
	}

	// Android获取一个用于打开AUDIO文件的intent
	private static Intent getAudioFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "audio/*");
		return intent;
	}

	// Android获取一个用于打开Html文件的intent
	private static Intent getHtmlFileIntent(String param) {

		Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content")
				.encodedPath(param).build();
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setDataAndType(uri, "text/html");
		return intent;
	}

	// Android获取一个用于打开图片文件的intent
	private static Intent getImageFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "image/*");
		return intent;
	}

	// Android获取一个用于打开PPT文件的intent
	private static Intent getPptFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
		return intent;
	}

	// Android获取一个用于打开Excel文件的intent
	private static Intent getExcelFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-excel");
		return intent;
	}

	// Android获取一个用于打开Word文件的intent
	private static Intent getWordFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/msword");
		return intent;
	}

	// Android获取一个用于打开CHM文件的intent
	private static Intent getChmFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/x-chm");
		return intent;
	}

	// Android获取一个用于打开文本文件的intent
	private static Intent getTextFileIntent(String param, boolean paramBoolean) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (paramBoolean) {
			Uri uri1 = Uri.parse(param);
			intent.setDataAndType(uri1, "text/plain");
		} else {
			Uri uri2 = Uri.fromFile(new File(param));
			intent.setDataAndType(uri2, "text/plain");
		}
		return intent;
	}

	// Android获取一个用于打开PDF文件的intent
	private static Intent getPdfFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/pdf");
		return intent;
	}

	/**
	 * 获取包资源信息
	 * 
	 * @param context
	 * @return
	 */
	public static PackageInfo getPackageMsg(Context context) {
		PackageManager pm = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = pm.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 
	 * @Title: isTablet
	 * @Description: 判断是否属于平板
	 * @param @param context
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	/**
	 * 
	 * @Title: autoScreenAdapter
	 * @Description: 屏幕自动适配、平板、手机
	 * @param @param context 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void autoScreenAdapter(Activity activity) {
		Window window = activity.getWindow();
		WindowManager wm = window.getWindowManager();
		Display display = wm.getDefaultDisplay(); // 获取屏幕宽、高
		WindowManager.LayoutParams params = window.getAttributes();
		// 屏幕宽度
		float screenWidth = display.getWidth();
		// 屏幕高度
		// float screenHeight = display.getHeight();

		// 判断设备是平板、手机
		if (Utils.isTablet(activity)) {
			// Log.i("is Tablet!");
			params.width = (int) (screenWidth * 0.70); // 宽度设置为屏幕的0.70
		} else {
			// Log.i("is phone!");
			params.width = (int) (screenWidth * 0.85); // 宽度设置为屏幕的0.85
		}

		window.setAttributes(params);
	}

	public static void setBackgroundDrawable(View view, Drawable drawable) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			view.setBackground(drawable);
		} else {
			view.setBackgroundDrawable(drawable);
		}
	}

	/**
	 * @Title: getFileSize
	 * @Description: 获取文件大小
	 * @param @param file
	 * @param @return
	 * @param @throws Exception
	 * @return int
	 */
	public static int getFileSize(File file) {
		int size = 0;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			size = fis.available();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	public static long getFileSize(String path) {
		long size = 0;
		try {
			File f = new File(path);
			size = f.length();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	public static void setBackgroundResource(View view, int resId, Context context) {
		if (view == null)
			return;
		try {
			Bitmap bm = BitmapFactory.decodeResource(context.getResources(), resId);
			BitmapDrawable bd = new BitmapDrawable(context.getResources(), bm);
			view.setBackgroundDrawable(bd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void recycleBackgroundResource(View view) {
		if (view == null)
			return;
		try {
			BitmapDrawable bd = (BitmapDrawable) view.getBackground();
			view.setBackgroundResource(0);// 别忘了把背景设为null，避免onDraw刷新背景时候出现used a
			// recycled bitmap错误
			bd.setCallback(null);
			bd.getBitmap().recycle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void recycleImageView(ImageView view) {
		if (view == null)
			return;
		try {
			Drawable bd = view.getDrawable();
			view.setImageBitmap(null);
			view.setBackgroundDrawable(null);
			view.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd.setCallback(null);
			view = null;
			//			bd.getBitmap().recycle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: drawableToBitmap
	 * @Description: drawable → Bitmap
	 * @param @param drawable
	 * @param @return
	 * @return Bitmap
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 
	 * @Title: getApplicationIcon
	 * @Description: 根据包名，获取应用icon，bitmap
	 * @param @param context
	 * @param @param packageName
	 * @param @return
	 * @return Bitmap
	 */
	public static Bitmap getApplicationIcon(Context context, String packageName) {
		Bitmap bitmap = null;
		Drawable drawable = null;
		PackageManager pm = context.getPackageManager();
		try {
			drawable = pm.getApplicationIcon(packageName);
			bitmap = drawableToBitmap(drawable);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public static boolean isCheckFileExist(String sdkDebugFileName) {
		try {
			File checkDebugFile = new File(Environment.getExternalStorageDirectory().getPath() + File.separator
					+ sdkDebugFileName);
			if (checkDebugFile.exists()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	* @Title: addFinishAct 
	* @Description: 添加需要finish的activity
	* @param @param activity    
	* @return void
	 */
	public static void addFinishAct(Activity activity) {
		finishList.add(activity);
	}

	/**
	* @Title: exitSystem 
	* @Description: 退出应用系统
	* @param     
	* @return void
	 */
	public static void exitSystem() {
		for (Activity act : finishList) {
			act.finish();
		}
		finishList.clear();
	}
	
	public static String convertVersionName(String versionName){
		if(versionName != null)
			return versionName.replace("(", "").replace(")", "").replace(" ", "");
		return "_";
	}
	
	/**
	* @Title: collapseStatusBar 
	* @Description:auto collapse status bar
	* @param @param context    
	* @return void
	 */
	public static void collapseStatusBar(Context context) {
		int currentApiVersion = android.os.Build.VERSION.SDK_INT;
		try {
			Object service = context.getSystemService("statusbar");
			Class<?> statusbarManager = Class
					.forName("android.app.StatusBarManager");
			Method collapse = null;
			if (service != null) {
				if (currentApiVersion <= 16) {
					collapse = statusbarManager.getMethod("collapse");
				} else {
					collapse = statusbarManager.getMethod("collapsePanels");
				}
				collapse.setAccessible(true);
				collapse.invoke(service);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
