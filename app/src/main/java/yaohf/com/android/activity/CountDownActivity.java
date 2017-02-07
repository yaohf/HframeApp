package yaohf.com.android.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import yaohf.com.android.R;
import yaohf.com.widget.countdown.CountDownProgressView;
import yaohf.com.widget.countdown.RoundProgressBarView;

/**
 * Created by viqgd on 2017/2/7.
 */

public class CountDownActivity extends BaseActivity {

    private RoundProgressBarView roundPBView;
    private CountDownProgressView countDownView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count_down_main);


        roundPBView = findById(R.id.roundView);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        roundPBView.setmRadius(width/4);
        ((Button)findViewById(R.id.btn_1)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                roundPBView.startCountDownTime(new RoundProgressBarView.OnProgressFinishListener() {
                    @Override
                    public void progressFinished() {

                    }
                }, 322.5, 100);
            }
        });

        countDownView = findById(R.id.countDownView);
        countDownView.setCountdownTime(10*1000);
        countDownView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownView.startCountDownTime(new CountDownProgressView.OnCountdownFinishListener() {
                    @Override
                    public void countdownFinished() {
                        Toast.makeText(mContext, "倒计时结束了--->该UI处理界面逻辑了", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    protected void activityHanlderMessage(Message m) {

    }
}
