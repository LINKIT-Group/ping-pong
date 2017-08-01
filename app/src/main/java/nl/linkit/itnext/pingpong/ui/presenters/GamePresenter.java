package nl.linkit.itnext.pingpong.ui.presenters;


import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.annotations.Nullable;
import nl.linkit.itnext.pingpong.R;
import nl.linkit.itnext.pingpong.core.AppDefine;
import nl.linkit.itnext.pingpong.managers.DBManager;
import nl.linkit.itnext.pingpong.managers.SchedulersManager;
import nl.linkit.itnext.pingpong.managers.VoiceManager;
import nl.linkit.itnext.pingpong.managers.VoiceManagerImpl;
import nl.linkit.itnext.pingpong.models.Player;
import nl.linkit.itnext.pingpong.models.PlayerGameStats;
import nl.linkit.itnext.pingpong.ui.contracts.GameContract;

import static nl.linkit.itnext.pingpong.core.AppDefine.MAXIMUM_POINT_FOR_MATCH;
import static nl.linkit.itnext.pingpong.core.AppDefine.MAXIMUM_POINT_FOR_SET;
import static nl.linkit.itnext.pingpong.core.AppDefine.REQUEST_CODE_END_GAME;
import static nl.linkit.itnext.pingpong.core.AppDefine.REQUEST_CODE_FIRST_PLAYER;
import static nl.linkit.itnext.pingpong.core.AppDefine.REQUEST_CODE_SECOND_PLAYER;
import static nl.linkit.itnext.pingpong.core.AppDefine.REQUEST_CODE_SPEAK;

/**
 * Developer: efe.kocabas
 * Date: 18/07/2017.
 */

public class GamePresenter implements GameContract.Presenter, VoiceManagerImpl.VoiceManagerListener {

    private final GameContract.View view;
    private final VoiceManager voiceManager;
    private final SchedulersManager schedulersManager;
    private final DBManager dbManager;

    private final static int EXACT_MATCH_SCORE = 500;
    private final static int LETTER_MATCH_SCORE = 25;

    private PlayerGameStats firstPlayer;
    private PlayerGameStats secondPlayer;
    private boolean isTieBreak;
    private List<Player> players = new ArrayList<>();


    public GamePresenter(GameContract.View view,
                         VoiceManager voiceManager,
                         SchedulersManager schedulersManager,
                         DBManager dbManager) {

        this.view = view;
        this.voiceManager = voiceManager;
        voiceManager.setVoiceManagerListener(this);
        this.schedulersManager = schedulersManager;
        this.dbManager = dbManager;
    }

    @Override
    public void onCreate() {

        dbManager.getPlayers()
                .subscribeOn(schedulersManager.background())
                .observeOn(schedulersManager.ui())
                .subscribe(players -> {

                    this.players = players;
                    voiceManager.speak(REQUEST_CODE_FIRST_PLAYER, R.string.speech_first_player);

                });
    }

    @Override
    public void onFinish() {
        voiceManager.shutdown();
    }

    @Override
    public void incrementFirstPlayerScore() {

        firstPlayer.incrementGameScore();
        view.setFirsPlayerScore(firstPlayer.getGameScore());
        evaluateNewScore(firstPlayer, secondPlayer);

        checkServiceChange();
    }

    @Override
    public void incrementSecondPlayerScore() {

        secondPlayer.incrementGameScore();
        view.setSecondPlayerScore(secondPlayer.getGameScore());
        evaluateNewScore(secondPlayer, firstPlayer);

        checkServiceChange();
    }

    @Nullable
    private void findBestMatchPlayer(ArrayList<String> results, int requestCode) {


        int bestScore = 0;
        Player bestMatchedPlayer = null;
        for (Player player : players) {

            ArrayList<String> pronunciations = new ArrayList<>(Arrays.asList(player.getPronunciations().split(",")));
            pronunciations.add(player.getName());
            int totalScore = getNameMatchScore(results, pronunciations);

            if (totalScore > bestScore) {

                bestMatchedPlayer = player;
                bestScore = totalScore;
            }
        }

        switch (requestCode) {
            case REQUEST_CODE_FIRST_PLAYER:
                firstPlayer = new PlayerGameStats(bestMatchedPlayer);
                view.setFirstPlayer(bestMatchedPlayer);
                voiceManager.speak(REQUEST_CODE_SECOND_PLAYER, R.string.speech_second_player);
                break;
            case REQUEST_CODE_SECOND_PLAYER:
                secondPlayer = new PlayerGameStats(bestMatchedPlayer);
                view.setSecondPlayer(bestMatchedPlayer);
                speakWhoStartsTheGame();
                break;
        }

    }

    private void checkServiceChange() {

        boolean timeForServiceChange = ((firstPlayer.getGameScore() + secondPlayer.getGameScore()) % 2 == 0) || isTieBreak;
        if (timeForServiceChange) {
            setServing(!firstPlayer.isServing());
        }
    }

    private void evaluateNewScore(PlayerGameStats playerScored, PlayerGameStats playerBeaten) {

        if (playerScored.getGameScore() == AppDefine.MAXIMUM_POINT_FOR_SET && playerBeaten.getGameScore() < AppDefine.MAXIMUM_POINT_FOR_SET - 1) {

            endSet(playerScored, playerBeaten);
        } else if (playerScored.getGameScore() == AppDefine.MAXIMUM_POINT_FOR_SET - 1 && playerBeaten.getGameScore() == AppDefine.MAXIMUM_POINT_FOR_SET - 1) {

            isTieBreak = true;
        } else if (isTieBreak && playerScored.getGameScore() > playerBeaten.getGameScore() + 1) {

            endSet(playerScored, playerBeaten);
        } else if (playerScored.getGameScore() == 6 && playerBeaten.getGameScore() == 0) {

            voiceManager.speak(REQUEST_CODE_SPEAK, R.string.speech_crawl_warning, playerBeaten.getPlayer().getName());
        } else if ((playerScored.getGameScore() == MAXIMUM_POINT_FOR_SET - 1) || (isTieBreak && playerScored.getGameScore() > playerBeaten.getGameScore())) {

            int resourceId = (playerScored.getSetScore() == MAXIMUM_POINT_FOR_MATCH - 1) ?
                    R.string.speech_match_point_for_player :
                    R.string.speech_set_point_for_player;

            voiceManager.speak(REQUEST_CODE_SPEAK, resourceId, playerScored.getPlayer().getName());
        }
    }

    private void endSet(PlayerGameStats winner, PlayerGameStats loser) {

        winner.incrementSetScore();
        winner.setGameScore(0);
        loser.setGameScore(0);
        isTieBreak = false;
        if (winner.getSetScore() == MAXIMUM_POINT_FOR_MATCH) {

            updatePlayerScore(winner.getPlayer());
            voiceManager.speak(REQUEST_CODE_END_GAME, R.string.speech_player_wins_the_game, winner.getPlayer().getName());
        } else {
            voiceManager.speak(REQUEST_CODE_SPEAK, R.string.speech_player_wins_the_set, winner.getPlayer().getName());
        }

        view.setFirsPlayerScore(0);
        view.setSecondPlayerScore(0);

    }


    private void speakWhoStartsTheGame() {

        boolean isFirstPlayer = Math.random() < 0.5;

        String playerName = (isFirstPlayer ? firstPlayer : secondPlayer).getPlayer().getName();
        voiceManager.speak(REQUEST_CODE_SPEAK, R.string.speech_player_starts_the_game, playerName);
        setServing(isFirstPlayer);
    }

    private void setServing(boolean isFirstPlayer) {

        firstPlayer.setServing(isFirstPlayer);
        secondPlayer.setServing(!isFirstPlayer);
        view.setServiceTurn(isFirstPlayer);
    }

    private int getNameMatchScore(ArrayList<String> results, ArrayList<String> pronunciations) {

        int totalScore = 0;
        for (String result : results) {

            result = result.toLowerCase();

            for (String playerName : pronunciations) {

                playerName = playerName.toLowerCase();

                if (result.equals(playerName)) {

                    totalScore += EXACT_MATCH_SCORE;
                } else {

                    totalScore += getScoreByLetterMatch(result, playerName, 2);
                    totalScore += getScoreByLetterMatch(result, playerName, 3);
                }
            }
        }

        Log.i("VOICE", String.format("%s: %d - %s", pronunciations, totalScore, results));
        return totalScore;
    }

    private int getScoreByLetterMatch(String word, String playerName, int searchLength) {

        int score = 0;
        for (int i = 0; i + searchLength <= word.length(); i++) {

            if (playerName.contains(word.substring(i, i + searchLength))) {
                score += LETTER_MATCH_SCORE * (searchLength - 1);
            }
        }

        return score;
    }

//    private boolean containsAnyKeyword(ArrayList<String> results, String[] keywords) {
//
//        for (String word : results) {
//
//            for (String keyword : keywords) {
//
//                if (word.contains(keyword)) {
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }

    @Override
    public void onResults(ArrayList<String> results, int requestCode) {

        if ((requestCode == REQUEST_CODE_FIRST_PLAYER) ||
                (requestCode == REQUEST_CODE_SECOND_PLAYER)) {

            findBestMatchPlayer(results, requestCode);
        }
    }

    @Override
    public void onSpeechCompleted(int requestCode) {

        if ((requestCode == REQUEST_CODE_FIRST_PLAYER) ||
                (requestCode == REQUEST_CODE_SECOND_PLAYER)) {

            voiceManager.listen(requestCode);
        } else if (requestCode == REQUEST_CODE_END_GAME) {

            view.finishGame();
        }
    }

    private void updatePlayerScore(Player player) {

        player.setScore(player.getScore() + 10);
        Completable.create(subscribe -> {

            dbManager.updatePlayerScore(player);

            subscribe.onComplete();
        })
                .subscribeOn(schedulersManager.background())
                .observeOn(schedulersManager.ui())
                .subscribe();

    }
}
