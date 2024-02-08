package org.wycliffe.mypd.app.analytics

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import org.ccci.gto.android.common.dagger.eager.EagerSingleton
import org.wycliffe.mypd.app.service.FirebaseAnalyticsService

/**
 * This Module is for setting up your Analytics Service.
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class AnalyticsModule {
    @Binds
    @IntoSet
    @EagerSingleton(threadMode = EagerSingleton.ThreadMode.MAIN)
    abstract fun mainEagerSingletons(firebase: FirebaseAnalyticsService): Any
}
