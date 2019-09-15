package yapp14th.co.kr.myplant.utils

import android.graphics.Bitmap
import android.util.Log
import java.io.*

fun getFile(file: File, bitmap : Bitmap): File {
    Log.d("FilePath : ", file.absolutePath)
    var fos: FileOutputStream? = null
    try {
        val bos = ByteArrayOutputStream()
        // bitmap = imgRotate(bitmap!!, orientation.toFloat())
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos)
        val bitmapData = bos.toByteArray()

        fos = FileOutputStream(file)
        fos.write(bitmapData)
        fos.flush()
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        fos!!.close()
    }

    return file
}

fun copyFile(pivotFile: File, newFile: File): File {
    var result: Boolean
    if (pivotFile.exists()) {
        try {
            var fis = FileInputStream(pivotFile)
            var newfos = FileOutputStream(newFile)
            var buffer = ByteArray(1024)
            var readcount = fis.read(buffer, 0, 1024)
            while (readcount != -1) {
                newfos.write(buffer, 0, readcount)
                readcount = fis.read(buffer, 0, 1024)
            }
            newfos.close()
            fis.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        result = true
    } else {
        result = false
    }
    return newFile
}