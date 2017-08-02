package com.channey.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.StrictMode
import android.text.TextUtils
import java.io.IOException
import java.io.InputStreamReader
import java.io.LineNumberReader

/**
 * Created by channey on 2017/8/2.
 * 设备工具类，主要用于获取设备的各种信息.
 */
object DeviceUtils {
    /**
     * 获取当前网络连接的类型信息
     * @return one of {@link ConnectivityManager#TYPE_MOBILE}, {@link
     * ConnectivityManager#TYPE_WIFI}, {@link ConnectivityManager#TYPE_WIMAX}, {@link
     * ConnectivityManager#TYPE_ETHERNET},  {@link ConnectivityManager#TYPE_BLUETOOTH}, or other
     * types defined by {@link ConnectivityManager}
     */
    fun getNetworkConnectedType(context: Context): Int {
        val mConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mNetworkInfo = mConnectivityManager.activeNetworkInfo
        if (mNetworkInfo != null && mNetworkInfo.isAvailable) {
            return mNetworkInfo.type
        }
        return -1
    }

    /**
     * 判断当前网络是否可用
     */
    fun isNetworkAvailable(context: Context): Boolean {
        var type = getNetworkConnectedType(context);
        return type >= 0
    }

    /**
     * 通过cat命令查看设备网卡信息并获取设备mac地址
     */
    fun getMacAddress(): String {
        var macSerial: String? = null
        var str: String? = ""
        val old = StrictMode.getThreadPolicy()
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder(old)
                .permitDiskWrites()
                .build())
        try {
            val pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address")
            val ir = InputStreamReader(pp.inputStream)
            val input = LineNumberReader(ir)

            while (null != str) {
                str = input.readLine()
                if (str != null) {
                    macSerial = str.trim { it <= ' ' }
                    break
                }
            }
        } catch (ex: IOException) {
            // 赋予默认值
            ex.printStackTrace()
        }
        StrictMode.setThreadPolicy(old)
        if (TextUtils.isEmpty(macSerial)) {
            macSerial = "02:00:00:00:00"
        }
        return macSerial!!
    }

//    private fun getMacAddressFromWlan(): String {
//        var result = ""
//        try {
//            val all = Collections.list(NetworkInterface.getNetworkInterfaces())
//            for (nif in all) {
//                if (!nif.name.equals("wlan0", ignoreCase = true)) continue
//
//                val macBytes = nif.hardwareAddress ?: return ""
//
//                val res1 = StringBuilder()
//                for (b in macBytes) {
//                    res1.append(Integer.toHexString(b and 0xFF) + ":")
//                }
//
//                if (res1.length > 0) {
//                    res1.deleteCharAt(res1.length - 1)
//                }
//                result = res1.toString()
//            }
//        } catch (ex: Exception) {
//        }
//        return result
//    }
}