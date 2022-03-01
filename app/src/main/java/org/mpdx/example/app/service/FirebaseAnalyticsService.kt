package org.mpdx.example.app.service
//
// import android.app.Application
// import android.os.Bundle
// import androidx.annotation.AnyThread
// import androidx.annotation.MainThread
// import com.google.firebase.analytics.FirebaseAnalytics
// import com.okta.oidc.net.response.UserInfo
// import java.util.Locale
// import javax.inject.Inject
// import javax.inject.Singleton
// import kotlinx.coroutines.CoroutineScope
// import kotlinx.coroutines.Dispatchers
// import kotlinx.coroutines.flow.launchIn
// import kotlinx.coroutines.flow.onEach
// import org.ccci.gto.android.common.compat.util.LocaleCompat
// import org.ccci.gto.android.common.okta.oidc.OktaUserProfileProvider
// import org.ccci.gto.android.common.okta.oidc.net.response.grMasterPersonId
// import org.ccci.gto.android.common.okta.oidc.net.response.ssoGuid
// import org.greenrobot.eventbus.EventBus
// import org.greenrobot.eventbus.Subscribe
// import org.greenrobot.eventbus.ThreadMode
// import org.mpdx.android.features.analytics.AnalyticsSystem
// import org.mpdx.android.features.analytics.BaseAnalyticsService
// import org.mpdx.android.features.analytics.KEY_APP_NAME
// import org.mpdx.android.features.analytics.KEY_CATEGORY
// import org.mpdx.android.features.analytics.KEY_CONTENT_LANGUAGE
// import org.mpdx.android.features.analytics.KEY_GR_MASTER_PERSON_ID
// import org.mpdx.android.features.analytics.KEY_LABEL
// import org.mpdx.android.features.analytics.KEY_LOGGED_IN_STATUS
// import org.mpdx.android.features.analytics.KEY_SSO_GUID
// import org.mpdx.android.features.analytics.model.AnalyticsActionEvent
// import org.mpdx.android.features.analytics.model.AnalyticsScreenEvent
// import org.mpdx.example.BuildConfig
//
// private const val APP_NAME = "MPDx App"
//
// @Singleton
// class FirebaseAnalyticsService @Inject constructor(
//    application: Application,
//    eventBus: EventBus,
//    private val oktaUserProfileProvider: OktaUserProfileProvider
// ) : BaseAnalyticsService() {
//    private val analytics = FirebaseAnalytics.getInstance(application)
//    private val coroutineScope = CoroutineScope(Dispatchers.Default)
//
//    init {
//        analytics.setUserProperty("debug", "${BuildConfig.DEBUG}")
//        analytics.setUserProperty(KEY_APP_NAME, APP_NAME)
//        analytics.setUserProperty(KEY_CONTENT_LANGUAGE, LocaleCompat.toLanguageTag(Locale.getDefault()))
//
//        eventBus.register(this)
//    }
//
//    // region Tracking Events
//    @MainThread
//    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
//    fun onAnalyticsScreenEvent(event: AnalyticsScreenEvent) {
//        if (event.isForSystem(AnalyticsSystem.FIREBASE)) {
//            handleScreenEvent(event)
//        }
//    }
//
//    @MainThread
//    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
//    fun onAnalyticsActionEvent(event: AnalyticsActionEvent) {
//        if (event.isForSystem(AnalyticsSystem.FIREBASE)) {
//            handleAction(event)
//        }
//    }
//    // endregion Tracking Events
//
//    @MainThread
//    private fun handleScreenEvent(event: AnalyticsScreenEvent) {
//        val bundle = Bundle().apply {
//            putString(FirebaseAnalytics.Param.SCREEN_NAME, event.screen)
//            putString("cru_appname", APP_NAME)
//            event.siteSection?.let { putString("cru_sitesection", event.siteSection) }
//            event.siteSubSection?.let { putString("cru_sitesubsection", event.siteSubSection) }
//        }
//        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
//    }
//
//    @MainThread
//    private fun handleAction(event: AnalyticsActionEvent) {
//        analytics.logEvent(event.action, buildEventParameters(event))
//    }
//
//    @MainThread
//    private fun buildEventParameters(event: AnalyticsActionEvent) = Bundle().apply {
//        event.label?.let { putString(KEY_LABEL, it) }
//        event.category?.let { putString(KEY_CATEGORY, it) }
//    }
//
//    // region UserProperties
//
//    @AnyThread
//    private fun updateUserProperties(userInfo: UserInfo?) {
//        analytics.setUserId(userInfo?.ssoGuid)
//        analytics.setUserProperty(KEY_LOGGED_IN_STATUS, "${userInfo != null}")
//        analytics.setUserProperty(KEY_SSO_GUID, userInfo?.ssoGuid)
//        analytics.setUserProperty(KEY_GR_MASTER_PERSON_ID, userInfo?.grMasterPersonId)
//    }
//    init {
//        oktaUserProfileProvider.userInfoFlow(false)
//            .onEach {
//                updateUserProperties(it)
//            }
//            .launchIn(CoroutineScope(Dispatchers.Default))
//    }
//    // endregion UserProperties
// }
