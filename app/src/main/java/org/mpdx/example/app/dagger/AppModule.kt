package org.mpdx.example.app.dagger

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.ccci.gto.android.common.dagger.eager.EagerModule
import org.greenrobot.eventbus.EventBus
import org.mpdx.android.R
import org.mpdx.android.library.core.AppConstantListener
import org.mpdx.android.library.core.AppResourceListener
import org.mpdx.android.utils.StringResolver
import org.mpdx.android.utils.TasksComparator
import org.mpdx.example.BuildConfig
import org.mpdx.example.features.splash.SplashActivity
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [EagerModule::class])
class AppModule {
    @Provides
    fun providesContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun providesStringResolver(@ApplicationContext context: Context): StringResolver {
        return StringResolver(context)
    }

    @Provides
    @Singleton
    fun providesEventBus(): EventBus {
        return EventBus.builder()
            .throwSubscriberException(true)
            .installDefaultEventBus()
    }

    @Provides
    fun providesConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun providesTasksComparator(stringResolver: StringResolver): TasksComparator {
        return TasksComparator(stringResolver)
    }

    @Provides
    fun providesAppConstant(): AppConstantListener {
        return object : AppConstantListener {
            override fun appID() = BuildConfig.APPLICATION_ID

            override fun buildVersion() = BuildConfig.VERSION_NAME

            override fun isDebug() = BuildConfig.DEBUG

            override fun baseApiUrl() = BuildConfig.MPDX_API_BASE_URI

            override fun versionCode() = BuildConfig.VERSION_CODE

            override fun getSplashActivityClass() = SplashActivity::class.java
        }
    }

    @Provides
    fun providesAppResource(): AppResourceListener {
        return object : AppResourceListener {
            /**
             * This is to provide the Drawer Logo
             * @sample R.drawable.logo
             * @return @Drawable Int
             */
            override fun getAppDrawerLogo() = R.drawable.logo
        }
    }
}
