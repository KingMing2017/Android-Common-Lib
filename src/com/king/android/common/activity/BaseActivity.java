package com.king.android.common.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.WindowCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.android.common.BaseApplication;
import com.king.android.common.AppLog;
import com.king.android.common.R;
import com.king.android.common.activity.ActivityStack;
import com.king.android.common.adapter.MenuListAdapter;
import com.king.android.common.net.HttpRequest;
import com.king.android.common.widget.LoadingLayout;

/**
 * Activity基类，继承AppCompatActivity
 * @author xiao_ming
 * ActionBarActivity is deprecated FragmentActivity
 */
public abstract class BaseActivity extends AppCompatActivity {

	protected final static int MODE_STANDARD = 0;
	protected final static int MODE_DRAWER = 1;

	protected String TAG = null;
	protected HttpRequest mHttpRequest = null;
	protected LoadingLayout mLoadingLayout = null;
	//	protected ProgressDialog mLoadingDialog = null;
	protected View mThisView = null;

	private Toolbar mToolbar = null;
	private SearchView mSearchView = null;
	private boolean isSearchViewVisibility = false;
	private boolean isSearchViewIconified = true; 
	private boolean isActionViewExpanded = false;
	private LinearLayout mContentLayout = null;
	private RelativeLayout mToolbarLayout = null;
	private LinearLayout mTitleLayout = null;
	private TextView mTitleTv = null;
	private TextView mRightTv = null;

	private DrawerLayout mDrawerLayout = null;
	private ActionBarDrawerToggle mDrawerToggle = null;
	private ListView mMenuListView = null;
	private MenuListAdapter mMenuAdapter = null; // 左侧菜单适配器

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_MODE_OVERLAY);
		super.onCreate(savedInstanceState);
		ActivityStack.getActivityManager().pushActivity(this);
		setContentView(R.layout.activity_base);

		onInit();
		onInitView();
		onInitData();
		onSetListener();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mHttpRequest != null && TAG != null)
			mHttpRequest.cancelPendingRequests(TAG);
		ActivityStack.getActivityManager().popActivity(this);
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main_base, menu);  

		MenuItem menuItem = menu.findItem(R.id.action_search);//在菜单中找到对应控件的item
		menuItem.setVisible(isSearchViewVisibility);
		mSearchView = (SearchView) MenuItemCompat.getActionView(menuItem);
		if (mSearchView != null){
			mSearchView.setIconified(isSearchViewIconified);
			if (isActionViewExpanded){
				mSearchView.onActionViewExpanded();
			}
			mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {

				@Override
				public boolean onClose() {
					// TODO Auto-generated method stub
					mTitleLayout.setVisibility(View.VISIBLE);
					return false;
				}
			});
			mSearchView.setOnSearchClickListener(new SearchView.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mTitleLayout.setVisibility(View.GONE);
				}
			});
			mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

				@Override
				public boolean onQueryTextSubmit(String arg0) {
					// TODO Auto-generated method stub
					return onSearchTextSubmit(arg0);
				}

				@Override
				public boolean onQueryTextChange(String arg0) {
					// TODO Auto-generated method stub
					return onSearchTextChange(arg0);
				}
			});
		}

		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * 设置布局
	 */
	protected abstract int onLayoutResID();
	/**
	 * 初始化容器控件
	 */
	protected abstract void onInitView();
	/**
	 * 初始化Base控件
	 */
	protected void onInit(){
		/*
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		    Window window = getWindow();
		    // Translucent status bar
		    window.setFlags(
		     WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
		     WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		    // Translucent navigation bar
		    window.setFlags(
		     WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
		     WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		 */

		AppLog.setLog(getClass());
		TAG = getClass().getSimpleName();
		mHttpRequest = BaseApplication.getHttpRequest();
		//		mLoadingDialog = new LoadingDialog(this);
		//		mLoadingDialog.setMessage(getString(R.string.tip_loading_info));

		mToolbar = (Toolbar) findViewById(R.id.activity_base_toolbar);
		mContentLayout = (LinearLayout) findViewById(R.id.activity_base_content_layout);
		mToolbarLayout = (RelativeLayout) findViewById(R.id.activity_base_toolbar_layout);
		mTitleLayout = (LinearLayout) findViewById(R.id.activity_base_toolbar_title_layout);
		mTitleTv = (TextView) findViewById(R.id.activity_base_title);
		mRightTv = (TextView) findViewById(R.id.activity_base_right);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_base_left_drawerlayout);
		mMenuListView = (ListView) findViewById(R.id.activity_base_left_drawerlayout_menu);

		mToolbar.setTitle("");
		setSupportActionBar(mToolbar);
		getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		if (getDisplayMode() == MODE_DRAWER){
			mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
			mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawerlayout_open, R.string.drawerlayout_close){

				@Override
				public void onDrawerClosed(View drawerView) {
					// TODO Auto-generated method stub
					super.onDrawerClosed(drawerView);
				}

				@Override
				public void onDrawerOpened(View drawerView) {
					// TODO Auto-generated method stub
					super.onDrawerOpened(drawerView);
				}

			};
			mDrawerToggle.syncState();
			mDrawerLayout.setDrawerListener(mDrawerToggle);

		} else if (getDisplayMode() == MODE_STANDARD) {
			mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		}

		setContentLayout(onLayoutResID());

		mLoadingLayout = LoadingLayout.wrap(mContentLayout);
		mLoadingLayout.showContent();

	}

	/**
	 * 设置监听器
	 */
	protected void onSetListener(){
		if (getDisplayMode() == MODE_STANDARD){
			// 一般是返回事件监听
			mToolbar.setNavigationOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					onBackBtnClickEventReceived();
				}
			});
		} else {
			mMenuListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					mMenuAdapter.setSelectedItem(position);
					mMenuAdapter.notifyDataSetChanged();
					if (mOnLeftMenuItemClickListener != null){
						mOnLeftMenuItemClickListener.onLeftMenuItemClickListener(parent, view, position, id);
					}
					if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
						mDrawerLayout.closeDrawer(GravityCompat.START);
					} else {
						mDrawerLayout.openDrawer(GravityCompat.START);
					}
				}
			});
		}
	}

	/**
	 * 初始化数据
	 */
	protected abstract void onInitData();

	protected void onBackBtnClickEventReceived(){
		finish();
	}

	protected void addViewInTitleLayout(View view){
		mTitleLayout.addView(view);
	}

	/**
	 * 设置内容页面
	 * @param id layout里面的布局 如R.layout.mainactivity
	 */
	protected void setContentLayout(int id) {
		if (mContentLayout != null){
			mThisView = getLayoutInflater().inflate(id, null, false);
			mContentLayout.addView(mThisView);
		} else {
			setContentView(id);
		}
	}

	/**
	 * MODE_STANDARD : 没有DrawerLayout
	 * @return
	 */
	protected int getDisplayMode(){
		return MODE_STANDARD;
	}

	/**
	 * 设置左侧菜单列表
	 * @param adapter
	 */
	protected void setLeftMenuList(MenuListAdapter adapter){
		mMenuAdapter = adapter;
		mMenuListView.setAdapter(mMenuAdapter);
	}

	/**
	 * 设置左侧菜单列表监听事件
	 * @param listener
	 */
	protected void setOnLeftMenuItemClickListener(OnLeftMenuItemClickListener listener){
		this.mOnLeftMenuItemClickListener = listener;
	}

	protected void setOnTitleLayoutClickListener(OnClickListener listener) {
		mTitleLayout.setOnClickListener(listener);
	}

	protected void setToolbarLayoutVisibility(boolean visible) {
		if (visible){
			mToolbarLayout.setVisibility(View.VISIBLE);
		} else {
			mToolbarLayout.setVisibility(View.GONE);
		}

	}

	/**
	 * 设置标题布局是否可见
	 * @param visible
	 */
	protected void setTitleLayoutVisibility(boolean visible) {
		if (visible)
			mTitleLayout.setVisibility(View.VISIBLE);
		else 
			mTitleLayout.setVisibility(View.GONE);
	}

	/**
	 * 设置右侧标题是否可见
	 * @param title
	 */
	protected void setToolbarRightVisibility(boolean visible){
		if (visible){
			mRightTv.setVisibility(View.VISIBLE);
		} else {
			mRightTv.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置右侧标题点击事件监听器
	 * @param title
	 */
	protected void setToolbarRightListener(View.OnClickListener l){
		mRightTv.setOnClickListener(l);
	}

	/**
	 * 设置居中标题
	 * @param title
	 */
	protected void setToolbarTitle(String title){
		mTitleTv.setText(title);
	}

	/**
	 * 设置居中标题
	 * @param resid
	 */
	protected void setToolbarTitle(int resid){
		mTitleTv.setText(resid);
	}

	/**
	 * 设置右侧标题
	 * @param title
	 */
	protected void setToolbarRightLable(String title){
		mRightTv.setText(title);
	}

	/**
	 * 设置左侧标题
	 * @param resid
	 */
	protected void setToolbarRightLable(int resid){
		mRightTv.setText(resid);
	}

	/**
	 * 设置左侧标题
	 * @param title
	 */
	protected void setToolbarLable(String title){
		getSupportActionBar().setTitle(title);
	}

	/**
	 * 设置左侧标题
	 * @param resid
	 */
	protected void setToolbarLable(int resid){
		getSupportActionBar().setTitle(resid);
	}

	/**
	 * 设置是否显示返回图标
	 * @param visible true：可见
	 */
	protected void setBackIconVisiblity(boolean visible){
		getSupportActionBar().setHomeButtonEnabled(visible); //设置返回键可用
		getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
	}

	/**
	 * 设置左侧返回图标
	 * @param resid
	 */
	protected void setBackIcon(int resid) {
		mToolbar.setNavigationIcon(resid);
	}

	/**
	 * 设置SearchView是否可见
	 * 
	 * @param visible true ：可见
	 */
	protected void setSearchViewVisiblity(boolean visible) {
		isSearchViewVisibility = visible;
	}

	/**
	 * 图标化或者展开SearchView
	 * @param iconify true值会把SearchView收缩成一个图标，false值会展开它
	 */
	protected void setSearchViewIconified(boolean iconify) {
		isSearchViewIconified = iconify;
	}

	/**
	 * 设置在SearchView内容为空时不显示取消的x按钮，内容不为空时显示.
	 * @param expanded true：启动此功能
	 */
	protected void setSearchActionViewExpanded(boolean expanded) {
		isActionViewExpanded = expanded;
	}

	/**
	 * 监听SearchView提交
	 * @param arg0
	 * @return
	 */
	protected boolean onSearchTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 监听SearchView文本变化
	 * @param arg0
	 * @return
	 */
	protected boolean onSearchTextChange(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 退出APP
	 * @param context
	 */
	protected void exit(){
		ActivityStack.getActivityManager().popAllActivityExceptOne(null);
		android.os.Process.killProcess(android.os.Process.myPid());
		//		System.exit(0);
	}

	public interface OnLeftMenuItemClickListener{
		public void onLeftMenuItemClickListener(AdapterView<?> parent, View view,
				int position, long id);
	}
	private OnLeftMenuItemClickListener mOnLeftMenuItemClickListener = null;

}
