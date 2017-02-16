package yaohf.com.tool.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Process;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by viqgd on 2017/2/16.
 */

public class PermissionUtils {

    @TargetApi(23)
    static String[] getShouldShowRationalePermissions(Object o, String... permissions) {
        List<String> strings = new ArrayList<>(1);
        if (o instanceof Activity) {
            for (String permission : permissions)
                if (((Activity) o).shouldShowRequestPermissionRationale(permission))
                    strings.add(permission);
        } else if (o instanceof Fragment) {
            for (String permission : permissions)
                if (((Fragment) o).shouldShowRequestPermissionRationale(permission))
                    strings.add(permission);
        } else
            throw new IllegalArgumentException("The " + o.getClass().getName() + " is not support.");
        return strings.toArray(new String[strings.size()]);
    }

    static int[] getPermissionsResults(Object o, String... permissions) {
        int[] results = new int[permissions.length];
        for (int i = 0; i < results.length; i++)
            results[i] = checkPermission(o, permissions[i]);
        return results;
    }

    static String[] getDeniedPermissions(Object o, String... permissions) {
        List<String> strings = new ArrayList<>(1);
        for (String permission : permissions)
            if (checkPermission(o, permission) != PackageManager.PERMISSION_GRANTED)
                strings.add(permission);
        return strings.toArray(new String[strings.size()]);
    }

    static int checkPermission(Object o, String permission) {
        return getActivity(o).checkPermission(permission, Process.myPid(), Process.myUid());
    }

    static Activity getActivity(Object o) {
        if (o instanceof Activity)
            return (Activity) o;
        else if (o instanceof Fragment)
            return ((Fragment) o).getActivity();
        throw new IllegalArgumentException("The " + o.getClass().getName() + " is not support to get the context.");
    }

    static <T extends Annotation> Method[] findMethodForRequestCode(Class<?> source, Class<T> annotation, int requestCode) {
        List<Method> methods = new ArrayList<>(1);
        for (Method method : source.getDeclaredMethods())
            if (method.isAnnotationPresent(annotation))
                if (isSameRequestCode(method, annotation, requestCode))
                    methods.add(method);
        return methods.toArray(new Method[methods.size()]);
    }

    static <T extends Annotation> boolean isSameRequestCode(Method method, Class<T> annotation, int requestCode) {
        if (PermissionYes.class.equals(annotation))
            return method.getAnnotation(PermissionYes.class).value() == requestCode;
        else if (PermissionNo.class.equals(annotation))
            return method.getAnnotation(PermissionNo.class).value() == requestCode;
        return false;
    }
}
