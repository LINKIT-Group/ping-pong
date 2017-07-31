package nl.linkit.itnext.pingpong.managers;

import java.util.List;

import io.reactivex.Observable;
import nl.linkit.itnext.pingpong.models.Player;

/**
 * Author: efe.cem.kocabas
 * Date: 05/12/2016.
 */

public interface DBManager {

    void insertPlayers(List<Player> playerList);

    Observable<List<Player>> getPlayers();

    void updatePlayerScore(Player player);

}
