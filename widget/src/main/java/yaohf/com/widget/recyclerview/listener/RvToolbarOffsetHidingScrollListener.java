package yaohf.com.widget.recyclerview.listener;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import yaohf.com.tool.L;
import yaohf.com.widget.recyclerview.Utils;


public  class RvToolbarOffsetHidingScrollListener extends RecyclerView.OnScrollListener {

    private final Toolbar mToolbar;
    private int mToolbarOffset = 0;
    private int mToolbarHeight;
    private boolean mControlsVisible = true;
    private static final float HIDE_THRESHOLD = 10;
    private static final float SHOW_THRESHOLD = 70;
    private int mScrollDistance;

    public RvToolbarOffsetHidingScrollListener(Context context, @NonNull Toolbar toolbar) {
        mToolbarHeight = Utils.getToolbarHeight(context);
        mToolbar = toolbar;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        clipToolbarOffset();
        onMoved(mToolbarOffset);

        if ((mToolbarOffset < mToolbarHeight && dy > 0) || (mToolbarOffset > 0 && dy < 0)) {
            mToolbarOffset += dy;
        }
        mScrollDistance += dy;
        L.v("onScrolled mScrollDistance>>" + mScrollDistance);
    }

    private boolean isReachTop(RecyclerView recyclerView, int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            return ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition()
                    == 0;
        }
        return false;
    }

    private boolean isReachBottom(RecyclerView recyclerView, int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            return ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition()
                    == recyclerView.getAdapter().getItemCount() - 1;
        }
        return false;
    }

    private void clipToolbarOffset() {
        if (mToolbarOffset > mToolbarHeight) {
            mToolbarOffset = mToolbarHeight;
        } else if (mToolbarOffset < 0) {
            mToolbarOffset = 0;
        }
    }

    private void setVisible() {
        if (mToolbarOffset > 0) {
            onShow();
            mToolbarOffset = 0;
        }
        mControlsVisible = true;
    }

    private void onShow() {
        mToolbar.animate()
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            //防止因toolbar隐藏而在顶部漏出一大片空白
            L.v("onScrollStateChanged mScrollDistance>>" + mScrollDistance + "mToolbarHeight " + mToolbarHeight);
            if (mScrollDistance > mToolbarHeight) {
                //根据mToolbarOffset判断是前进或回滚
                if (mControlsVisible) {
                    if (mToolbarOffset > HIDE_THRESHOLD) {
                        setInvisible();
                    } else {
                        setVisible();
                    }
                } else {
                    if ((mToolbarHeight - mToolbarOffset) > SHOW_THRESHOLD) {
                        setVisible();
                    } else {
                        if (isReachTop(recyclerView, newState) || isReachBottom(recyclerView, newState)) {
                            setVisible();
                        } else setInvisible();
                    }
                }
            } else {
                setVisible();
            }
        }
    }

    private void setInvisible() {
        if (mToolbarOffset < mToolbarHeight) {
            onHide();
            mToolbarOffset = mToolbarHeight;
        }
        mControlsVisible = false;
    }

    private void onHide() {
        mToolbar.animate()
                .translationY(-mToolbar.getHeight())
                .setInterpolator(new AccelerateInterpolator(2))
                .start();
    }

    public void onMoved(int distance) {
        mToolbar.setTranslationY(-distance);
    }
}