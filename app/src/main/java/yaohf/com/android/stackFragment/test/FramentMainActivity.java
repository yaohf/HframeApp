package yaohf.com.android.stackFragment.test;


import android.os.Bundle;
import android.os.Message;

import yaohf.com.android.R;
import yaohf.com.android.activity.BaseActivity;
import yaohf.com.android.stackFragment.RootFragment;

/**
 * Fragment Root
 */
public class FramentMainActivity extends BaseActivity {

    @Override
    public RootFragment getRootFragment() {
        return new HomeFragment();
    }

    @Override
    public void onCreateNow(Bundle savedInstanceState) {
        setAnim(R.anim.next_in, R.anim.next_out, R.anim.quit_in, R.anim.quit_out);
    }

    @Override
    protected void activityHanlderMessage(Message m) {

    }

    /**
     * Set the time to click to Prevent repeated clicks,default 500ms
     *
     * @param CLICK_SPACE Repeat click time(ms)
     */
    public void setClickSpace(long CLICK_SPACE) {
        manager.setClickSpace(CLICK_SPACE);
    }
}
