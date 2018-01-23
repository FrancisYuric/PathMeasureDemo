package com.example.xushiyun.pathmeasuredemo.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
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

public class PathTracingView extends View {

    private Path mDst;
    private Path mPath;
    private Paint mPaint;
    private float mLength;

    private float mAnivalue;

    private PathMeasure mPathMeasure;

    public PathTracingView(Context context) {
        super(context);
    }

    public PathTracingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mPath = new Path();
        mDst = new Path();

        mPath.addCircle(400, 400, 100, Path.Direction.CW);
        mPathMeasure = new PathMeasure();
        //进行path和pathMeasure相关联
        mPathMeasure.setPath(mPath, true);

        mLength = mPathMeasure.getLength();


        final ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnivalue = (float) animator.getAnimatedValue();
                invalidate();
            }
        });
    }

    public PathTracingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDst.reset();
        mDst.lineTo(0,0);//避免硬件加速bug,不使用的话,getSegment会失效

        //实现window常见的加载动画
        float stop = mLength * mAnivalue;
        float start = (float) (stop - ((0.5 - Math.abs(mAnivalue - 0.5)) * mLength));
        mPathMeasure.getSegment(0, stop, mDst, true);
        canvas.drawPath(mDst, mPaint);
    }
}
