package com.galis.galistest.draw;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.galis.galistest.utils.AppUtil;

/**
 * Author: galis
 * Date: 2014/11/12 14:14
 * Version: 1.6
 * Describe:
 */


public class CalTimeActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DefaultCalTimer calTimer = new  DefaultCalTimer(this);
        calTimer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AppUtil.dip2px(this,50)));
        calTimer.setRemainTime(100000,true);
        setContentView(calTimer);
    }
}
