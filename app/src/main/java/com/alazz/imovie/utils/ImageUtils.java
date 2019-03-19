package com.alazz.imovie.utils;

import android.content.Context;
import android.widget.ImageView;

import com.alazz.imovie.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import static com.alazz.imovie.utils.Constant.BASE_URL_IMAGE;


public class ImageUtils {


    public static  void onLoadImage(Context context, String url, ImageView imageView){

        Glide.with(context)
                .load(BASE_URL_IMAGE.concat(url))
                .apply(RequestOptions.noTransformation()
                        .placeholder(R.drawable.ic_placeholder).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);

    }
}
