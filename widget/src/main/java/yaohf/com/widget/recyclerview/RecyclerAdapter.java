package yaohf.com.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by viqgd on 2017/1/18.
 */

public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    protected final List<T> mItems;
    protected final Context mContext;

    protected LayoutInflater mInflater;
    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mLongClickListener;

    public RecyclerAdapter(Context ctx, List<T> list) {
        mItems = (list != null) ? list : new ArrayList<T>();
        mContext = ctx;
        mInflater = LayoutInflater.from(ctx);

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //实例化ViewHodler
        final RecyclerViewHolder holder = new RecyclerViewHolder(mContext, mInflater.inflate(getItemLayoutId(viewType), parent, false));
        //触发 item click event
        if (mClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            });
        }
        //触发 long click event
        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongClickListener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
                    return true;
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        bindData(holder, position, mItems.get(position));
    }


    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public void add(int pos, T item) {
        mItems.add(pos, item);
        notifyItemInserted(pos);
    }

    public void delete(int pos) {
        mItems.remove(pos);
        notifyItemRemoved(pos);
    }

    /**
     * 重新排序
     * @param fromPosition
     * @param toPosition
     */
    public void swap(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     * 重写该方法，根据viewType设置item的layout
     *
     * @param viewType 通过重写getItemViewType（）设置，默认item是0
     * @return
     */
    protected abstract int getItemLayoutId(int viewType);

    /**
     * 重写该方法进行item数据项视图的数据绑定
     *
     * @param holder   通过holder获得item中的子View，进行数据绑定
     * @param position 该item的position
     * @param item     映射到该item的数据
     */
    protected abstract void bindData(RecyclerViewHolder holder, int position, T item);

    @Override
    public void onViewAttachedToWindow(RecyclerViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }


    /**
     * 自定义Item Click event
     */
    public interface OnItemClickListener {
        void onItemClick(View itemView, int pos);
    }

    /**
     * 自定义item long event
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(View itemView, int pos);
    }

     public final void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }

    public final void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mLongClickListener = listener;
    }

}
