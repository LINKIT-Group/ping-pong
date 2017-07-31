package nl.linkit.itnext.pingpong.ui.common;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import nl.linkit.itnext.pingpong.R;

/**
 * Developer: efe.kocabas
 * Date: 20/07/2017.
 */

public class LeaderboardItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        int genericMargin = view.getContext().getResources().getDimensionPixelOffset(R.dimen.generic_margin);
        boolean isTop = parent.getChildLayoutPosition(view) == 0;

        outRect.left = genericMargin;
        outRect.right = genericMargin;
        outRect.bottom = genericMargin;
        outRect.top = isTop ? genericMargin : 0;
    }
}
