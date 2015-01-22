package com.galis.demo.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;


/**
 * 弹出窗
 * Author: galis
 * Date: 2014/10/16 14:09
 * Version: 1.6
 * Describe:
 */


public class DefaultPopupWindow extends PopupWindow {

    private Context mContext;
    private ViewGroup contentView;
    private int mResId;




    private DefaultPopupWindow(Context context) {
        super(context);
    }


    @Override
    public ViewGroup getContentView() {
        return contentView;
    }

    public static DefaultPopupWindow newWindow(Context context,int resId){
        DefaultPopupWindow popupWindow = new DefaultPopupWindow(context);
        popupWindow.mContext = context;
        popupWindow.mResId= resId;
        popupWindow.init();
        return popupWindow;
    }


    public void showFromBottom()
    {
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setAnimationStyle(android.R.style.Animation_Dialog);
        Activity activity = (Activity) mContext;
//        Timer timer = new Timer();
//        timer.
        ViewGroup content = (ViewGroup) activity.findViewById(android.R.id.content);
        View rootView = content.getChildAt(0);
        showAtLocation(rootView, Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL,0,0);
    }



    private void init(){
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        setFocusable(true);
        setOutsideTouchable(true);
        contentView = (ViewGroup) LayoutInflater.from(mContext).inflate(mResId,null);
        setContentView(contentView);
    }
}
