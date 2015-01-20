package com.galis.galistest.tag;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Author: galis
 * Date: 2014/11/12 10:39
 * Version: 1.6
 * Describe:
 */


public class TagActivity extends Activity {

    private TagListView mTagListView;
    private ListView mListView;
    private TextView mTextView;
    private TextView mDragView;
    private String[] datas = new String[]{"sdfsdf","sdfsdfsdf"};
    private boolean isRun = false;
    private int mHeight;
    private int mEndHeight;
private float mFirstDownY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        Button button = new Button(this);
        button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setText("run/unRun");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRun) {
                    mTextView.startAnimation(new ExpandCollapseAnimation(mTextView, 1000, 0,TagActivity.this));
                } else {
                    mTextView.startAnimation(new ExpandCollapseAnimation(mTextView, 1000, 1,TagActivity.this));
                }
                isRun = !isRun;
            }
        });
        linearLayout.addView(button);
//        mTagListView = new TagListView(this);
//        mTagListView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        linearLayout.addView(mTagListView);
//        mListView = new ListView(this);
//        mListView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        mListView.setAdapter(new ArrayAdapter<String>(TagActivity.this,android.R.layout.simple_list_item_1,datas));
//        linearLayout.addView(mListView);

        mTextView = new TextView(this);
        mTextView.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));
        mTextView.setText("sldfkjsljdflsdjf\nsldjflskjdflksjflsjlkfjslkjf\nsldjfslkjflksjflsjldkjslkjdflsj\n" +
                "sldjfslkjflksjflsjldkjslkjdflsj\n" +
                "sldjfslkjflksjflsjldkjslkjdflsj\n" +
                "sldjfslkjflksjflsjldkjslkjdflsj\n" +
                "sldjfslkjflksjflsjldkjslkjdflsj\n" +
                "sldjfslkjflksjflsjldkjslkjdflsj\n" +
                "sldjfslkjflksjflsjldkjslkjdflsj");
        mTextView.setVisibility(View.GONE);
        mTextView.measure(0, 0);
        mEndHeight = mTextView.getMeasuredHeight();

        linearLayout.addView(mTextView);
        mDragView= new TextView(this);
        mDragView.setLayoutParams(new LinearLayout.LayoutParams(-1,50));
        mDragView.setBackgroundColor(Color.RED);
        mDragView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mFirstDownY = event.getY();
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    float deltaY = event.getY() - mFirstDownY;
                    mTextView.getLayoutParams().height = Math.round(mTextView.getLayoutParams().height + deltaY);
                    mTextView.requestLayout();
                    return true;
                } else {
                    mEndHeight = mTextView.getLayoutParams().height;
                    ExpandCollapseAnimation animation =  new ExpandCollapseAnimation(mTextView, 1000, 1, TagActivity.this);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            mTextView.measure(0, 0);
                            mEndHeight = mTextView.getMeasuredHeight();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    mTextView.startAnimation(animation);
                    return true;
                }
            }
        });
        linearLayout.addView(mDragView);
        setContentView(linearLayout);
    }


    public class ExpandCollapseAnimation extends Animation {
        private View mAnimatedView;

        private int mType;

        /**
         * Initializes expand collapse animation, has two types, collapse (1) and expand (0).
         *
         * @param view     The view to animate
         * @param duration
         * @param type     The type of animation: 0 will expand from gone and 0 size to visible and layout size defined in xml.
         *                 1 will collapse view and set to gone
         */
        public ExpandCollapseAnimation(View view, int duration, int type, Activity activity) {
            setDuration(duration);
            mAnimatedView = view;

//            setHeightForWrapContent(activity, mAnimatedView);

//            mEndHeight = mAnimatedView.getLayoutParams().height;

            mType = type;
            if (mType == 0) {
                mAnimatedView.getLayoutParams().height = 0;
                mAnimatedView.setVisibility(View.VISIBLE);
            }
            setInterpolator(new AccelerateDecelerateInterpolator());
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f) {
                if (mType == 0) {
                    mAnimatedView.getLayoutParams().height = (int) (mEndHeight * interpolatedTime);
                } else {
                    mAnimatedView.getLayoutParams().height = mEndHeight - (int) (mEndHeight * interpolatedTime);
                }
                mAnimatedView.requestLayout();
            } else {
                if (mType == 0) {
                    mAnimatedView.getLayoutParams().height = mEndHeight;
                    mAnimatedView.requestLayout();
                } else {
                    mAnimatedView.getLayoutParams().height = 0;
                    mAnimatedView.setVisibility(View.GONE);
                    mAnimatedView.requestLayout();
                    mAnimatedView.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;     // Return to wrap
                }
            }
        }

        /**
         * This method can be used to calculate the height and set it for views with wrap_content as height.
         * This should be done before ExpandCollapseAnimation is created.
         *
         * @param activity
         * @param view
         */

        public void setHeightForWrapContent(Activity activity, View view) {
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

            int screenWidth = metrics.widthPixels;

            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(screenWidth, View.MeasureSpec.EXACTLY);

            view.measure(widthMeasureSpec, heightMeasureSpec);
            int height = view.getMeasuredHeight();
            view.getLayoutParams().height = height;
        }
    }
}
