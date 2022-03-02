package org.mpdx.example.app.dagger

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.ccci.gto.android.common.dagger.eager.EagerModule
import org.greenrobot.eventbus.EventBus
import org.mpdx.android.library.core.AppConstantListener
import org.mpdx.android.library.okta.listener.OktaAppListener
import org.mpdx.android.utils.StringResolver
import org.mpdx.android.utils.TasksComparator
import org.mpdx.example.BuildConfig
import org.mpdx.example.R
import org.mpdx.example.features.splash.SplashActivity

/** This module is to create dagger references for App Specific Dagger Providers. */
@InstallIn(SingletonComponent::class)
@Module(includes = [EagerModule::class])
class AppModule {
    //region App Providers
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
    // endregion App Providers

    /** This Provider allows all the library to use App Specific constants */
    @Provides
    fun providesAppContanstant(): AppConstantListener {
        return object : AppConstantListener {
            override fun appID() = BuildConfig.APPLICATION_ID

            override fun buildVersion() = BuildConfig.VERSION_NAME

            override fun isDebug() = BuildConfig.DEBUG

            override fun baseApiUrl() = BuildConfig.MPDX_API_BASE_URI

            override fun versionCode() = BuildConfig.VERSION_CODE

            override fun getSplashActivityClass() = SplashActivity::class.java
        }
    }

    /** This Providers allows the Okta Library to use App specific Constants */
    @Provides
    fun providesOktaAppConstant(): OktaAppListener {
        return object : OktaAppListener {
            override fun oktaDiscoveryUri() = BuildConfig.OKTA_DISCOVERY_URI

            override fun oktaClientId() = BuildConfig.OKTA_CLIENT_ID

            override fun oktaAuthScheme() = BuildConfig.OKTA_AUTH_SCHEME

            override fun tabColor() = R.color.primary_blue
        }
    }
}
