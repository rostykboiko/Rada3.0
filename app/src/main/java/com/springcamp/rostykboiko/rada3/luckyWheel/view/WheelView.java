package com.springcamp.rostykboiko.rada3.luckyWheel.view;


import android.animation.Animator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.List;

public class WheelView extends View {
    private final int DEFAULT_PADDING = 5, DEFAULT_ROTATION_TIME = 9000;

    private RectF range = new RectF();
    private Paint archPaint;
    private int padding, radius, center, mWheelBackground;
    private List<WheelItem> mWheelItems;
    private OnLuckyWheelReachTheTarget mOnLuckyWheelReachTheTarget;

    public WheelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initComponents() {
        //arc paint object
        archPaint = new Paint();
        archPaint.setAntiAlias(true);
        archPaint.setDither(true);
        //rect rang of the arc
        range = new RectF(padding, padding, padding + radius, padding + radius);
    }

    /**
     * Get the angele of the target
     *
     * @return Number of angle
     */
    private float getAngleOfIndexTarget(int target) {
        return (360 / mWheelItems.size()) * target;
    }

    /**
     * Function to set wheel background
     *
     * @param wheelBackground Wheel background color
     */
    public void setWheelBackgoundWheel(int wheelBackground) {
        mWheelBackground = wheelBackground;
        invalidate();
    }

    /**
     * Function to set wheel listener
     *
     * @param onLuckyWheelReachTheTarget target reach listener
     */
    public void setWheelListener(OnLuckyWheelReachTheTarget onLuckyWheelReachTheTarget) {
        mOnLuckyWheelReachTheTarget = onLuckyWheelReachTheTarget;
    }

    /**
     * Function to add wheels items
     *
     * @param wheelItems Wheels model item
     */
    public void addWheelItems(List<WheelItem> wheelItems) {
        mWheelItems = wheelItems;
        invalidate();
    }

    /**
     * Function to draw wheel background
     *
     * @param canvas Canvas of draw
     */
    private void drawWheelBackground(Canvas canvas) {
        Paint backgroundPainter = new Paint();
        backgroundPainter.setAntiAlias(true);
        backgroundPainter.setDither(true);
        backgroundPainter.setColor(mWheelBackground);
        canvas.drawCircle(center, center, center, backgroundPainter);
    }



    /**
     * Function to rotate wheel to target
     *
     * @param target target number
     */
    public void rotateWheelToTarget(int target) {

        float wheelItemCenter = 270 - getAngleOfIndexTarget(target) + (360 / mWheelItems.size()) / 2;
        animate().setInterpolator(new DecelerateInterpolator())
                .setDuration(DEFAULT_ROTATION_TIME)
                .rotation((360 * 15) + wheelItemCenter)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (mOnLuckyWheelReachTheTarget != null) {
                            mOnLuckyWheelReachTheTarget.onReachTarget();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawWheelBackground(canvas);
        initComponents();

        float tempAngle = 0;
        float sweepAngle = 360 / mWheelItems.size();

        for (int i = 0; i < mWheelItems.size(); i++) {
            archPaint.setColor(mWheelItems.get(i).color);
            canvas.drawArc(range, tempAngle, sweepAngle, true, archPaint);
            tempAngle += sweepAngle;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
        padding = getPaddingLeft() == 0 ? DEFAULT_PADDING : getPaddingLeft();
        radius = width - padding * 2;
        center = width / 2;
        setMeasuredDimension(width, width);

    }
}