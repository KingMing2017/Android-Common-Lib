package com.king.android.common.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.king.android.common.AppLog;
import com.king.android.common.BaseApplication;
import com.king.android.common.R;
import com.king.android.common.net.HttpRequest;
import com.king.android.common.widget.dialog.LoadingDialog;

public abstract class BaseFragment extends Fragment {

	protected String TAG = null;
	protected HttpRequest mHttpRequest = null;
	protected ProgressDialog mLoading = null;
	
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = onCreateView(inflater, container);
		
		initComponent(view);
		onInitView(view);
		onInitData();
		onSetListener();
		
		return view;
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		if (mHttpRequest != null && TAG != null)
			mHttpRequest.cancelPendingRequests(TAG);
	}
	
	/**
	 * 初始化Base控件
	 */
	protected void initComponent(View view){

		AppLog.setLog(this.getClass());
		TAG = getClass().getSimpleName();
		mHttpRequest = BaseApplication.getHttpRequest();
		
		mLoading = new LoadingDialog(getActivity());
		mLoading.setMessage(getString(R.string.tip_loading_info));
	}
	
	/**
	 * 设置显示页面
	 */
	public abstract View onCreateView(LayoutInflater inflater, ViewGroup container);

	/**
	 * 初始化容器控件
	 */
	protected abstract void onInitView(View view);
	
	/**
	 * 设置监听器
	 */
	protected abstract void onSetListener();
	
	/**
	 * 初始化数据
	 */
	protected abstract void onInitData();
	
}
