package yaohf.com.android.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import yaohf.com.android.ApplicationManager;
import yaohf.com.android.R;
import yaohf.com.android.service.ScreenService;
import yaohf.com.tool.L;

/**
 * Created by viqgd on 2017/2/24.
 */

public class ScreenActivity extends BaseActivity {

    private static final int REQUEST_CODE = 1;
    private MediaProjectionManager mMediaProjectionManager;
//    private ScreenRecorder mRecorder;

    private Button  btnScreen;

    private Intent mIntent = null;
    private int result;

    int width = 1280;
    int height = 720;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        setTitle(R.string.screen_title);
        if(savedInstanceState != null)
            L.v("Bundle >>" + savedInstanceState);
        btnScreen = findById(R.id.btn_screen);
        btnScreen.setOnClickListener(onClickListener);
        mMediaProjectionManager = (MediaProjectionManager)getApplication().getSystemService(Context.MEDIA_PROJECTION_SERVICE);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;
    }

    private void startIntent()
    {
        if(mIntent != null && result != 0 )
        {
            L.v("-------two -------");
            ((ApplicationManager)getApplication()).setResult(result);
            ((ApplicationManager)getApplication()).setIntent(mIntent);
//            ((ApplicationManager)getApplication()).setmRecorder(mRecorder);
            ((ApplicationManager)getApplication()).setmMediaProjectionManager(mMediaProjectionManager);
            Intent intent = new Intent(mContext, ScreenService.class);
            startService(intent);
        }else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                L.v("------init-------");
                startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(), REQUEST_CODE);
                ((ApplicationManager)getApplication()).setmMediaProjectionManager(mMediaProjectionManager);
            }
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        L.v("start");
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        L.v("start");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level)
        {
            case TRIM_MEMORY_COMPLETE:
                L.v("内存不足,并且该进程在后台进程列表最后一个，马上就要被清理");
            break;
            case TRIM_MEMORY_MODERATE:
                L.v("内存不足，并且该进程在后台进程列表的中部。");
                break;
            case TRIM_MEMORY_BACKGROUND:
                L.v("内存不足，并且该进程是后台进程");
                break;
            case TRIM_MEMORY_UI_HIDDEN:
                L.v("内存不足，并且该进程的UI已经不可见了");
                break;
            case TRIM_MEMORY_RUNNING_CRITICAL:
                L.v("内存不足(后台进程不足3个)，并且该进程优先级比较高，需要清理内存");
            break;
            case TRIM_MEMORY_RUNNING_LOW:
                L.v("内存不足(后台进程不足5个)，并且该进程优先级比较高，需要清理内存");
                break;
            case TRIM_MEMORY_RUNNING_MODERATE:
                L.v("内存不足(后台进程超过5个)，并且该进程优先级比较高，需要清理内存      ");
                break;


        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        L.v("start");
        if( resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                    L.v("REQUEST_CODE");
                    result = resultCode;
                    mIntent = data;
                    ((ApplicationManager)getApplication()).setResult(result);
                    ((ApplicationManager)getApplication()).setIntent(mIntent);
                    Intent intent = new Intent(mContext, ScreenService.class);
                    startService(intent);


//                    MediaProjection mediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
//                    if (mediaProjection == null) {
//                        L.v("media projection is null");
//                        return;
//                    }
//                    // video size
//                    File file = new File(Environment.getExternalStorageDirectory(),
//                            "record_" + width + "x" + height + "_" + System.currentTimeMillis() + ".mp4");
//                    final int bitrate = 6000000;
//                    mRecorder = new ScreenRecorder(width, height, bitrate, 1, mediaProjection, file.getAbsolutePath());
//                    mRecorder.start();
//                    btnScreen.setText(R.string.screen_end);
//                    Toast.makeText(this, "Screen recorder is running...", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
//        moveTaskToBack(true);
        L.v("end");
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.btn_screen:
                    startIntent();
//                    if (mRecorder != null) {
//                        mRecorder.quit();
//                        mRecorder = null;
//                        ((Button)view).setText(R.string.screen_start);
//                    } else {
//                        Intent captureIntent = mMediaProjectionManager.createScreenCaptureIntent();
//                        startActivityForResult(captureIntent, REQUEST_CODE);
//                    }
                    break;
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.v("start");
//        if(mRecorder != null){
//            mRecorder.quit();
//            mRecorder = null;
//            L.v("end");
//        }
    }

    @Override
    protected void activityHanlderMessage(Message m) {

    }
}
