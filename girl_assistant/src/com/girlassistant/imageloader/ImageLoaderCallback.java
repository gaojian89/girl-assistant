package com.girlassistant.imageloader;

import android.graphics.Bitmap;

public interface ImageLoaderCallback {

    public boolean handleImageLoaded(Bitmap bm);

    public void sendLoadedMessage(Bitmap bm);

}
