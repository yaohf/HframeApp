package yaohf.com.widget.panel.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import yaohf.com.widget.R;
import yaohf.com.widget.panel.ISketchListener;
import yaohf.com.widget.panel.PanelPreferencesUtils;

public class PaintSeekBarView extends PanelView {

	private int size;
	private ISketchListener mListener;


	private Resources res;
	
	private SeekBar seekBar;
	

	@Override
	public void setSelectListener(ISketchListener listener) {
		super.setSelectListener(listener);
		mListener = listener;
	}
	
	@Override
	public void setPicBackground(int id) {
		super.setPicBackground(id);
	
	}
	
	private View view;

	public PaintSeekBarView(Context context) {
		super(context);
		init();
	}

	public PaintSeekBarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public PaintSeekBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		view = inflate(getContext(), R.layout.seekbar_paint_size, null);
		seekBar = (SeekBar) view.findViewById(R.id.paint_size_sbar);
		size = PanelPreferencesUtils.getPaintSize(getContext());
		res = getContext().getResources();
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if(progress == 0)
					progress = 1;
				PanelPreferencesUtils.setPaintSize(getContext(), progress);
				// ((ImageButton)view.findViewById(ids[i])).setImageResource(panSizes[i]);
				mListener.setSketchPaintSize(progress);
				
			}
		});

	
	}
	


}
