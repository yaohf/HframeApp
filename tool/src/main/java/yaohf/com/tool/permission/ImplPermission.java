package yaohf.com.tool.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by viqgd on 2017/2/16.
 */

public class ImplPermission implements Permission {

    private String[] permissions;
    private String[] deniedPermissions;
    private int requestCode;
    private Object object;
    private RationaleListener listener;

    ImplPermission(Object o) {
        if (o == null)
            throw new IllegalArgumentException("The argument can not be null.");
        this.object = o;
    }

    @Override
    public Permission permission(String... permissions) {
        if (permissions == null)
            throw new IllegalArgumentException("The permissions can not be null.");
        this.permissions = permissions;
        return this;
    }

    @Override
    public Permission requestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    @Override
    public Permission rationale(RationaleListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void send() {
        if (permissions.length == 0) {
            invokeOnRequestPermissionsResult(object, requestCode, permissions, new int[0]);
        } else if (object instanceof Activity || object instanceof Fragment) {
            if (Build.VERSION.SDK_INT >= 23) {
                deniedPermissions = PermissionUtils.getDeniedPermissions(object, permissions);
                if (deniedPermissions.length > 0) { // Denied permissions size > 0.
                    final String[] rationalePermissions = PermissionUtils.getShouldShowRationalePermissions(object, deniedPermissions);
                    if (rationalePermissions.length > 0 && listener != null) // Remind users of the purpose of permissions.
                        listener.showRequestPermissionRationale(requestCode, rationale);
                    else
                        invokeRequestPermissions(object, requestCode, deniedPermissions);
                } else { // All permission granted.
                    int[] grantResults = new int[permissions.length];
                    for (int i = 0; i < grantResults.length; i++)
                        grantResults[i] = PackageManager.PERMISSION_GRANTED;
                    invokeOnRequestPermissionsResult(object, requestCode, permissions, grantResults);
                }
            } else // Check all permission result and dispatch.
                invokeOnRequestPermissionsResult(object, requestCode, permissions, PermissionUtils.getPermissionsResults(object, permissions));
        } else {
            Log.w("AndPermission", "The " + object.getClass().getName() + " is not support");
        }
    }

    private Rationale rationale = new Rationale() {
        @Override
        public void cancel() {
            invokeOnRequestPermissionsResult(object, requestCode, permissions, PermissionUtils.getPermissionsResults(object, permissions));
        }

        @Override
        public void resume() {
            invokeRequestPermissions(object, requestCode, deniedPermissions);
        }
    };

    @TargetApi(23)
    private static void invokeRequestPermissions(Object o, int requestCode, String... permissions) {
        if (o instanceof Activity)
            ((Activity) o).requestPermissions(permissions, requestCode);
        else if (o instanceof Fragment)
            ((Fragment) o).requestPermissions(permissions, requestCode);
    }

    private static void invokeOnRequestPermissionsResult(Object o, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (o instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                ((Activity) o).onRequestPermissionsResult(requestCode, permissions, grantResults);
            else if (o instanceof ActivityCompat.OnRequestPermissionsResultCallback)
                ((ActivityCompat.OnRequestPermissionsResultCallback) o).onRequestPermissionsResult(requestCode, permissions, grantResults);
        } else if (o instanceof Fragment) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ((Fragment) o).onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }
}
