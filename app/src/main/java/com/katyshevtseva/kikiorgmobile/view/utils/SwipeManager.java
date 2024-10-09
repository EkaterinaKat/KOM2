package com.katyshevtseva.kikiorgmobile.view.utils;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.katyshevtseva.kikiorgmobile.utils.knobs.NoArgKnob;

public class SwipeManager {
    private final int slop;
    private float downX;
    private float downY;
    private boolean swiping;
    private SwipeDirection direction;
    private NoArgKnob leftSwipeListener;
    private NoArgKnob rightSwipeListener;

    public SwipeManager(Context context) {
        slop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public Boolean dispatchTouchEvent(MotionEvent ev) {
        boolean blockButtonExecution = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                swiping = false;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (swiping) {
                    blockButtonExecution = true;
                    if (direction == SwipeDirection.LEFT && leftSwipeListener != null) {
                        leftSwipeListener.execute();
                    }
                    if (direction == SwipeDirection.RIGHT && rightSwipeListener != null) {
                        rightSwipeListener.execute();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float x = ev.getX();
                float y = ev.getY();
                float xDelta = Math.abs(x - downX);
                float yDelta = Math.abs(y - downY);

                // Обрабатываем свайп по горизонтали
                if (xDelta > slop && xDelta / 2 > yDelta) {
                    swiping = true;
                    direction = x > downX ? SwipeDirection.LEFT : SwipeDirection.RIGHT;
                    return true;
                }
                break;
        }

        if (blockButtonExecution)
            return false;
        return null;
    }

    public void setLeftSwipeListener(NoArgKnob leftSwipeListener) {
        this.leftSwipeListener = leftSwipeListener;
    }

    public void setRightSwipeListener(NoArgKnob rightSwipeListener) {
        this.rightSwipeListener = rightSwipeListener;
    }

    private enum SwipeDirection {
        LEFT, RIGHT
    }
}
