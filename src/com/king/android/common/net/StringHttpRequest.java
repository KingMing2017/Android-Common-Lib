package com.king.android.common.net;

import org.json.JSONObject;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.king.android.common.AppLog;

public class StringHttpRequest extends BaseHttpRequest {

	public StringHttpRequest(Context context) {
		// TODO Auto-generated constructor stub
		super.initialize(context);
	}

	@Override
	protected Request newRequest(int method, String url,
			JSONObject bodyParams, final OnResponseListener listener) {
		// TODO Auto-generated method stub
		StringRequest request = new StringRequest(method, url, 
				new Response.Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						// TODO Auto-generated method stub
						AppLog.d(StringHttpRequest.class, "出参："+arg0.toString());
						if (listener != null){
							listener.onResponse(getResult(arg0), HttpRequest.RESULT_OK);
						}
					}
				}, new Response.ErrorListener() {
		
					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						AppLog.e(StringHttpRequest.class, "error："+arg0.toString());
						if (listener != null)
							listener.onResponse(null, handleError(arg0));
					}
				});
		
		AppLog.d(StringHttpRequest.class, "入参："+request.getUrl());
		
		return request;
	}

}
