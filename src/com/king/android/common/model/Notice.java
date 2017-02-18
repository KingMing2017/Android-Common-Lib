package com.king.android.common.model;

import android.app.PendingIntent;
import android.support.v4.app.NotificationCompat;

public class Notice {

	private int id = 0;
	private String title = null;
	private String content = null;
	private String ticker = null;
	private long when = 0;
	private int smallIcon = 0;
	private PendingIntent  contentIntent = null;
	private int priority = 0;
	private int number = 0;
	private NotificationCompat.InboxStyle style = null;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public long getWhen() {
		return when;
	}
	public void setWhen(long when) {
		this.when = when;
	}
	public int getSmallIcon() {
		return smallIcon;
	}
	public void setSmallIcon(int smallIcon) {
		this.smallIcon = smallIcon;
	}
	public PendingIntent getContentIntent() {
		return contentIntent;
	}
	public void setContentIntent(PendingIntent contentIntent) {
		this.contentIntent = contentIntent;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public NotificationCompat.InboxStyle getStyle() {
		return style;
	}
	public void setStyle(NotificationCompat.InboxStyle style) {
		this.style = style;
	}
	
}
