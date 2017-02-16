package yaohf.com.tool.permission;

/**
 * Created by viqgd on 2017/2/16.
 */

public interface Rationale {

    /**
     * Cancel request permission.
     */
    void cancel();

    /**
     * Go request permission.
     */
    void resume();
}
