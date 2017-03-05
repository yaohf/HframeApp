package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import yaohf.com.widget.ViewHolder;


/**
 *  通用Adapter
 * @author yhf
 *
 * @param <T>
 */
public abstract class VirtualAdapter<T> extends BaseAdapter {

	public Context mContext;
	public List<T> mDatas;
	public LayoutInflater inflater;
	public int position;
	private int layoutId;
	
	
 public VirtualAdapter(Context context, List<T> l,int layout) {
	 this.mContext = context;
	 this.mDatas = l;
	 this.layoutId = layout;
	 inflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		return mDatas == null ? 0 : mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public  View getView(int position, View convertView, ViewGroup parent)
	{
		this.position = position;
		ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);
		convert(holder,getItem(position),position);
		return holder.getConvertView();
	}
	
	/**
	 * 示例
	 * TextView name = holder.getView(R.id.tv_name);
		name.setText(T.getName());
	 * @param holder
	 * @param t
	 */
	public abstract void convert(ViewHolder holder, T t,int position);

}
