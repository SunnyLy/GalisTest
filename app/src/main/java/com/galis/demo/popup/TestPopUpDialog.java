package com.galis.demo.popup;

import android.app.Dialog;
import android.content.Context;

import com.galis.demo.R;

/**
 * Author: galis
 * Date: 2014/10/22 14:14
 * Version: 1.6
 * Describe:
 * <p>规则弹出，背景变暗</p>
 */


public class TestPopUpDialog extends DefaultPopUpDialog{

    public TestPopUpDialog(Context context) {
        super(context,PopUpMode.STARTFROMBOTTOM);
    }

    @Override
    protected void doMything(Dialog mDialog) {

        mDialog.setContentView(R.layout.h_dialog_good_style);
    }
}
