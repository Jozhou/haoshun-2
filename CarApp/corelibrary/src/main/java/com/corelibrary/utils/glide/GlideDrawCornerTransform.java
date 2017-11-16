package com.corelibrary.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by Administrator on 2017/4/26.
 */
public class GlideDrawCornerTransform extends BitmapTransformation {
    private int radius;
    boolean left_top;
    boolean right_top;
    boolean right_bottom;
    boolean left_bottom;

    public GlideDrawCornerTransform(Context context, int radius, boolean left_top, boolean right_top, boolean right_bottom, boolean left_bottom) {
        super(context);
        this.radius = radius;
        this.left_top = left_top;
        this.right_top = right_top;
        this.right_bottom = right_bottom;
        this.left_bottom = left_bottom;
    }

    @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return RoundCornerCrop(pool, toTransform);
    }

    private Bitmap RoundCornerCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap output = Bitmap.createBitmap(source.getWidth(),
                source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, source.getWidth(), source.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        if (!left_top) {
            canvas.drawRect(0, 0, radius, radius, paint);
        }
        if (!right_top) {
            canvas.drawRect(rect.right - radius, 0, rect.right, radius, paint);
        }
        if (!right_bottom) {
            canvas.drawRect(rect.right - radius, rect.bottom - radius, rect.right, rect.bottom, paint);
        }
        if (!left_bottom) {
            canvas.drawRect(0, rect.bottom - radius, radius, rect.bottom, paint);
        }
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, rect, rect, paint);

        return output;
    }

    @Override public String getId() {
        return getClass().getName() + radius;
    }
}
