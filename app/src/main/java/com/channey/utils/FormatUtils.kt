package com.channey.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by channey on 2017/8/2.
 * 格式转换的工具类.
 * exp: 时间戳 <-> 日期字符串
 */
object FormatUtils {
    /**
     * 将字符串格式时间转换为时间戳
     * @param format 日期格式，如2016年11月28日，2016.11.28
     * *
     * @param date  时间戳
     * *
     * @return
     */
    fun string2TimeStamp(format: String, date: String): Long {
        var temp: Long = 0
        val sdf = SimpleDateFormat(format)
        sdf.timeZone = TimeZone.getTimeZone("GMT+8") //设置时区
        try {
            val d = sdf.parse(date)
            temp = d.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return temp
    }

    /**
     * 将时间戳转为指定格式的日期格式
     * @param stamp 时间戳字符串
     * *
     * @param format 想转化的日期格式 {@link Keys.DateFormat}
     * *
     * @return
     */
    fun timeStamp2String(stamp: Long, format: String): String {
        var date: String? = null
        val sdf = SimpleDateFormat(format)
        sdf.timeZone = TimeZone.getTimeZone("GMT+8") //设置时区
        date = sdf.format(Date(stamp))//单位秒
        return date
    }

    /**
     * 将dip数值转为pixel
     * @param dip
     */
    fun dip2px(context: Context, dip: Int): Int {
        val dm = context.resources.displayMetrics
        val dpi = dm.densityDpi
        return dip * (dpi / 160)
    }

    /**
     * 将pixel数值转为dip
     * @param px
     */
    fun px2dip(context: Context, px: Int): Int {
        val dm = context.resources.displayMetrics
        val dpi = dm.densityDpi
        return px * 160 / dpi
    }
}