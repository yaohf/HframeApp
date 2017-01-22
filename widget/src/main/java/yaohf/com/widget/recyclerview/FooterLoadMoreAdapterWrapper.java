package yaohf.com.widget.recyclerview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import yaohf.com.widget.R;

/**
 * Created by viqgd on 2017/1/20.
 */

public class FooterLoadMoreAdapterWrapper extends HeaderAndFooterAdapterWrapper {

    public static final int PULL_TO_LOAD_MORE = 0;
    public static final int LOADING = 1;
    public static final int NO_MORE = 2;
    private int mFooterStatus = PULL_TO_LOAD_MORE;
    private String toLoadText = "上拉加载更多...";
    private String noMoreText = "没有更多了！";
    private String loadingText = "正在拼命加载...";

    public FooterLoadMoreAdapterWrapper(RecyclerAdapter adapter) {
        super(adapter);
    }

    /**
     * 滑动至item 底部时Footer 的View 变化
     * @param holder
     * @param position
     */
    @Override
    protected void onBindFooter(RecyclerViewHolder holder, int position) {
        ProgressBar progressBar = holder.findViewById(R.id.progressBar);
        switch (mFooterStatus)
        {
            case PULL_TO_LOAD_MORE:
                progressBar.setVisibility(View.VISIBLE);
                holder.setText(R.id.tv_footer,toLoadText);
                break;
            case LOADING:
                progressBar.setVisibility(View.VISIBLE);
                holder.setText(R.id.tv_footer,loadingText);
                break;
            case NO_MORE:
                progressBar.setVisibility(View.INVISIBLE);
                holder.setText(R.id.tv_footer,noMoreText);
                break;
        }
    }
    public void setOnReachFooterListener(RecyclerView recyclerView,final OnReachFooterListener listener )
    {
        if(recyclerView == null || listener == null)
            return;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(isReachBottom(recyclerView,newState) && mFooterStatus != LOADING)
                {
                    setFooterStatus(LOADING);
                    listener.onReach();
                }
            }
        });
    }

    /**
     * 更改footer 状态
     * @param status
     */
    public void setFooterStatus(int status) {
        mFooterStatus = status;
        notifyDataSetChanged();
    }

    /**
     * 检测是否滑至底部
     * @param recyclerView
     * @param newState
     * @return
     */
    public boolean isReachBottom(RecyclerView recyclerView, int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            return ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition()
                    == recyclerView.getAdapter().getItemCount() - 1;
        }
        return false;
    }



    public interface OnReachFooterListener {
        void onReach();
    }


    public void setToLoadText(String toLoadText) {
        this.toLoadText = toLoadText;
    }

    public void setNoMoreText(String noMoreText) {
        this.noMoreText = noMoreText;
    }

    public void setLoadingText(String loadingText) {
        this.loadingText = loadingText;
    }
}
