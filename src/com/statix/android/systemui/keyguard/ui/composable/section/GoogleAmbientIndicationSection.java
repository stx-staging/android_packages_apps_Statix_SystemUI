package com.google.android.systemui.keyguard.ui.composable.section;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.power.domain.interactor.PowerInteractor;

import com.google.android.systemui.keyguard.ui.viewmodel.KeyguardAmbientIndicationViewModel;

// ?????
// import com.statix.android.systemui.dagger.DaggerGlobalRootComponentStatix.SysUIGoogleSysUIComponentImpl.SwitchingProvider.AnonymousClass39;

public final class GoogleAmbientIndicationSection {
    public final ActivityStarter activityStarter;
//    public final AnonymousClass39 delayedWakeLockFactory;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final PowerInteractor powerInteractor;
    public final KeyguardAmbientIndicationViewModel viewModel;

    public GoogleAmbientIndicationSection(KeyguardUpdateMonitor keyguardUpdateMonitor, ActivityStarter activityStarter, PowerInteractor powerInteractor, /** AnonymousClass39 anonymousClass39, **/ KeyguardAmbientIndicationViewModel keyguardAmbientIndicationViewModel) {
        this.viewModel = keyguardAmbientIndicationViewModel;
        this.powerInteractor = powerInteractor;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.activityStarter = activityStarter;
//        this.delayedWakeLockFactory = anonymousClass39;
    }
}
