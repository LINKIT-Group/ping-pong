package nl.linkit.itnext.pingpong.managers;


import io.reactivex.Scheduler;

/**
 * Created by efe.cem.kocabas on 07/12/2016.
 */

public interface SchedulersManager {

    Scheduler ui();

    Scheduler background();

}
