package yaohf.com.widget.panel.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import yaohf.com.widget.R;
import yaohf.com.widget.panel.ISketchListener;


public class PicSelectView extends PanelView {

	private ISketchListener mListener;

	@Override
	public void setSelectListener(ISketchListener listener) {
		super.setSelectListener(listener);
		mListener = listener;
	}
	private View view;

	public PicSelectView(Context context) {
		super(context);
		init();
	}

	public PicSelectView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public PicSelectView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		view = inflate(getContext(), R.layout.pic_select_layout, null);
		view.findViewById(R.id.camera_tv).setOnClickListener(this);
		view.findViewById(R.id.album_tv).setOnClickListener(this);
		addView(view);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		int mode = 0;
		final int id = v.getId();
		
		if(id == R.id.camera_tv) {
			mode = Integer.parseInt(v.getTag().toString());
		} else if(id == R.id.album_tv){
			mode = Integer.parseInt(v.getTag().toString());
		}
		mListener.setPicMode(mode);
//		TEvent.trigger(AppConstant.TEVENT_POPUPVIEW, new Object[] { 0 });

	}

}
