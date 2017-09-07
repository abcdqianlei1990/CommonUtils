package com.channey.utils

import java.math.BigDecimal

/**
 * Created by channey on 2017/9/7.
 */
object NumberFormatUtils {

    /**
     * @param number 需要处理掉整数
     * @param countToKeep 需要保留的小数位数
     */
    fun parseToTenThousandKeepPoint(number:Int,countToKeep:Int):String{
        if (number < 10000){
            throw Exception("number must bigger than 10000")
        }
        if (number % 10000 == 0) return (number/10000).toString()

        var number = number / 10000.0
        val b = BigDecimal(number)
        val value = b.setScale(countToKeep, BigDecimal.ROUND_HALF_UP).toDouble()
        return value.toString()
    }
}