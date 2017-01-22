package yaohf.com.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import utils.L;
import yaohf.com.widget.R;

/**
 * Item 跨行滑动
 */
public class ItemTouchAdapter extends RecyclerAdapter<String> {

    public ItemTouchAdapter(Context ctx, List<String> list)
    {
        super(ctx, list);
    }

    @Override
    protected int getItemLayoutId(int viewType)
    {
        return R.layout.item_cardview;
    }

    @Override
    protected void bindData(final RecyclerViewHolder holder, int position, String item) {
        holder.setText(R.id.tv_num, item);
        holder.getView(R.id.iv_reorder).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //让Activity实现OnStartDragListener接口，以便调用ItemTouchHelper的startDrag方法
                L.v( ((OnStartActionListener) mContext));
                if (event.getAction() == MotionEvent.ACTION_DOWN)
//                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN)
                    ((OnStartActionListener) mContext).onStartDrag(holder);
                return false;
            }
        });
    }


    public interface OnStartActionListener {
        void onStartDrag(RecyclerView.ViewHolder viewHolder);

        void onStartSwipe(RecyclerView.ViewHolder viewHolder);
    }
}
