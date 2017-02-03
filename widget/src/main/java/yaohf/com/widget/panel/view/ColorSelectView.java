package yaohf.com.widget.panel.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import utils.L;
import yaohf.com.widget.R;
import yaohf.com.widget.panel.ISketchListener;


public class ColorSelectView extends PanelView {

	private int color;
	private ISketchListener mListener;
	private LinearLayout root_lv;

	@Override
	public void setSelectListener(ISketchListener listener) {
		super.setSelectListener(listener);
		mListener = listener;

	}
	@Override
	public void setPicBackground(int id) {
		super.setPicBackground(id);
		root_lv.setBackgroundResource(id);
		root_lv.setPadding(20, 0, 0, 0);
	}
	

	private int[] ids = new int[] { R.id.c_1, R.id.c_2, R.id.c_3, R.id.c_4,
			R.id.c_5, R.id.c_6, R.id.c_7 };
	private View view;

	public ColorSelectView(Context context) {
		super(context);
		init();
	}

	public ColorSelectView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ColorSelectView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		view = inflate(getContext(), R.layout.color_select_layout, null);

		
		
		root_lv = (LinearLayout) view.findViewById(R.id.color_lv);
		final int size = root_lv.getChildCount();

		for (int i = 1; i < size; i++) {
			view.findViewById(ids[i - 1]).setOnClickListener(this);
		}
		addView(view);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		L.v("color>>" + getResources());

		Resources res = getContext().getResources();
		final int id = v.getId();
		if (id == R.id.c_1) {
			color = res.getColor(R.color.pen_c_1);
		} else if (id == R.id.c_2) {
			color = res.getColor(R.color.pen_c_2);
		} else if (id == R.id.c_3) {
			color = res.getColor(R.color.pen_c_3);
		} else if (id == R.id.c_4) {
			color = res.getColor(R.color.pen_c_4);
		} else if (id == R.id.c_5) {
			color = res.getColor(R.color.pen_c_5);

		} else if (id == R.id.c_6) {
			color = res.getColor(R.color.pen_c_6);
		} else if (id == R.id.c_7) {
			color = res.getColor(R.color.pen_c_7);
		}
		mListener.setSketchColor(color);
//		TEvent.trigger(AppConstant.TEVENT_POPUPVIEW, new Object[] { 0 });

	}

}
