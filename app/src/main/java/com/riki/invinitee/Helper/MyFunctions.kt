package com.riki.invinitee.Helper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.URL

object MyFunctions {

    fun loadBitmap(url: String): Bitmap {
        val inputStream: InputStream?
        val bis: BufferedInputStream?
        val conn = URL(url).openConnection()
        conn.connect()
        inputStream = conn.getInputStream()
        bis = BufferedInputStream(inputStream!!, 1024)
        return BitmapFactory.decodeStream(bis)
    }

    fun bitmapToBase64(bitmap: Bitmap?): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 75, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}