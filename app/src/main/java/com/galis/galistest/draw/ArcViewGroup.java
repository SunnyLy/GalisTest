package com.galis.galistest.draw;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;

import com.galis.galistest.R;

/**
 * Author: galis
 * Date: 2015/1/20 16:27
 * Version: 2.0
 * Describe:
 */


public class ArcViewGroup extends FrameLayout{

    private boolean mIsNeedToDrawBg = false;
    private int mConstantDistance_PX;//中心点到贝塞尔曲线的距离
    private int mMaxBerizerHeight_PX;//贝塞尔曲线控制点的高度（这里用的是二分方程式，即一个控制点）
    private int mChildWidth_PX;//贝塞尔曲线控制点的高度（这里用的是二分方程式，即一个控制点）
    private int mCurrentBerizerHeight;
    private int mWidth;
    private int mHeight;
    private boolean mIsRun;

    private Paint mPaint;
    private Path mPath;
    private Point2D[] mPoints2D;
    private ValueAnimator mDragOutAnimator;

    public ArcViewGroup(Context context) {
        this(context, null);
    }

    public ArcViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPath = new Path();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcViewGroup);
        mIsNeedToDrawBg = typedArray.getBoolean(R.styleable.ArcViewGroup_is_need_draw_bg, false);
        mConstantDistance_PX = (int) typedArray.getDimension(R.styleable.ArcViewGroup_constant_distance, 200);
        mMaxBerizerHeight_PX = (int) typedArray.getDimension(R.styleable.ArcViewGroup_berizer_height, 60);
        mChildWidth_PX = (int) typedArray.getDimension(R.styleable.ArcViewGroup_child_width, 60);

        mCurrentBerizerHeight = 0;

        mPoints2D = new Point2D[]{
                new Point2D(0, 0),
                new Point2D(0, 0),
                new Point2D(0, 0)
        };
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        resolvePoints();
        int childCount = getChildCount();
        int margin = (mWidth - childCount * mChildWidth_PX) / (childCount + 1);
        int left, right, top, bottom;
        int centerX;
        for (int i = 0; i < childCount; i++) {
            DropOfWater water = (DropOfWater) getChildAt(i);
            left = mChildWidth_PX * i + margin * (i + 1);
            right = left + mChildWidth_PX;
            centerX = left + mChildWidth_PX / 2;
            top = mConstantDistance_PX + (int) getBerizerCurrentY(centerX / (mWidth*1.0f)) - water.getmLongHeight();
            bottom = top + water.getWaterHeight();
            getChildAt(i).layout(left, top, right, bottom);
        }
    }

    public void drag(float f) {
        if (mIsRun) {
            mDragOutAnimator.cancel();
        }
        mCurrentBerizerHeight = (int) (mMaxBerizerHeight_PX * f);
        for (int i = 0; i < getChildCount(); i++) {
            DropOfWater water = (DropOfWater) getChildAt(i);
            water.drag(f);
        }
    }

    public void dragOut() {
        final int currentBerzieHeight = mCurrentBerizerHeight;
        mDragOutAnimator = ValueAnimator.ofFloat(1, 0);
        mDragOutAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentBerizerHeight = (int) (currentBerzieHeight * (Float) animation.getAnimatedValue());
            }
        });
        mDragOutAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mIsRun = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mIsRun = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mIsRun = false;
            }
        });
        mDragOutAnimator.setDuration(500);
        mDragOutAnimator.setInterpolator(new BounceInterpolator());
        mDragOutAnimator.start();

        for (int i = 0; i < getChildCount(); i++) {
            DropOfWater water = (DropOfWater) getChildAt(i);
            water.dragOut();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mIsNeedToDrawBg){
            mPaint.setColor(Color.WHITE);
        }else {
            mPaint.setAlpha(0);
        }
        mPaint.setStyle(Paint.Style.FILL);
        mPath.reset();
        mPath.moveTo(0, 0);
        mPath.quadTo(mWidth / 2, mCurrentBerizerHeight, mWidth, 0);
        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.lineTo(0, 0);
        canvas.drawPath(mPath, mPaint);
    }

    class Point2D {
        public float x;
        public float y;

        Point2D(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    private float getBerizerCurrentY(float t) {
        float a1 = (float) ((1.0 - t) * (1.0 - t) * mPoints2D[0].y);
        float a2 = (float) (2.0 * t * (1 - t) * mPoints2D[1].y);
        float a3 = t * t * mPoints2D[2].y;
        return a1 + a2 + a3;
    }

    private void resolvePoints() {
        mPoints2D[1].x = mWidth / 2;
        mPoints2D[1].y = mCurrentBerizerHeight;
        mPoints2D[2].x = mWidth;
        mPoints2D[2].y = 0;
    }

}
