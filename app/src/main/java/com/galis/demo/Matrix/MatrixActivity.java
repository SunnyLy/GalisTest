package com.galis.demo.Matrix;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.galis.demo.R;

/**
 * Author: galis
 * Date: 2014/11/26 11:00
 * Version: 2.0
 * Describe:
 */


public class MatrixActivity extends Activity implements View.OnClickListener {

    private Button mBtPost;
    private Button mBtPre;
    private Button mBtScale;
    private ImageView mIvTarget;
    private RectF mRect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my);
        mBtPost = (Button) findViewById(R.id.post);
        mBtPost.setOnClickListener(this);
        mBtPre = (Button) findViewById(R.id.pre);
        mBtPre.setOnClickListener(this);
        mBtScale = (Button) findViewById(R.id.scale);
        mBtScale.setOnClickListener(this);
        mIvTarget = (ImageView) findViewById(R.id.targetIgView);

        mRect = new RectF(0, 0, mIvTarget.getDrawable().getIntrinsicWidth(), mIvTarget.getDrawable().getIntrinsicHeight());
    }

    private void wasteTime() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.post:
                Matrix postMatrix = mIvTarget.getImageMatrix();
                postMatrix.postRotate(10);
                postMatrix.preRotate(10);
                mIvTarget.setImageMatrix(postMatrix);
                mIvTarget.invalidate();
                break;
            case R.id.pre:
                Matrix preMatrix = new Matrix();
                preMatrix.preRotate(10);
                mIvTarget.setImageMatrix(preMatrix);
                break;
            case R.id.scale:
                Matrix scaleMatrix = new Matrix();
                scaleMatrix.setTranslate(100, 100);
                scaleMatrix.preRotate(10);
                mIvTarget.setImageMatrix(scaleMatrix);
                break;

        }
    }
}
