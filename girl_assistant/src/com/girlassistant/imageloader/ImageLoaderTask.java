package com.girlassistant.imageloader;

import java.io.File;

import com.girlassistant.base.App;
import com.girlassistant.base.ImageCache;
import com.girlassistant.utils.CommonUtils;
import com.girlassistant.utils.ImageUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Process;


public class ImageLoaderTask implements Runnable {
    public String imgUrl;
    private boolean cacheOnly;
    public ImageLoaderCallback callback;
    @SuppressWarnings("unused")
	private Context appContext;
    private ImageCache imageCache;
    public int imageWidth = -1;
    public int imageHeight = -1;

    public ImageLoaderTask(String imageUrl, ImageLoaderCallback callback, boolean cacheOnly, int reqWidth, int reqHeight) {
        this.imgUrl = imageUrl;
        this.callback = callback;
        this.cacheOnly = cacheOnly;
        this.imageWidth = reqWidth;
        this.imageHeight = reqHeight;
        this.appContext = App.getInstance().getApplicationContext();
        this.imageCache = ImageCache.getInstance();
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        Bitmap bitmap = null;
        if (imageWidth != -1 && imageHeight != -1) {
            bitmap = imageCache.getBitmapFromCache(imgUrl, imageWidth, imageHeight);
        } else {
            bitmap = imageCache.getBitmapFromDisk(imgUrl);
        }
        if (null == bitmap && !cacheOnly && CommonUtils.isNetworkConnected()) {
            bitmap = downloadBitmapFromNet(imgUrl);
        }
        if (null != callback && bitmap != null) {
            callback.sendLoadedMessage(bitmap);
        }
        callback = null;
        imageCache = null;
        bitmap = null;
    }

    public Bitmap downloadBitmapFromNet(String imgUrl) {
        File aimfile = imageCache.getFileForKey(imgUrl);
        boolean isSuccess = ImageManager.download(imgUrl, aimfile);
        if (isSuccess) {
            if (imageWidth != -1 && imageHeight != -1) {
                return ImageUtils.decodeSampledBitmapFromFile(aimfile.getPath(), imageWidth, imageHeight);
            } else {
                return ImageUtils.readImageFromFile(aimfile);
            }
            
        }
        return null;

    }
}
