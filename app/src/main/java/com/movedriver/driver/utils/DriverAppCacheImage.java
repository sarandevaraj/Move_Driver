package com.movedriver.driver.utils;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.collection.LruCache;

import android.widget.ImageView;

/**
 * Created by developer on 2/11/16.
 */
public class DriverAppCacheImage {
    private static final LruCache<String, Bitmap> mMemoryCache;

    public DriverAppCacheImage() {

    }

    static {
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(@NonNull String key, @NonNull Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        DriverSystems.out.println("Image... storing cache _try");
        if (key != null) if (getBitmapFromMemCache(key) == null) {
            DriverSystems.out.println("Image... storing cache" + key);
            mMemoryCache.put(key, bitmap);
        }
    }

    public static Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    public static boolean loadBitmap(String resId, ImageView imageView) {
//        final String imageKey = String.valueOf(resId);
        boolean imageAvailable = false;
        DriverSystems.out.println("Image... Loaded from cache_try");
        if (resId != null) {

            final Bitmap bitmap = getBitmapFromMemCache(resId);
            if (bitmap != null) {
                DriverSystems.out.println("Image... Loaded from cache");
                imageView.setImageBitmap(bitmap);
                imageAvailable = true;
            }
        }
        return imageAvailable;
    }


}

