package com.king.android.common.widget.dialog;

import android.app.ProgressDialog;
import android.content.Context;

public class LoadingDialog extends ProgressDialog {

	public LoadingDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		//		this.setCancelable(false);
		this.setCanceledOnTouchOutside(false);
	}

	public void setMessage(String msg){
		this.setMessage(msg);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
}
