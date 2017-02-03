package yaohf.com.widget.panel;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by viqgd on 2017/1/31.
 */

public class PanelPreferencesUtils {

    private static final String PREFERENCE_NAME = "panel_preferences";



    /**
     *
     *
     * @Title: getPaintSize
     *
     * @Description: 获取画板画笔大小
     *
     * @param @param c
     * @param @return
     *
     * @return int
     *
     * @throws
     */
    public static int getPaintSize(Context c) {
        SharedPreferences sp = c.getSharedPreferences(PREFERENCE_NAME, 0);
        int size = sp.getInt("paint_size", -1);
        return size;
    }

    /**
     *
     *
     * @Title: setPaintSize
     *
     * @Description: 设置画板画笔大小
     *
     * @param @param c
     * @param @param subjectId
     *
     * @return void
     *
     * @throws
     */
    public static void setPaintSize(Context c, int size) {
        SharedPreferences sp = c.getSharedPreferences(PREFERENCE_NAME, 0);
        sp.edit().putInt("paint_size", size).commit();
    }

    /**
     *
     *
     * @Title: getShapeType
     *
     * @Description: 获取画板形状选项
     *
     * @param @param c
     * @param @return
     *
     * @return int
     *
     * @throws
     */
    public static int getShapeType(Context c) {
        SharedPreferences sp = c.getSharedPreferences(PREFERENCE_NAME, 0);
        int size = sp.getInt("shape_type", -1);
        return size;
    }

    /**
     *
     *
     * @Title: setShapeType
     *
     * @Description: 设置画板画笔大小
     *
     * @param @param c
     * @param @param subjectId
     *
     * @return void
     *
     * @throws
     */
    public static void setShapeType(Context c, int type) {
        SharedPreferences sp = c.getSharedPreferences(PREFERENCE_NAME, 0);
        sp.edit().putInt("shape_type", type).commit();
    }

    /**
     *
     *
     * @Title: getShapeType
     *
     * @Description: 获取画板形状选项
     *
     * @param @param c
     * @param @return
     *
     * @return int
     *
     * @throws
     */
    public static int getSketchHeight(Context c) {
        SharedPreferences sp = c.getSharedPreferences(PREFERENCE_NAME, 0);
        int size = sp.getInt("sketch_height", -1);
        return size;
    }

    /**
     *
     *
     * @Title: setShapeType
     *
     * @Description: 设置画板画笔大小
     *
     * @param @param c
     * @param @param subjectId
     *
     * @return void
     *
     * @throws
     */
    public static void setSketchHeight(Context c, int height) {
        SharedPreferences sp = c.getSharedPreferences(PREFERENCE_NAME, 0);
        sp.edit().putInt("sketch_height", height).commit();
    }


    /*
     * 画板宽度
     */
    public static int getSketchWidth(Context c)
    {
        SharedPreferences sp = c.getSharedPreferences(PREFERENCE_NAME, 0);
        int width = sp.getInt("sketch_width", -1);
        return width;
    }

    /**
     *
     * @Description: 设置画板宽度
     * @param @param c
     * @param @param width
     * @return void
     * @throws
     */
    public static void setSetchWidth(Context c, int width)
    {
        SharedPreferences sp = c.getSharedPreferences(PREFERENCE_NAME, 0);
        sp.edit().putInt("sketch_width", width).commit();

    }





}
