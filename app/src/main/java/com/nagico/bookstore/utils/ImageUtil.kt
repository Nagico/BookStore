package com.nagico.bookstore.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import java.io.ByteArrayOutputStream

object ImageUtil {
    fun getBytesFromBitmap(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    fun getBitmapFromBytes(bytes: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    /**
     * bitmap -  drawable
     * @param context
     * @param bm
     * @return
     */
    fun getDrawable(context: Context, bm: Bitmap?): Drawable {
        return BitmapDrawable(context.resources, bm)
    }
}