package com.galis.galistest.draw;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: galis
 * Date: 2015/1/16 10:07
 * Version: 2.0
 * Describe:
 */


public class CanvasDraw extends Activity {

    private int mCurrentY;
    private ArcViewGroup waterGroup;

    @Override
    protected void onCreate(Bundle onSaveInstance) {
        super.onCreate(onSaveInstance);
        waterGroup  = new ArcViewGroup(this);
        setContentView(waterGroup);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mCurrentY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = (int) (event.getY() - mCurrentY);
                if (deltaY > 0) {
                    float f = deltaY > 500 ? 1.0f : deltaY / 500f;
                    waterGroup.drag(f);
                }
                System.out.println(event.getY()+"");
                break;
            case MotionEvent.ACTION_UP:
                waterGroup.dragOut();
                break;
        }
        return true;
    }

    public class SampleView extends View {
        Region.Op[] operatios = new Region.Op[]{
                Region.Op.DIFFERENCE,
                Region.Op.REVERSE_DIFFERENCE,
                Region.Op.REPLACE,
                Region.Op.INTERSECT,
                Region.Op.UNION,
                Region.Op.XOR
        };

        Paint paint;
        Rect rect;
        Rect rect2;
        Rect rect3;

        public SampleView(Context context) {
            super(context);
            setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            setVisibility(VISIBLE);

            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(1);

            rect = new Rect(100, 100, 200, 200);
            rect2 = new Rect(100 + 50, 100 + 50, 200 + 50, 200 + 50);
            rect3 = new Rect();


        }

        public SampleView(Context context, AttributeSet set) {
            super(context, set);
        }

        public SampleView(Context context, AttributeSet set, int defAttr) {
            super(context, set, defAttr);
        }


        /**
         * 不要在Draw这里新建资源
         *
         * @param canvas
         */
        @Override
        public void onDraw(Canvas canvas) {

            canvas.drawColor(Color.WHITE);
            for (int i = 0; i < operatios.length; i++) {
                canvas.save();
                canvas.translate(600 * (i % 2), 300 * (i / 2));
                drawOp(canvas, operatios[i]);
                drawOriginRect(canvas, 6);
                canvas.translate(0, 250);
                drawTag(canvas, operatios[i] + "");
                canvas.restore();
            }


            canvas.save();
            canvas.translate(0, 1000);
            canvas.clipRect(200, 100, 400, 300);
            RectF oval2 = new RectF(200, 100, 400, 500);// 设置个新的长方形，扫描测量
            canvas.drawOval(oval2, paint);
            canvas.restore();

            canvas.save();
            canvas.translate(0, 1000);
            canvas.clipRect(200, 300, 400, 400);
            paint.setColor(Color.BLUE);
            canvas.drawCircle(300, 300, 100, paint);
            canvas.restore();
        }


        /**
         * stroke 和 fill 对于封闭图形和文字
         *
         * @param canvas
         * @param op
         */
        public void drawOp(Canvas canvas, Region.Op op) {
            Region region = new Region();
            region.set(rect);
            region.op(rect2, op);
            RegionIterator regionIterator = new RegionIterator(region);
//            paint.setStrokeWidth(10);
            paint.setColor(0xFFFF00FF);
            paint.setStyle(Paint.Style.FILL);
            while (regionIterator.next(rect3)) {
                canvas.drawRect(rect3, paint);
            }
        }

        public void drawOriginRect(Canvas canvas, int strokeWidth) {
            paint.setStrokeWidth(strokeWidth);
            paint.setStyle(Paint.Style.STROKE);
            int inset = strokeWidth / 2;
            paint.setColor(Color.RED);
            canvas.drawRect(rect.left + inset, rect.top + inset, rect.right - inset, rect.bottom - inset, paint);
            paint.setColor(Color.BLUE);
            canvas.drawRect(rect2.left + inset, rect2.top + inset, rect2.right - inset, rect2.bottom - inset, paint);
        }

        public void drawTag(Canvas canvas, String tag) {
            paint.setStrokeWidth(1);
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            paint.setTextSize(36);
            paint.setColor(Color.RED);
            canvas.drawText(tag, 100, 50, paint);

        }
    }
}
