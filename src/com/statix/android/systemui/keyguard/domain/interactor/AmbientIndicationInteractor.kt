package com.google.android.systemui.keyguard.domain.interactor

import com.android.systemui.dagger.SysUISingleton
import com.android.systemui.keyguard.data.repository.KeyguardRepository

import com.google.android.systemui.keyguard.data.repository.AmbientIndicationRepository

import javax.inject.Inject

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@SysUISingleton
class AmbientIndicationInteractor
@Inject
constructor(
    private val ambientIndicationRepository: AmbientIndicationRepository,
    private val keyguardRepository: KeyguardRepository,
) {
    val ambientMusicState = MutableStateFlow(ambientIndicationRepository.ambientMusic)
    val reverseChargingMessage = MutableStateFlow(ambientIndicationRepository.reverseChargingMessage)
    val wirelessChargingMessage = MutableStateFlow(ambientIndicationRepository.wirelessChargingMessage)

    fun hideAmbientMusic() {
        ambientIndicationRepository.ambientMusic.value = null
        keyguardRepository.ambientIndicationVisible.value = false
    }
}
