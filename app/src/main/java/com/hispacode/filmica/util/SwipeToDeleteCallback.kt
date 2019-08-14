package com.hispacode.filmica.util

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.hispacode.filmica.R

abstract class SwipeToDeleteCallback: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {

    override fun onMove(
        p0: RecyclerView,
        p1: RecyclerView.ViewHolder,
        p2: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView

        //Paint Color
        val color = ContextCompat.getColor(recyclerView.context, R.color.colorPrimaryDark)
        val  background = ColorDrawable(color)
        background.setBounds(
            itemView.left,
            itemView.top,
            itemView.left + dX.toInt(),
            itemView.bottom
        )
        background.draw(c)

        val checkIcon = ContextCompat.getDrawable(recyclerView.context,R.drawable.ic_check)!!

        val iconMargin = (itemView.height - checkIcon.intrinsicHeight) / 3
        val iconTop = itemView.top + (itemView.height - checkIcon.intrinsicHeight) / 2
        val iconLeft = itemView.left + iconMargin
        val iconRight = itemView.left + iconMargin + checkIcon.intrinsicWidth
        val iconBottom = iconTop + checkIcon.intrinsicHeight

        checkIcon.setBounds(
            iconLeft,
            iconTop,
            iconRight,
            iconBottom
        )

        checkIcon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

}