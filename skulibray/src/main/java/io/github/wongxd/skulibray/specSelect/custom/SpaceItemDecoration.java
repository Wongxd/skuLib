package io.github.wongxd.skulibray.specSelect.custom;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int c;
    private int r;

    /**
     *
     * @param row 竖直间距
     * @param clo 水平间距
     */
    public SpaceItemDecoration(int row,int clo) {
        this.r = row;
        this.c = clo;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = r;
        outRect.left = c;
        outRect.right = c;
        outRect.bottom = r;
    }
}