package com.king.android.common.widget.dialog;

import com.flyco.animation.Attention.Swing;
import com.flyco.dialog.widget.base.BaseDialog;

import android.content.Context;
import android.view.View;

public abstract class MultiDialog extends BaseDialog {

	private Context mContext = null;
	public MultiDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}

	@Override
	public View onCreateView() {
		// TODO Auto-generated method stub
		widthScale(0.85f);
        showAnim(new Swing());

        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(mContext, onLayoutResID(), null);
        onInitView(inflate);
        onSetListener();
        
        return inflate;
	}
	
	protected abstract int onLayoutResID();
	protected abstract void onInitView(View view);
	protected abstract void onSetListener();
}
