package yaohf.com.tool.permission;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by viqgd on 2017/2/16.
 */

public class FramePermission {

    /**
     * In the Activity.
     *
     * @param activity {@link Activity}.
     * @return {@link Permission}.
     */
    public static Permission with(Activity activity) {
        return new ImplPermission(activity);
    }

    /**
     * In the Activity.
     *
     * @param fragment {@link Fragment}.
     * @return {@link Permission}.
     */
    public static Permission with(Fragment fragment) {
        return new ImplPermission(fragment);
    }

    /**
     * Request permissions in the activity.
     *
     * @param activity    {@link Activity}.
     * @param requestCode request code.
     * @param permissions all permissions.
     */
    public static void send(Activity activity, int requestCode, String... permissions) {
        with(activity).requestCode(requestCode).permission(permissions).send();
    }

    /**
     * Request permissions in the activity.
     *
     * @param fragment    {@link Fragment}.
     * @param requestCode request code.
     * @param permissions all permissions.
     */
    public static void send(Fragment fragment, int requestCode, String... permissions) {
        with(fragment).requestCode(requestCode).permission(permissions).send();
    }

    /**
     * Should show rationale permissions;
     *
     * @param activity    {@link Activity}.
     * @param permissions permissions.
     * @return true, other wise false.
     */
    public static boolean getShouldShowRationalePermissions(Activity activity, String... permissions) {
        return PermissionUtils.getShouldShowRationalePermissions(activity, permissions).length > 0;
    }

    /**
     * Should show rationale permissions;
     *
     * @param fragment    {@link Fragment}.
     * @param permissions permissions.
     * @return true, other wise false.
     */
    public static boolean getShouldShowRationalePermissions(Fragment fragment, String... permissions) {
        return PermissionUtils.getShouldShowRationalePermissions(fragment, permissions).length > 0;
    }

    /**
     * Check permissions;
     *
     * @param activity    {@link Activity}.
     * @param permissions permissions.
     * @return true, other wise false.
     */
    public static boolean checkPermission(Activity activity, String... permissions) {
        return PermissionUtils.getDeniedPermissions(activity, permissions).length <= 0;
    }

    /**
     * Check permissions;
     *
     * @param fragment    {@link Fragment}.
     * @param permissions permissions.
     * @return true, other wise false.
     */
    public static boolean checkPermission(Fragment fragment, String... permissions) {
        return PermissionUtils.getDeniedPermissions(fragment, permissions).length <= 0;
    }

    /**
     * Parse the request results.
     *
     * @param o            {@link Activity} or {@link Fragment}.
     * @param requestCode  request code.
     * @param permissions  all permissions.
     * @param grantResults results.
     */
    public static void onRequestPermissionsResult(Object o, int requestCode, String[] permissions, int[] grantResults) {
        onRequestPermissionsResult(o, requestCode, permissions, grantResults, null);
    }

    /**
     * Parse the request results.
     *
     * @param o            {@link Activity} or {@link Fragment}.
     * @param requestCode  request code.
     * @param permissions  all permissions.
     * @param grantResults results.
     * @param listener     {@link PermissionListener}.
     */
    public static void onRequestPermissionsResult(Object o, int requestCode, String[] permissions, int[] grantResults, PermissionListener listener) {
        List<String> deniedPermissions = new ArrayList<>(1);
        for (int i = 0; i < grantResults.length; i++)
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
                deniedPermissions.add(permissions[i]);
        if (listener == null) {
            callback(o, deniedPermissions.size() > 0 ? PermissionNo.class : PermissionYes.class, requestCode);
        } else if (deniedPermissions.size() > 0) {
            listener.onFailed(requestCode);
        } else {
            listener.onSucceed(requestCode);
        }
    }

    static <T extends Annotation> void callback(Object o, Class<T> clazz, int requestCode) {
        Method[] methods = PermissionUtils.findMethodForRequestCode(o.getClass(), clazz, requestCode);
        try {
            for (Method method : methods) {
                if (!method.isAccessible()) method.setAccessible(true);
                method.invoke(o, null);
            }
        } catch (Exception e) {
            Log.e("AndPermission", "Callback methods fail.");
        }
    }

    private FramePermission() {
    }
}
