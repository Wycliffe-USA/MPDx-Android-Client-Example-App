package org.mpdx.example.app.analytics

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import org.ccci.gto.android.common.dagger.eager.EagerSingleton
import org.mpdx.example.app.service.FirebaseAnalyticsService

@InstallIn(SingletonComponent::class)
@Module
abstract class AnalyticsModule {
    @Binds
    @IntoSet
    @EagerSingleton(threadMode = EagerSingleton.ThreadMode.MAIN)
    abstract fun mainEagerSingletons(firebase: FirebaseAnalyticsService): Any
}
