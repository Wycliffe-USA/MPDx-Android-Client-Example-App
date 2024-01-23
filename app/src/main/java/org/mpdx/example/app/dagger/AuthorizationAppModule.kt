package org.mpdx.example.app.dagger

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.ccci.gto.android.common.dagger.eager.EagerModule
import org.mpdx.android.library.oauth.listener.OAuthAppListener
import org.mpdx.example.BuildConfig

@InstallIn(SingletonComponent::class)
@Module(includes = [EagerModule::class])
class AuthorizationAppModule {

    @Provides
    fun providesOAuthAppConstant(): OAuthAppListener {
        return object : OAuthAppListener {
            override fun authProvider() = BuildConfig.AUTH_PROVIDER

            override fun authorizationEndPoint() = BuildConfig.AUTH_END_POINT

            override fun clientId() = BuildConfig.CLIENT_ID

            override fun redirectUri() = BuildConfig.REDIRECT_URI

            override fun tokenEndPoint() = BuildConfig.TOKEN_END_POINT
        }
    }
}
