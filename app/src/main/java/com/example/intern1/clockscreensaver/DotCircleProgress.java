package com.example.intern1.clockscreensaver;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eyalgofer on 6/20/17.
 */
public class DotCircleProgress extends View {



    private int mSubCurProgress;// sub progress

    private final int POINT_RADIUS = 16;
    private final int POINT_WIDTH = 32;
    private int mCircleRadius;// radius of the circle
    public int mCircleX;// the center_X of the circle
    public int mCircleY;// the center_Y of the circle
    public int mAngle;// angle of the progress

    private List<Point> mPointList;

    private Paint dotPaintOff;
    private Paint dotPaintOn;

    public DotCircleProgress(Context paramContext) {
        this(paramContext, null);
    }

    public DotCircleProgress(Context paramContext,
                             AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initParams();
        initDotList();
        initColors();
    }

    /**
     * init default param
     */
    private void initParams() {
        this.mSubCurProgress = 0;
        this.mCircleRadius = (int)getResources().getDimension(R.dimen.clock_view_width) / 2 - POINT_WIDTH;
        this.mCircleX = (int)getResources().getDimension(R.dimen.clock_view_width) / 2 - POINT_WIDTH;
        this.mCircleY = (int)getResources().getDimension(R.dimen.clock_view_width) / 2 - POINT_WIDTH;
    }

    private void initColors(){
        dotPaintOff = new Paint();
        dotPaintOff.setAntiAlias(true);
        dotPaintOff.setStyle(Paint.Style.FILL);
        dotPaintOff.setColor(this.getContext().getResources().getColor(R.color.dot_normal));

        dotPaintOn = new Paint();
        dotPaintOn.setAntiAlias(true);
        dotPaintOn.setStyle(Paint.Style.FILL);
        dotPaintOn.setColor(this.getContext().getResources().getColor(R.color.dot_highlighted));
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int i = 0;
        for (Point point : mPointList){
            canvas.drawCircle(point.x,point.y,POINT_RADIUS,mSubCurProgress > i | mSubCurProgress == 0 ?
                    dotPaintOn : dotPaintOff);
            i++;
        }
    }

    /**
     * set the progress
     *
     * @param progress
     *            [0,60]
     */
    public void setProgress(int progress) {
        this.mSubCurProgress = progress;
        invalidate();
    }

    public void initDotList(){
        mPointList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            mAngle = (int) (360.0F * i / 60);
            float radian = (float) ((mAngle - 90) * Math.PI / 180);// radian of the angle

            // new X value for the dot
            int newX = (int) (Math.cos(radian) * mCircleRadius  + mCircleX)
                    + POINT_WIDTH;
            // new Y value for the dot
            int newY = (int) (Math.sin(radian) * mCircleRadius + mCircleY)
                    + POINT_WIDTH;
            // add the new point to points list
            mPointList.add(new Point(newX,newY));
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // remember subtract the width of the dot
        mCircleX = (right - left) / 2 - POINT_WIDTH;
        mCircleY = (bottom - top) / 2 - POINT_WIDTH;
        mCircleRadius = (right - left) / 2 - POINT_WIDTH;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Log.i(TAG, "onSizeChanged");
        mCircleX = w / 2 - POINT_WIDTH;
        mCircleY = h / 2 - POINT_WIDTH;
        mCircleRadius = w / 2 - POINT_WIDTH;
        setProgress(mSubCurProgress);
    }
}