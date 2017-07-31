package nl.linkit.itnext.pingpong.ui.presenters;


import nl.linkit.itnext.pingpong.core.AppDefine;
import nl.linkit.itnext.pingpong.managers.DBManager;
import nl.linkit.itnext.pingpong.managers.LogManager;
import nl.linkit.itnext.pingpong.managers.RestManager;
import nl.linkit.itnext.pingpong.managers.SchedulersManager;
import nl.linkit.itnext.pingpong.ui.contracts.LeaderboardContract;

/**
 * Developer: efe.kocabas
 * Date: 18/07/2017.
 */

public class LeaderboardPresenter implements LeaderboardContract.Presenter {

    private final static String PINGPONG_ENDPOINT = AppDefine.BASE_URL + "players.json";

    private final LeaderboardContract.View view;
    private final RestManager restManager;
    private final SchedulersManager schedulersManager;
    private final LogManager logManager;
    private final DBManager dbManager;

    public LeaderboardPresenter(LeaderboardContract.View view,
                                RestManager restManager,
                                SchedulersManager schedulersManager,
                                LogManager logManager,
                                DBManager dbManager) {

        this.view = view;
        this.restManager = restManager;
        this.schedulersManager = schedulersManager;
        this.logManager = logManager;
        this.dbManager = dbManager;
    }

    @Override
    public void onCreate() {

        downloadPlayers();

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void downloadPlayers() {

        restManager.getPingpongApi().getPlayers(PINGPONG_ENDPOINT)
                .flatMap(players -> {
                    dbManager.insertPlayers(players);
                    return dbManager.getPlayers();
                })
                .subscribeOn(schedulersManager.background())
                .observeOn(schedulersManager.ui())
                .subscribe(view::showPlayers, logManager::logError);
    }
}
