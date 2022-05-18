package com.nagico.bookstore.utils

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

object ShapeableImageLoader{
    @BindingAdapter("load")
    @JvmStatic
    open fun loadImage(
        imageView: ShapeableImageView?,
        url: String?
    ) {
        if(imageView==null)
            return
        Glide.with(imageView!!.context)
            .load(url)
            .into(imageView)
    }

    @BindingAdapter("color")
    @JvmStatic
    open fun setColor(
        imageView: ShapeableImageView?,
        color: String?
    ) {
        if(imageView==null)
            return
        imageView.setBackgroundColor(android.graphics.Color.parseColor(color!!))
    }
}