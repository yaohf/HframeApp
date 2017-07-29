package yaohf.com.android.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import yaohf.com.android.R;
import yaohf.com.android.download.DownLoadObserver;
import yaohf.com.android.download.DownloadManager;
import yaohf.com.api.info.DownloadInfo;
import yaohf.com.tool.L;
import yaohf.com.widget.recyclerview.RecyclerAdapter;
import yaohf.com.widget.recyclerview.RecyclerViewHolder;

/**
 * Created by viqgd on 2017/3/12.
 */

public class DownloadAdapter extends RecyclerAdapter<DownloadInfo> {
    public DownloadAdapter(Context ctx, List<DownloadInfo> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.activity_download_item;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position,final DownloadInfo info) {
        L.v(info);
      final ProgressBar pbDownload = holder.getProgressBar(R.id.pb_download);
        Button btnStart = holder.getButton(R.id.btn_start);
        Button btnCancel = holder.getButton(R.id.btn_cancel);


        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_start: {
                        DownloadManager.getInstance().download(info.getUrl(), new DownLoadObserver() {
                            @Override
                            public void onNext(DownloadInfo value) {
                                super.onNext(value);
                                pbDownload.setMax((int) value.getTotal());
                                pbDownload.setProgress((int) value.getProgress());
                            }

                            @Override
                            public void onComplete() {
                                if (downloadInfo != null) {
                                    Toast.makeText(mContext, downloadInfo.getFileName() + "-DownloadComplete", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    break;
                    case R.id.btn_cancel:
                        DownloadManager.getInstance().cancel(info.getUrl());
                        break;
                }
                
            }
        };

        btnStart.setOnClickListener(clickListener);
        btnCancel.setOnClickListener(clickListener);
    }



}
