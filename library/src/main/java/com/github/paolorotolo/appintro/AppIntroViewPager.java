package com.github.paolorotolo.appintro;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class AppIntroViewPager extends ViewPager {

    private boolean pagingEnabled;
    private boolean nextPagingEnabled;
    private float initialXValue;
    private int lockPage;

    public AppIntroViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        pagingEnabled = true;
        nextPagingEnabled = true;
        lockPage = 0;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (checkPagingState(event)) {
            return false;
        }

        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (checkPagingState(event)) {
            return false;
        }

        return super.onTouchEvent(event);
    }

    private boolean checkPagingState(MotionEvent event) {
        if (!pagingEnabled) {
            return true;
        }

        if (!nextPagingEnabled) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                initialXValue = event.getX();
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                if (detectSwipeToRight(event)) {
                    return true;
                }
            }
        }
        return false;
    }

    // To enable/disable swipe
    public void setNextPagingEnabled(boolean nextPagingEnabled) {
        this.nextPagingEnabled = nextPagingEnabled;
        if (!nextPagingEnabled) {
            lockPage = getCurrentItem();
        }
    }

    public boolean isNextPagingEnabled() {
        return nextPagingEnabled;
    }

    public boolean isPagingEnabled() {
        return pagingEnabled;
    }

    public void setPagingEnabled(boolean pagingEnabled) {
        this.pagingEnabled = pagingEnabled;
    }

    public int getLockPage() {
        return lockPage;
    }

    public void setLockPage(int lockPage) {
        this.lockPage = lockPage;
    }

    // Detects the direction of swipe. Right or left.
    // Returns true if swipe is in right direction
    private boolean detectSwipeToRight(MotionEvent event) {
        final int SWIPE_THRESHOLD = 0; // detect swipe
        boolean result = false;

        try {
            float diffX = event.getX() - initialXValue;
            if (Math.abs(diffX) > SWIPE_THRESHOLD) {
                if (diffX < 0) {
                    // swipe from right to left detected ie.SwipeLeft
                    result = true;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }
}