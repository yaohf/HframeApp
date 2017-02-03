package yaohf.com.widget.panel;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * 
 * 类描述：四方形，矩形， 长方形 项目名称：javaapk.com-Panel 类名称：SketchRectangle
 * 
 * @author: yaohf 创建时间：2015-6-11 下午6:22:10 修改备注：
 * @version
 * 
 */
public class SketchRectangle implements ISketchPadTool {

	private Point point1, point2, point3, point4;
	private Rect rect;
	private Rect point1Rect, point2Rect, point3Rect, point4Rect;

	public Point downPoint = new Point();
	public Point movePoint = new Point();
	public Point eventPoint = new Point();

	private Paint paint;
	int downState;
	int moveState;

	public SketchRectangle(int size, int color) {

		rect = new Rect();
		point1 = new Point();
		point2 = new Point();
		point3 = new Point();
		point4 = new Point();

		paint = new Paint();
		paint.setDither(true);
		paint.setStrokeWidth(size);
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setColor(color);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawRect(rect, paint);
	}

	@Override
	public boolean hasDraw() {
		return false;
	}

	@Override
	public void cleanAll() {

	}

	@Override
	public void touchDown(Point point, float x, float y) {
		downPoint.set(point.x, point.y);
		if (point2Rect != null) {
			if (point1Rect.contains(downPoint.x, downPoint.y)) {
				downState = 1;
			} else if (point2Rect.contains(downPoint.x, downPoint.y)) {
				downState = 2;
			} else if (point3Rect.contains(downPoint.x, downPoint.y)) {
				downState = 3;
			} else if (point4Rect.contains(downPoint.x, downPoint.y)) {
				downState = 4;
			} else if (rect.contains(downPoint.x, downPoint.y)) {
				downState = 5;
			} else {
				downState = 0;
			}
		}
	}

	@Override
	public void touchMove(Point point, float x, float y) {
		movePoint.set(point.x, point.y);
		switch (downState) {
		case 1:
			point1.set(point.x, point.y);
			point3.set(point1.x, point2.y);
			point4.set(point2.x, point1.y);
			moveState = 1;
			break;
		case 2:
			point2.set(point.x, point.y);
			point3.set(point1.x, point2.y);
			point4.set(point2.x, point1.y);
			moveState = 2;
			break;
		case 3:
			point3.set(point.x, point.y);
			point1.set(point3.x, point4.y);
			point2.set(point4.x, point3.y);
			moveState = 3;
			break;
		case 4:
			point4.set(point.x, point.y);
			point1.set(point3.x, point4.y);
			point2.set(point4.x, point3.y);
			moveState = 4;
			break;
		default:
			getStartPoint();
			moveState = 0;
			break;
		}
		setRect();
	}

	@Override
	public void touchUp(float x, float y) {

	}

	public void getStartPoint() {

		if (downPoint.x < movePoint.x && downPoint.y < movePoint.y) {
			point1.set(downPoint.x, downPoint.y);
			point2.set(movePoint.x, movePoint.y);
			point3.set(point1.x, point2.y);
			point4.set(point2.x, point1.y);
		} else if (downPoint.x < movePoint.x && downPoint.y > movePoint.y) {
			point3.set(downPoint.x, downPoint.y);
			point4.set(movePoint.x, movePoint.y);
			point1.set(point3.x, point4.y);
			point2.set(point4.x, point3.y);
		} else if (downPoint.x > movePoint.x && downPoint.y > movePoint.y) {
			point2.set(downPoint.x, downPoint.y);
			point1.set(movePoint.x, movePoint.y);
			point3.set(point1.x, point2.y);
			point4.set(point2.x, point1.y);
		} else if (downPoint.x > movePoint.x && downPoint.y < movePoint.y) {
			point4.set(downPoint.x, downPoint.y);
			point3.set(movePoint.x, movePoint.y);
			point1.set(point3.x, point4.y);
			point2.set(point4.x, point3.y);
		}

		setRect();

	}

	public void setRect() {
		point1Rect = new Rect(point1.x - 30, point1.y - 30, point1.x + 30,
				point1.y + 30);
		point2Rect = new Rect(point2.x - 30, point2.y - 30, point2.x + 30,
				point2.y + 30);
		point3Rect = new Rect(point3.x - 30, point3.y - 30, point3.x + 30,
				point3.y + 30);
		point4Rect = new Rect(point4.x - 30, point4.y - 30, point4.x + 30,
				point4.y + 30);

		rect.set(point1.x, point1.y, point2.x, point2.y);

	}

}
