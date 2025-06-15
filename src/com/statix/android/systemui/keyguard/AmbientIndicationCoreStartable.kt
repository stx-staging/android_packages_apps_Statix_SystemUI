package com.google.android.systemui.keyguard

import android.app.AlarmManager
import android.content.Context
import android.content.IntentFilter
import android.os.UserHandle

import com.android.keyguard.KeyguardUpdateMonitor
import com.android.systemui.dagger.SysUISingleton
import com.android.systemui.CoreStartable
import com.android.systemui.user.domain.interactor.SelectedUserInteractor

import com.google.android.systemui.ambientmusic.AmbientIndicationService
import com.google.android.systemui.keyguard.domain.interactor.AmbientIndicationInteractor

import javax.inject.Inject

@SysUISingleton
class AmbientIndicationCoreStartable
@Inject
constructor(
    private val alarmManager: AlarmManager,
    private val context: Context,
    private val keyguardUpdateMonitor: KeyguardUpdateMonitor,
    private val selectedUserInteractor: SelectedUserInteractor,
    private val ambientIndicationInteractor: AmbientIndicationInteractor
) : CoreStartable {

    override fun start() {
        val ambientIndicationService = AmbientIndicationService(
            alarmManager,
            context,
            keyguardUpdateMonitor,
            selectedUserInteractor,
            ambientIndicationInteractor
        )
        if (ambientIndicationService.mStarted) {
            return
        }
        ambientIndicationService.mStarted = true
        val intentFilter = IntentFilter().apply {
            addAction("com.google.android.ambientindication.action.AMBIENT_INDICATION_SHOW")
            addAction("com.google.android.ambientindication.action.AMBIENT_INDICATION_HIDE")
        }
        ambientIndicationService.mContext.registerReceiverAsUser(
            ambientIndicationService,
            UserHandle.ALL,
            intentFilter,
            "com.google.android.ambientindication.permission.AMBIENT_INDICATION",
            null,
            2
        )
        ambientIndicationService.mKeyguardUpdateMonitor.registerCallback(ambientIndicationService.mCallback)
    }
}
