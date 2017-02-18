package com.king.android.common.widget.indicator;

/**
 * @author xiao_ming
 *
 */
public class TabModel {

	private String name = null;
	private int drawableId = 0;
	private int tips = 0;
	private boolean state = false;
	private float selectSize, unSelectSize;
	private int selectColor, unSelectColor;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDrawableId() {
		return drawableId;
	}
	public void setDrawableId(int drawableId) {
		this.drawableId = drawableId;
	}
	public int getTips() {
		return tips;
	}
	public void setTips(int tips) {
		this.tips = tips;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public float getSelectSize() {
		return selectSize;
	}
	public void setSelectSize(float selectSize) {
		this.selectSize = selectSize;
	}
	public float getUnSelectSize() {
		return unSelectSize;
	}
	public void setUnSelectSize(float unSelectSize) {
		this.unSelectSize = unSelectSize;
	}
	public int getSelectColor() {
		return selectColor;
	}
	public void setSelectColor(int selectColor) {
		this.selectColor = selectColor;
	}
	public int getUnSelectColor() {
		return unSelectColor;
	}
	public void setUnSelectColor(int unSelectColor) {
		this.unSelectColor = unSelectColor;
	}
	
}
