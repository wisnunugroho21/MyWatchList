package com.example.android.moviedb3.behaviour;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.android.moviedb3.eventHandler.OnAppBarToolbarTransparentStateChangedListener;

/**
 * Created by nugroho on 17/09/17.
 */

public class BackdropImageBehaviour extends CoordinatorLayout.Behavior<ImageView>
{
    OnAppBarToolbarTransparentStateChangedListener onAppBarToolbarTransparentStateChangedListener;
    int toolbarPosition;

    public BackdropImageBehaviour(OnAppBarToolbarTransparentStateChangedListener onAppBarToolbarTransparentStateChangedListener, Toolbar toolbar)
    {
        this.onAppBarToolbarTransparentStateChangedListener = onAppBarToolbarTransparentStateChangedListener;
        toolbarPosition = toolbar.getBottom();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, ImageView child, View directTargetChild, View target, int nestedScrollAxes)
    {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, ImageView child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed)
    {
        int childBottom = child.getBottom();
        if(toolbarPosition == childBottom)
        {
            if (dyConsumed > 0) {
                slideDown();
            } else if (dyConsumed < 0) {
                slideUp();
            }
        }
    }

    private void slideUp()
    {
        onAppBarToolbarTransparentStateChangedListener.OnAppBarToolbarTransparentStateChanged(true);
    }

    private void slideDown()
    {
        onAppBarToolbarTransparentStateChangedListener.OnAppBarToolbarTransparentStateChanged(false);
    }
}
