package yaohf.com.widget.recyclerview;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by viqgd on 2017/1/19.
 */

public class HeaderAndFooterAdapterWrapper extends RecyclerAdapter {

    private final RecyclerAdapter mAdapter;
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();
    private SparseArrayCompat<Integer> mHeaderLayoutIds = new SparseArrayCompat<>();
    private SparseArrayCompat<Integer> mFooterLayoutIds = new SparseArrayCompat<>();

    public HeaderAndFooterAdapterWrapper(RecyclerAdapter adapter)
    {
        super(adapter.mContext,adapter.mItems);
        this.mAdapter = adapter;
    }
    //header 总量
    public int getHeadersCount() {
        return mHeaderViews.size() + mHeaderLayoutIds.size();
    }
    //Footer 总量
    public int getFootersCount() {
        return mFooterViews.size() + mFooterLayoutIds.size();
    }



    private boolean isHeaderViewPos(int position)
    {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position)
    {
        return position >= getHeadersCount() + mAdapter.getItemCount();
    }

    public void addHeader(View view) {
        mHeaderViews.put(getHeadersCount() + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addFooter(View view) {
        mFooterViews.put(getFootersCount() + BASE_ITEM_TYPE_FOOTER, view);
    }

    public void addHeader(int layoutId) {
        mHeaderLayoutIds.put(getHeadersCount() + BASE_ITEM_TYPE_HEADER, layoutId);
    }

    public void addFooter(int layoutId) {
        mFooterLayoutIds.put(getFootersCount() + BASE_ITEM_TYPE_FOOTER, layoutId);
    }
    //headerView 实例
    private View getHeaderView(int viewType, Context context, ViewGroup parent) {
        View header = mHeaderViews.get(viewType);
        if (header != null) {
            return header;
        }
        Integer layoutId = mHeaderLayoutIds.get(viewType);
        if (layoutId == null){
            return null;
        }
        return LayoutInflater.from(context).inflate(layoutId,parent,false);
    }

    //底部　Footerview 实例
    private View getFooterView(int viewType, Context context, ViewGroup parent) {
        View view = mFooterViews.get(viewType);
        if (view != null)
            return view;
        Integer layoutId = mFooterLayoutIds.get(viewType);
        if (layoutId == null)
            return null;
        return LayoutInflater.from(context).inflate(layoutId, parent, false);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return BASE_ITEM_TYPE_HEADER + position;
        } else if (isFooterViewPos(position)) {
            int posInMap = position - getHeadersCount() - mAdapter.getItemCount();
            return posInMap + BASE_ITEM_TYPE_FOOTER;
        }
        return mAdapter.getItemViewType(position - getHeadersCount());
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = getHeaderView(viewType, context, parent);
        if (view != null) {
            return new RecyclerViewHolder(context, view);
        }
        view = getFooterView(viewType, context, parent);
        if (view != null) {
            return new RecyclerViewHolder(context, view);
        }
        return (RecyclerViewHolder) mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            onBindHeader(holder, position);
            return;
        }
        if (isFooterViewPos(position)) {
            onBindFooter(holder, position);
            return;
        }
        mAdapter.onBindViewHolder(holder, position - getHeadersCount());
    }

    protected void onBindFooter(RecyclerViewHolder holder, int position) {
    }

    protected void onBindHeader(RecyclerViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + getHeadersCount() + getFootersCount();
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return mAdapter.getItemLayoutId(viewType);
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, Object item) {
        mAdapter.bindData(holder, position, item);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mAdapter.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager manager = (GridLayoutManager) layoutManager;
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeaderViewPos(position) || isFooterViewPos(position)) ?
                            manager.getSpanCount() : 1;
                }
            });
            manager.setSpanCount(manager.getSpanCount());
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerViewHolder holder) {
        mAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p =
                        (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }
}
