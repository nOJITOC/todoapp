package com.mmteams91.todoapp.core.ui.decorations

import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.support.annotation.IntDef
import android.support.annotation.Px
import android.support.v7.widget.RecyclerView
import com.mmteams91.todoapp.core.ui.decorations.Corners.ALL
import com.mmteams91.todoapp.core.ui.decorations.Corners.BOTTOM_LEFT
import com.mmteams91.todoapp.core.ui.decorations.Corners.BOTTOM_RIGHT
import com.mmteams91.todoapp.core.ui.decorations.Corners.NO_GRAVITY
import com.mmteams91.todoapp.core.ui.decorations.Corners.TOP_LEFT
import com.mmteams91.todoapp.core.ui.decorations.Corners.TOP_RIGHT

/**
 * Created by mmaruknin on 29.12.18.
 */
class CornerItemDecoration(var getCornersInfo: (parent: RecyclerView, position: Int) -> ItemCornerInfo?) : RecyclerView.ItemDecoration() {


    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        val path = Path()
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)
            if (position < 0 || position > parent.adapter!!.itemCount - 1) {
                continue
            }
            val info = getCornersInfo.invoke(parent, position) ?: continue
            var rect = Rect()
            parent.getDecoratedBoundsWithMargins(child, rect)
            val radii = getCornersRadii(info)
            path.addRoundRect(rect.let { RectF(it) }, radii, Path.Direction.CW)
        }
        canvas.clipPath(path)
    }

    private fun getCornersRadii(info: ItemCornerInfo): FloatArray {
        return info.run {
            floatArrayOf(topLeft, topLeft,
                    topRight, topRight,
                    bottomRight, bottomRight,
                    bottomLeft, bottomLeft
            )
        }
    }
}


@IntDef(value = [TOP_LEFT, TOP_RIGHT, BOTTOM_RIGHT, BOTTOM_LEFT, NO_GRAVITY, ALL])
@MustBeDocumented
@Retention
@kotlin.annotation.Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.LOCAL_VARIABLE)
annotation class CornerGravity

object Corners {
    const val TOP_LEFT = 0x0001
    const val TOP_RIGHT = 0x0010
    const val BOTTOM_RIGHT = 0x0100
    const val BOTTOM_LEFT = 0x1000
    const val NO_GRAVITY = 0x0000
    const val ALL = 0x1111
}

class ItemCornerInfo {
    var topLeft = 0f
    var topRight = 0f
    var bottomRight = 0f
    var bottomLeft = 0f

    fun addCorner(@CornerGravity gravity: Int, @Px radius: Int) {
        if (gravity and TOP_LEFT == TOP_LEFT) topLeft = radius.toFloat()
        if (gravity and TOP_RIGHT == TOP_RIGHT) topRight = radius.toFloat()
        if (gravity and BOTTOM_RIGHT == BOTTOM_RIGHT) bottomRight = radius.toFloat()
        if (gravity and BOTTOM_LEFT == BOTTOM_LEFT) bottomLeft = radius.toFloat()
    }
}