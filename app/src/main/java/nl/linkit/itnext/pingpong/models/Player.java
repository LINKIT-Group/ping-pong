package nl.linkit.itnext.pingpong.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Developer: efe.kocabas
 * Date: 18/07/2017.
 */

@Entity
public class Player {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "pronunciations")
    private String pronunciations;

    @ColumnInfo(name = "avatar_name")
    private String avatarName;

    @ColumnInfo(name = "score")
    private int score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPronunciations(String pronunciations) {
        this.pronunciations = pronunciations;
    }

    public String getPronunciations() {
        return pronunciations;
    }
}
