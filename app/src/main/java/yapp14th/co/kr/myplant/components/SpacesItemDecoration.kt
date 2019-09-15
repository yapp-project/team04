package yapp14th.co.kr.myplant.components

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration(offset: Int) : RecyclerView.ItemDecoration() {

    private var offset: Int = offset

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        outRect.left = offset
        outRect.right = offset;
        outRect.bottom = offset;
    }
}