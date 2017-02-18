package com.king.android.common.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Notification;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.king.android.common.AppLog;
import com.king.android.common.R;
import com.king.android.common.adapter.MenuListAdapter;
import com.king.android.common.model.Notice;
import com.king.android.common.net.BaseHttpRequest;
import com.king.android.common.net.BaseHttpRequest.OnResponseListener;
import com.king.android.common.utils.NotificationUtils;


public class MainActivity extends BaseActivity {

	@Override
	protected int onLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.activity_main;
	}

	@Override
	protected void onInitView() {
		// TODO Auto-generated method stub
		setToolbarTitle("标题");
		setBackIconVisiblity(false);
//		setBackIcon(R.drawable.ic_btn_back_normal);
	}

	@Override
	protected void onInitData() {
		// TODO Auto-generated method stub
//		final MaterialDialog dialog = new MaterialDialog(this);
//        dialog.isTitleShow(false)//
//                .content("您要退出程序?")//
//                .contentTextSize(18)
//                .btnText("取消", "确定")//
//                .showAnim(new BounceTopEnter())//
//                .dismissAnim(new SlideBottomExit())//
//                .show();
		
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("CloudID", "123");
//		String url = "http://172.168.1.25:8080/business/AppInterface/GetUserInf";
//		mHttpRequest.getJsonHttpRequest().doGetRequest(url, map, new OnResponseListener() {
//			
//			@Override
//			public void onResponse(Object obj, String msg) {
//				// TODO Auto-generated method stub
//				
//			}
//		}, TAG);
		
//		Notice notice = new Notice();
//		notice.setId(3);
//		notice.setTitle("Title");
//		notice.setContent("Content");
//		notice.setTicker("I am coming");
//		notice.setSmallIcon(R.drawable.ic_launcher);
//		notice.setContentIntent(NotificationUtils.getDefalutIntent(this, Notification.FLAG_AUTO_CANCEL));
//		NotificationUtils.showNotify(this, notice);
		
		String[] lvs = {"首页", "WIFI", "设置", "关于"};
		MenuListAdapter adapter = new MenuListAdapter(this, lvs);
		setLeftMenuList(adapter);
		setOnLeftMenuItemClickListener(new OnLeftMenuItemClickListener() {
			
			@Override
			public void onLeftMenuItemClickListener(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});
		
		 final MaterialDialog dialog = new MaterialDialog(this);
	        dialog//
	                .btnNum(1)
	                .content("为保证咖啡豆的新鲜度和咖啡的香味，并配以特有的传统烘焙和手工冲。")//
	                .btnText("确定")//
	                .show();

	        dialog.setOnBtnClickL(new OnBtnClickL() {
	            @Override
	            public void onBtnClick() {
	                dialog.dismiss();
	            }
	        });
	}

	@Override
	protected int getDisplayMode() {
		// TODO Auto-generated method stub
		return MODE_DRAWER;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onPrepareOptionsMenu(menu);
		setSearchViewVisiblity(false);
		
		menu.removeGroup(10);
		menu.add(10, 123, 100, "设置");
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
		case 123:
			System.out.println("shezhi");
			break;
		}
		return true;
	}
	

}
