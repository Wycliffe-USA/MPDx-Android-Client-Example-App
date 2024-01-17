package org.mpdx.example.app.dagger

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.ccci.gto.android.common.dagger.eager.EagerModule
// import org.mpdx.android.library.okta.listener.OktaAppListener

@InstallIn(SingletonComponent::class)
@Module(includes = [EagerModule::class])
class OktaAppModule {

//    @Provides
//    fun providesOktaAppConstant(): OktaAppListener {
//        return object : OktaAppListener {
//            override fun oktaDiscoveryUri() = BuildConfig.OKTA_DISCOVERY_URI
//
//            override fun oktaClientId() = BuildConfig.OKTA_CLIENT_ID
//
//            override fun oktaAuthScheme() = BuildConfig.OKTA_AUTH_SCHEME
//
//            override fun tabColor() = R.color.primary_blue
//        }
//    }
}
