package org.wycliffe.mypd.app.dagger

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.ccci.gto.android.common.dagger.eager.EagerModule
import org.mpdx.android.library.oauth.OAuthConfig
import org.wycliffe.mypd.BuildConfig

@InstallIn(SingletonComponent::class)
@Module(includes = [EagerModule::class])
class AuthorizationAppModule {
    @Provides
    @Reusable
    fun oauthConfig() = OAuthConfig(
        authorizationEndPoint = BuildConfig.AUTH_END_POINT,
        tokenEndPoint = BuildConfig.TOKEN_END_POINT,
        clientId = BuildConfig.CLIENT_ID,
        redirectUri = BuildConfig.REDIRECT_URI,
        authProvider = BuildConfig.AUTH_PROVIDER,
    )
}
