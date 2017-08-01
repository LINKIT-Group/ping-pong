package nl.linkit.itnext.pingpong.core;

import javax.inject.Singleton;

import dagger.Component;
import nl.linkit.itnext.pingpong.ui.activities.GameActivity;
import nl.linkit.itnext.pingpong.ui.activities.LeaderboardActivity;

/**
 * Developer: efe.kocabas
 * Date: 18/07/2017.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(LeaderboardActivity activity);

    void inject(GameActivity activity);
}
