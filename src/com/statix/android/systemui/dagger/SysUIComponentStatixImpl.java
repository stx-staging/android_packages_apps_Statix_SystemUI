package com.statix.android.systemui.dagger;

import android.os.Handler;
import android.os.Looper;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.util.wakelock.DelayedWakeLock;
import com.android.systemui.util.wakelock.WakeLockLogger;

import com.statix.android.systemui.dagger.SysUIComponentStatix;

import dagger.internal.Provider;
import dagger.Provides;

// Stuff for AmbientIndication
public Provider provideWakeLockLogProvider;
public final Provider provideBgLooperProvider;

public Handler backgroundHandler() {
    return new Handler((Looper) this.provideBgLooperProvider.get());
}

public WakeLockLogger wakeLockLogger() {
    return new WakeLockLogger((LogBuffer) this.provideWakeLockLogProvider.get());
}

public final class SysUIComponentStatixImpl implements SysUIComponentStatix {
    public final class SwitchingProvider implements Provider {
        public final GlobalRootComponentStatix sysUIGoogleGlobalRootComponentImpl;
        public final SysUIComponentStatix sysUIGoogleSysUIComponentImpl;

        public final class AnonymousClass39 {
            public AnonymousClass39() {
            }

            public final DelayedWakeLock create(String str) {
                SwitchingProvider switchingProvider = SwitchingProvider.this;
                return new DelayedWakeLock(switchingProvider.sysUIGoogleSysUIComponentImpl.backgroundHandler(), switchingProvider.sysUIGoogleGlobalRootComponentImpl.context, switchingProvider.sysUIGoogleSysUIComponentImpl.wakeLockLogger(), str);
            }
        }
    }
}
