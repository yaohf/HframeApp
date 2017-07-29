package yaohf.com.android.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import yaohf.com.android.R;
import yaohf.com.android.adapter.DownloadAdapter;
import yaohf.com.api.info.DownloadInfo;
import yaohf.com.tool.L;
import yaohf.com.tool.permission.FramePermission;
import yaohf.com.tool.permission.PermissionNo;
import yaohf.com.tool.permission.PermissionYes;

/**
 * Created by viqgd on 2017/3/12.
 */
public class DownloadActivity extends BaseActivity {

    private RecyclerView rvDownload;
    private List<DownloadInfo> downs = new ArrayList<DownloadInfo>();

    private static final int SDCARD_REQEST_CODE = 100;
    private static final  String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        setTitle(R.string.download_title);

        initDownloadData();
        L.v("dwons.size>>"+ downs.size());
        rvDownload = findById(R.id.rv_download);
//        final GridLayoutManager gridLayout = new GridLayoutManager(mContext, 3);
        final LinearLayoutManager linearLayout = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);

        rvDownload.setLayoutManager(linearLayout);
        rvDownload.setAdapter(new DownloadAdapter(mContext,downs));
    requsetSDCardPermission();
    }

    public void requsetSDCardPermission() {
        FramePermission.with(this)
                .requestCode(SDCARD_REQEST_CODE)
                .permission(PERMISSIONS_STORAGE)
                .send();
    }

    @PermissionYes(SDCARD_REQEST_CODE)
    private void requestSdcardSuccess(){
        L.v("GRANT ACCESS SDCARD!");
    }
    @PermissionNo(SDCARD_REQEST_CODE)
    private void requestSdcardFailed(){
        Toast.makeText(mContext, "DENY ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
    }

    private void initDownloadData()
    {
        DownloadInfo info = null;

        info = new DownloadInfo("http://ftp-apk.pconline.com.cn/ef19af4e28462271af1117efaf868bc2/pub/download/201010/renshengrili_v4.0.04.05.apk");
        downs.add(info);
        info = new DownloadInfo("http://gdown.baidu.com/data/wisegame/96b2bbc7f3f4df42/QQyuedu_96.apk");
        downs.add(info);
        info = new DownloadInfo("http://gdown.baidu.com/data/wisegame/be22e178fad158f1/miguyuedu_117.apk");
        downs.add(info);
        info = new DownloadInfo("http://gdown.baidu.com/data/wisegame/a3ca214b36d13246/GGBookxiaoshuoyueduqi_112.apk");
        downs.add(info);
        info = new DownloadInfo("http://gdown.baidu.com/data/wisegame/47b6431fbeae8d76/VIVAchangdu_136.apk");
        downs.add(info);
//        for(int i = 0; i < 5; i++)
//        {
//
//        }
    }

    @Override
    protected void activityHanlderMessage(Message m) {

    }
}