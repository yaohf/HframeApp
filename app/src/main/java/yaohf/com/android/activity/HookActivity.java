package yaohf.com.android.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import yaohf.com.android.R;

/**
 *
 */

public class HookActivity extends BaseActivity {

    private static final int hookID = (int) System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hook_main);

        Button btnHook1 = findById(R.id.btn_hook1);
        Button btnHook2 = findById(R.id.btn_hook2);
        hook(btnHook1,btnHook2);
    }

    public void onClick(View v) {
        HashMap<String,String> map = new HashMap<String,String>();
        switch (v.getId()) {
            case R.id.btn_hook1:
                map.put("java", "web");
                map.put("android", "phone");
                break;
            case R.id.btn_hook2:

                map.put(".net", "web");
                map.put("ios", "phone");
                break;
        }
        v.setTag(hookID, map);
    }


    private void hook(View view) {
        try {
            Class clazzView = Class.forName("android.view.View");
            Method method = clazzView.getDeclaredMethod("getListenerInfo");
            method.setAccessible(true);
            Object listenerInfo = method.invoke(view);
            Class clazzInfo = Class.forName("android.view.View$ListenerInfo");
            Field field = clazzInfo.getDeclaredField("mOnClickListener");
            field.set(listenerInfo, new HookListener((View.OnClickListener) field.get(listenerInfo)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void hook(View... views) {
        for (View view : views) {
            hook(view);
        }
    }


    public static class HookListener implements View.OnClickListener {

        private View.OnClickListener mOriginalListener;

        public HookListener(View.OnClickListener originalListener) {
            mOriginalListener = originalListener;
        }
        @Override
        public void onClick(View v) {
            if (mOriginalListener != null) {
                mOriginalListener.onClick(v);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("hook succeed.\n");
            Object obj = v.getTag(hookID);
            if (obj instanceof HashMap && !((Map) obj).isEmpty()) {
                for (Map.Entry<String, String> entry : ((Map<String, String>) obj).entrySet()) {
                    sb.append("key => ")
                            .append(entry.getKey())
                            .append(" ")
                            .append("value => ")
                            .append(entry.getValue())
                            .append("\n");
                }
            } else {
                sb.append("params => null\n");
            }
            Toast.makeText(v.getContext(), sb.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void activityHanlderMessage(Message m) {

    }
}
