package utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by viqgd on 2017/2/1.
 */

public class WidGetUtils {

    /**
     * @Description:  获取磁盘空间大小
     * @param @return
     * @return long
     * @throws
     */
    @SuppressLint("NewApi")
    public static long getAvailaleSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(path.getPath());
        long blockSize = statFs.getBlockSizeLong();
        long availableBlocks = statFs.getAvailableBlocksLong();
        long size = (availableBlocks * blockSize) / 1024 / 1024;
        return size;
    }

    @SuppressLint("SdCardPath")
    public static String getImagePathFromUri(Uri fileUrl, Context c) {
        String fileName = null;
        Uri filePathUri = fileUrl;
        if (fileUrl != null) {
            if (fileUrl.getScheme().toString().compareTo("content") == 0) {
                // content:// uri
                Cursor cursor = c.getContentResolver().query(fileUrl, null,
                        null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int column_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    fileName = cursor.getString(column_index); // 图片名称

                    // Android 4.1
                    // /storage/sdcard0
                    if (!fileName.startsWith("/storage")
                            && !fileName.startsWith("/mnt")) {
                        //拼接 /mnt+fileName
                        fileName = "/mnt" + fileName;
                    }
                    cursor.close();
                }
            } else if (fileUrl.getScheme().compareTo("file") == 0) // 以file 为开头时
            {
                fileName = filePathUri.toString();// 图片名称
                fileName = filePathUri.toString().replace("file://", "");
                int index = fileName.indexOf("/sdcard");
                fileName = index == -1 ? fileName : fileName.substring(index);

                if (!fileName.startsWith("/mnt")) {
                    //图片路径
                    fileName += "/mnt";
                }
            }
        }
        return fileName;
    }

    /**
     * 根据Uri获取图片的路径
     *
     * @param uri
     * @return
     */
    public  static String getPathByUri(Activity act, Uri uri) {
        String path = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor actualimagecursor = act.managedQuery(uri, proj, null, null, null);
        if (actualimagecursor == null) {
            path = getImagePathFromUri(uri, act);
            return path;
        }
        int actual_image_column_index = actualimagecursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        path = actualimagecursor.getString(actual_image_column_index);
        return path;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getPhotoFileName(String id) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'" + id
                + "'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpeg";

    }


}
