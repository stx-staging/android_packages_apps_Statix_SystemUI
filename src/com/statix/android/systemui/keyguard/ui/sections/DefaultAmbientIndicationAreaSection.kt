package com.google.android.systemui.keyguard.ui.sections

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.android.keyguard.KeyguardUpdateMonitor
import com.android.systemui.keyguard.shared.model.KeyguardSection
import com.android.systemui.lifecycle.RepeatWhenAttachedKt
import com.android.systemui.plugins.ActivityStarter
import com.android.systemui.power.domain.interactor.PowerInteractor

import com.google.android.systemui.keyguard.ui.binder.KeyguardAmbientIndicationAreaViewBinder
import com.google.android.systemui.keyguard.ui.binder.KeyguardAmbientIndicationAreaViewBinder.bind
import com.google.android.systemui.keyguard.ui.viewmodel.KeyguardAmbientIndicationViewModel

import com.statix.android.systemui.dagger.SysUIComponentStatix.SwitchingProvider
import com.statix.android.systemui.res.R

import javax.inject.Inject

class DefaultAmbientIndicationAreaSection
@Inject
constructor(
    private val keyguardUpdateMonitor: KeyguardUpdateMonitor,
    private val activityStarter: ActivityStarter,
    private val powerInteractor: PowerInteractor,
    private val delayedWakeLockFactory: SwitchingProvider.AnonymousClass39,
    private val keyguardAmbientIndicationViewModel: KeyguardAmbientIndicationViewModel
) : KeyguardSection() {

    private var ambientIndicationAreaHandle: KeyguardAmbientIndicationAreaViewBinder? = null

    override fun addViews(constraintLayout: ConstraintLayout) {
        LayoutInflater.from(constraintLayout.context).inflate(
            R.layout.ambient_indication,
            constraintLayout,
            false
        ).also {
            constraintLayout.addView(it)
        }
    }

    override fun applyConstraints(constraintSet: ConstraintSet) {
        constraintSet.constrainWidth(R.id.ambient_indication_container, -1)
        if (!keyguardUpdateMonitor.isUdfpsSupported) {
            constraintSet.constrainHeight(R.id.ambient_indication_container, -2)
            constraintSet.connect(
                R.id.ambient_indication_container,
                ConstraintSet.TOP,
                com.android.systemui.res.R.id.device_entry_icon_view,
                ConstraintSet.BOTTOM
            )
            constraintSet.connect(
                R.id.ambient_indication_container,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START
            )
            constraintSet.connect(
                R.id.ambient_indication_container,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END
            )
        } else {
            constraintSet.constrainHeight(R.id.ambient_indication_container, 0)
            constraintSet.connect(
                R.id.ambient_indication_container,
                ConstraintSet.TOP,
                com.android.systemui.res.R.id.device_entry_icon_view,
                ConstraintSet.BOTTOM
            )
            constraintSet.connect(
                R.id.ambient_indication_container,
                ConstraintSet.BOTTOM,
                com.android.systemui.res.R.id.keyguard_indication_area,
                ConstraintSet.TOP
            )
            constraintSet.connect(
                R.id.ambient_indication_container,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START
            )
            constraintSet.connect(
                R.id.ambient_indication_container,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END
            )
        }
    }

    override fun bindData(constraintLayout: ConstraintLayout) {
        ambientIndicationAreaHandle = KeyguardAmbientIndicationAreaViewBinder.bind(
            constraintLayout,
            keyguardAmbientIndicationViewModel,
            powerInteractor,
            keyguardUpdateMonitor,
            activityStarter,
            delayedWakeLockFactory
        )
    }

    override fun removeViews(constraintLayout: ConstraintLayout) {
        ambientIndicationAreaHandle.disposableHandle.dispose()
        constraintLayout.findViewById<View?>(R.id.ambient_indication_container)?.let {
            constraintLayout.removeView(it)
        }
    }
}
