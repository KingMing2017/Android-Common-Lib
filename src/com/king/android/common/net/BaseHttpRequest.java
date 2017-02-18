package com.king.android.common.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import org.json.JSONObject;
import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public abstract class BaseHttpRequest {
	/**
	 * 默认超时时间
	 */
	protected final int DEFAULT_TIMEOUT_MS = 5000;
	
	private static RequestQueue mRequestQueue = null;

	public interface OnResponseListener {
		public void onResponse(Object obj, String msg);
	}
	
//	public BaseHttpRequest(Context context){
//		this.mRequestQueue = Volley.newRequestQueue(context);
//	}
	
	protected static void initialize(Context context){
        if (mRequestQueue == null){
            synchronized (BaseHttpRequest.class){
                if (mRequestQueue == null){
                    mRequestQueue = Volley.newRequestQueue(context) ;
                }
            }
        }
        mRequestQueue.start();
    }
	
	protected Object getResult(Object obj){
		return obj;
	}
	
	/**
	 * 以GET方式请求
	 * @param url
	 * @param params
	 * @param listener
	 * @param tag
	 */
	public void doGetRequest(String url, Map params, OnResponseListener listener, String tag){
		Request request = newRequest(Method.GET, url, params, null, listener, tag);
		addRequest(request, tag);
	}
	
	/**
	 * 以POST方式请求
	 * @param base
	 * @param urlParams
	 * @param bodyParams
	 * @param listener
	 * @param tag
	 */
	public void doPostRequest(String base, Map urlParams, JSONObject bodyParams, OnResponseListener listener, String tag){
		Request request = newRequest(Method.POST, base, urlParams, bodyParams, listener, tag);
		addRequest(request, tag);
	}
	
	/**
	 * 取消TAG请求
	 * @param tag
	 */
	public void cancelPendingRequests(String tag){
		if (this.mRequestQueue != null){
			this.mRequestQueue.cancelAll(tag);
		}
	}
	
	protected abstract Request newRequest(int method, String url, JSONObject bodyParams, OnResponseListener listener);
	
	protected Request newRequest(int method, String base, Map urlParams, JSONObject bodyParams, OnResponseListener listener, String tag){
		String url = base;
		if (urlParams != null){
			try {
				url = getUrl(base, urlParams);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return newRequest(method, url, bodyParams, listener);
	}
	
	private void addRequest(Request request, String tag){
		request.setTag(tag);
		request.setRetryPolicy(new DefaultRetryPolicy(
				DEFAULT_TIMEOUT_MS, 
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		mRequestQueue.add(request);
	}
	
	/**
	 * 发送请求
	 * 
	 * 请求URL携带JSON请求包 (-_-奇葩)
	 * @param baseUrl
	 * @param jsonParams
	 * @param listener
	 * @param tag
	 */
	public void sendRequest(String baseUrl, JSONObject jsonParams, OnResponseListener listener, String tag){
		Request request = newRequest(baseUrl, jsonParams, listener, tag);
		mRequestQueue.add(request);
	}


	/**
	 * 创建请求
	 * 
	 * URL携带JSON请求包(-_-奇葩)
	 * @param baseUrl
	 * @param jsonParams JSON请求包
	 * @param listener
	 * @param tag
	 * @return
	 */
	private Request newRequest(String baseUrl, JSONObject jsonParams, final OnResponseListener listener, String tag){
		StringBuffer sb = new StringBuffer();
		sb.append(baseUrl);
		if (jsonParams != null){
			sb.append("/");
			try {
				sb.append(java.net.URLEncoder.encode(jsonParams.toString(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		JsonObjectRequest request = new JsonObjectRequest(sb.toString(), null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				// TODO Auto-generated method stub
//				AppLog.e(HttpRequest.class, "出参："+arg0.toString());
				if (listener != null){
					listener.onResponse((JSONObject) getResult(arg0), HttpRequest.RESULT_OK);
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
//				AppLog.e(HttpRequest.class, "出参："+arg0.toString());
				if (listener != null)
					listener.onResponse(null, handleError(arg0));
			}
		});

		request.setTag(tag);
		request.setRetryPolicy(new DefaultRetryPolicy(
				5000, 
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

//		AppLog.e(HttpRequest.class, "入参："+request.getUrl());

		return request;
	}

	protected String handleError(Object error){
		VolleyError err = (VolleyError) error;
		err.printStackTrace();
		if (error instanceof TimeoutError) {
			return handleTimeoutError(error);
		} else if (isServerProblem(error)){
			return handleServerError(error);
		} else if (isNetworkProblem(error)){
			return handleNetworkError(error);
		}

		return "未知错误";
	}

	private String handleServerError(Object err) {
		VolleyError error = (VolleyError) err;
		NetworkResponse response = error.networkResponse;
		if (response != null) {
			switch (response.statusCode) {
			case 404:
			case 422:
			case 401:
			}
		}
		return "服务器错误";
	}

	private String handleNetworkError(Object err){
		return "网络错误，请检查网络设置";
	}

	private String handleTimeoutError(Object err){
		return "连接超时，请稍后重试";
	}

	private boolean isServerProblem(Object error){
		return (error instanceof ServerError) || (error instanceof AuthFailureError);
	}

	private boolean isNetworkProblem(Object error) {
		return (error instanceof NetworkError) || (error instanceof NoConnectionError);
	}

	private String getUrl(String base, Map params) throws UnsupportedEncodingException{
		StringBuffer sb = new StringBuffer();
		sb.append(base);
		sb.append("?");
		String p = getParams(params);
		sb.append(p);
		return sb.toString();
	}

	private String getParams(Map map) throws UnsupportedEncodingException{
		StringBuffer sb = new StringBuffer();
		for (Object key : map.keySet()){
			sb.append(key.toString());
			sb.append("=");
			String v = URLEncoder.encode(map.get(key).toString(), "UTF-8");;
			sb.append(v);
			sb.append("&");
		}
		return sb.toString();
	}
}
