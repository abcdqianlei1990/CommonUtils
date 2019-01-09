package com.channey.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet

import com.channey.utils.R


/**
 * 可设置圆角的ImageView
 * @author channey
 * @date 20190109
 */
class RoundedImageView : AppCompatImageView {
    private var mRadius = DEFAULT_RADIUS
    private var mTopLeftRadius = DEFAULT_RADIUS
    private var mTopRightRadius = DEFAULT_RADIUS
    private var mBottomLeftRadius = DEFAULT_RADIUS
    private var mBottomRightRadius = DEFAULT_RADIUS
    private var paint: Paint? = null

    companion object {
        private const val TAG = "RoundedImageView"
        private const val DEFAULT_RADIUS = 0f
    }

    private fun initPaint() {
        paint = Paint()
        paint!!.color = Color.WHITE
        paint!!.isAntiAlias = true
    }

    constructor(context: Context) : super(context) {
        initPaint()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        getAttrs(attrs)
        initPaint()
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.RoundedImageView)
        mRadius = array.getDimension(R.styleable.RoundedImageView_riv_radius, DEFAULT_RADIUS)
        mTopLeftRadius = array.getDimension(R.styleable.RoundedImageView_riv_topLeftRadius, DEFAULT_RADIUS)
        mTopRightRadius = array.getDimension(R.styleable.RoundedImageView_riv_topRightRadius, DEFAULT_RADIUS)
        mBottomLeftRadius = array.getDimension(R.styleable.RoundedImageView_riv_bottomLeftRadius, DEFAULT_RADIUS)
        mBottomRightRadius = array.getDimension(R.styleable.RoundedImageView_riv_bottomRightRadius, DEFAULT_RADIUS)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawLeftTop(canvas)
        drawRightTop(canvas)
        drawLeftBottom(canvas)
        drawRightBottom(canvas)
    }

    private fun drawLeftTop(canvas: Canvas) {
        val path = Path()
        val radius = if (mTopLeftRadius != DEFAULT_RADIUS) mTopLeftRadius else mRadius
        path.moveTo(0f, radius)
        path.lineTo(0f, 0f)
        path.lineTo(radius, 0f)
        path.arcTo(RectF(0f, 0f, radius * 2, radius * 2), -90f, -90f)
        path.close()
        canvas.drawPath(path, paint!!)
    }

    private fun drawLeftBottom(canvas: Canvas) {
        val path = Path()
        val radius = if (mBottomLeftRadius != DEFAULT_RADIUS) mBottomLeftRadius else mRadius
        path.moveTo(0f, height - radius)
        path.lineTo(0f, height.toFloat())
        path.lineTo(radius, height.toFloat())
        path.arcTo(RectF(0f, height - radius * 2, radius * 2, height.toFloat()), 90f, 90f)
        path.close()
        canvas.drawPath(path, paint!!)
    }

    private fun drawRightBottom(canvas: Canvas) {
        val path = Path()
        val radius = if (mBottomRightRadius != DEFAULT_RADIUS) mBottomRightRadius else mRadius
        path.moveTo(width - radius, height.toFloat())
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(width.toFloat(), height - radius)
        val oval = RectF(width - radius * 2, height - radius * 2, width.toFloat(), height.toFloat())
        path.arcTo(oval, 0f, 90f)
        path.close()
        canvas.drawPath(path, paint!!)
    }

    private fun drawRightTop(canvas: Canvas) {
        val path = Path()
        val radius = if (mTopRightRadius != DEFAULT_RADIUS) mTopRightRadius else mRadius
        path.moveTo(width.toFloat(), radius)
        path.lineTo(width.toFloat(), 0f)
        path.lineTo(width - radius, 0f)
        path.arcTo(RectF(width - radius * 2, 0f, width.toFloat(), 0 + radius * 2), -90f, 90f)
        path.close()
        canvas.drawPath(path, paint!!)
    }
}
