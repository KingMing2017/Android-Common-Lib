/**
 * Copyright 2015 bingoogolapple
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.king.android.common.widget.badge;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

class BGADragBadgeView extends View {
    private static final String TAG = BGADragBadgeView.class.getSimpleName();
    private BGABadgeViewHelper mBadgeViewHelper;
    private Paint mBadgePaint;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private int mStartX;
    private int mStartY;
    private ValueAnimator mAnimator;

    public BGADragBadgeView(Context context, BGABadgeViewHelper badgeViewHelper) {
        super(context);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mBadgeViewHelper = badgeViewHelper;
        mAnimator = new ValueAnimator();
        initBadgePaint();
        initLayoutParams();
    }

    private void initBadgePaint() {
        mBadgePaint = new Paint();
        mBadgePaint.setAntiAlias(true);
        mBadgePaint.setStyle(Paint.Style.FILL);
        // 设置mBadgeText居中，保证mBadgeText长度�?时，文本也能居中
        mBadgePaint.setTextAlign(Paint.Align.CENTER);
        mBadgePaint.setTextSize(mBadgeViewHelper.getBadgeTextSize());
    }

    private void initLayoutParams() {
        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.gravity = Gravity.LEFT + Gravity.TOP;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.format = PixelFormat.TRANSLUCENT;
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mLayoutParams.width = mWindowManager.getDefaultDisplay().getWidth();
        mLayoutParams.height = mWindowManager.getDefaultDisplay().getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBadgeViewHelper.isShowDrawable()) {
            drawDrawableBadge(canvas);
        } else {
            drawTextBadge(canvas);
        }
    }

    private void drawDrawableBadge(Canvas canvas) {
        canvas.drawBitmap(mBadgeViewHelper.getBitmap(), mStartX, mStartY, mBadgePaint);
    }

    private void drawTextBadge(Canvas canvas) {
        // 设置徽章背景�?        mBadgePaint.setColor(mBadgeViewHelper.getBadgeBgColor());
        // 绘制徽章背景
        canvas.drawRoundRect(new RectF(mStartX, mStartY, mStartX + mBadgeViewHelper.getBadgeRectF().width(), mStartY + mBadgeViewHelper.getBadgeRectF().height()), mBadgeViewHelper.getBadgeRectF().height() / 2, mBadgeViewHelper.getBadgeRectF().height() / 2, mBadgePaint);

        // 设置徽章文本颜色
        mBadgePaint.setColor(mBadgeViewHelper.getBadgeTextColor());
        float x = mStartX + mBadgeViewHelper.getBadgeRectF().width() / 2;
        // 注意：绘制文本时的y是指文本底部，�?不是文本的中�?        
        float y = mStartY + mBadgeViewHelper.getBadgeRectF().height() - mBadgeViewHelper.getBadgePadding();
        // 绘制徽章文本
        String badgeText = mBadgeViewHelper.getBadgeText() == null ? "" : mBadgeViewHelper.getBadgeText();
        canvas.drawText(badgeText, x, y, mBadgePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int badgeWidth = (int) mBadgeViewHelper.getBadgeRectF().width();
        int badgeHeight = (int) mBadgeViewHelper.getBadgeRectF().height();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mAnimator.isRunning()) {
                    mStartX = (int) event.getRawX() - badgeWidth / 2;
                    mStartY = (int) event.getRawY() - badgeHeight / 2 - getStatusBarHeight(getContext());
                    mWindowManager.addView(this, mLayoutParams);
                    postInvalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mAnimator.isRunning()) {
                    int newX = (int) event.getRawX() - badgeWidth / 2;
                    int newY = (int) event.getRawY() - badgeHeight / 2 - getStatusBarHeight(getContext());
                    if (newX < 0) {
                        newX = 0;
                    }
                    if (newY < 0) {
                        newY = 0;
                    }
                    if (newX > mWindowManager.getDefaultDisplay().getWidth() - badgeWidth) {
                        newX = mWindowManager.getDefaultDisplay().getWidth() - badgeWidth;
                    }
                    if (newY > mWindowManager.getDefaultDisplay().getHeight() - badgeHeight) {
                        newY = mWindowManager.getDefaultDisplay().getHeight() - badgeHeight;
                    }
                    mStartX = newX;
                    mStartY = newY;
                    postInvalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!mAnimator.isRunning()) {
                    if (mBadgeViewHelper.satisfyMoveDismissCondition(event)) {
                        startDismissAnim();
                    } else {
                        mWindowManager.removeView(this);
                    }
                }
                break;
        }
        return true;
    }

    private void startDismissAnim() {
        mAnimator.setFloatValues(1.0f, 0.0f);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = Float.parseFloat(animation.getAnimatedValue().toString());
                ViewCompat.setAlpha(BGADragBadgeView.this, alpha);
            }
        });
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (getParent() != null) {
                    mWindowManager.removeView(BGADragBadgeView.this);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        mAnimator.start();
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

}