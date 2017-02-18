package com.king.android.common.activity;

import java.util.List;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.king.android.common.R;
import com.king.android.common.utils.BaseUtils;
import com.king.android.common.widget.indicator.Indicator;
import com.king.android.common.widget.indicator.IndicatorViewPager;
import com.king.android.common.widget.indicator.IndicatorViewPager.OnIndicatorPageChangeListener;
import com.king.android.common.widget.indicator.TabModel;
import com.king.android.common.widget.indicator.ViewPagerFragmentPagerAdapter;

public abstract class FooterActivity extends BaseActivity {

	private ViewPager mViewPager = null;
	private IndicatorViewPager mIndicatorViewPager = null;
	private Indicator mIndicator = null;
	private List<TabModel> mTabList = null;
	private boolean isFinish = false;
	
	@Override
	protected int onLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.activity_template_footer;
	}

	@Override
	protected void onInitView() {
		// TODO Auto-generated method stub
		mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
	}

	@Override
	protected void onInitData() {
		// TODO Auto-generated method stub
		mTabList = getTabs();
		if (mTabList != null){
			mIndicator = new Indicator(this, mTabList);
			mIndicatorViewPager = new IndicatorViewPager(mIndicator, mViewPager);
			
		}

		List<Fragment> fragments = getFragments();  

		if (fragments != null){
			
			// 设置viewpager保留界面不重新加载的页面数量
			mViewPager.setOffscreenPageLimit(fragments.size());

			ViewPagerFragmentPagerAdapter viewPagerFragmentPagerAdapter = new ViewPagerFragmentPagerAdapter(getSupportFragmentManager(), fragments);
			mViewPager.setAdapter(viewPagerFragmentPagerAdapter);
		}
		
		mIndicatorViewPager.setOnIndicatorPageChangeListener(new OnIndicatorPageChangeListener() {

			@Override
			public void onIndicatorPageChangeListener(int position, String name) {
				// TODO Auto-generated method stub
				onPageChangeListener(position, name);
			}
		});
		
		switchFragment(0);
	}

	protected void switchFragment(int position){
		mIndicator.selectTab(position);
		mViewPager.setCurrentItem(position);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // 返回键
			if (isFinish){
				exit();
			} else {
				isFinish = true;
				BaseUtils.showShortToast(this, getString(R.string.tip_once_again_exit_app));
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						isFinish = false;
					}
				}, 2000);
			}
			
		}
		return false;
	}

	protected abstract List<TabModel> getTabs();
	protected abstract List<Fragment> getFragments();
	protected abstract void onPageChangeListener(int position, String name);

}
