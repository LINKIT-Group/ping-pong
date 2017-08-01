package nl.linkit.itnext.pingpong.ui.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import nl.linkit.itnext.pingpong.R;

/**
 * Created by efe.cem.kocabas on 12/01/2017.
 */

public class LeaderboardViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.order_textview)
    public TextView order;

    @BindView(R.id.avatar_imageview)
    public ImageView avatar;

    @BindView(R.id.name_textview)
    public TextView name;

    @BindView(R.id.score_textview)
    public TextView score;

    public LeaderboardViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
