package com.king.android.common.exception;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/** 
 * UncaughtException处理器当程序发生Uncaught异常的时侯由该类来接管程序,并记录发送错误报告
 *  
 * @author user 
 *  
 */  
@SuppressLint("SimpleDateFormat")
public class ExceptionHandler implements UncaughtExceptionHandler {  
      
    public static final String TAG = "CrashHandler";  
      
    //系统默认的UncaughtException处理器
    private Thread.UncaughtExceptionHandler mDefaultHandler;  
    //CrashHandler实例  
    private static ExceptionHandler INSTANCE = new ExceptionHandler();  
    //程序的Context对象 
    private Context mContext;  
    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();  
  
   
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");  
    //异常提交参数
    private ExceptionInfo exceptionInfo;
    //保存一场信息的文件路径
    private String execFilePath = null;
    //保存本地标志
    private boolean flag = true;
  
    /** 保证只有一个CrashHandler实例 */  
    private ExceptionHandler() {  
    }  
  
    /** 获取CrashHandler实例 ,单例模式  */  
    public static ExceptionHandler getInstance() {  
        return INSTANCE;  
    }  
  
    /** 
     * 初始化
     *  
     * @param context 
     */  
    public void init(Context context, String path, boolean flag) {  
        this.mContext = context;  
        this.execFilePath = path;
        this.flag = flag;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();  
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);  
    }  
  
    /** 
     * 当UncaughtException发生时会转入该函数来处理
     */  
    @Override  
    public void uncaughtException(Thread thread, Throwable ex) {  
        if (!handleException(ex) && mDefaultHandler != null) {  
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);  
        } else {  
//        	new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//				   	try {
//				   		if(ServiceConfig.isSxceptionSubmit) {
//				   			ExceptionHttpUtil.post(ServiceConfig.exceptionSubmitUrl, exceptionInfo);	
//				   		}
//					} catch (ClientProtocolException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}finally {
//			            //锟�锟斤拷绋嬪簭  
//			            android.os.Process.killProcess(android.os.Process.myPid());  
//			            System.exit(1);  
//					}
//				}
//			}).start();
				
		
        }  
    }  
  
    /** 
     * 自定义错误处理收集错误信息 发送错误报告等操作均在此完成. 
     *  
     * @param ex  异常信息
     * @return true:如果处理了该异常信息;否则返回false. 
     */  
    private boolean handleException(Throwable ex) {  
        if (ex == null) {  
            return false;  
        }  
        //使用Toast来显示异常信息
        new Thread() {  
            @Override  
            public void run() {  
                Looper.prepare();  
                Toast.makeText(mContext, "程序出现异常,即将退出程序", Toast.LENGTH_LONG).show();  
                Looper.loop();  
            }  
        }.start();  
        //收集设备参数信息
        collectDeviceInfo(mContext);  
        //保存日志文件      
        saveCrashInfo2File(ex);  
        return true;  
    }  
      
    /** 
     * 收集设备参数信息
     * @param ctx 
     */  
    public void collectDeviceInfo(Context ctx) { 
    	exceptionInfo = new ExceptionInfo();
        try {  
            PackageManager pm = ctx.getPackageManager(); // 获得包管理器
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES); // 寰楀埌璇ュ簲鐢ㄧ殑淇℃伅锛屽嵆涓籄ctivity 
            if (pi != null) {  
                String versionName = pi.versionName == null ? "null" : pi.versionName;  
                String versionCode = pi.versionCode + "";  
                infos.put("versionName", versionName);  
                infos.put("versionCode", versionCode);  
                exceptionInfo.setServiceName(versionName);
                exceptionInfo.setVersion(versionCode);
            }  
        } catch (NameNotFoundException e) {  
            Log.e(TAG, "an error occured when collect package info", e);  
        }  
        Field[] fields = Build.class.getDeclaredFields();  // 反射机制
        for (Field field : fields) {  
            try {  
                field.setAccessible(true);  
                infos.put(field.getName(), field.get(null).toString());  
                Log.d(TAG, field.getName() + " : " + field.get(null));  
            } catch (Exception e) {  
                Log.e(TAG, "an error occured when collect crash info", e);  
            }  
        }  
    }  
  
    /** 
     * 保存错误信息到文件中 
     *  
     * @param ex 
     * @return  返回文件名称,便于将文件传送到服务�?
     */  
    private String saveCrashInfo2File(Throwable ex) {  
        StringBuffer sb = new StringBuffer();  
        for (Map.Entry<String, String> entry : infos.entrySet()) {  
            String key = entry.getKey();  
            String value = entry.getValue();  
            sb.append(key + "=" + value + "\n");  
        }  
          
        Writer writer = new StringWriter();  
        PrintWriter printWriter = new PrintWriter(writer);  
        ex.printStackTrace(printWriter);  
        Throwable cause = ex.getCause();  
    	// 循环获取的异常信息写入writer实例
        while (cause != null) {  
            cause.printStackTrace(printWriter);  
            cause = cause.getCause();  
        }  
        printWriter.close();  
        String result = writer.toString();  
        sb.append(result);  
        try {  
            long timestamp = System.currentTimeMillis();  
            String time = formatter.format(new Date());  
            String fileName = "crash-" + time + "-" + timestamp + ".log"; 
            exceptionInfo.setLogTime(time);
            exceptionInfo.setExceptionInfo(sb.toString());
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {  
                String path = this.execFilePath;  
                File dir = new File(path);  
                if (!dir.exists()) {  
                    dir.mkdirs();  
                }  
                if (this.flag) {
                    FileOutputStream fos = new FileOutputStream(path + fileName);  
                    fos.write(sb.toString().getBytes());  
                    fos.close();  	
                }
            }  
            return fileName;  
        } catch (Exception e) {  
            Log.e(TAG, "an error occured while writing file...", e);  
        }  
        return null;  
    }  
}  