package nl.linkit.itnext.pingpong.managers;


import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by efe.cem.kocabas on 07/12/2016.
 */

public class SchedulersManagerImpl implements SchedulersManager {

    @Override public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    @Override public Scheduler background() {
        return Schedulers.io();
    }
}

