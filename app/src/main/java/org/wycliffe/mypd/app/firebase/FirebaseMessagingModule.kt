package org.wycliffe.mypd.app.firebase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ElementsIntoSet
import org.ccci.gto.android.common.dagger.eager.EagerSingleton

/**
 * This Module is for setting up Firebase Messaging.  Code is commented because Firebase is not set up in this example.
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class FirebaseMessagingModule {
    companion object {
        @Provides
        @ElementsIntoSet
        @EagerSingleton(threadMode = EagerSingleton.ThreadMode.MAIN_ASYNC)
        internal fun asyncEagerSingletons(firebaseMessagingManager: FirebaseMessagingManager) =
            setOf<Any>(firebaseMessagingManager)
    }
}
