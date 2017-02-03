package yaohf.com.widget.panel;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class SketchLine implements ISketchPadTool {

	
	private Point lPoint1, lPoint2;
	private Rect lPoint1Rect, lPoint2Rect;
	
	private Paint paint;
	
	public Point downPoint = new Point();
	
	int downState;
	int moveState;
	
	public SketchLine(int size,int color)
	{
		new Point();
		lPoint1 = new Point();
		lPoint2 = new Point();
		lPoint1Rect = new Rect();
		lPoint2Rect = new Rect();
		
		paint = new Paint();
		paint.setDither(true);
		paint.setStrokeWidth(size);
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setColor(color);
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawLine(lPoint1.x, lPoint1.y, lPoint2.x, lPoint2.y, paint);
	}

	@Override
	public boolean hasDraw() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void cleanAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void touchDown(Point point, float x, float y) {

		downPoint.set(point.x, point.y);

		if (lPoint1Rect.contains(point.x, point.y)) {
			Log.i("onTouchDown", "downState = 1");
			downState = 1;
		} else if (lPoint2Rect.contains(point.x, point.y)) {
			Log.i("onTouchDown", "downState = 2");
			downState = 2;
		} else {
			Log.i("onTouchDown", "downState = 0");
			downState = 0;
		}
	}

	@Override
	public void touchMove(Point point, float x, float y) {

		switch (downState) {
		case 1:
			lPoint1.set(point.x, point.y);
			moveState = 1;
			break;
		case 2:
			lPoint2.set(point.x, point.y);
			moveState = 2;
			break;
		default:
			lPoint1.set(downPoint.x, downPoint.y);
			lPoint2.set(point.x, point.y);
			break;
		}
	}

	@Override
	public void touchUp(float x, float y) {

		lPoint1Rect.set(lPoint1.x - 25, lPoint1.y - 25, lPoint1.x + 25,
				lPoint1.y + 25);
		lPoint2Rect.set(lPoint2.x - 25, lPoint2.y - 25, lPoint2.x + 25,
				lPoint2.y + 25);
	}

	
	public boolean panduan(Point point) {

		double lDis = Math.sqrt((lPoint1.x - lPoint2.x)
				* (lPoint1.x - lPoint2.x) + (lPoint1.y - lPoint2.y)
				* (lPoint1.y - lPoint2.y));

		double lDis1 = Math.sqrt((point.x - lPoint1.x) * (point.x - lPoint1.x)
				+ (point.y - lPoint1.y) * (point.y - lPoint1.y));
		double lDis2 = Math.sqrt((point.x - lPoint2.x) * (point.x - lPoint2.x)
				+ (point.y - lPoint2.y) * (point.y - lPoint2.y));


		if (lDis1 + lDis2 >= lDis + 0.00f && lDis1 + lDis2 <= lDis + 5.00f) {
			return true;
		} else {
			return false;
		}
	}
}
