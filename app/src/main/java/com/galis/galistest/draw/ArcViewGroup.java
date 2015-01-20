package com.galis.galistest.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Author: galis
 * Date: 2015/1/20 16:27
 * Version: 2.0
 * Describe:
 */


public class ArcViewGroup extends ViewGroup{

    private int mShort_DP;
    private RectF mArcShader;//弧形遮罩层
    private Paint mPaint;

    public ArcViewGroup(Context context) {
        super(context);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(new DropOfWater(context));
        DropOfWater water2 = new DropOfWater(context);
        water2.setmIsFill(false);
        addView(water2);
        addView(new DropOfWater(context));

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setWillNotDraw(false);
    }

    public ArcViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int screenWith = getMeasuredWidth();
        int childCount = getChildCount();
        DropOfWater water = (DropOfWater) getChildAt(0);
        int childWidth =  water.getLayoutParams().width;
        int margin = (screenWith - childCount*childWidth)/(childCount+1);
        int left,right,top,bottom;
        for(int i=0;i<childCount;i++){
            left = childWidth*i+margin*(i+1);
            right = left + childWidth;
            top = 0;
            bottom = top+ water.getWaterHeight();
            getChildAt(i).layout(left,top,right,bottom);
        }
    }

    public void drag(float f){
        for(int i = 0;i<getChildCount();i++){
            DropOfWater water = (DropOfWater) getChildAt(i);
            water.drag(f);
        }
    }

    public void dragOut(){
        for(int i = 0;i<getChildCount();i++){
            DropOfWater water = (DropOfWater) getChildAt(i);
            water.dragOut();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int screeWidth = getMeasuredWidth();
        mPaint.setColor(Color.BLACK);
//        mPaint.setAlpha(0);
        mPaint.setStyle(Paint.Style.FILL);
        mArcShader = new RectF(0,0,screeWidth,180);
        canvas.save();
        canvas.translate(0,-90);
        canvas.clipRect(0,90,screeWidth,180);
        canvas.drawOval(mArcShader,mPaint);
        canvas.restore();
    }
}
