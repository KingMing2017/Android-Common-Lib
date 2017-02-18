package com.king.android.common.utils;

import com.king.android.common.model.Notice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class NotificationUtils {
	
	private static NotificationManager getNotificationManager(Context context){
		return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	/**
	 * 显示通知栏
	 * @param context
	 * @param notice
	 */
	public static void showNotify(Context context, Notice notice){
		
		NotificationManager notificationManager = getNotificationManager(context);
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		builder.setContentTitle(notice.getTitle())
				.setContentText(notice.getContent())
				.setContentIntent(notice.getContentIntent())
				.setNumber(notice.getNumber())//显示数量
				.setTicker(notice.getTicker())//通知首次出现在通知栏，带上升动画效果的
				.setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
				.setPriority(notice.getPriority())//设置该通知优先级
//				.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消  
				.setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
				.setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
				//Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
				.setSmallIcon(notice.getSmallIcon())
				.setStyle(notice.getStyle())
				;
		
		notificationManager.notify(notice.getId(), builder.build());
	}
	
	/** 
	 * 清除当前创建的通知栏 
	 */
	public static void clearNotify(Context context, int notifyId){
		getNotificationManager(context).cancel(notifyId);//删除一个特定的通知ID对应的通知
	}
	
	/**
	 * 清除所有通知栏
	 * */
	public static void clearAllNotify(Context context) {
		getNotificationManager(context).cancelAll();// 删除你发的所有通知
	}
	
	/**
	 * @获取默认的pendingIntent,为了防止2.3及以下版本报错
	 * @flags属性:  
	 * 在顶部常驻:Notification.FLAG_ONGOING_EVENT  
	 * 点击去除： Notification.FLAG_AUTO_CANCEL 
	 */
	public static PendingIntent getDefalutIntent(Context context, int flags){
		PendingIntent pendingIntent= PendingIntent.getActivity(context, 1, new Intent(), flags);
		return pendingIntent;
	}
}
