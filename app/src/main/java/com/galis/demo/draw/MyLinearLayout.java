package com.galis.demo.draw;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Author: galis
 * Date: 2014/10/21 12:09
 * Version: 1.6
 * Describe:
 */


public class MyLinearLayout extends ViewGroup{
    public MyLinearLayout(Context context) {
        super(context);
        addChildView(1);
        addChildView(2);
        addChildView(3);
    }


    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        //获取该ViewGroup的实际长和宽  涉及到MeasureSpec类的使用
        int specSize_Widht = MeasureSpec.getSize(widthMeasureSpec);
        int specSize_Widht_mode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize_Heigth = MeasureSpec.getSize(heightMeasureSpec);
        int specSize_Heigth_mode = MeasureSpec.getMode(heightMeasureSpec);

        Log.i("sp", "**** specSize_Widht " + specSize_Widht + " * specSize_Heigth   *****" + specSize_Heigth);
        Log.i("sp", "**** specSize_Widht_mode " + specSize_Widht_mode+ " * specSize_Heigth_mode   *****" + specSize_Heigth_mode);

        //设置本ViewGroup的宽高
        setMeasuredDimension(specSize_Widht,200);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(50, 50) ;   //简单的设置每个子View对象的宽高为 50px , 50px
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(0, i *child.getMeasuredHeight(),child.getMeasuredWidth(),(i+1)*child.getMeasuredHeight());
        }
    }

    private void addChildView(int id)
    {
        TextView textView = new TextView(getContext());
//        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        textView.setId(id);
        textView.setText(id+"");
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(12);
        addView(textView);
    }


}
