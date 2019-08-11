package com.hispacode.filmica.util

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.hispacode.filmica.R
import kotlin.math.ceil

class GridOffsetDecoration: RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val offset = view.context.resources.getDimensionPixelSize(R.dimen.offset_grid)
        val position = parent.getChildAdapterPosition(view)

        val items = parent.adapter?.itemCount ?: 0
        val columns = (parent.layoutManager as GridLayoutManager).spanCount
        val rows = (items / columns) + 1

        val row = getRow(position, columns)
        val column = getColumn(position,columns)

        val topOffset = if (row == 1) offset else offset / 2
        val leftOffset = if (column == 1) offset else offset / 2

        val bottomOffset = if (row == rows) offset else offset / 2
        val rightOffset = if (column == columns) offset else offset / 2

        outRect.set(leftOffset, topOffset, rightOffset, bottomOffset)

    }

    private fun getRow(position: Int, columns: Int) =
            ceil((position.toDouble() + 1) / columns.toDouble()).toInt()

    private fun getColumn(position: Int, columns: Int) =
            (position % columns) + 1
}