/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.statix.android.systemui.power.dagger;

import com.android.systemui.power.EnhancedEstimates;
import com.android.systemui.power.PowerNotificationWarnings;
import com.android.systemui.power.PowerUI;
import com.android.systemui.power.data.repository.PowerRepositoryModule;

import com.statix.android.systemui.power.EnhancedEstimatesStatixImpl;

import dagger.Binds;
import dagger.Module;

/** Dagger Module for code in the power package. */
@Module(
        includes = {
            PowerRepositoryModule.class,
        })
public interface StatixPowerModule {
    /** */
    @Binds
    EnhancedEstimates bindEnhancedEstimates(EnhancedEstimatesStatixImpl enhancedEstimates);

    /** */
    @Binds
    PowerUI.WarningsUI provideWarningsUi(PowerNotificationWarnings controllerImpl);
}
