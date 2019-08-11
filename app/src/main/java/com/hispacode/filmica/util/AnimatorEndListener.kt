package com.hispacode.filmica.util

import android.animation.Animator

class AnimatorEndListener(
    private val callback: (Animator) -> Unit
): Animator.AnimatorListener {
    override fun onAnimationRepeat(animation: Animator) {
    }
    override fun onAnimationCancel(animation: Animator) {
    }
    override fun onAnimationStart(animation: Animator) {
    }
    override fun onAnimationEnd(animation: Animator) {
        callback.invoke(animation)
    }
}