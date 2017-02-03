package yaohf.com.widget.panel;


import android.graphics.Canvas;
import android.graphics.Point;

public interface ISketchPadTool
{
    public void draw(Canvas canvas);
    public boolean hasDraw();
    public void cleanAll();
    public void touchDown(Point point, float x, float y);
    public void touchMove(Point point, float x, float y);
    public void touchUp(float x, float y);
}
