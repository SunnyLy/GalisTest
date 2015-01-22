package com.galis.demo.draw;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.galis.demo.R;
import com.galis.demo.utils.AppUtil;

/**
 * Author: galis
 * Date: 2014/10/27 10:24
 * Version: 1.7
 * Describe:倒计时组件
 */


public class DefaultCalTimer extends LinearLayout {

    private static final int INTERVAL = 10;
    private static final int M_INTERVAL= 15;
//    private static final int INTERVAL = 10;
//    private static final int M_INTERVAL= 15;
    private TextView hourView;
    private TextView minView;
    private TextView secView;
    private TextView mSecView;
    private CalThread mThread;

    public void setRemainTime(int remainTime,boolean canRun) {

        if(remainTime<=0)
        {
            if(mThread!=null&&mThread.isAlive())
            {
                mThread.setCanRun(false);
            }
            return;
        }

        if(mThread!=null&&mThread.isAlive())
        {
            mThread.setRemainTimes(remainTime);
            mThread.setCanRun(canRun);
            return;
        }
        mThread= new CalThread(new Runnable() {
            @Override
            public void run() {
                int time = mThread.getRemainTimes()*10;
                while (time>=0&&mThread.isCanRun())
                {
                    try {
                        handler.sendEmptyMessage(time);
                        time--;
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(0);

            }
        },remainTime,canRun);
        mThread.start();
    }



    public DefaultCalTimer(Context context) {
        super(context);
        init();
    }


    private void init(){
        setOrientation(HORIZONTAL);
        initView();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setCurTime(msg.what);
        }
    };
    private void initView(){
        addIconView();
        hourView=addNumTimeView();
        addDivideView("时");
        minView = addNumTimeView();
        addDivideView("分");
        secView = addNumTimeView();
        addDivideView("秒");
        mSecView = addNumTimeView();
    }

    private void addIconView()
    {
        ImageView igView = new ImageView(getContext());
        LinearLayout.LayoutParams igLp = new  LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        igLp.setMargins(0,0,M_INTERVAL,0);
        igView.setLayoutParams(igLp);
        igView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        igView.setImageResource(R.drawable.icon_countdown);
        addView(igView);
    }

    private TextView addNumTimeView()
    {
        TextView tView = new AdapterTextView(getContext());
        addView(tView);
        return tView;
    }

    private void addDivideView(String divideStr)
    {
        TextView tView = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        lp.setMargins(M_INTERVAL,0,M_INTERVAL,0);
        tView.setLayoutParams(lp);
        tView.setTextSize(10);
        tView.setTextColor(Color.GRAY);
        tView.setText(divideStr);
        addView(tView);
    }

    private void setCurTime(int time)
    {
        int mSecond = time%10;
        int remainTime = time /10;
        int second = remainTime % 60;
        int minute = remainTime/60%60;
        int hour = remainTime/60/60;
        hourView.setText(getTimeValue(hour));
        minView.setText(getTimeValue(minute));
        secView.setText(getTimeValue(second));
        mSecView.setText(mSecond+"");

    }

    private String getTimeValue(int t)
    {
        String value ;
        if(t==0)
        {
            value="00";
        }
        else if(t<10)
        {
            value="0"+t;
        }
        else
        {
            value = t+"";
        }
        return value;
    }

    class AdapterTextView extends TextView {

        private final int TEXTSIZE = 12;
        private float textSize;
        AdapterTextView(Context context) {
            super(context);
            setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            setPadding(0,0,0,0);
            setTextColor(Color.parseColor("#ef6f53"));
//            textSize = TypedValue.applyDimension(
//                    TypedValue.COMPLEX_UNIT_SP, TEXTSIZE, Resources.getSystem().getDisplayMetrics());
            setTextSize(TypedValue.COMPLEX_UNIT_PX, AppUtil.dip2px(getContext(),12));
            setGravity(Gravity.CENTER_HORIZONTAL);
            setBackgroundColor(Color.parseColor("#ffffff"));
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            int height = MeasureSpec.getSize(heightMeasureSpec);
            int length = getText().toString().length();
            setMeasuredDimension(AppUtil.dip2px(getContext(),12)*length-10,AppUtil.dip2px(getContext(),50));
        }


    }


    class CalThread extends Thread {
        private int remainTimes;
        private boolean canRun;

        public boolean isCanRun() {
            return canRun;
        }

        public void setCanRun(boolean canRun) {
            this.canRun = canRun;
        }


        public int getRemainTimes() {
            return remainTimes;
        }

        public void setRemainTimes(int remainTimes) {
            this.remainTimes = remainTimes;
        }

        public CalThread(Runnable runnable,int  time,boolean run)
        {
            super(runnable);
            this.remainTimes = time;
            this.canRun = run;

        }

    }
}
