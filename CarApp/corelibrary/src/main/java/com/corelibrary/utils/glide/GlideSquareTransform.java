package com.corelibrary.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by Administrator on 2017/4/26.
 */
public class GlideSquareTransform extends BitmapTransformation {
    public GlideSquareTransform(Context context) {
        super(context);
    }

    @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return squareCrop(pool, toTransform);
    }

    private Bitmap squareCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap result = pool.get(size, size, Bitmap.Config.RGB_565);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        canvas.drawBitmap(source, -x, -y, paint);
        return result;
    }

    @Override public String getId() {
        return getClass().getName();
    }
}
