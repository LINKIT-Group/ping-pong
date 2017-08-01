package nl.linkit.itnext.pingpong.models;

/**
 * Developer: efe.kocabas
 * Date: 24/07/2017.
 */

public class PlayerGameStats {

    private Player player;
    private int setScore;
    private int gameScore;
    private boolean isServing;

    public PlayerGameStats(Player player) {
        this.player = player;
        this.setScore = 0;
        this.gameScore = 0;
        this.isServing = false;
    }

    public Player getPlayer() {
        return player;
    }

    public int getSetScore() {
        return setScore;
    }

    public void setSetScore(int setScore) {
        this.setScore = setScore;
    }

    public int getGameScore() {
        return gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    public boolean isServing() {
        return isServing;
    }

    public void setServing(boolean serving) {
        isServing = serving;
    }

    public void incrementGameScore() {
        this.gameScore = this.gameScore + 1;
    }

    public void incrementSetScore() {
        this.setScore = this.setScore + 1;
    }
}
