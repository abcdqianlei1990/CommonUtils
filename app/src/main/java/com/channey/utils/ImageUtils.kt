package com.channey.utils

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Base64
import android.media.ExifInterface
import android.net.Uri
import android.util.Log
import android.widget.Toast
import java.io.*


/**
 * Created by channey on 2017/8/15.
 */
object ImageUtils {
    /**
     * 将Drawable转为Bitmap
     * @param drawable
     */
    fun drawable2Bitmap(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return bitmap

    }

    fun bitmapToBase64(bitmap: Bitmap): String? {
        var result: String? = null
        var baos: ByteArrayOutputStream? = null
        try {
            if (bitmap != null) {
                baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

                baos.flush()
                baos.close()

                val bitmapBytes = baos.toByteArray()
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                if (baos != null) {
                    baos.flush()
                    baos.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return result
    }

    fun compressByScale(image: Bitmap): Bitmap {

        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        // 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
        if (baos.toByteArray().size / 1024 > 1024) {
            baos.reset()// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 80, baos)// 这里压缩50%，把压缩后的数据存放到baos中
        }
        var isBm = ByteArrayInputStream(baos.toByteArray())
        val newOpts = BitmapFactory.Options()
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true
        var bitmap = BitmapFactory.decodeStream(isBm, null, newOpts)
        newOpts.inJustDecodeBounds = false
        val w = newOpts.outWidth
        val h = newOpts.outHeight
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        // float hh = 800f;// 这里设置高度为800f
        // float ww = 480f;// 这里设置宽度为480f
        val hh = 512f
        val ww = 512f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        var be = 1// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (newOpts.outWidth / ww).toInt()
        } else if (w < h && h > hh) { // 如果高度高的话根据高度固定大小缩放
            be = (newOpts.outHeight / hh).toInt()
        }
        if (be <= 0)
            be = 1
        newOpts.inSampleSize = be // 设置缩放比例
        // newOpts.inPreferredConfig = Config.RGB_565;//降低图片从ARGB888到RGB565

        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = ByteArrayInputStream(baos.toByteArray())
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts)

        return compressImage(bitmap)// 压缩好比例大小后再进行质量压缩

        //return bitmap;
    }

    /**
     * 质量压缩方法

     * @param image
     * *
     * @return
     */
    fun compressImage(image: Bitmap): Bitmap {

        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        var options = 90

        while (baos.toByteArray().size / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset() // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos)// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10// 每次都减少10
        }
        val isBm = ByteArrayInputStream(baos.toByteArray())// 把压缩后的数据baos存放到ByteArrayInputStream中
        val bitmap = BitmapFactory.decodeStream(isBm, null, null)// 把ByteArrayInputStream数据生成图片
        return bitmap
    }

    /**
     * 保存图片到本地
     * @param context
     * @param path 图片源文件路径
     * @param name 目标文件名
     */
    fun saveImg2Local(context: Context, path: String?, name:String):Boolean{
        if (StringUtils.isEmpty(path)) return false
        try {
            var sourceFile = File(path)
            var sdcardPath = System.getenv("EXTERNAL_STORAGE")  //获得sd卡路径
            var dir = "$sdcardPath/"    //图片保存的文件夹名
            var dirFile = File(dir)    //已File来构建
            if (!dirFile.exists()) {   //如果不存在  就mkdirs()创建此文件夹
                dirFile.mkdirs()
            }
            var file = File(dir + name)    //将要保存的图片文件
            if (file.exists()) {
                Toast.makeText(context, "该图片已存在!", Toast.LENGTH_SHORT).show()
                return false
            }
            sourceFile.copyTo(file,true)
            var uri = Uri.fromFile(file)    //获得图片的uri
            context.sendBroadcast( Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))  //发送广播通知更新图库，这样系统图库可以找到这张图片
            return true

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 保存图片到本地
     * @param context
     * @param bitmap 图片源文件
     * @param name 目标文件名
     */
    fun saveImg2Local(context: Context, bitmap: Bitmap, name:String):Boolean{
        try {
            var sdcardPath = System.getenv("EXTERNAL_STORAGE")  //获得sd卡路径
            var dir = "$sdcardPath/"    //图片保存的文件夹名
            var dirFile = File(dir)    //已File来构建
            if (!dirFile.exists()) {   //如果不存在  就mkdirs()创建此文件夹
                dirFile.mkdirs()
            }
            var file = File(dir + name)    //将要保存的图片文件
            if (file.exists()) {
                Toast.makeText(context, "该图片已存在!", Toast.LENGTH_SHORT).show()
                return false
            }

            var outputStream = FileOutputStream(file)   //构建输出流
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)  //compress到输出outputStream
            var uri = Uri.fromFile(file)    //获得图片的uri
            context.sendBroadcast( Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))  //发送广播通知更新图库，这样系统图库可以找到这张图片
            return true

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 读取图片属性：旋转的角度
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */

    fun getImageDegree(path:String):Int {
        var degree = 0;
        try {
            var exifInterface = ExifInterface(path);
            var orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
            }
        } catch (e: IOException) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     * @param degree
     * @param bitmap
     * @return Bitmap
     */
    fun rotateImage(degree: Int, bitmap: Bitmap): Bitmap {
        //旋转图片 动作
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        // 创建新的图片
        return Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.width, bitmap.height, matrix, true)
    }
}