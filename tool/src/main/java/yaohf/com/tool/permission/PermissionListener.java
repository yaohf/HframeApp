package yaohf.com.tool.permission;

/**
 * Created by viqgd on 2017/2/16.
 */

public interface PermissionListener {

    void onSucceed(int requestCode);
    void onFailed(int requestCode);

}
