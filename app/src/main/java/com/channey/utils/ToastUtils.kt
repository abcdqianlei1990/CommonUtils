package com.channey.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by channey on 2017/8/2.
 * toast util
 */
object ToastUtils {
    lateinit var toast:Toast
    /**
     * show toast
     * @param context
     * @param msg 显示内容
     */
    fun showToast(context:Context,msg:String){
        if(toast == null){
            toast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        }
        toast.show()
    }


}