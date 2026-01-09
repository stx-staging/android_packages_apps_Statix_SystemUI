package com.statix.android.systemui.qs.panels.data.repository

import android.content.ComponentName
import com.android.systemui.dagger.SysUISingleton
import com.android.systemui.qs.external.CustomTile
import com.android.systemui.qs.panels.data.repository.DefaultLargeTilesRepository
import com.android.systemui.qs.pipeline.shared.TileSpec
import javax.inject.Inject

@SysUISingleton
class DefaultLargeTilesRepositoryStatixImpl @Inject constructor() : DefaultLargeTilesRepository {
    override val defaultLargeTiles =
        setOf(
            TileSpec.create("internet"),
            TileSpec.create("bt"),
            TileSpec.create("dnd"),
            TileSpec.create(QUICK_SHARE_COMPONENT_NAME),
        )

    companion object {
        val QUICK_SHARE_COMPONENT_NAME =
            ComponentName(
                "com.google.android.gms",
                "com.google.android.gms.nearby.sharing.SharingTileService",
            )
    }
}
