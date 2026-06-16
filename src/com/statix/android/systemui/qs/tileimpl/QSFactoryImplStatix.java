/*
 * Copyright (C) 2022 StatiXOS
 * SPDX-License-Identifer: Apache-2.0
 */

package com.statix.android.systemui.qs.tileimpl;


import com.android.systemui.dagger.SysUISingleton;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.tileimpl.QSFactoryImpl;
import com.android.systemui.qs.tileimpl.QSTileImpl;

import dagger.Lazy;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * A factory that creates Quick Settings tiles based on a tileSpec
 *
 * <p>To create a new tile within SystemUI, the tile class should extend {@link QSTileImpl} and have
 * a public static final TILE_SPEC field which serves as a unique key for this tile. (e.g. {@link
 * com.android.systemui.qs.tiles.BluetoothTile#TILE_SPEC})
 *
 * <p>After, create or find an existing Module class to house the tile's binding method (e.g. {@link
 * com.android.systemui.accessibility.AccessibilityModule}). If creating a new module, add your
 * module to the SystemUI dagger graph by including it in an appropriate module.
 */
@SysUISingleton
public class QSFactoryImplStatix extends QSFactoryImpl {

    @Inject
    public QSFactoryImplStatix(
            Lazy<QSHost> qsHostLazy,
            Provider<CustomTile.Factory> customTileBuilderProvider,
            Map<String, Provider<QSTileImpl<?>>> tileMap) {
        super(qsHostLazy, customTileBuilderProvider, tileMap);
    }
}
