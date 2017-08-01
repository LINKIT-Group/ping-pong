package nl.linkit.itnext.pingpong.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.linkit.itnext.pingpong.R;
import nl.linkit.itnext.pingpong.core.PPApplication;
import nl.linkit.itnext.pingpong.managers.DBManager;
import nl.linkit.itnext.pingpong.managers.LogManager;
import nl.linkit.itnext.pingpong.managers.RestManager;
import nl.linkit.itnext.pingpong.managers.SchedulersManager;
import nl.linkit.itnext.pingpong.models.Player;
import nl.linkit.itnext.pingpong.ui.adapters.LeaderboardAdapter;
import nl.linkit.itnext.pingpong.ui.common.LeaderboardItemDecoration;
import nl.linkit.itnext.pingpong.ui.contracts.LeaderboardContract;
import nl.linkit.itnext.pingpong.ui.presenters.LeaderboardPresenter;

/**
 * Developer: efe.kocabas
 * Date: 18/07/2017.
 */

public class LeaderboardActivity extends AppCompatActivity implements LeaderboardContract.View {

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    @Inject
    RestManager restManager;

    @Inject
    DBManager DBManager;

    @Inject
    SchedulersManager schedulersManager;

    @Inject
    LogManager logManager;

    LeaderboardPresenter presenter;
    LeaderboardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        ButterKnife.bind(this);

        PPApplication.getAppComponent().inject(this);
        adapter = new LeaderboardAdapter();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new LeaderboardItemDecoration());

        presenter = new LeaderboardPresenter(this, restManager, schedulersManager, logManager, DBManager);
        presenter.onCreate();

    }

    @Override
    public void showPlayers(List<Player> players) {

        adapter.setData(players);
    }

    @OnClick(R.id.start_button)
    public void startButtonClick() {

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);

    }
}
