package com.statix.android.systemui.qs.panels.data.repository

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.res.Resources
import com.android.systemui.dagger.SysUISingleton
import com.android.systemui.dagger.qualifiers.Main
import com.android.systemui.qs.external.CustomTile
import com.android.systemui.qs.flags.QsSplitInternetTile
import com.android.systemui.qs.panels.data.repository.DefaultLargeTilesRepository
import com.android.systemui.qs.pipeline.shared.TileSpec
import com.android.systemui.res.R
import javax.inject.Inject

@SysUISingleton
@SuppressLint("ShadeDisplayAwareContextChecker")
class DefaultLargeTilesRepositoryStatixImpl @Inject constructor(@Main resources: Resources) :
    DefaultLargeTilesRepository {
    override val defaultLargeTiles =
        if (QsSplitInternetTile.isEnabled) {
            resources
                .getStringArray(R.array.quick_settings_large_tiles_default_split)
                .map(TileSpec::create)
                .toSet()
        } else {
            setOf(
                TileSpec.create("internet"),
                TileSpec.create("bt"),
                TileSpec.create("dnd"),
                TileSpec.create(QUICK_SHARE_COMPONENT_NAME),
            )
        }

    companion object {
        val QUICK_SHARE_COMPONENT_NAME =
            ComponentName(
                "com.google.android.gms",
                "com.google.android.gms.nearby.sharing.SharingTileService",
            )
    }
}
