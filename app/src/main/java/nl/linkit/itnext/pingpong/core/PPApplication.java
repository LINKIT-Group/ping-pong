package nl.linkit.itnext.pingpong.core;

import android.app.Application;

/**
 * Developer: efe.kocabas
 * Date: 18/07/2017.
 */

public class PPApplication extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initDI();

    }

    private void initDI() {

        setAppComponent(DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build());
    }

    public static void setAppComponent(AppComponent newAppComponent) {

        appComponent = newAppComponent;
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

}
