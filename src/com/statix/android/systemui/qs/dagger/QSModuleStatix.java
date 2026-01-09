package com.statix.android.systemui.qs.dagger;

import com.android.systemui.qs.dagger.QSModuleBase;
import com.statix.android.systemui.qs.panels.dagger.PanelsModuleStatix;

import dagger.Module;

@Module(includes = { PanelsModuleStatix.class, QSModuleBase.class })
public interface QSModuleStatix { }
