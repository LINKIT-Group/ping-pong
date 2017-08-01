package nl.linkit.itnext.pingpong.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Developer: efe.kocabas
 * Date: 19/07/2017.
 */

@Dao
public interface PlayerDao {

    @Query("SELECT * FROM player ORDER BY score DESC")
    List<Player> getAll();

    @Query("SELECT COUNT(*) from player")
    int countPlayers();

    @Insert
    long insert(Player player);

    @Delete
    void delete(Player player);

    @Query("UPDATE player set name = :name, avatar_name = :avatarName WHERE id = :id")
    int updatePlayerNameAndAvatar(int id, String name, String avatarName);

    @Query("UPDATE player set score = :score WHERE id = :id")
    int updatePlayerScore(int id, int score);
}
