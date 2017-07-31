package nl.linkit.itnext.pingpong.core;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nl.linkit.itnext.pingpong.managers.DBManager;
import nl.linkit.itnext.pingpong.managers.DBManagerImpl;
import nl.linkit.itnext.pingpong.managers.LogManager;
import nl.linkit.itnext.pingpong.managers.LogManagerImpl;
import nl.linkit.itnext.pingpong.managers.RestManager;
import nl.linkit.itnext.pingpong.managers.RestManagerImpl;
import nl.linkit.itnext.pingpong.managers.SchedulersManager;
import nl.linkit.itnext.pingpong.managers.SchedulersManagerImpl;

/**
 * Developer: efe.kocabas
 * Date: 18/07/2017.
 */

@Module
public class AppModule {

    private final Context context;

    public AppModule(Context context) {

        this.context = context;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    RestManager provideRestManager() {

        return new RestManagerImpl();
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase() {

        return Room.databaseBuilder(context, AppDatabase.class, "pingpongDb").build();
    }

    @Provides
    @Singleton
    DBManager provideDBManager(final AppDatabase db) {
        return new DBManagerImpl(db);
    }

    @Provides
    @Singleton
    SchedulersManager provideSchedulersManager() {
        return new SchedulersManagerImpl();
    }

    @Provides
    @Singleton
    LogManager provideLogManager() {

        return new LogManagerImpl();
    }
}
