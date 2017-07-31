package nl.linkit.itnext.pingpong.ui.contracts;

import nl.linkit.itnext.pingpong.models.Player;
import nl.linkit.itnext.pingpong.ui.common.BasePresenter;

/**
 * Developer: efe.kocabas
 * Date: 18/07/2017.
 */

public interface GameContract {

    interface View {

        void setFirstPlayer(Player player);
        void setSecondPlayer(Player player);
        void setFirsPlayerScore(int score);
        void setSecondPlayerScore(int score);
        void finishGame();
        void setServiceTurn(boolean isFirstPlayerServing);
    }

    interface Presenter extends BasePresenter {

        void incrementFirstPlayerScore();
        void incrementSecondPlayerScore();

    }
}
