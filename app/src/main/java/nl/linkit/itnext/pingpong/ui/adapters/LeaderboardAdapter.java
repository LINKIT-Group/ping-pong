package nl.linkit.itnext.pingpong.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import nl.linkit.itnext.pingpong.R;
import nl.linkit.itnext.pingpong.core.AppDefine;
import nl.linkit.itnext.pingpong.custom.CircleTransform;
import nl.linkit.itnext.pingpong.models.Player;
import nl.linkit.itnext.pingpong.ui.holders.LeaderboardViewHolder;

/**
 * Author: efe.cem.kocabas
 * Date: 12/01/2017.
 */

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardViewHolder> {

    private List<Player> playerList = new ArrayList<>();

    public void setData(List<Player> playerList) {

        this.playerList = playerList;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.playerList.size();
    }

    @Override
    public LeaderboardViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        return new LeaderboardViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_leaderboard, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(LeaderboardViewHolder holder, int position) {

        Player player = playerList.get(position);
        holder.order.setText(String.format(Locale.getDefault(), "%d", position + 1));
        holder.name.setText(player.getName());
        holder.score.setText(String.format(Locale.getDefault(), "%d", player.getScore()));

        String url = AppDefine.BASE_URL + player.getAvatarName();
        Context context = holder.avatar.getContext();
        Picasso.with(context).load(url)
                .transform(new CircleTransform())
                .into(holder.avatar);

    }


}

