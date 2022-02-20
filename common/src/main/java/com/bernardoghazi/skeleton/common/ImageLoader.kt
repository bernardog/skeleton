package com.bernardoghazi.skeleton.common

import android.content.Context
import android.widget.ImageView
import coil.Coil
import coil.ImageLoader
import coil.load
import coil.util.CoilUtils
import okhttp3.OkHttpClient

class ImageLoader(context: Context) {

    init {
        Coil.setImageLoader(
            ImageLoader.Builder(context)
                .crossfade(true)
                .error(R.drawable.broken_image)
                .okHttpClient(
                    OkHttpClient.Builder().cache(
                        CoilUtils.createDefaultCache(context)
                    ).build()
                ).build()
        )
    }

    fun loadImageByUrl(url: String, imageView: ImageView) {
        imageView.load(url)
    }
}
