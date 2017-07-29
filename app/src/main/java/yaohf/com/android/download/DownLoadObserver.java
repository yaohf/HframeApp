package yaohf.com.android.download;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import yaohf.com.api.info.DownloadInfo;
import yaohf.com.tool.L;

public  abstract class DownLoadObserver implements Observer<DownloadInfo> {
    protected Disposable d;//可以用于取消注册的监听者
    protected DownloadInfo downloadInfo;
    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        L.v("Disposable dispose");
        d.dispose();
    }

    @Override
    public void onNext(DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
