package com.channey.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator

/**
 * Created by channey on 2017/8/2.
 */
object AnimatorUtils {
    /**
     * 对象点击缩放动画
     * @param target
     */
    fun performClickAnimator(target: Any) {
        val animator1 = ObjectAnimator.ofFloat(target, "scaleX", 1.0f, 0.9f, 1.0f)
        val animator2 = ObjectAnimator.ofFloat(target, "scaleY", 1.0f, 0.9f, 1.0f)
        val set = AnimatorSet()
        set.play(animator1)
        set.play(animator2)
        set.duration = 500
        set.start()
    }
}