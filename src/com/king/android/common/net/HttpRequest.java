package com.king.android.common.net;

import android.content.Context;

public class HttpRequest {

	public static final String RESULT_OK = "Success";
	public static final String RESULT_ERROR = "Failed";
	
	private Context mContext = null;
	private StringHttpRequest mStringHttpRequest = null;
	private JsonHttpRequest mJsonHttpRequest = null;
	
	public HttpRequest(Context context){
		this.mContext = context;
	}
	
	public StringHttpRequest getStringHttpRequest(){
		if (this.mStringHttpRequest == null)
			this.mStringHttpRequest = new StringHttpRequest(mContext);
		return this.mStringHttpRequest;
	}
	
	public void setStringHttpRequest(StringHttpRequest request){
		this.mStringHttpRequest = request;
	}
	
	public JsonHttpRequest getJsonHttpRequest(){
		if (this.mJsonHttpRequest == null)
			this.mJsonHttpRequest = new JsonHttpRequest(mContext);
		return this.mJsonHttpRequest;
	}
	
	public void setJsonHttpRequest(JsonHttpRequest request){
		this.mJsonHttpRequest = request;
	}
	
	public void cancelPendingRequests(String tag){
		if (this.mStringHttpRequest != null){
			this.mStringHttpRequest.cancelPendingRequests(tag);
		}
		if (this.mJsonHttpRequest != null){
			this.mJsonHttpRequest.cancelPendingRequests(tag);
		}
	}
	
}
