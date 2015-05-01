package com.aluxian.loremsql

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

public class ItemSpacingDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = (view.getLayoutParams() as RecyclerView.LayoutParams).getViewPosition()
        val margin = view.getContext().getResources().getDimensionPixelSize(R.dimen.list_margin)

        outRect.left = margin
        outRect.right = margin
        outRect.top = margin
        outRect.bottom = if (position == parent.getAdapter().getItemCount() - 1) margin else 0
    }

}
