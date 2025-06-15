package com.google.android.systemui.keyguard.data.repository

import com.android.systemui.dagger.SysUISingleton

import javax.inject.Inject

import kotlinx.coroutines.flow.MutableStateFlow

@SysUISingleton
class AmbientIndicationRepository
@Inject
constructor(
) {
    val ambientMusic = MutableStateFlow<String?>(null)
    val reverseChargingMessage = MutableStateFlow("")
    val wirelessChargingMessage = MutableStateFlow("")
}
