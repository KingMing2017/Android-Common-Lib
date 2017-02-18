package com.king.android.common.net;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.king.android.common.AppLog;

import android.content.Context;

public class JsonHttpRequest extends BaseHttpRequest {

	public JsonHttpRequest(Context context) {
		// TODO Auto-generated constructor stub
		super.initialize(context);
	}
	
	@Override
	protected Request newRequest(int method, String url,
			final JSONObject bodyParams, final OnResponseListener listener) {
		// TODO Auto-generated method stub
		JsonObjectRequest request = new JsonObjectRequest(method, url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject arg0) {
						// TODO Auto-generated method stub
						AppLog.d(JsonHttpRequest.class, "出参："+arg0.toString());
						if (listener != null){
							listener.onResponse(getResult(arg0), HttpRequest.RESULT_OK);
						}
					}
				}, new Response.ErrorListener() {
		
					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						AppLog.e(JsonHttpRequest.class, "error："+arg0.toString());
						if (listener != null)
							listener.onResponse(null, handleError(arg0));
					}
				}){
					@Override
					public byte[] getBody() {
						// TODO Auto-generated method stub
						if (bodyParams == null)
							return null;
						AppLog.e(JsonHttpRequest.class, bodyParams.toString());
						return bodyParams.toString().getBytes();
					}
		
				};

		AppLog.d(JsonHttpRequest.class, "入参："+request.getUrl());

		return request;
	}

}
