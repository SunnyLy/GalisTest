package com.galis.demo.Scroll;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Author: galis
 * Date: 2014/12/3 15:41
 * Version: 2.0
 * Describe:
 */


public class ScrollActivity extends Activity implements View.OnClickListener {

    private Scroller mScroller;
    private ScrollView mScrollView;

    private int mCurrentX;
    private int mCurrentY;
    LinearLayout headLayout;
    LinearLayout contentLayout;

    private DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * 布局
         */
        headLayout = new LinearLayout(this);
        headLayout.setOrientation(LinearLayout.VERTICAL);
        headLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        Button button = new Button(this);
        button.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        button.setText("动起来");
        button.setOnClickListener(this);
        headLayout.addView(button);

        mScrollView = new ScrollView(this);
        mScrollView.setLayoutParams(new ViewGroup.LayoutParams(50, 50));
        mScrollView.setBackgroundColor(Color.RED);
        contentLayout = new LinearLayout(this);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        contentLayout.addView(mScrollView);
        headLayout.addView(contentLayout);

        drawView = new DrawView(this);
        drawView.setLayoutParams(new LinearLayout.LayoutParams(-1, 500));
        drawView.setBackgroundColor(Color.GREEN);
        headLayout.addView(drawView);

        setContentView(headLayout);
        mCurrentX = 0;

    }

    @Override
    public void onClick(View v) {
        if (mScroller == null) {
            mScroller = new Scroller(this);
        }
        mCurrentY = mScrollView.getTop();
            System.out.println(mCurrentX + "@" + mCurrentY);
        mScroller.startScroll(mScroller.getCurrX(), mCurrentY, -500, 0, 1000);
        mScrollView.invalidate();
        mScrollView.requestLayout();
    }

    class ScrollView extends View {
        public ScrollView(Context context) {
            super(context);
        }

        @Override
        public void computeScroll() {
            super.computeScroll();
            if (mScroller != null && mScroller.computeScrollOffset()) {
                int x = mScroller.getCurrX();
                int y = mScroller.getCurrY();
                drawView.setPoint(x, y);
                contentLayout.scrollTo(x, 0);
                invalidate();
                System.out.println(x+"@"+y);
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
                System.out.println("onDraw");
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                System.out.println("onMeasure");
        }
    }

    class DrawView extends View{
        float oldX;
        float oldY;
        float curX;
        float curY;

        public void setPoint(float x,float y){
            oldX = curX;
            oldY = curY;
            curX = x;
            curY = y;
            invalidate();
        }

        public DrawView(Context context) {
            super(context);
            oldX = 0;
            oldY = 500;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(50);

            System.out.println(oldX+"T"+curX);
            canvas.drawLine(-oldX,oldY+500,-curX,curY+500,paint);
        }
    }




}
