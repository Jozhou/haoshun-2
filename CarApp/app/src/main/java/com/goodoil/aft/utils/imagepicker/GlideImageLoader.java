package com.goodoil.aft.utils.imagepicker;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.loader.ImageLoader;

/**
 * Created by Administrator on 2017/5/6.
 */
public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
