package nl.linkit.itnext.pingpong.ui.contracts;

import java.util.List;

import nl.linkit.itnext.pingpong.models.Player;
import nl.linkit.itnext.pingpong.ui.common.BasePresenter;

/**
 * Developer: efe.kocabas
 * Date: 18/07/2017.
 */

public interface LeaderboardContract {

    interface View {

        void showPlayers(List<Player> players);
    }

    interface Presenter extends BasePresenter {

        void downloadPlayers();
    }
}
