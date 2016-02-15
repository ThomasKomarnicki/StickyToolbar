package com.example.tdk10.toolbar.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by tdk10 on 2/14/2016.
 */
public class Behavior2 extends CoordinatorLayout.Behavior<AppBarLayout> {

    private static final String TAG = "Behavior2";

    private int scrollingContentOffset = 196;
    private boolean dirty;

    public Behavior2() {
    }

    public Behavior2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public int getScrollingContentOffset(){
        return scrollingContentOffset;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

//        Log.d(TAG, "on scroll");
        scrollingContentOffset += dyUnconsumed + dyConsumed;
        dirty = true;
        coordinatorLayout.dispatchDependentViewsChanged(child);


    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {

        return true;
    }

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
