package com.corelibrary.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by Administrator on 2017/8/18.
 */

public class GlideOriginalTransform extends BitmapTransformation {

    public GlideOriginalTransform(Context context) {
        super(context);
    }

    @Override
    public String getId() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap source, int outWidth, int outHeight) {
        if (source == null) return null;

        int width = source.getWidth();
        int height = source.getHeight();
        Bitmap result = pool.get(width, height, Bitmap.Config.RGB_565);
        if (result == null) {
            result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        canvas.drawBitmap(source, 0, 0, paint);
        return result;
    }
}
