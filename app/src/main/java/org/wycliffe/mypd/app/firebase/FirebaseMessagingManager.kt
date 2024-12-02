package org.wycliffe.mypd.app.firebase

import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.mpdx.android.core.UserDeviceApi
import org.mpdx.android.core.model.UserDevice
import org.mpdx.android.core.services.ConnectivityService
import org.mpdx.android.library.auth.AuthenticationListener
import org.mpdx.android.library.core.AppConstantListener
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "FirebseMessagingManager"

@Singleton
class FirebaseMessagingManager @Inject constructor(
    eventBus: EventBus,
    private val authenticationListener: AuthenticationListener,
    private val userDeviceApi: UserDeviceApi,
    private val appConstantListener: AppConstantListener,
) : CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext get() = Dispatchers.Main + job

    init {
        eventBus.register(this)
    }

    @Subscribe(sticky = true)
    fun onConnectivityEvent(event: ConnectivityService.ConnectivityEvent) {
        if (event == ConnectivityService.ConnectivityEvent.ONLINE) registerDevice()
    }

    private fun registerDevice() = launch {
        if (!authenticationListener.isAuthenticated()) return@launch

        try {
            FirebaseInstanceId.getInstance().instanceId.await()
                ?.let { userDeviceApi.registerUserDevice(UserDevice(it.token, appConstantListener.buildVersion())) }
        } catch (e: IOException) {
            Timber.tag(TAG).d(e, "Error registering device")
        }
    }
}
