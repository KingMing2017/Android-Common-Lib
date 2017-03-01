package com.king.android.common.widget.indicator;

import java.util.List;

import com.king.android.common.R;
import com.king.android.common.widget.indicator.FooterTabAdapter.OnTabTouchListener;

import android.app.Activity;
import android.view.View;
import android.widget.GridView;

/**
 * @author xiao_ming
 *
 */
public class Indicator {

	private Activity mActivity = null;
	private GridView mTabGV = null;
	private FooterTabAdapter mTabAdapter = null;
	private List<TabModel> mTabList = null;
	private int mTabIndex = 0;

	public Indicator(Activity activity, List<TabModel> list){
		this.mActivity = activity;
		this.mTabList = list; 
		this.mTabAdapter = new FooterTabAdapter(activity, list);
		this.mTabAdapter.setOnTabTouchListener(mOnTabTouchListener);
		this.mTabGV = (GridView) mActivity.findViewById(R.id.main_footer_gridview);
		this.mTabGV.setAdapter(mTabAdapter);
	}

	public void notifyDataSetChanged(){
		this.mTabAdapter.notifyDataSetChanged();
	}

	public void selectTab(int posiont){
		clearTabItemState();
		selectTabItem(posiont);
		notifyDataSetChanged();
		
		if (mOnItemSelectedListener != null)
			mOnItemSelectedListener.onItemSelectedListener(posiont);
	}
	
	/**
	 * 当前选中TAB的下标
	 * @return
	 */
	public int getTabIndex(){
		return mTabIndex;
	}
	
	/**
	 * 当前选中TAB的Name
	 * @return
	 */
	public String getTabName(){
		return mTabList.get(mTabIndex).getName();
	}
	
	private void clearTabItemState(){
		for (TabModel tab : mTabList){
			tab.setState(false);
		}
	}

	private void selectTabItem(int position){
		mTabIndex = position;
		mTabList.get(mTabIndex).setState(true);
		mTabAdapter.tab(position);
	}

	// 底栏Footer点击事件监听
	private OnTabTouchListener mOnTabTouchListener = new OnTabTouchListener() {

		@Override
		public void onTabTouchListener(View view, int preTabItem,
				int currentTabItem) {
			// TODO Auto-generated method stub
			if (preTabItem != currentTabItem){
				selectTab(currentTabItem);
			}
//			if (mOnItemSelectedListener != null)
//				mOnItemSelectedListener.onItemSelectedListener(preTabItem, currentTabItem);
		}

	};
	
	private OnItemSelectedListener mOnItemSelectedListener = null;
	public void setOnItemSelectedListener(OnItemSelectedListener listener){
		this.mOnItemSelectedListener = listener;
	}
	public interface OnItemSelectedListener{
		public void onItemSelectedListener(int currentTabItem);
	}
}
