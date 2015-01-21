package com.galis.galistest.draw;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;

import com.galis.galistest.R;

/**
 * Author: galis
 * Date: 2015/1/20 11:50
 * Version: 2.0
 * Describe:
 */


public class DropOfWater extends View {

    private boolean mIsRun;

    private int mLong_DP;//半长轴
    private int mShort_DP;//半短轴

    public int getmLongHeight() {
        return mLongHeight;
    }

    public void setmLongHeight(int mLongHeight) {
        this.mLongHeight = mLongHeight;
    }

    private int mLongHeight;//当前长轴高度

    private boolean mIsFill;//是否填充
    private int mStokeWidth_PX;//笔画宽度
    private int mBackgroundColor;//图片颜色
    private Paint mPaint;//画笔

    private String mTag;//标签
    private Bitmap mTagBitmap;//标签Icon
    private int mTagTextColor;//Tag颜色
    private int mTagMarginTop_Dp;//标签离Icon的距离
    private int mTagTextSize_SP;//字体大小

    public int getWaterHeight() {
        return mLongHeight +mShort_DP+mTagMarginTop_Dp+mTagTextSize_SP;
    }

    private RectF mOvalRectF;
    private RectF mClipCircleRectF;

    private ValueAnimator mDragOutAnimator;

    public boolean ismIsFill() {
        return mIsFill;
    }

    public void setmIsFill(boolean mIsFill) {
        this.mIsFill = mIsFill;
    }

    public DropOfWater(Context context) {
        this(context, null);
    }

    public DropOfWater(Context context, AttributeSet attrs) {
        super(context, attrs);



        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DropOfWater);

        mLong_DP = (int) typedArray.getDimension(R.styleable.DropOfWater_long_dp, 200f);//半长轴
        mShort_DP = (int) typedArray.getDimension(R.styleable.DropOfWater_short_dp, 100f);//半短轴
        mLongHeight = mShort_DP;

        mOvalRectF = new RectF(0, 0, mShort_DP, mLongHeight);
        mClipCircleRectF = new RectF(0, 0, mShort_DP * 2, mShort_DP);

        mIsFill = typedArray.getBoolean(R.styleable.DropOfWater_fill, true);//是否填充
        mStokeWidth_PX = typedArray.getDimensionPixelSize(R.styleable.DropOfWater_stroke_width,2);

        mBackgroundColor = typedArray.getColor(R.styleable.DropOfWater_background_color, Color.BLUE);//图片颜色

        mTag = typedArray.getString(R.styleable.DropOfWater_tag);//标签
        BitmapDrawable bitmapDrawable = (BitmapDrawable) typedArray.getDrawable(R.styleable.DropOfWater_tag_drawable);//标签Icon
        if (bitmapDrawable != null) {
            mTagBitmap = bitmapDrawable.getBitmap();
        }
        mTagTextColor = typedArray.getColor(R.styleable.DropOfWater_tag_text_color, Color.GRAY);//Tag颜色
        mTagMarginTop_Dp = (int) typedArray.getDimension(R.styleable.DropOfWater_tag_margint_top, 48);//标签离Icon的距离
        mTagTextSize_SP = (int) typedArray.getDimension(R.styleable.DropOfWater_tag_text_size, 36);//字体大小
        mPaint.setTextSize(mTagTextSize_SP);
        mPaint.setTextAlign(Paint.Align.CENTER);
        setLayoutParams(new ViewGroup.LayoutParams(mShort_DP * 2, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(mStokeWidth_PX);
        if(ismIsFill()){
            mPaint.setStyle(Paint.Style.FILL);
        }else{
            mPaint.setStyle(Paint.Style.STROKE);
        }

        int inset = mStokeWidth_PX/2;

        canvas.save();
        mOvalRectF.set(0, 0, mShort_DP * 2, mLongHeight);//clipRect
        canvas.clipRect(mOvalRectF);
        mPaint.setColor(mBackgroundColor);
        mOvalRectF.set(0+inset, 0+inset, mShort_DP * 2-inset, mLongHeight * 2-inset);//drawRect
        canvas.drawOval(mOvalRectF, mPaint);
        canvas.restore();

        if(mTagBitmap!=null){
            canvas.drawBitmap(mTagBitmap,mShort_DP, mLongHeight,mPaint);
        }

        canvas.save();
        canvas.translate(0, mLongHeight);
        canvas.clipRect(mClipCircleRectF);
        mPaint.setColor(mBackgroundColor);
        canvas.drawCircle(mShort_DP, 0, mShort_DP-inset, mPaint);
        canvas.restore();

        //画标签
        mPaint.setStyle(Paint.Style.FILL);
        canvas.save();

        canvas.translate(0, mLongHeight + mShort_DP +mTagMarginTop_Dp+mTagTextSize_SP);

        mPaint.setColor(mTagTextColor);
        if (TextUtils.isEmpty(mTag)) {
            mTag = "test";
        }
        canvas.drawText(mTag, mShort_DP, 0, mPaint);
        canvas.restore();

    }

    /**
     * 放手
     */
    public void dragOut() {
        final int currentHeight = mLongHeight;
        mDragOutAnimator = ValueAnimator.ofFloat(1.0f, 0);
        mDragOutAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLongHeight = (int) (((currentHeight - mShort_DP) * (Float) animation.getAnimatedValue()) + mShort_DP);
                getLayoutParams().height = getWaterHeight();
                requestLayout();
            }
        });
        mDragOutAnimator.addListener(new AnimatorListenerAdapter() {
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

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mIsRun = false;
            }
        });
        mDragOutAnimator.setInterpolator(new BounceInterpolator());
        mDragOutAnimator.setDuration(500);
        mDragOutAnimator.start();
    }

    /**
     * 拉动过程中
     */
    public void drag(float f) {
        if (mIsRun) {
            mDragOutAnimator.cancel();
        }
        mLongHeight = (int) (mShort_DP + (mLong_DP - mShort_DP) * f);
        getLayoutParams().height = getWaterHeight();
        requestLayout();
    }

}
