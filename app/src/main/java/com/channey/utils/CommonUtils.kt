package com.channey.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

object CommonUtils {

    /**
     * 复制到剪贴板
     * @param context
     * @param content
     */
    fun clipboard(context: Context,content:String){
        val cm:ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Label", content)
        cm.primaryClip = clipData
    }
}