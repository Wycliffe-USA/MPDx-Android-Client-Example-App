package org.mpdx.example.features.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.okta.oidc.clients.web.WebAuthClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import org.ccci.gto.android.common.okta.oidc.clients.sessions.isAuthenticatedLiveData
import org.mpdx.android.core.modal.ModalActivity
import org.mpdx.android.features.base.BaseActivity
import org.mpdx.android.features.onboarding.OnboardingActivity
import org.mpdx.android.features.secure.UnlockFragment

fun Activity.startSplashActivity() = startActivity(Intent(this, SplashActivity::class.java))

@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    @Inject
    internal lateinit var oktaClient: WebAuthClient

    private var isLoggedIn: Boolean? = null
    private var isInProgress: Boolean? = null

    // region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        oktaClient.sessionClient.isAuthenticatedLiveData.observe(this) {
            if (isInProgress == true) {
                if (it != isLoggedIn) {
                    startNextActivity()
                    isInProgress = false
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (deepLinkIsOtherAccount) return
        when {
            oktaClient.isInProgress -> {
                isLoggedIn = oktaClient.sessionClient.isAuthenticated
                isInProgress = true
            }
            appPrefs.hasCompletedOnboarding() -> {
                startNextActivity()
            }
            else -> {
                OnboardingActivity.startActivity(this)
            }
        }
    }
    // endregion Lifecycle

    private fun startNextActivity() {
        if (!oktaClient.sessionClient.isAuthenticated) {
            showLoginDialogIfNecessary()
        } else {
            // XXX: we need to always go to UnlockFragment because it initializes some preferences
//            if (mRealmManager.isUnlocked) {
//                startActivity(MainActivity.getIntent(this, deepLinkType, deepLinkId))
//            } else {
//                ModalActivity.launchActivity(this, UnlockFragment.create(deepLinkType, deepLinkId, deepLinkTime), true)
//            }
            ModalActivity.launchActivity(this, UnlockFragment.create(deepLinkType, deepLinkId, deepLinkTime), true)
            finish()
        }
    }

    // region Login Dialog
    private fun showLoginDialogIfNecessary() {
        if (oktaClient.sessionClient.isAuthenticated) return
        oktaClient.signIn(this, null)
    }
    // endregion Login Dialog

    override fun showUnlockScreen() = false
    override val pageName = "Splash"
}
