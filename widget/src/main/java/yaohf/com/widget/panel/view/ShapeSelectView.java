package yaohf.com.widget.panel.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import utils.L;
import yaohf.com.widget.R;
import yaohf.com.widget.panel.ISketchListener;
import yaohf.com.widget.panel.PanelPreferencesUtils;

public class ShapeSelectView extends PanelView {

	private int shapeType;
	private ISketchListener mListener;

	public ImageButton[] ib = new ImageButton[4];
	
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

	private int[] ids = new int[] { R.id.shape_1, R.id.shape_2, R.id.shape_3,R.id.shape_4 };
	private int [] defaultIds = {R.drawable.xz_6,R.drawable.xz_1,R.drawable.xz_5,R.drawable.xz_4};

	private int[] mDrawables = { R.drawable.xz_xz6,R.drawable.xz_xz1,R.drawable.xz_xz5 ,R.drawable.xz_xz4};
	private View view;

	public ShapeSelectView(Context context) {
		super(context);
		init();
	}

	public ShapeSelectView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ShapeSelectView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		view = inflate(getContext(), R.layout.shape_select_layout, null);
		root_lv = (LinearLayout) view.findViewById(R.id.paint_lv);
		shapeType = PanelPreferencesUtils.getShapeType(getContext());
		for (int i = 0; i < ib.length; i++) {
			ib[i] = (ImageButton) view.findViewById(ids[i]);
			ib[i].setOnClickListener(this);
			if (shapeType == i)
				ib[i].setImageResource(mDrawables[shapeType]);
		}
		addView(view);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		L.v("color>>" + getResources());
		final int id = v.getId();
		if(id == R.id.shape_1)
		{
			shapeType = 0;
		}else if(id == R.id.shape_2){
			shapeType = 1;
		}else if(id == R.id.shape_3){
			shapeType = 2;
		}else if(id == R.id.shape_4){
			shapeType = 3;
		}
		for (int i = 0; i < ids.length; i++) {
			if (ids[i] == id) {
				ib[i].setImageResource(mDrawables[i]);
				break;
			}
		}
		for (int i = 0; i < ids.length; i++) {
			if (ids[i] != id) {
				ib[i].setImageResource(defaultIds[i]);
			}
		}
		PanelPreferencesUtils.setShapeType(getContext(), shapeType);
		mListener.setShapeType(shapeType);

	}

	public void setSetting(int id) {

	}
}
