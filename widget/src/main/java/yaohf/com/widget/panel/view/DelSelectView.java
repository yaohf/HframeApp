package yaohf.com.widget.panel.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import yaohf.com.widget.R;
import yaohf.com.widget.panel.ISketchAbstractListener;


public class DelSelectView extends PanelView {

	/** mode 1 全部， 2 仅涂鸦 **/
	private ISketchAbstractListener mListener;

//	@Override
//	public void setSelectListener(ISketchListener listener) {
//		super.setSelectListener(listener);
//		mListener = listener;
//	}
	@Override
	public void setSelectAbstractListener(ISketchAbstractListener listener) {
		super.setSelectAbstractListener(listener);
		mListener = listener;
	}
	private View view;

	public DelSelectView(Context context) {
		super(context);
		init();
	}

	public DelSelectView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public DelSelectView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		view = inflate(getContext(), R.layout.del_select_layout, null);
		view.findViewById(R.id.all_tv).setOnClickListener(this);
		view.findViewById(R.id.graffiti_tv).setOnClickListener(this);
		addView(view);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		int mode = 0;
		final int id = v.getId();
		if(id == R.id.all_tv) {
			mode = Integer.parseInt(v.getTag().toString());
		} else if(id == R.id.graffiti_tv){
			mode = Integer.parseInt(v.getTag().toString());
		}
		mListener.setAbsDeleteAll(mode);
//		TEvent.trigger(AppConstant.TEVENT_POPUPVIEW, new Object[] { 0 });
	}
}
