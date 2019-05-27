package com.bjjy.buildtalk.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class NoScrollGridView extends GridView {

    private OnTouchInvalidPositionListener mTouchInvalidPosListener;

    public NoScrollGridView(Context context) {
        super(context);
    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mTouchInvalidPosListener != null) {
            /*返回一个int值，判断触摸后坐标能否映射到一个item上*/
            int motionPosition = pointToPosition((int) event.getX(), (int) event.getY());
            /*映射不到item上（即触摸空白区域）并且是抬起动作，则执行回调操作，并且自己消费，不往父布局传递*/
            if (motionPosition == INVALID_POSITION && event.getAction() == MotionEvent.ACTION_UP) {
                mTouchInvalidPosListener.onTouchInvalidPosition();
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    public void setOnTouchInvalidPositionListener(OnTouchInvalidPositionListener listener) {
        mTouchInvalidPosListener = listener;
    }

    public interface OnTouchInvalidPositionListener {
        void onTouchInvalidPosition();
    }
}  