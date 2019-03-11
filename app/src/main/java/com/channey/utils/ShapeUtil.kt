package com.channey.utils

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View

object ShapeUtil {
    const val PROPERTY_SOLID_COLOR = "solidColor"
    const val PROPERTY_STROKE_WIDTH = "strokeWidth"
    const val PROPERTY_STROKE_COLOR = "strokeColor"
    const val PROPERTY_RADIUS = "radius"
    const val PROPERTY_LEFTTOP_RADIUS = "leftTopRadius"
    const val PROPERTY_RIGHTTOP_RADIUS = "rightTopRadius"
    const val PROPERTY_LEFTBOTTOM_RADIUS = "leftBottomRadius"
    const val PROPERTY_RIGHTBOTTOM_RADIUS = "rightBottomRadius"
    const val PROPERTY_GRADIENT_COLORS = "colors"
    const val PROPERTY_ORIENTATION = "orientation"

    /**
     * set shape to target view.
     * @param view target view
     * @param properties config property map
     */
    fun setShape(view: View,properties:Map<String,Any>){
        if (properties.isNotEmpty()){
            var solidColor:Int?
            var strokeColor:Int?
            var strokeWidth:Int?
            var radius:Float?
            var leftTopRadius:Float?
            var rightTopRadius:Float?
            var leftBottomRadius:Float?
            var rightBottomRadius:Float?
            var colors:IntArray?
            var orientation:GradientDrawable.Orientation?
            try {
                solidColor = properties[PROPERTY_SOLID_COLOR] as Int?
            }catch (e:ClassCastException){
                throw Exception("solidColor must be a integer value.")
            }
            try {
                colors = properties[PROPERTY_GRADIENT_COLORS] as IntArray?
            }catch (e:ClassCastException){
                throw Exception("solidColor must be IntArray.")
            }
            try {
                orientation = properties[PROPERTY_ORIENTATION] as GradientDrawable.Orientation?
            }catch (e:ClassCastException){
                throw Exception("solidColor must be a integer value.")
            }
            try {
                strokeColor = properties[PROPERTY_STROKE_COLOR] as Int?
            }catch (e:ClassCastException){
                throw Exception("strokeColor must be a integer value.")
            }
            try {
                strokeWidth = properties[PROPERTY_STROKE_WIDTH] as Int?
            }catch (e:ClassCastException){
                throw Exception("strokeWidth must be a integer value.")
            }
            try {
                radius = properties[PROPERTY_RADIUS] as Float?
            }catch (e:ClassCastException){
                throw Exception("radius must be a float value.")
            }
            try {
                leftTopRadius = properties[PROPERTY_LEFTTOP_RADIUS] as Float?
            }catch (e:ClassCastException){
                throw Exception("leftTopRadius must be a float value.")
            }
            try {
                rightTopRadius = properties[PROPERTY_RIGHTTOP_RADIUS] as Float?
            }catch (e:ClassCastException){
                throw Exception("rightTopRadius must be a float value.")
            }
            try {
                leftBottomRadius = properties[PROPERTY_LEFTBOTTOM_RADIUS] as Float?
            }catch (e:ClassCastException){
                throw Exception("leftBottomRadius must be a float value.")
            }
            try {
                rightBottomRadius = properties[PROPERTY_RIGHTBOTTOM_RADIUS] as Float?
            }catch (e:ClassCastException){
                throw Exception("rightBottomRadius must be a float value.")
            }

            var drawable = GradientDrawable()
            if (solidColor != null) drawable.setColor(solidColor)
            if (colors != null && colors.isNotEmpty()) drawable.colors = colors
            if (orientation != null) drawable.orientation = orientation
            if (strokeColor != null){
                var w = 1
                if (strokeWidth != null) {
                    w =  strokeWidth
                }
                drawable.setStroke(w,strokeColor!!)
            }
            if (leftTopRadius != null || rightTopRadius != null || leftBottomRadius != null || rightBottomRadius != null){
                if (leftTopRadius == null) leftTopRadius = 0f
                if (rightTopRadius == null) rightTopRadius = 0f
                if (leftBottomRadius == null) leftBottomRadius = 0f
                if (rightBottomRadius == null) rightBottomRadius = 0f
                var array = floatArrayOf(
                        leftTopRadius,
                        leftTopRadius,
                        rightTopRadius,
                        rightTopRadius,
                        rightBottomRadius,
                        rightBottomRadius,
                        leftBottomRadius,
                        leftBottomRadius
                )
                drawable.cornerRadii = array
            }else{
                if (radius != null) drawable.cornerRadius = radius
            }
            view.background = drawable
        }else{
            throw Exception("shape properties must not be null.")
        }
    }

    /**
     * set shape to target view.
     * @param view target
     * @param solidColor
     * @param gradientColors
     * @param strokeWidth
     * @param strokeColor
     * @param radius
     */
    fun setShape(
            view: View,
            solidColor:Int? = null,
            gradientColors:IntArray ?= null,
            orientation:GradientDrawable.Orientation ?= null,
            strokeWidth:Int? = null,
            strokeColor:Int? = null,
            radius:Float = 0f,
            leftTopRadius:Float = 0f,
            rightTopRadius:Float = 0f,
            leftBottomRadius:Float = 0f,
            rightBottomRadius:Float = 0f
    ){
        var drawable = GradientDrawable()
        if (solidColor != null) drawable.setColor(solidColor)
        if (gradientColors != null && gradientColors.isNotEmpty()) drawable.colors = gradientColors
        if (orientation != null) drawable.orientation = orientation
        if (strokeColor != null){
            var w = 1
            if (strokeWidth != null){
                w = strokeWidth
            }
            drawable.setStroke(w,strokeColor)
        }
        if (leftTopRadius != 0f || leftBottomRadius != 0f || rightTopRadius != 0f || rightBottomRadius != 0f){
            var array = floatArrayOf(
                    leftTopRadius,
                    leftTopRadius,
                    rightTopRadius,
                    rightTopRadius,
                    rightBottomRadius,
                    rightBottomRadius,
                    leftBottomRadius,
                    leftBottomRadius
            )
            drawable.cornerRadii = array
        }else{
            drawable.cornerRadius = radius
        }
        view.background = drawable
    }

}