package com.channey.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by channey on 2017/8/2.
 * mToast util
 */
object ToastUtils {
    private var mToast:Toast? = null

    /** mToast last show time*/
    private var mLastShowTime:Long = 0

    private var DURATION_TIME_OUT = 2000 //ms

    /**
     * show mToast
     * @param context
     * @param msg message for show
     */
    fun showToast(context:Context,msg:String){
        var currentTime = System.currentTimeMillis()
        var temp = currentTime - mLastShowTime
        if (temp >= DURATION_TIME_OUT){
            if (mToast == null){
                mToast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);
            }else{
                mToast!!.setText(msg)
            }
            mLastShowTime = currentTime
            mToast!!.show()
        }
    }

}