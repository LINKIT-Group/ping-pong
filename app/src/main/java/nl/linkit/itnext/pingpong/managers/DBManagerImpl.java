package nl.linkit.itnext.pingpong.managers;

import java.util.List;

import io.reactivex.Observable;
import nl.linkit.itnext.pingpong.core.AppDatabase;
import nl.linkit.itnext.pingpong.models.Player;

/**
 * Author: efe.cem.kocabas
 * Date: 05/12/2016.
 */

public class DBManagerImpl implements DBManager {

    private AppDatabase db;

    public DBManagerImpl(AppDatabase db) {

        this.db = db;
    }

    @Override
    public void insertPlayers(List<Player> playerList) {

        for (Player player : playerList) {

            int affectedRows = db.userDao().updatePlayerNameAndAvatar(player.getId(), player.getName(), player.getAvatarName());
            if(affectedRows == 0) {
                db.userDao().insert(player);
            }
        }
    }

    @Override
    public Observable<List<Player>> getPlayers() {

        return Observable.create(subscriber -> subscriber.onNext(db.userDao().getAll()));
    }

    @Override
    public void updatePlayerScore(Player player) {

        db.userDao().updatePlayerScore(player.getId(), player.getScore());
    }
}
