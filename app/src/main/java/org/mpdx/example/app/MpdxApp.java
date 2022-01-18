package org.mpdx.example.app;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.ccci.gto.android.common.dagger.eager.EagerSingletonInitializer;
import org.ccci.gto.android.common.firebase.crashlytics.timber.CrashlyticsTree;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatDelegate;
import dagger.hilt.android.HiltAndroidApp;
import io.realm.Realm;
import io.realm.log.RealmLog;
import timber.log.Timber;

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

    private void initRealm() {
        Realm.init(this);
        RealmLog.add((level, tag, throwable, message) -> Timber.tag(tag).log(level, throwable, message));
    }

    private void initializeCrashlytics() {
        Timber.plant(new CrashlyticsTree());
    }

    @Inject
    EagerSingletonInitializer mEagerInitializer;
}
