package yaohf.com.widget.panel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;

import utils.BitmapUtil;
import utils.L;

/**
 * The SketchPadView class provides method to draw strokes on it like as a
 * canvas or sketch pad. We use touch event to draw strokes, when user touch
 * down and touch move, we will remember these point of touch move and set them
 * to a Path object, then draw the Path object, so that user can see the strokes
 * are drawing real time. When touch up event is occurring, we draw the path to
 * a bitmap which is hold by a canvas, and then draw the bitmap to canvas to
 * display these strokes to user.
 * 
 */
public class SketchPadView extends View implements IUndoCommand {

	public ArrayList<ISketchPadTool> m_undoStacks = new ArrayList<ISketchPadTool>();
	// 板檫
	public static final int STROKE_NONE = 0;
	// 涂鸦
	public static final int STROKE_PEN = 0;
	// line 直线
	public static final int STROKE_LINE = 1;

	// shape 形状
	public static final int STROKE_RECTANGLE = 2;
	// cricle 圆形
	public static final int STROKE_CRICLE = 3;

	public static final int STROKE_ERASER = 10;
	public static final int UNDO_SIZE = 20;

	private boolean m_isEnableDraw = true;
	//是否对图片作操作
	private boolean m_isDirty = false;
	private boolean m_isTouchUp = false;
	private boolean m_isSetForeBmp = false;
	private int m_bkColor = Color.WHITE;

	private int m_strokeType = STROKE_PEN;
	private int m_strokeColor = Color.BLACK;
	private int m_penSize = CommonDef.MIDDLE_PEN_WIDTH;
	private int m_eraserSize = CommonDef.MIDDLE_ERASER_WIDTH;
	private int m_canvasWidth = 100;
	private int m_canvasHeight = 100;
	private boolean m_canClear = true;

	private Paint m_bitmapPaint = null;
	private Bitmap m_foreBitmap = null;
	private SketchPadUndoStack m_undoStack = null;
	private Bitmap m_tempForeBitmap = null;
	private Bitmap m_bkBitmap;
	private Canvas m_canvas = null;
	private ISketchPadTool m_curTool = null;
	private ISketchPadCallback m_callback = null;
	private boolean isfirst = false;
	private Context context;
//	private RectF mImgDesRect;
//	private Paint paint;

	private Point evevtPoint;

	private int windowHeight;
	private int windowWidth;

	// 添加Tevent 的trigger Type
	private String teventType;

	// 画板的index,用于返回操作用
	private int index;
	
	private int teventSendSize;

	private int triggerType;
	public void setTeventType(String type) {
		this.teventType = type;
	}

	
	public void setTeventType(String type,int teventType) {
		this.teventType = type;
		this.triggerType = teventType;
	}
	public void setIndex(int i) {
		this.index = i;
	}

	public SketchPadView(Context context) {
		this(context, null);
	}

	public SketchPadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		if (BitmapUtil.getSketchWidth(context) > 0) {
			windowWidth = BitmapUtil.getSketchWidth(context);
		} else {
			windowWidth = context.getResources().getDisplayMetrics().widthPixels;
			// windowWidth = (windowWidth / 4) * 3;
		}
		if (BitmapUtil.getSketchHeight(context) > 0) {
			windowHeight = BitmapUtil.getSketchHeight(context);
		} else {
			windowHeight = context.getResources().getDisplayMetrics().heightPixels;
			// windowHeight = (windowHeight / 4) * 3;
		}

		L.v("windowWidth>>" + windowWidth + "\t windowHeight>>" + windowHeight);
		isfirst = true;
//		Bitmap bitmap = null;
		try {
			m_bkBitmap = BitmapFactory.decodeStream(context.getAssets().open(
					"paper.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
//		m_bkBitmap = bitmap;
		// bitmap.recycle();
		initialize();
	}

	public SketchPadView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize();
	}

	public void destory() {
		if (m_bkBitmap != null && !m_bkBitmap.isRecycled()) {
			m_bkBitmap.recycle();
		}
		if (m_tempForeBitmap != null && !m_tempForeBitmap.isRecycled())
			m_tempForeBitmap.recycle();

		if (m_foreBitmap != null && !m_foreBitmap.isRecycled())
			m_foreBitmap.recycle();
		m_canvas = null;
		context = null;
		m_undoStacks.clear();
		m_undoStack.onDestory();
		m_undoStacks = null;
		m_curTool = null;
		m_callback = null;
		evevtPoint = null;
	}

	public void setStacks(ArrayList<ISketchPadTool> l) {
		// m_undoStacks.clear();
		m_undoStacks = l;
	}

	public boolean isDirty() {
		return m_isDirty;
	}

	public void setDrawStrokeEnable(boolean isEnable) {
		m_isEnableDraw = isEnable;
	}

	public void setBkColor(int color) {
		if (m_bkColor != color) {
			m_bkColor = color;
			invalidate();
		}
	}

	public void setForeBitmap(Bitmap foreBitmap) {
		Log.v("hef", "-----------setForeBitmap");
		if (foreBitmap != m_foreBitmap && null != foreBitmap) {
			// Recycle the bitmap.
			if (null != m_foreBitmap) {
				m_foreBitmap.recycle();
			}

			m_isSetForeBmp = true;

			// Remember the temporary fore bitmap.
			m_tempForeBitmap = BitmapUtil.duplicateBitmap(foreBitmap);

			// Here create a new fore bitmap to avoid crashing when set bitmap
			// to canvas.
			m_foreBitmap = BitmapUtil.duplicateBitmap(foreBitmap);
			if (null != m_foreBitmap && null != m_canvas) {
				m_canvas.setBitmap(m_foreBitmap);
			}

			m_canClear = true;

			invalidate();
		}
	}

	public Bitmap getForeBitmap() {
		return m_foreBitmap;
	}

	public void setBkBitmap(Bitmap bmp) {
		if (m_bkBitmap != bmp) {
			m_bkBitmap = bmp;
			invalidate();
		}
	}

	public Bitmap getBkBitmap() {
		return m_bkBitmap;
	}

	public void setStrokeType(int type) {
		switch (type) {
		case STROKE_PEN:
			m_curTool = new SketchPadPen(m_penSize, m_strokeColor);
			break;
		case STROKE_LINE:
			m_curTool = new SketchLine(m_penSize, m_strokeColor);
			break;
		case STROKE_RECTANGLE:
			m_curTool = new SketchRectangle(m_penSize, m_strokeColor);
			break;
		case STROKE_CRICLE:
			m_curTool = new SketchCircle(m_penSize, m_strokeColor);
			break;

		case STROKE_ERASER:
			m_curTool = new SketchPadEraser(m_eraserSize);
			break;
		}

		m_strokeType = type;
	}

	public void setStrokeSize(int size, int type) {
		switch (type) {
		case STROKE_PEN:
			m_penSize = size;
			break;

		case STROKE_ERASER:
			m_eraserSize = size;
			break;
		}
	}

	public void setStrokeColor(int color) {
		m_strokeColor = color;
	}

	public int getStrokeSize() {
		return m_penSize;
	}

	public int getStrokeColor() {
		return m_strokeColor;
	}

	public void clearAllStrokes() {
		if (m_canClear) {
			// Clear the undo stack.
			m_undoStack.clearAll();
			m_undoStacks.clear();
			// Recycle the temporary fore bitmap
			if (null != m_tempForeBitmap) {
				m_tempForeBitmap.recycle();
				m_tempForeBitmap = null;
				m_foreBitmap.recycle();
				m_foreBitmap = null;
			}
			// Create a new fore bitmap and set to canvas.
			createStrokeBitmap(m_canvasWidth, m_canvasHeight);
			invalidate();
			m_isDirty = true;
			m_canClear = false;
		}
	}

	/**
	private Paint m_bitmapPaint = null;
	private Bitmap m_foreBitmap = null;
	private SketchPadUndoStack m_undoStack = null;
	private Bitmap m_tempForeBitmap = null;
	private Bitmap m_bkBitmap;
	*/
	public void clearRecycleAll(){
		m_undoStack.clearAll();
		m_undoStacks.clear();
		if (null != m_tempForeBitmap && !m_tempForeBitmap.isRecycled()) {
			m_tempForeBitmap.recycle();
			m_tempForeBitmap = null;
		
		}
		if(null != m_foreBitmap && !m_foreBitmap.isRecycled())
		{
			m_foreBitmap.recycle();
			m_foreBitmap = null;
		}
		
		if(null != m_bkBitmap && !m_bkBitmap.isRecycled())
		{
			m_bkBitmap.recycle();
			m_bkBitmap = null;
		}
		if(null != m_foreBitmap && !m_foreBitmap.isRecycled())
		{
			m_foreBitmap.recycle();
			m_foreBitmap = null;
		}
		if(m_canvas != null)
		{
			m_canvas = null;
		}
	}
	
	public void clearCanvas() {

		m_undoStack.clearAll();
		m_undoStacks.clear();
//		Bitmap bitmap = null;
//		try {
//			bitmap = BitmapFactory.decodeStream(context.getAssets().open(
//					"paper.png"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		m_canvas.drawBitmap(bitmap, 0, 0, m_bitmapPaint);
		
		try {
			m_bkBitmap = BitmapFactory.decodeStream(context.getAssets().open(
					"paper.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		invalidate();
		m_isDirty = true;
		m_canClear = false;
	}

	public Bitmap getCanvasSnapshot() {
		setDrawingCacheEnabled(true);
		buildDrawingCache(true);
		Bitmap bmp = getDrawingCache(true);

		if (null == bmp) {
			Log.d("leehong2",
					"getCanvasSnapshot getDrawingCache == null");
		}
//		return bmp;
		return BitmapUtil.duplicateBitmap(bmp);
	}

	public void cleanDrawingCache() {

		setDrawingCacheEnabled(false);
	}

	public void setCallback(ISketchPadCallback callback) {
		m_callback = callback;
	}

	public ISketchPadCallback getCallback() {
		return m_callback;
	}

	@Override
	public void onDeleteFromRedoStack() {
		// Do nothing currently.
	}

	@Override
	public void onDeleteFromUndoStack() {
		// Do nothing currently.
	}

	@Override
	public void redo() {
		if (null != m_undoStack) {
			m_undoStack.redo();
		}
	}

	@Override
	public void historydo() {
		if (null != m_undoStack) {
			m_undoStack.historydo();
		}
	}

	@Override
	public void undo() {
		Log.v("hef", m_undoStack + "m_undoStack");
		if (null != m_undoStack) {
			m_undoStack.undo();
		}
	}

	@Override
	public boolean canUndo() {
		if (null != m_undoStack) {
			return m_undoStack.canUndo();
		}

		return false;
	}

	@Override
	public boolean canRedo() {
		if (null != m_undoStack) {
			return m_undoStack.canRedo();
		}

		return false;
	}

	@Override
	public boolean canHistorydo() {
		if (null != m_undoStack) {
			return m_undoStack.canHistorydo();
		}

		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		evevtPoint.set((int) event.getX(), (int) event.getY());
		if (null != m_callback) {
			int action = event.getAction();
			if (MotionEvent.ACTION_DOWN == action) {
				m_callback.onTouchDown(this, event);
			} else if (MotionEvent.ACTION_UP == action) {
				m_callback.onTouchUp(this, event);
			}
		}

		if (m_isEnableDraw) {
			m_isTouchUp = false;
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// This will create a new stroke tool instance with specified
				// stroke type.
				// And the created tool will be added to undo stack.
				if(teventType != null)
				{
					getParent().requestDisallowInterceptTouchEvent(true);
				}
				setStrokeType(m_strokeType);

				m_curTool.touchDown(evevtPoint, event.getX(), event.getY());
				invalidate();
				break;

			case MotionEvent.ACTION_MOVE:
				m_curTool.touchMove(evevtPoint, event.getX(), event.getY());
				// If current stroke type is eraser, draw strokes on bitmap hold
				// by m_canvas.
				if (STROKE_ERASER == m_strokeType) {
					m_curTool.draw(m_canvas);
				}

				invalidate();
				m_isDirty = true;
				m_canClear = true;
				break;

			case MotionEvent.ACTION_UP:
				m_isTouchUp = true;
//				if (m_curTool.hasDraw()) {
					// Add to undo stack.
					m_undoStack.push(m_curTool);
//				}

				m_curTool.touchUp(event.getX(), event.getY());
				// Draw strokes on bitmap which is hold by m_canvas.
				m_curTool.draw(m_canvas);

				invalidate();
				m_isDirty = true;
				m_canClear = true;
				break;
			}
		}

		// Here must return true if enable to draw, otherwise the stroke may NOT
		// be drawn.
		return true;
	}

	protected void setCanvasSize(int width, int height) {
		if (width > 0 && height > 0) {
			if (m_canvasWidth != width || m_canvasHeight != height) {
				m_canvasWidth = width;
				m_canvasHeight = height;

				createStrokeBitmap(m_canvasWidth, m_canvasHeight);
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// Draw background color.
		canvas.drawColor(m_bkColor);

		// Draw background bitmap.
		if (null != m_bkBitmap) {
			canvas.drawBitmap(m_bkBitmap, 0, 0, null);
		}
		//
		if (null != m_foreBitmap) {
			canvas.drawBitmap(m_foreBitmap, 0, 0, m_bitmapPaint);
		}
		if (isfirst) {
			historydo();
			isfirst = false;
		}
		// if (null != m_curTool) {
		// // Do NOT draw current tool stroke real time if stroke type is NOT
		// // eraser, because
		// // eraser is drawn on bitmap hold by m_canvas.
		if (STROKE_ERASER != m_strokeType) {
			// // We do NOT draw current tool's stroke to canvas when ACTION_UP
			// // event is occurring,
			// // because the stroke has been drawn to bitmap hold by m_canvas.
			// // But the tool will be
			// // drawn if undo or redo operation is performed.
			// canvas.drawBitmap(m_bkBitmap, 0, 0, null);
			// // Draw background bitmap.
			// if (null != m_bkBitmap) {
			// RectF dst = new RectF(getLeft(), getTop(), getRight(),
			// getBottom());
			// Rect rst = new Rect(0, 0, m_bkBitmap.getWidth(),
			// m_bkBitmap.getHeight());
			// canvas.drawBitmap(m_bkBitmap, rst, dst, m_bitmapPaint);
			// }
			//

			if (!m_isTouchUp) {

				m_curTool.draw(canvas);

			}
		}
		//
		// }else{
		// paint = new Paint();
		// mImgDesRect = new RectF(getLeft(), getTop(), getRight(),
		// getBottom());
		// int sc = canvas.saveLayer(mImgDesRect.left, mImgDesRect.top,
		// mImgDesRect.right, mImgDesRect.bottom, null,
		// Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG
		// | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
		// | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
		// | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
		// paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
		// canvas.drawBitmap(m_bkBitmap, 0, 0, null);
		// // Draw background bitmap.
		// if (null != m_bkBitmap) {
		// RectF dst = new RectF(getLeft(), getTop(), getRight(), getBottom());
		// Rect rst = new Rect(0, 0, m_bkBitmap.getWidth(),
		// m_bkBitmap.getHeight());
		// canvas.drawBitmap(m_bkBitmap, rst, dst, null);
		// }
		//
		// if (null != m_foreBitmap) {
		// canvas.drawBitmap(m_foreBitmap, 0, 0, paint);
		// }
	}

	// }
	// }
	
	public void setCanvas(Canvas c)
	{
		this.m_canvas = c;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		// If NOT set fore bitmap, call setCanvasSize() method to set canvas
		// size, it will
		// create a new fore bitmap and set to canvas.

		if (!m_isSetForeBmp) {
			setCanvasSize(windowWidth, windowHeight);
		}

		m_canvasWidth = windowWidth;
		m_canvasHeight = windowHeight;
		m_isSetForeBmp = false;
	}

	protected void setTempForeBitmap(Bitmap tempForeBitmap) {
		if (null != tempForeBitmap) {
			if (null != m_foreBitmap) {
				m_foreBitmap.recycle();
			}

			m_foreBitmap = BitmapUtil.duplicateBitmap(tempForeBitmap);

			if (null != m_foreBitmap && null != m_canvas) {
				m_canvas.setBitmap(m_foreBitmap);
				invalidate();
			}
		}
	}

	protected void initialize() {
		m_canvas = new Canvas();
		m_bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		m_bitmapPaint.setAntiAlias(true);
		m_undoStack = new SketchPadUndoStack(this, UNDO_SIZE);
		// Set stroke type and create a stroke tool.
		setStrokeType(STROKE_PEN);
		evevtPoint = new Point();
	}

	protected void createStrokeBitmap(int w, int h) {
		m_canvasWidth = w;
		m_canvasHeight = h;
		Bitmap bitmap = Bitmap.createBitmap(m_canvasWidth, m_canvasHeight,
				Bitmap.Config.ARGB_8888);
		// Bitmap bitmap = null;
		// try {
		// bitmap = BitmapFactory.decodeStream(context.getAssets().open(
		// "a.png"));
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		if (null != bitmap) {
			m_foreBitmap = bitmap;
			// Set the fore bitmap to m_canvas to be as canvas of strokes.
			m_canvas.setBitmap(m_foreBitmap);
		}
	}

	public class SketchPadUndoStack {
		private int m_stackSize = 0;
		private SketchPadView m_sketchPad = null;
		private ArrayList<ISketchPadTool> m_undoStack = new ArrayList<ISketchPadTool>();
		private ArrayList<ISketchPadTool> m_redoStack = new ArrayList<ISketchPadTool>();
		private ArrayList<ISketchPadTool> m_removedStack = new ArrayList<ISketchPadTool>();

		public SketchPadUndoStack(SketchPadView sketchPad, int stackSize) {
			m_sketchPad = sketchPad;
			m_stackSize = stackSize;
		}

		public void onDestory(){
			if(m_sketchPad != null)
			{
				m_sketchPad = null;
			}
			clearAll();
		}
		
		public boolean canHistorydo() {
			// TODO Auto-generated method stub
			return (m_undoStacks.size() > 0);
		}

		public int size() {
			// TODO Auto-generated method stub
			return m_undoStack.size();
		}

		public void push(ISketchPadTool sketchPadTool) {
			if (null != sketchPadTool) {
				// if (m_undoStack.size() == m_stackSize && m_stackSize > 0) {
				// ISketchPadTool removedTool = m_undoStack.get(0);
				// m_removedStack.add(removedTool);
				// m_undoStack.remove(0);
				// }

				m_undoStack.add(sketchPadTool);
				m_undoStacks.add(sketchPadTool);
			}
		}

		public void clearAll() {
			m_redoStack.clear();
			m_undoStack.clear();
			m_removedStack.clear();
		}

		public void undo() {
			if (canUndo() && null != m_sketchPad) {
				ISketchPadTool removedTool = m_undoStack
						.get(m_undoStack.size() - 1);
				m_redoStack.add(removedTool);
				m_undoStack.remove(m_undoStack.size() - 1);

				if (null != m_tempForeBitmap) {
					// Set the temporary fore bitmap to canvas.
					m_sketchPad.setTempForeBitmap(m_sketchPad.m_tempForeBitmap);
				} else {
					// Create a new bitmap and set to canvas.
					m_sketchPad.createStrokeBitmap(m_sketchPad.m_canvasWidth,
							m_sketchPad.m_canvasHeight);
				}

				Canvas canvas = m_sketchPad.m_canvas;

				// First draw the removed tools from undo stack.
				for (ISketchPadTool sketchPadTool : m_removedStack) {
					sketchPadTool.draw(canvas);
				}

				for (ISketchPadTool sketchPadTool : m_undoStack) {
					sketchPadTool.draw(canvas);
				}

				m_sketchPad.invalidate();
			}
		}

		// public void drawHistory(){
		// Canvas canvas = m_sketchPad.m_canvas;
		// for (ISketchPadTool sketchPadTool : m_undoStack) {
		// sketchPadTool.draw(canvas);
		// }
		// }
		public void redo() {
			// if (canRedo() && null != m_sketchPad) {
			if (canRedo() && null != m_sketchPad) {
				ISketchPadTool removedTool = m_redoStack
						.get(m_redoStack.size() - 1);
				m_undoStack.add(removedTool);
				m_redoStack.remove(m_redoStack.size() - 1);

				if (null != m_tempForeBitmap) {
					// Set the temporary fore bitmap to canvas.
					m_sketchPad.setTempForeBitmap(m_sketchPad.m_tempForeBitmap);
				} else {
					// Create a new bitmap and set to canvas.
					m_sketchPad.createStrokeBitmap(m_sketchPad.m_canvasWidth,
							m_sketchPad.m_canvasHeight);
				}

				Canvas canvas = m_sketchPad.m_canvas;

				// First draw the removed tools from undo stack.
				for (ISketchPadTool sketchPadTool : m_removedStack) {
					sketchPadTool.draw(canvas);
				}
				for (ISketchPadTool sketchPadTool : m_undoStack) {
					L.v("m_undoStacks for sketchPadTool " + sketchPadTool);
					sketchPadTool.draw(canvas);
				}

				m_sketchPad.invalidate();
			}
		}

		public void historydo() {
			// if (canRedo() && null != m_sketchPad) {
			if (canHistorydo() && null != m_sketchPad) {
				// ISketchPadTool removedTool = m_redoStack
				// .get(m_redoStack.size() - 1);
				// m_undoStack.add(removedTool);
				// m_redoStack.remove(m_redoStack.size() - 1);

				if (null != m_tempForeBitmap) {
					// Set the temporary fore bitmap to canvas.
					m_sketchPad.setTempForeBitmap(m_sketchPad.m_tempForeBitmap);
				} else {
					// Create a new bitmap and set to canvas.
					m_sketchPad.createStrokeBitmap(m_sketchPad.m_canvasWidth,
							m_sketchPad.m_canvasHeight);
				}

				Canvas canvas = m_sketchPad.m_canvas;

				// First draw the removed tools from undo stack.
				// for (ISketchPadTool sketchPadTool : m_removedStack) {
				// sketchPadTool.draw(canvas);
				// }
				for (ISketchPadTool sketchPadTool : m_undoStacks) {
					sketchPadTool.draw(canvas);
				}

				m_sketchPad.invalidate();
			}
		}

		public boolean canUndo() {
			return (m_undoStack.size() > 0);

		}

		public boolean canRedo() {
			return (m_redoStack.size() > 0);
		}

	}

	/**
	 * 取得当前bitmap
	 */
	public Bitmap getBitmap() {
		if (m_bkBitmap != null && !m_bkBitmap.isRecycled())
			return m_bkBitmap;
		return null;
	}

	/**
	 * 
	 * @Description: 取得bitmap的base64
	 * @return String
	 * @throws
	 */
	public String getBitmapBase64() {
		Bitmap bitmap = getCanvasSnapshot();
		// saveImage("/sketch_cache.png",bitmap);
		String base64 = "";
		if (bitmap != null) {
			base64 = BitmapUtil.bitmapToBase64(bitmap);
		} else {
			return "";
		}
		BitmapUtil.recycledBitmap(bitmap);
		return base64;
	}

	// public void saveImage(String imagePath,Bitmap bitmap)
	// {
	//
	// String imageName = null;
	// int nStart = imagePath.lastIndexOf('/');
	// int nEnd = imagePath.lastIndexOf('.');
	//
	// imageName = imagePath.substring(nStart,nEnd);
	// imageName += ".png";
	// imageName = Environment.getExternalStorageDirectory().getAbsolutePath()
	// +"/" + imageName;
	// File file = new File(imageName);
	//
	// try {
	// file.createNewFile();
	// FileOutputStream out = new FileOutputStream(file);
	// bitmap.compress(CompressFormat.PNG, 100, out);
	// out.flush();
	// out.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//

}
