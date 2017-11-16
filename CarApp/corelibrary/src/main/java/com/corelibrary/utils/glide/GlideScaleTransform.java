package com.corelibrary.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by Administrator on 2017/4/26.
 */
public class GlideScaleTransform extends BitmapTransformation {

    private float scale;

    public GlideScaleTransform(Context context, float scale) {
        super(context);
        this.scale = scale;
    }


    @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        if (toTransform == null) return null;

        int width;
        int height;
        int x;
        int y;
        int sourceWidth = toTransform.getWidth();
        int sourceHeight = toTransform.getHeight();
        if (sourceHeight * scale >= sourceWidth) {
            width = sourceWidth;
            height = (int) (sourceWidth / scale);
            x = 0;
            y = (sourceHeight - height) / 2;
        } else {
            height = sourceHeight;
            width = (int) (sourceHeight * scale);
            x = (sourceWidth - width) / 2;
            y = 0;
        }
        return Bitmap.createBitmap(toTransform, x, y, width, height);
    }

    @Override public String getId() {
        return getClass().getName() + "scale=" + scale;
    }
}
