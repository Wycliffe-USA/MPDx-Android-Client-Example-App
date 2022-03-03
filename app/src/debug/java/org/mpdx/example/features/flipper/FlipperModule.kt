package org.mpdx.example.features.flipper

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.core.FlipperPlugin
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import com.kgurgul.flipper.RealmDatabaseDriver
import com.kgurgul.flipper.RealmDatabaseProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import io.realm.Realm
import javax.inject.Singleton
import okhttp3.Interceptor
import org.ccci.gto.android.common.dagger.eager.EagerSingleton

/**
 * This module is an example of adding Flipper to your build.  This module is only available in debug builds.
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class FlipperModule {
    @Binds
    @IntoSet
    @EagerSingleton(threadMode = EagerSingleton.ThreadMode.ASYNC)
    abstract fun flipperClientEagerSingleton(flipperClient: FlipperClient): Any

    @Binds
    @IntoSet
    abstract fun flipperPluginNetwork(networkFlipperPlugin: NetworkFlipperPlugin): FlipperPlugin

    companion object {
        @Provides
        @Singleton
        internal fun flipperClient(context: Context, plugins: Set<@JvmSuppressWildcards FlipperPlugin>): FlipperClient {
            SoLoader.init(context, false)
            return AndroidFlipperClient.getInstance(context).apply {
                addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
                addPlugin(SharedPreferencesFlipperPlugin(context))
                plugins.forEach { addPlugin(it) }
                start()
            }
        }

        @Provides
        @Singleton
        internal fun flipperNetworkPlugin() = NetworkFlipperPlugin()

        @IntoSet
        @Provides
        @Singleton
        internal fun flipperRealmPlugin(context: Context): FlipperPlugin = DatabasesFlipperPlugin(
            RealmDatabaseDriver(
                context,
                object : RealmDatabaseProvider {
                    override fun getRealmConfigurations() = listOfNotNull(Realm.getDefaultConfiguration())
                }
            )
        )

        @IntoSet
        @Provides
        @Singleton
        internal fun flipperOkHttpInterceptor(networkPlugin: NetworkFlipperPlugin): Interceptor =
            FlipperOkhttpInterceptor(networkPlugin)
    }
}
