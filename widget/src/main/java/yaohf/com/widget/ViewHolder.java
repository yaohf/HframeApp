package yaohf.com.widget;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 通用ViewHolder
 * @version 1.0
 */
public class ViewHolder {

	private SparseArray<View> mViews;
	
	@SuppressWarnings("unused")
	private int mPosition;
	private View mConvertView;
	private Context mContext;

	/**
	 * 实始化使用ViewHolder 用到的api
	 * @param context
	 * @param parent
	 * @param layoutId
	 * @param position
     */
	public ViewHolder(Context context, ViewGroup parent,int layoutId,int position)
	{
		this.mContext = context;
		this.mPosition = position;
		mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(mContext).inflate(layoutId, parent,false);
		mConvertView.setTag(this);
	}

	/**
	 *得到一个ViewHolder
     */
	public static ViewHolder get(Context context, View convertView, ViewGroup parent,int layoutId,int position)
	{
		if(convertView == null)
		{
			return new ViewHolder(context,parent,layoutId,position);
		}
		else
		{
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.mPosition = position;
			return holder;
		}
	}
	
	/**
	 * 从已有的集合中取出放进去的View ,没有添加一个view
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId)
	{
		View view = mViews.get(viewId);
		if(view == null)
		{
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}
	
	public View getConvertView()
	{
		return mConvertView;
	}
	
	public ViewHolder setText(int id, String text)
	{
		TextView tv = getView(id);
		tv.setText(text);
		return this;
	}
	public int getPosition()
	{
		return this.mPosition;
	}
}