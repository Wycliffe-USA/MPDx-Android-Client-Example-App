package org.mpdx.example.feature.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.mpdx.android.core.modal.ModalActivity
import org.mpdx.android.features.base.BaseActivity
import org.mpdx.android.features.onboarding.OnboardingActivity
import org.mpdx.android.features.secure.UnlockFragment
import org.mpdx.android.library.oauth.OAuthManager
import timber.log.Timber
import javax.inject.Inject

fun Activity.startSplashActivity() = startActivity(Intent(this, SplashActivity::class.java))

/**
 * This class serves as the entry point into the MPDx Application.
 */
@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    @Inject
    internal lateinit var oauthManger: OAuthManager

    // region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!oauthManger.authState.isAuthorized) {
            oauthManger.launchOAuthorization(this)
        }
    }

    override fun onResume() {
        try {
            super.onResume()
        } catch (e: Exception) {
            Timber.e(e)
        }
        if (deepLinkIsOtherAccount) return
        when {
            appPrefs.hasCompletedOnboarding() -> {
                startNextActivity()
            }
            else -> {
                OnboardingActivity.startActivity(this)
            }
        }
    }
    // endregion Lifecycle

    /**
     * This method check if your are logged into Okta and if you are starts the MPDx Application.
     */
    private fun startNextActivity() {
        if (!oauthManger.authState.isAuthorized) {
            showLoginDialogIfNecessary()
        } else {
            ModalActivity.launchActivity(this, UnlockFragment.create(deepLinkType, deepLinkId, deepLinkTime), true)
            finish()
        }
    }

    // region Login Dialog
    private fun showLoginDialogIfNecessary() {
        if (oauthManger.authState.isAuthorized) return
        oauthManger.launchOAuthorization(this)
    }
    // endregion Login Dialog

    override fun showUnlockScreen() = false
    override val pageName = "Splash"
}
