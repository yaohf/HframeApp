package utils;

import android.content.Context;
import android.graphics.drawable.StateListDrawable;

public class SelectorUtils {


    private static SelectorUtils instance;

    private Context mContext;


    public static SelectorUtils getInstance(Context c) {
        if (instance == null)
            instance = new SelectorUtils(c);
        return instance;
    }

    private SelectorUtils(Context c) {
        this.mContext = c.getApplicationContext();
    }

    /**
     * 对Drawable不同状态时设置背景， 点击选中后呈现背景图片
     *
     * @param defaultDrawable
     * @param selecdDrawable
     * @return
     */
    public StateListDrawable getClickStateDrawable(int defaultDrawable, int selecdDrawable) {
        StateListDrawable drawable = new StateListDrawable();
        //Non focused states
        drawable.addState(new int[]{-android.R.attr.state_enabled},
                mContext.getResources().getDrawable(selecdDrawable));
        //Focused states
        drawable.addState(new int[]{-android.R.attr.state_focused, -android.R.attr.state_pressed},
                mContext.getResources().getDrawable(defaultDrawable));
        //Pressed
        drawable.addState(new int[]{-android.R.attr.state_focused},
                mContext.getResources().getDrawable(defaultDrawable));
        return drawable;
    }


    /**
     * <item android:drawable="@drawable/question_bank_press" android:state_enabled="false"></item>
     * <item android:drawable="@drawable/question_bank_press" android:state_pressed="true"/>
     * <item android:drawable="@drawable/question_bank_normal" android:state_focused="false" android:state_pressed="false"/>
     * <item android:drawable="@drawable/question_bank_press"  android:state_focused="true"/>
     * <item android:drawable="@drawable/question_bank_normal" android:state_focused="false"/>
     */

    public StateListDrawable getStateDrawable(int defaultDrawable, int selecdDrawable) {
        StateListDrawable drawable = new StateListDrawable();
        //Non focused states
        drawable.addState(new int[]{-android.R.attr.state_enabled},
                mContext.getResources().getDrawable(selecdDrawable));

        drawable.addState(new int[]{android.R.attr.state_pressed},
                mContext.getResources().getDrawable(selecdDrawable));

        //Focused states
        drawable.addState(new int[]{-android.R.attr.state_focused, -android.R.attr.state_pressed},
                mContext.getResources().getDrawable(defaultDrawable));

        drawable.addState(new int[]{android.R.attr.state_focused},
                mContext.getResources().getDrawable(selecdDrawable));

        //Pressed
        drawable.addState(new int[]{-android.R.attr.state_focused},
                mContext.getResources().getDrawable(defaultDrawable));
        return drawable;
    }
}
