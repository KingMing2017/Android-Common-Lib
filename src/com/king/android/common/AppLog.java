package com.king.android.common;

import java.util.HashMap;

import com.king.android.common.download.Downloader;
import com.king.android.common.exception.ExceptionHandler;
import com.king.android.common.net.JsonHttpRequest;
import com.king.android.common.net.StringHttpRequest;

import android.util.Log;

/**
 * Logµ÷ÊÔÊä³ö
 * @author xiao_ming
 *
 */
public class AppLog {
	private static final boolean DEBUG = BuildConfig.DEBUG; 
	private final static HashMap<Class<?>, Boolean> map = new HashMap<Class<?>, Boolean>();

	private AppLog() {
        throw new AssertionError();
    }
	
	public static void initialize(){
		if (!DEBUG) return;
		map.put(ExceptionHandler.class, true);
		map.put(JsonHttpRequest.class, true);
		map.put(StringHttpRequest.class, true);
		map.put(Downloader.class, true);
	}
	
	public static void setLog(Class<?> cls){
		map.put(cls, true);
	}

	public static void v(Class<?> cls, String msg){
		if (!DEBUG) return;
		if (isDebug(cls)){
			Log.v(cls.getSimpleName(), msg);
		}
	}

	public static void d(Class<?> cls, String msg){
		if (!DEBUG) return;
		if (isDebug(cls)){
			Log.d(cls.getSimpleName(), msg);
		}
	}

	public static void i(Class<?> cls, String msg){
		if (!DEBUG) return;
		if (isDebug(cls)){
			Log.i(cls.getSimpleName(), msg);
		}
	}

	public static void w(Class<?> cls, String msg){
		if (!DEBUG) return;
		if (isDebug(cls)){
			Log.w(cls.getSimpleName(), msg);
		}
	}

	public static void e(Class<?> cls, String msg){
		if (!DEBUG) return;
		if (isDebug(cls)){
			Log.e(cls.getSimpleName(), msg);
		}
	}

	public static void e(Class<?> cls, String msg, Exception e){
		if (!DEBUG) return;
		if (isDebug(cls)){
			Log.e(cls.getSimpleName(), msg, e);
		}
	}
	
	public static void a(Class<?> cls, String msg){
		if (!DEBUG) return;
		if (isDebug(cls)){
			Log.println(Log.ASSERT, cls.getSimpleName(), msg);
		}
	}
	
	private static boolean isDebug(Class<?> cls){
		if (map.get(cls) != null){
			return map.get(cls);
		} 
		return false;
	}
}
