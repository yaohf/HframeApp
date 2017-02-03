package yaohf.com.widget.panel.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

import yaohf.com.widget.panel.ISketchAbstractListener;
import yaohf.com.widget.panel.ISketchListener;


public abstract class PanelView extends FrameLayout implements OnClickListener {

	public PanelView(Context context) {
		super(context);
	}

	public PanelView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public PanelView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	
	
	@Override
	public void onClick(View v) {

	}
	public void setSelectListener(ISketchListener listener){
	}
	public void setSelectAbstractListener(ISketchAbstractListener listener){
	}
	
	
	public void setPicBackground(int id){
	}
}
