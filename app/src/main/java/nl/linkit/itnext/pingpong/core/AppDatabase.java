package nl.linkit.itnext.pingpong.core;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import nl.linkit.itnext.pingpong.models.Player;
import nl.linkit.itnext.pingpong.models.PlayerDao;

/**
 * Developer: efe.kocabas
 * Date: 19/07/2017.
 */

@Database(entities = {Player.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PlayerDao userDao();
}
