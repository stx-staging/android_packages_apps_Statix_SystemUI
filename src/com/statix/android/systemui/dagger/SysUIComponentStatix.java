/*
 * Copyright (C) 2022 StatiXOS
 * SPDX-License-Identifer: Apache-2.0
 */

package com.statix.android.systemui.dagger;

import android.os.Handler;
import android.os.Looper;

import com.android.systemui.dagger.DependencyProvider;
import com.android.systemui.dagger.SysUIComponent;
import com.android.systemui.dagger.SysUISingleton;
import com.android.systemui.dagger.SystemUICoreStartableModule;
import com.android.systemui.dagger.SystemUIModule;
import com.android.systemui.keyguard.CustomizationProvider;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.NotificationInsetsModule;
import com.android.systemui.statusbar.QsFrameTranslateModule;
import com.android.systemui.unfold.SysUIUnfoldModule;
import com.android.systemui.util.wakelock.DelayedWakeLock;
import com.android.systemui.util.wakelock.WakeLockLogger;

import com.statix.android.systemui.dagger.SystemUIStatixCoreStartableModule;
import com.statix.android.systemui.dagger.SystemUIStatixModule;

import dagger.inject.Inject;
import dagger.internal.Provider;
import dagger.Provides;
import dagger.Subcomponent;

/**
 * Dagger Subcomponent for Core SysUI used in AOSP.
 */
@SysUISingleton
@Subcomponent(modules = {
        DependencyProvider.class,
        NotificationInsetsModule.class,
        QsFrameTranslateModule.class,
        StatixComponentBinder.class,
        SystemUIModule.class,
        SystemUIStatixModule.class,
        SystemUICoreStartableModule.class,
        SystemUIStatixCoreStartableModule.class,
        SysUIUnfoldModule.class})
public interface SysUIComponentStatix extends SysUIComponent {

    /**
     * Builder for a SysUIComponentStatix.
     */
    @SysUISingleton
    @Subcomponent.Builder
    interface Builder extends SysUIComponent.Builder {
        SysUIComponentStatix build();
    }

    /**
     * Member injection into the supplied argument.
     */
    void inject(CustomizationProvider customizationProvider);

    // Stuff for AmbientIndication

//    public static DefaultAmbientIndicationAreaSection m1828$$Nest$mdefaultAmbientIndicationAreaSection(DaggerSysUIGoogleGlobalRootComponent$SysUIGoogleSysUIComponentImpl daggerSysUIGoogleGlobalRootComponent$SysUIGoogleSysUIComponentImpl) {
//        KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) daggerSysUIGoogleGlobalRootComponent$SysUIGoogleSysUIComponentImpl.keyguardUpdateMonitorProvider.get();
//        KeyguardAmbientIndicationViewModel keyguardAmbientIndicationViewModel = daggerSysUIGoogleGlobalRootComponent$SysUIGoogleSysUIComponentImpl.keyguardAmbientIndicationViewModel();
//        return new DefaultAmbientIndicationAreaSection(keyguardUpdateMonitor, (ActivityStarter) daggerSysUIGoogleGlobalRootComponent$SysUIGoogleSysUIComponentImpl.activityStarterImplProvider.get(), (PowerInteractor) daggerSysUIGoogleGlobalRootComponent$SysUIGoogleSysUIComponentImpl.powerInteractorProvider.get(), (SwitchingProvider.AnonymousClass39) daggerSysUIGoogleGlobalRootComponent$SysUIGoogleSysUIComponentImpl.factoryProvider34.get(), keyguardAmbientIndicationViewModel);
//    }

//    public static GoogleAmbientIndicationSection m1853$$Nest$mgoogleAmbientIndicationSection(DaggerSysUIGoogleGlobalRootComponent$SysUIGoogleSysUIComponentImpl daggerSysUIGoogleGlobalRootComponent$SysUIGoogleSysUIComponentImpl) {
//        KeyguardAmbientIndicationViewModel keyguardAmbientIndicationViewModel = daggerSysUIGoogleGlobalRootComponent$SysUIGoogleSysUIComponentImpl.keyguardAmbientIndicationViewModel();
//        return new GoogleAmbientIndicationSection((KeyguardUpdateMonitor) keyguardUpdateMonitorProvider.get(), (ActivityStarter) activityStarterImplProvider.get(), (PowerInteractor) powerInteractorProvider.get(), (SwitchingProvider.AnonymousClass39) factoryProvider34.get(), keyguardAmbientIndicationViewModel);
//    }
}
