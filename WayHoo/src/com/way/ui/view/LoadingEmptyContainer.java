package com.way.ui.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.way.yahoo.R;

/**
 * This class is the default empty state view for most listviews/fragments
 * It allows the ability to set a main text, a main highlight text and a secondary text
 * By default this container has some strings loaded, but other classes can call the apis to change
 * the text
 */
public class LoadingEmptyContainer extends FrameLayout {
    private static final int LOADING_DELAY = 300;

    private Handler mHandler;
    private Runnable mShowLoadingRunnable;

    public LoadingEmptyContainer(Context context, AttributeSet attrs) {
        super(context, attrs);

        mHandler = new Handler();
        mShowLoadingRunnable = new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.progressbar).setVisibility(View.VISIBLE);
                getNoResultsContainer().setVisibility(View.INVISIBLE);
            }
        };
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        hideAll();
    }

    public void hideAll() {
        findViewById(R.id.progressbar).setVisibility(View.INVISIBLE);
        getNoResultsContainer().setVisibility(View.INVISIBLE);
    }

    public void showLoading() {
        hideAll();

        //if (!mHandler.hasCallbacks(mShowLoadingRunnable)) {
        mHandler.removeCallbacks(mShowLoadingRunnable);
            mHandler.postDelayed(mShowLoadingRunnable, LOADING_DELAY);
        //}
    }

    public void showNoResults() {
        mHandler.removeCallbacks(mShowLoadingRunnable);

        findViewById(R.id.progressbar).setVisibility(View.INVISIBLE);
        getNoResultsContainer().setVisibility(View.VISIBLE);
    }

    public NoResultsContainer getNoResultsContainer() {
        return (NoResultsContainer)findViewById(R.id.no_results_container);
    }
}

