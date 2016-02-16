package com.example.tdk10.toolbar.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by tdk10 on 2/14/2016.
 */
public class ScrollingBehavior extends CoordinatorLayout.Behavior {

    private static final String TAG = "ScrollingBehavior";

    public ScrollingBehavior() {
    }

    public ScrollingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child,
                                          View dependency) {
//        Log.d(TAG, "dependent view changed");
        updateOffset(parent, child, dependency);
        Log.d(TAG, "offset scroll view to "+child.getTop());
        return false;
    }

    private boolean updateOffset(CoordinatorLayout parent, View child, View dependency) {
        final CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) dependency.getLayoutParams()).getBehavior();
        if (behavior instanceof Behavior2) {
            // Offset the child so that it is below the app-bar (with any overlap)
            final int offset = ((Behavior2) behavior).getScrollingContentOffset();
            // todo move the scrolling content this much
//            ViewCompat.offsetTopAndBottom(child, offset);
            child.setTop(offset);
            child.setBottom(parent.getBottom());
//            child.setTop(offset);
            return true;
        }
        return false;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        // First lay out the child as normal
        parent.onLayoutChild(child,layoutDirection);

        // Now offset us correctly to be in the correct position. This is important for things
        // like activity transitions which rely on accurate positioning after the first layout.
        final List<View> dependencies = parent.getDependencies(child);
        for (int i = 0, z = dependencies.size(); i < z; i++) {
            if (updateOffset(parent, child, dependencies.get(i))) {
                // If we updated the offset, break out of the loop now
                break;
            }
        }
        return true;
    }
}
