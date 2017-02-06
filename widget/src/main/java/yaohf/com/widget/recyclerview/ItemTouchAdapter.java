package yaohf.com.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import yaohf.com.widget.R;

/**
 * Item 跨行滑动
 */
public class ItemTouchAdapter<T> extends RecyclerAdapter<Object> {

    public ItemTouchAdapter(Context ctx, List<Object> list)
    {
        super(ctx, list);
    }


    @Override
    protected int getItemLayoutId(int viewType)
    {
        return R.layout.item_cardview;
    }

    @Override
    protected void bindData(final RecyclerViewHolder holder, final int position,final Object item) {
        holder.setText(R.id.tv_num, item.toString());
        holder.getView(R.id.iv_reorder).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //让Activity实现OnStartDragListener接口，以便调用ItemTouchHelper的startDrag方法
                if (event.getAction() == MotionEvent.ACTION_DOWN)
//                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN)
                    ((OnStartActionListener) mContext).onStartDrag(holder);
                return false;
            }
        });
        //触发 item click event
        if (mClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(holder.itemView, item, position);
                }
            });
        }
        //触发 long click event
        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongClickListener.onItemLongClick(holder.itemView,item,position);
                    return true;
                }
            });
        }
    }


    public interface OnStartActionListener {
        void onStartDrag(RecyclerView.ViewHolder viewHolder);

        void onStartSwipe(RecyclerView.ViewHolder viewHolder);
    }
}
