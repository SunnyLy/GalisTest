package com.galis.demo.tag;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.galis.demo.R;

/**
 * Author: galis
 * Date: 2014/11/12 10:40
 * Version: 1.6
 * Describe:每行不定数量的标签列表
 */


public class TagListView extends ViewGroup{


    private final int HORIZONTAL = 10;
    private final int VERTICAL = 10;
    private float density;
    private static final String[] TESTSTRS = new String[]{
            "galis",
            "lsm",
            "ga",
            "gal",
            "gal",
            "gal",
            "galislsmlsmlsmlsm",
            "gal",
            "galislsmlsmlsdf",
            "gal",
            "galislsmlsmlsmlsm",
            "galislsmlsmlsmlsm",
            "galislsmlsmlsmlsmsljflskjdflksjlkdfjslkdjfsldjfslkjfdlsjdflksjlkfdjslkdjflkj",
            "galislsmlsmlsmlsm",
            "galislsmlsmlsmlsm",
    } ;


    public TagListView(Context context) {

        super(context);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);

        density = dm.density;
        for (int i = 0; i < TESTSTRS.length; i++) {
            TextView tView = new TextView(getContext());
            tView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
            tView.setText(TESTSTRS[i]);
            tView.setTextSize(15);
            tView.setTextColor(Color.BLACK);
            tView.setPadding(30, 30, 30, 30);
            tView.setBackgroundResource(R.drawable.btn_shop_style_black);
            addView(tView);
        }
    }

    public TagListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int widthSpec = MeasureSpec.makeMeasureSpec((int) (width-2*HORIZONTAL*density), MeasureSpec.AT_MOST);
        for (int i = 0; i < getChildCount(); i++) {
            TextView childView = (TextView) getChildAt(i);
            childView.measure(widthSpec,0);//测量他的大小？
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int parentWidth = getMeasuredWidth();
        float hSpacing = HORIZONTAL*density;
        float vSpacing = VERTICAL*density;
        int lineHeight = 0;
        int totalHeight = 0;
        int curWidth = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            int width = childView.getMeasuredWidth();
            int height = childView.getMeasuredHeight();
            if(curWidth+width+hSpacing>parentWidth)
            {
                curWidth = 0;
                totalHeight+= lineHeight+vSpacing;
            }
            childView.layout((int) (curWidth+hSpacing), (int) (totalHeight+vSpacing), (int) (curWidth+width+hSpacing), (int) (totalHeight+height+vSpacing));
            curWidth+=width+hSpacing;
            lineHeight = Math.max(height,lineHeight);
        }
    }



}
