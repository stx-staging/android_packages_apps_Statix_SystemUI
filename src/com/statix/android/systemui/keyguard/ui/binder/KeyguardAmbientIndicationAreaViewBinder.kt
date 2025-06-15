package com.google.android.systemui.keyguard.ui.binder

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle

import com.android.app.tracing.coroutines.launchTraced as launch
import com.android.keyguard.KeyguardUpdateMonitor
import com.android.systemui.lifecycle.repeatWhenAttached
import com.android.systemui.plugins.ActivityStarter
import com.android.systemui.power.domain.interactor.PowerInteractor

import com.google.android.systemui.ambientmusic.AmbientIndicationContainer
import com.google.android.systemui.keyguard.shared.AmbientIndicationMusic
import com.google.android.systemui.keyguard.ui.viewmodel.KeyguardAmbientIndicationViewModel

import com.statix.android.systemui.dagger.SysUIComponentStatix.SwitchingProvider

import kotlinx.coroutines.DisposableHandle

object KeyguardAmbientIndicationAreaViewBinder {
    fun bind(
        viewGroup: ViewGroup,
        viewModel: KeyguardAmbientIndicationViewModel,
        powerInteractor: PowerInteractor,
        keyguardUpdateMonitor: KeyguardUpdateMonitor,
        activityStarter: ActivityStarter,
        anonymousClass39: SwitchingProvider.AnonymousClass39
    ): DisposableHandle {
        val ambientIndicationContainer = viewGroup.findViewById<AmbientIndicationContainer>(R.id.ambient_indication_container)

        // Initialize AmbientIndicationContainer if found
        ambientIndicationContainer?.let {
            it.mPowerInteractor = powerInteractor
            it.mKeyguardUpdateMonitor = keyguardUpdateMonitor
            it.mActivityStarter = activityStarter
            it.mDelayedWakeLockFactory = anonymousClass39
            it.mWakeLock = it.createWakeLock()
            // TODO: fix this
            it.mInflateListeners.add(AmbientIndicationContainer(it))
            it.getChildAt(0)
            // TODO: fix this
            AmbientIndicationContainer.`$r8$lambda$DFan0h9JQgIimo3ogLWaY_C9MMU`(it)
        }

        val disposableHandle =
            ambientIndicationContainer?.repeatWhenAttached {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch("$TAG#indicationAreaTranslationX") {
                        viewModel.indicationAreaTranslationX.collect { translationX ->
                            ambientIndicationContainer.translationX = translationX
                        }
                    }

                    launch("$TAG#indicationAreaTranslationY") {
                        viewModel.indicationAreaTranslationY.collect { translationY ->
                            ambientIndicationContainer.translationY = translationY
                        }
                    }

                    launch("$TAG#ambientIndicationMusicState") {
                        viewModel.ambientIndicationMusicState.collect { ambientIndicationMusic ->
                            if (ambientIndicationMusic == null) {
                                ambientIndicationContainer.setAmbientMusic(null, null, null, 0, false, null)
                            } else {
                                ambientIndicationContainer.setAmbientMusic(
                                    ambientIndicationMusic.text,
                                    ambientIndicationMusic.openIntent,
                                    ambientIndicationMusic.favoritingIntent,
                                    ambientIndicationMusic.iconOverride.toInt(),
                                    ambientIndicationMusic.skipUnlock == true,
                                    ambientIndicationMusic.iconDescription
                                )
                            }
                        }
                    }

                    launch("$TAG#reverseChargingMessage") {
                        viewModel.reverseChargingMessage.collect { message ->
                            if (ambientIndicationContainer.mReverseChargingMessage != message || ambientIndicationContainer.mWirelessChargingMessage != null) {
                                ambientIndicationContainer.mWirelessChargingMessage = null
                                ambientIndicationContainer.mReverseChargingMessage = message
                                ambientIndicationContainer.updatePill()
                            }
                        }
                    }

                    launch("$TAG#wirelessChargingMessage") {
                        viewModel.wirelessChargingMessage.collect { message ->
                            if (ambientIndicationContainer.mWirelessChargingMessage != message || ambientIndicationContainer.mReverseChargingMessage != null) {
                                ambientIndicationContainer.mWirelessChargingMessage = message
                                ambientIndicationContainer.mReverseChargingMessage = null
                                ambientIndicationContainer.updatePill()
                            }
                        }
                    }
                }
            }
        // If ambientIndicationContainer is null, repeatWhenAttached will not be called, resulting in a null disposableHandle.
        // Returning a no-op DisposableHandle for this case to satisfy the non-nullable return type.
        return disposableHandle ?: DisposableHandle { /* No-op dispose */ }
    }

    private const val TAG = "KeyguardAmbientIndicationAreaViewBinder"
}

