package nl.linkit.itnext.pingpong.managers;

import java.util.List;

import io.reactivex.Observable;
import nl.linkit.itnext.pingpong.models.Player;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Author: efe.cem.kocabas
 * Date: 29/11/2016.
 */

public interface RestManager {

    PingpongApi getPingpongApi();

    interface PingpongApi {

        @GET
        Observable<List<Player>> getPlayers(@Url String url);
    }

}
