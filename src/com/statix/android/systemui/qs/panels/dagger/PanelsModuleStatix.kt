package com.statix.android.systemui.qs.panels.dagger

import com.android.systemui.qs.panels.dagger.PanelsModuleBase
import com.android.systemui.qs.panels.data.repository.DefaultLargeTilesRepository
import com.statix.android.systemui.qs.panels.data.repository.DefaultLargeTilesRepositoryStatixImpl
import dagger.Binds
import dagger.Module

@Module(includes = [PanelsModuleBase::class])
interface PanelsModuleStatix {
    @Binds
    fun bindDefaultLargeTilesSpecsRepository(
        impl: DefaultLargeTilesRepositoryStatixImpl
    ): DefaultLargeTilesRepository
}
