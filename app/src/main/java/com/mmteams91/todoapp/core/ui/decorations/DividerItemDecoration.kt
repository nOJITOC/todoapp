package com.mmteams91.todoapp.core.ui.decorations

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.ColorInt
import android.support.annotation.IntDef
import android.support.annotation.Px
import android.support.v7.widget.RecyclerView
import android.view.View
import com.mmteams91.todoapp.core.ui.decorations.Gravities.BOTTOM
import com.mmteams91.todoapp.core.ui.decorations.Gravities.LEFT
import com.mmteams91.todoapp.core.ui.decorations.Gravities.RIGHT
import com.mmteams91.todoapp.core.ui.decorations.Gravities.TOP

/**
 * Created by mmaruknin on 29.12.18.
 */
class DividerItemDecoration(var getDividerInfo: (parent: RecyclerView, position: Int) -> ItemDividerInfo?) : RecyclerView.ItemDecoration() {

    private val paint = Paint()
    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        canvas.save()
        val childCount = parent.childCount

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)
            if (position < 0 || position > parent.adapter!!.itemCount - 1) {
                continue
            }
            val info = getDividerInfo.invoke(parent, position) ?: continue
            val rect = Rect()
            parent.getDecoratedBoundsWithMargins(child, rect)
            when (info.gravity) {
                LEFT -> {
                    rect.right = rect.left + info.size
                    rect.top += info.startMargin
                    rect.bottom -= info.endMargin
                }
                RIGHT -> {
                    rect.left = rect.right - info.size
                    rect.top += info.startMargin
                    rect.bottom -= info.endMargin
                }
                TOP -> {
                    rect.bottom = rect.top + info.size
                    rect.left += info.startMargin
                    rect.right -= info.endMargin
                }
                BOTTOM -> {
                    rect.top = rect.bottom - info.size
                    rect.left += info.startMargin
                    rect.right -= info.endMargin
                }
            }
            paint.color = info.color
            canvas.drawRect(rect, paint)
        }
        canvas.restore()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position < 0 || position > parent.adapter!!.itemCount - 1) {
            outRect.setEmpty()
            return
        }
        val info = getDividerInfo.invoke(parent, position)
        if (info == null) {
            outRect.setEmpty()
            return
        }
        val dividerGravity = info.gravity
        var margins = Rect()

        when (dividerGravity) {
            LEFT -> margins.left += info.size
            TOP -> margins.top += info.size
            RIGHT -> margins.right += info.size
            BOTTOM -> margins.bottom += info.size
        }
        outRect.set(margins)
    }
}

@IntDef(value = [LEFT, TOP, RIGHT, BOTTOM]
)
@MustBeDocumented
@Retention
@kotlin.annotation.Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.LOCAL_VARIABLE)
annotation class Gravity

object Gravities {
    const val LEFT = 1
    const val TOP = 2
    const val RIGHT = 3
    const val BOTTOM = 4
}

class ItemDividerInfo(@Px var size: Int, @ColorInt var color: Int, @Gravity var gravity: Int = BOTTOM) {
    var startMargin: Int = 0
    var endMargin: Int = 0

}