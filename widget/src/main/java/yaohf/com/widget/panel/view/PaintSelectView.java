package yaohf.com.widget.panel.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import yaohf.com.tool.L;
import yaohf.com.widget.R;
import yaohf.com.widget.panel.ISketchListener;
import yaohf.com.widget.panel.PanelPreferencesUtils;

public class PaintSelectView extends PanelView {

	private int size;
	private ISketchListener mListener;

	private String[] paintArrays;

	private Resources res;
	
	private LinearLayout root_lv;

	public ImageButton[] ib = new ImageButton[6];

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

	private int[] ids = new int[] { R.id.pen_1, R.id.pen_2, R.id.pen_3,
			R.id.pen_4, R.id.pen_5, R.id.pen_6 };
	private int [] defaultIds = {R.drawable.pen_1,R.drawable.pen_2,R.drawable.pen_3,R.drawable.pen_4,R.drawable.pen_5,R.drawable.pen_6};

	private int[] panSizes = { R.drawable.pen_xz1, R.drawable.pen_xz2,
			R.drawable.pen_xz3, R.drawable.pen_xz4, R.drawable.pen_xz5,
			R.drawable.pen_xz6 };
	private View view;

	public PaintSelectView(Context context) {
		super(context);
		init();
	}

	public PaintSelectView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public PaintSelectView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		view = inflate(getContext(), R.layout.paint_select_layout, null);
		root_lv = (LinearLayout) view.findViewById(R.id.paint_lv);
		size = PanelPreferencesUtils.getPaintSize(getContext());
		res = getContext().getResources();
		paintArrays = res.getStringArray(R.array.paint_size_arrays);
		// LinearLayout color_lv = (LinearLayout)
		// view.findViewById(R.id.paint_lv);
		for (int i = 0; i < ib.length; i++) {
			ib[i] = (ImageButton) view.findViewById(ids[i]);
			ib[i].setOnClickListener(this);
			if (Integer.parseInt(paintArrays[i]) == size)
				ib[i].setImageResource(panSizes[i]);

		}
		addView(view);
	
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		L.v("color>>" + getResources());
		final int id = v.getId();
		if(id == R.id.pen_1)
		{
			size = Integer.parseInt(paintArrays[0]);
		}else if(id == R.id.pen_2)
		{
			size = Integer.parseInt(paintArrays[1]);
		}else if(id == R.id.pen_3)
		{
			size = Integer.parseInt(paintArrays[2]);
		}else if(id == R.id.pen_4)
		{
			size = Integer.parseInt(paintArrays[3]);
		}else if(id == R.id.pen_5)
		{
			size = Integer.parseInt(paintArrays[4]);
		}else if(id == R.id.pen_6)
		{
			size = Integer.parseInt(paintArrays[5]);
		}
	
		for (int i = 0; i < ids.length; i++) {
			if (ids[i] == id) {
				ib[i].setImageResource(panSizes[i]);
				break;
			}
		}
		for (int i = 0; i < ids.length; i++) {
			if (ids[i] != id) {
				ib[i].setImageResource(defaultIds[i]);
			}
		}

		PanelPreferencesUtils.setPaintSize(getContext(), size);
		// ((ImageButton)view.findViewById(ids[i])).setImageResource(panSizes[i]);
		mListener.setSketchPaintSize(size);
//		TEvent.trigger(AppConstant.TEVENT_POPUPVIEW, new Object[] { 0 });

	}

	public void setSetting(int id) {

	}

}
