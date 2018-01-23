package com.example.xushiyun.pathmeasuredemo.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by xushiyun on 2018/1/23.
 * Project Name: PathMeasureDemo
 * Package Name: com.example.xushiyun.pathmeasuredemo.views
 * File Name:    PathTracingView
 * Descripetion: Todo
 */

public class PathPaintView extends View {

    private Path mPath;
    private Paint mPaint;
    private float mLength;

    private float mAnivalue;
    private PathEffect mEffect;//设置风格,路径风格


    private PathMeasure mPathMeasure;

    public PathPaintView(Context context) {
        super(context);
    }

    public PathPaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mPath = new Path();

        mPath.lineTo(100,100);
        mPath.lineTo(100,500);
        mPath.lineTo(400,300);
        mPath.close();

        mPathMeasure = new PathMeasure();
        //进行path和pathMeasure相关联
        mPathMeasure.setPath(mPath, true);

        mLength = mPathMeasure.getLength();


        final ValueAnimator animator = ValueAnimator.ofFloat(1, 0);
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnivalue = (float) animator.getAnimatedValue();
                mEffect = new DashPathEffect(new float[]{mLength, mLength}, mLength*mAnivalue);
                mPaint.setPathEffect(mEffect);
                invalidate();
            }
        });
        animator.start();
    }

    public PathPaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mPath, mPaint);
    }
}
