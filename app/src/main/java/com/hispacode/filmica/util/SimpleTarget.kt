package com.hispacode.filmica.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class SimpleTarget(
    private val callback: (Bitmap) -> Unit
): Target {
    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
    }
    override fun onBitmapFailed(errorDrawable: Drawable?) {
    }
    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
        callback.invoke(bitmap)
    }
}