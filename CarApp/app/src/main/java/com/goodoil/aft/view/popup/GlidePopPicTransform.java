package com.goodoil.aft.view.popup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by Administrator on 2017/4/26.
 */
public class GlidePopPicTransform extends BitmapTransformation {

    private int width;
    private int height;

    public GlidePopPicTransform(Context context, int width, int height) {
        super(context);
        this.width = width;
        this.height = height;
    }


    @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        if (toTransform == null) return null;

        int sourceWidth = toTransform.getWidth();
        int sourceHeight = toTransform.getHeight();

        if (sourceWidth * height >= sourceHeight * width) {
            int resultWidth = sourceHeight * width / height;
            int resultHeight = sourceHeight;
            int x = (sourceWidth - resultWidth) / 2;
            int y = 0;
            Matrix matrix = new Matrix();
            float scale = width * 1.0f / resultWidth;
            matrix.setScale(scale, scale);
            return toTransform.createBitmap(toTransform, x, y, resultWidth, resultHeight, matrix, false);
        } else {
            int resultWidth = sourceWidth;
            int resultHeight = sourceWidth * height / width;
            int y = (sourceHeight - resultHeight) / 2;
            int x = 0;
            Matrix matrix = new Matrix();
            float scale = height * 1.0f / resultHeight;
            matrix.setScale(scale, scale);
            return toTransform.createBitmap(toTransform, x, y, resultWidth, resultHeight, matrix, false);
        }
    }

    @Override public String getId() {
        return getClass().getName() + "width=" + width + ",height=" + height;
    }
}
