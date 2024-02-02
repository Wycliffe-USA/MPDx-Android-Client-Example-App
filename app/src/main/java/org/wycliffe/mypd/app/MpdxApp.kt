package org.wycliffe.mypd.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.log.RealmLog
import org.ccci.gto.android.common.dagger.eager.EagerSingletonInitializer
import org.ccci.gto.android.common.firebase.crashlytics.timber.CrashlyticsTree
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
open class MpdxApp : Application() {
    override fun onCreate() {
        initializeCrashlytics()
        AndroidThreeTen.init(this)
        initRealm()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        super.onCreate()
    }

    private fun initRealm() {
        Realm.init(this)
        RealmLog.add { level: Int, tag: String?, throwable: Throwable?, message: String? ->
            Timber.apply { if (tag != null) this.tag(tag) }.log(level, throwable, message)
        }
    }

    private fun initializeCrashlytics() {
        Timber.plant(CrashlyticsTree())
    }

    @Inject
    lateinit var eagerInitializer: EagerSingletonInitializer
}
