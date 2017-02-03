package yaohf.com.widget.panel;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;

/**
 * 
* 类描述： 圆形     
* 项目名称：javaapk.com-Panel   
* 类名称：SketchCircle   
* @author: yaohf  
* 创建时间：2015-6-11 下午6:15:39    
* 修改备注：   
* @version    
*
 */
public class SketchCircle implements ISketchPadTool {

	private Point rDotsPoint;
	private int radius = 0;
	private Double dtance = 0.0;
	
	public Paint paint;
	public Point downPoint = new Point();
	
	
	public SketchCircle(int penSize,int penColor){
		rDotsPoint = new Point();
		paint = new Paint();
		paint.setDither(true);
		paint.setStrokeWidth(penSize);
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setColor(penColor);
		
	}
    
	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(rDotsPoint.x, rDotsPoint.y, radius, paint);
	}

	@Override
	public boolean hasDraw() {
		return false;
	}

	@Override
	public void cleanAll() {
		
	}

	@Override
	public void touchDown(Point point,float x, float y) {
		downPoint.set(point.x, point.y);
		if (radius != 0) {
			dtance = Math.sqrt((downPoint.x - rDotsPoint.x)
					* (downPoint.x - rDotsPoint.x)
					+ (downPoint.y - rDotsPoint.y)
					* (downPoint.y - rDotsPoint.y));
			if (dtance >= radius - 20 && dtance <= radius + 20) {
			} else if (dtance < radius) {
			} else if (dtance > radius) {
			}
		} else {
		}
	}

	@Override
	public void touchMove(Point point,float x, float y) {
		rDotsPoint.set(downPoint.x, downPoint.y);
		radius = (int) Math.sqrt((point.x - rDotsPoint.x)
				* (point.x - rDotsPoint.x)
				+ (point.y - rDotsPoint.y)
				* (point.y - rDotsPoint.y));
	}

	@Override
	public void touchUp(float x, float y) {
		// TODO Auto-generated method stub
		
	}

}
