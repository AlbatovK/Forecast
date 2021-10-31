package com.albatros.forecast.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.*

private const val link = "https://yastatic.net/weather/i/icons/funky/dark/%s.svg"

fun ImageView.loadSvgInto(url: String) {
    ImageLoader.Builder(context).let {
        it.componentRegistry { add(SvgDecoder(context)) }
        it.build()
    }.let { loader ->
        ImageRequest.Builder(context).let {
            it.crossfade(true)
            it.crossfade(500)
            it.data(link.format(url))
            it.target(this@loadSvgInto)
            it.build()
        }.let {
            loader.enqueue(it)
        }
    }
}

fun bitmapFromSvgAsync(url: String, context: Context): Deferred<Bitmap> = GlobalScope.async {
    ImageLoader.Builder(context).let {
        it.componentRegistry { add(SvgDecoder(context)) }
        it.build()
    }.let { loader ->
        ImageRequest.Builder(context).let {
            it.data(link.format(url))
            it.allowHardware(false)
            it.build()
        }.let {
            val result = (loader.execute(it) as SuccessResult).drawable
            return@async (result as BitmapDrawable).bitmap
        }
    }
}