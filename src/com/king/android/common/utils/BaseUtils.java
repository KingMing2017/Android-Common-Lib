package com.king.android.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.widget.Toast;

public class BaseUtils {

	/**
	 * Show the notification for a short period of time
	 * @param context
	 * @param msg
	 */
	public static void showShortToast(Context context, String text){
		ToastUtils.show(context, text, Toast.LENGTH_SHORT);
	}

	/**
	 * Show the notification for a long period of time
	 * @param context
	 * @param msg
	 */
	public static void showLongToast(Context context, String text){
		ToastUtils.show(context, text, Toast.LENGTH_LONG);
	}

	/**
	 * Activity跳转
	 * @param context
	 * @param clazz
	 */
	public static void toActivity(Context context, Class<?> clazz){
		Intent intent = new Intent(context, clazz);
		context.startActivity(intent);
	}
	
	/**
	 * Activity跳转
	 * @param context
	 * @param clazz
	 */
	public static void toActivity(Context context, Class<?> clazz, String key, Bundle bundle){
		Intent intent = new Intent(context, clazz);
		intent.putExtra(key, bundle);
		context.startActivity(intent);
	}

	/**
	 * Activity跳转
	 * @param context
	 * @param clazz
	 * @param key
	 * @param value
	 */
	public static void toActivity(Context context, Class<?> clazz, String key, Object object){
		Intent intent = new Intent(context, clazz);
		if (object instanceof String) {  
			intent.putExtra(key, (String)object);
		} else if (object instanceof Integer) {  
			intent.putExtra(key, (Integer) object);
		}
		context.startActivity(intent);
	}

	/**
	 * Activity跳转
	 * @param context
	 * @param action
	 */
	public static void toActivity(Context context, String action){
		Intent intent = new Intent(action);
		context.startActivity(intent); 
	}

	/**
	 * 
	 * @param context
	 * @param clazz
	 * @param key 
	 * @param value : ArrayList<? extends Parcelable>
	 */
	public static void toActivity(Context context, Class<?> clazz, String key, ArrayList<? extends Parcelable> value){
		Intent intent = new Intent(context, clazz);
		intent.putParcelableArrayListExtra(key, value);
		context.startActivity(intent);
	}

	/**
	 * startActivityForResult
	 * @param activity
	 * @param clazz
	 * @param requestCode
	 */
	public static void toActivityForResult(Activity activity, Class<?> clazz, int requestCode){
		Intent intent = new Intent(activity, clazz);
		activity.startActivityForResult(intent, requestCode);
	}
	
	/**
	 * startActivityForResult
	 * @param activity
	 * @param clazz
	 * @param requestCode
	 * @param key
	 * @param bundle
	 */
	public static void toActivityForResult(Activity activity, Class<?> clazz, int requestCode, String key, Bundle bundle){
		Intent intent = new Intent(activity, clazz);
		intent.putExtra(key, bundle);
		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * 启动Service
	 * @param context
	 * @param action
	 * @return
	 */
	public static Intent startService(Context context, String action){
		Intent intent = new Intent();
		intent.setAction(action);
		intent.setPackage(context.getPackageName());
		context.startService(intent);
		return intent;
	}

	/**
	 * 发送消息给Handler
	 * @param handler
	 * @param what
	 */
	public static void sendMessage(Handler handler, int what){
		handler.sendEmptyMessage(what);
	}

	/**
	 * 发送消息给Handler
	 * @param handler
	 * @param what
	 * @param obj
	 */
	public static void sendMessage(Handler handler, int what, String obj){
		Message msg = new Message();
		msg.what = what;
		msg.obj = obj;
		handler.sendMessage(msg);
	}
	
	/**
	 * MD5加密
	 * @param arg0
	 * @return
	 */
	public static String md5(String arg0){
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		md5.update(arg0.getBytes());
		byte[] md5BytesData = md5.digest();// 加密
		return byte2HexString(md5BytesData, false);
	}

	private static String byte2HexString(byte[] byteData, boolean ifAddBlank) {
		if (byteData == null || byteData.length == 0)
			return null;
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder stringData = new StringBuilder("");
		int bit;
		for (int i = 0; i < byteData.length; i++) {
			bit = (byteData[i] & 0x0f0) >> 4;
			stringData.append(chars[bit]);
			bit = byteData[i] & 0x0f;
			stringData.append(chars[bit]);
			if (ifAddBlank) {
				stringData.append(' ');
			}
		}
		return stringData.toString().trim();
	}
	
	/** 
	 * 返回当前程序版本信息 
	 */  
	public static PackageInfo getAppVersionInfo(Context context) {  
	    PackageInfo pi = null;
	    try {  
	        // ---get the package info---  
	        PackageManager pm = context.getPackageManager();  
	        pi = pm.getPackageInfo(context.getPackageName(), 0);  
	    } catch (Exception e) {  
	    	e.printStackTrace();
	    }  
	    return pi;  
	}  
	
	public static int getAndroidSdkApiLevel(){
		return android.os.Build.VERSION.SDK_INT;
	}

}
