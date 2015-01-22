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


public abstract class DefaultPopUpDialog  {

    protected Context mContext;
    PopUpMode mMode;
    private Dialog mDialog;
    public static enum PopUpMode{
        STARTFROMBOTTOM(0),
        STARTFROMMIDDLE(1);
        private int mMode;
        PopUpMode(int mode){
            this.mMode = mode;
        }
    }

    public DefaultPopUpDialog(Context context,PopUpMode mode) {
        int style = mode==PopUpMode.STARTFROMBOTTOM?R.style.MyDialogStyleBottom:-1;
        mContext = context;
        mMode = mode;

        mDialog = new Dialog(context,style);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        doMything(mDialog);
    }

    /**
     * 做监听事件等动作
     */
    protected abstract void doMything(Dialog mDialog);
    public void show()
    {
        if(mDialog==null)
        {
            throw new NullPointerException("mDialog is NUll");
        }
        mDialog.show();
    }


//    public boolean onTouchEvent(MotionEvent event) {
//        if (.shouldCloseOnTouch(mContext, event)) {
//            cancel();
//            return true;
//        }
//
//        return false;
//    }

}
