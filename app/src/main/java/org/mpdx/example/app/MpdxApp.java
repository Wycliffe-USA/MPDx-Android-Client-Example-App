package org.mpdx.example.app;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.ccci.gto.android.common.dagger.eager.EagerSingletonInitializer;
//import org.ccci.gto.android.common.firebase.crashlytics.timber.CrashlyticsTree;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatDelegate;
import dagger.hilt.android.HiltAndroidApp;
import io.realm.Realm;
import io.realm.log.RealmLog;
import timber.log.Timber;

/**
 * This class is for setting up the Application class.  In this class you will initialize any application
 * level functionality. You are also required to add the @HiltAndroidApp annotation to get Dagger Hilt
 * working.
 */
@HiltAndroidApp
public class MpdxApp extends Application {
    @Override
    public void onCreate() {
        initializeCrashlytics();
        AndroidThreeTen.init(this);
        initRealm();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        super.onCreate();
    }

    /**
     * This method is to initialize the Realm Database which is used for the MPDx app.
     */
    private void initRealm() {
        Realm.init(this);
        RealmLog.add((level, tag, throwable, message) -> Timber.tag(tag).log(level, throwable, message));
    }

    /**
     * This is to add Crashlytics to Timber.
     */
    private void initializeCrashlytics() {
//        Timber.plant(new CrashlyticsTree());
    }

    @Inject
    EagerSingletonInitializer mEagerInitializer;
}
