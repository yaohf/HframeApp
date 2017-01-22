package yaohf.com.android.activity;

import android.os.Bundle;
import android.os.Message;

import yaohf.com.android.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void activityHanlderMessage(Message m) {

    }
}
