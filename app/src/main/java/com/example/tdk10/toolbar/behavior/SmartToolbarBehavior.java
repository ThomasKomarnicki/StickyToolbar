package com.example.tdk10.toolbar.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by tdk10 on 2/12/2016.
 */
public class SmartToolbarBehavior extends CoordinatorLayout.Behavior<AppBarLayout> {

    private static final String TAG = "ToolbarBehavior";

    private boolean firstRunInit = true;
    private boolean correctingHideScroll = false;

    public SmartToolbarBehavior() {}

    public SmartToolbarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, AppBarLayout child, int layoutDirection) {
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, AppBarLayout child, View dependency) {
        if(dependency instanceof RecyclerView){
            return true;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, AppBarLayout child, View dependency) {
        if(dependency instanceof RecyclerView){
            if(firstRunInit) {
                firstRunInit = false;
//                dependency.setTranslationY(child.getHeight());
                dependency.setPadding(0,child.getHeight(),0,0);
//                ViewCompat.offsetTopAndBottom(dependency, child.getHeight());
                dependency.scrollTo(0,0);
                ((RecyclerView) dependency).scrollToPosition(0);
//                ViewCompat.offsetTopAndBottom(dependency,child.getHeight());
                return true;
            }
        }
        return super.onDependentViewChanged(parent, child, dependency);
    }

    float startTranslation = 0;

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        Log.d(TAG, "on StartNestedScroll");
        startTranslation = child.getTranslationY();
        return true;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
//        Log.d(TAG, "on nestedScroll");

//        if(!isHiding){
//            target.scrollBy(0,-dyUnconsumed);
//            isHiding = false;
//        }

        Log.d(TAG, "nested scroll: dyConsumed = "+dyConsumed +", dyUnconsumed = "+dyUnconsumed);
        if(dyConsumed >= 100){
            correctingHideScroll = true;
            target.scrollBy(0,-dyConsumed);
        }

    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target) {

    }

    private int lastDy = -1;
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed) {
        Log.d(TAG, "NestedPreScroll = " + dy);
        if(dy > 0){
            if(child.getTranslationY() <= -child.getHeight()) {
                // don't consume scroll
                consumed[1] = 0;

            }else{
                // consume scroll and move child and target

                float newTranslation = Math.max(startTranslation-dy, -child.getHeight());
                child.setTranslationY(newTranslation);
//                target.setTranslationY(newTranslation);
                int newPadding = (int) (child.getHeight() + newTranslation);
                target.setPadding(0, newPadding,0,0);
//                target.setTop(newPadding);
//                Log.d(TAG, "setting padding to "+ newPadding);

                consumed[1] = Math.min(dy,child.getHeight());
//                if(lastDy != -1){
//                    if(lastDy > dy){
//                        consumed[1] = lastDy - dy;
//                    }
//                }

                // if lastDy > dy : do not consume the difference?
//                if(lastDy > dy){
//                    Log.d(TAG, "changing consumed amount from "+consumed[1] + " to "+(consumed[1] - (lastDy- dy)));
//                    consumed[1] = consumed[1] - (lastDy - dy);
//
//                }


//                consumed[1] = dy;
                lastDy = dy;
            }
        }else{
            // scrolling down, toolbar should be being shown


        }

        if(correctingHideScroll){
            correctingHideScroll = false;
        }

        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }
}
