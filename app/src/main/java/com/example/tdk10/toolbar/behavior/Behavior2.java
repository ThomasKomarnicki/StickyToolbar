package com.example.tdk10.toolbar.behavior;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.animation.ValueAnimatorCompat;
import android.support.v7.widget.ViewUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by tdk10 on 2/14/2016.
 */
public class Behavior2 extends CoordinatorLayout.Behavior<AppBarLayout> {

    private static final String TAG = "Behavior2";

    /**
     * how much the scrolling view's top bound should be offset by, always between parent.getTop()
     * and appBarLayout.getHeight()
     */
    private int scrollingContentOffset;
    private boolean dirty;

    /**
     * animates appBarLayout hidden or close after scroll is released
     */
    private ValueAnimator valueAnimator;

    public Behavior2() {
    }

    public Behavior2(Context context, AttributeSet attrs) {
        super(context, attrs);

    }


    public int getScrollingContentOffset(){
        return scrollingContentOffset;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, AppBarLayout child, int layoutDirection) {
        scrollingContentOffset = child.getHeight();
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);

        /* for setting the scroll view offset*/
        Log.d(TAG, "pre scroll dy "+dy);
        final int scrollingContentBefore = scrollingContentOffset;
        scrollingContentOffset = MathUtils.constrain(scrollingContentOffset - dy,coordinatorLayout.getTop(),child.getHeight());
        consumed[1] = scrollingContentBefore - scrollingContentOffset;

//        dirty = true;
        // if we are consuming the scroll, this the scrolling view is moving and needs to be updated
        if(consumed[1] != 0) {
            coordinatorLayout.dispatchDependentViewsChanged(child);
        }

        /* for positioning the appBar */

//        float appBarTranslation = child.getTranslationY();
//        float newTranslation = MathUtils.constrain(appBarTranslation - dy,-child.getHeight(),coordinatorLayout.getTop());
//        child.setTranslationY(newTranslation);
    }

//    @Override
//    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target) {
//        super.onStopNestedScroll(coordinatorLayout, child, target);
//
//        if(valueAnimator == null){
//            valueAnimator = new ValueAnimator();
//        }
//    }
//
//    @Override
//    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY, boolean consumed) {
//        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
//
//        // todo on fling
//    }

    @Override
    public boolean isDirty(CoordinatorLayout parent, AppBarLayout child) {
        Log.d(TAG, "checking if dirty");
        if(dirty){
            dirty = false;
            return true;
        }
        return false;
    }
}
