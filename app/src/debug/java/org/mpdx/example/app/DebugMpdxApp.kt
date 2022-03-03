package org.mpdx.example.app

import android.content.Context
import androidx.multidex.MultiDex
import leakcanary.LeakCanary
import okhttp3.logging.HttpLoggingInterceptor
import org.ccci.gto.android.common.leakcanary.crashlytics.CrashlyticsOnHeapAnalyzedListener
import org.ccci.gto.android.common.okhttp3.util.addGlobalNetworkInterceptor
import timber.log.Timber

/**
 * This class is debug version of the MpdxApp which will allow you set up different listeners and interceptors.
 */
class DebugMpdxApp : MpdxApp() {
    override fun onCreate() {
        configLeakCanary()

        enableOkHttpLogging()
        Timber.plant(Timber.DebugTree())

        super.onCreate()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun configLeakCanary() {
        LeakCanary.config = LeakCanary.config.copy(
            onHeapAnalyzedListener = CrashlyticsOnHeapAnalyzedListener
        )
    }

    private fun enableOkHttpLogging() {
        addGlobalNetworkInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
    }
}
