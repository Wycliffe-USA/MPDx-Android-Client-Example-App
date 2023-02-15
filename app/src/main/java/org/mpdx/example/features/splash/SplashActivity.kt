package org.mpdx.example.features.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.okta.oidc.AuthorizationStatus
import com.okta.oidc.clients.web.WebAuthClient
import com.okta.oidc.util.AuthorizationException
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import org.ccci.gto.android.common.okta.oidc.clients.sessions.isAuthenticatedLiveData
import org.mpdx.android.core.modal.ModalActivity
import org.mpdx.android.features.base.BaseActivity
import org.mpdx.android.features.onboarding.OnboardingActivity
import org.mpdx.android.features.secure.UnlockFragment
import org.mpdx.android.library.okta.listener.OktaActivityListener
import timber.log.Timber

fun Activity.startSplashActivity() = startActivity(Intent(this, SplashActivity::class.java))

/**
 * This class serves as the entry point into the MPDx Application.
 */
@AndroidEntryPoint
class SplashActivity : BaseActivity(), OktaActivityListener {
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
        try {
            super.onResume()
        } catch (e: Exception) {
            Timber.e(e)
        }
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

    /**
     * This method check if your are logged into Okta and if you are starts the MPDx Application.
     */
    private fun startNextActivity() {
        if (!oktaClient.sessionClient.isAuthenticated) {
            showLoginDialogIfNecessary()
        } else {
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

    /**
     * Method called when login is canceled with a result.
     */
    override fun onCancel() {
        // Reload when Login Canceled
        startSplashActivity()
        finish()
    }
    /**
     * Method called on error with a the authorized request call.
     *
     * @param msg       error message
     * @param exception The [com.okta.oidc.util.AuthorizationException] type of exception
     */
    override fun onError(msg: String?, exception: AuthorizationException?) {
        // Track errors logged by Okta
        Timber.e(exception, msg)
    }

    /**
     * Method called on authorized with a result.
     *
     * @param result Result of the authorized request.
     */
    override fun onSuccess(result: AuthorizationStatus) {}
}
