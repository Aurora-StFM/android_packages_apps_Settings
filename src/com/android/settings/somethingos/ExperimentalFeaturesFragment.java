/*
 * SPDX-FileCopyrightText: 2026 The SomethingOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.android.settings.somethingos;

import android.app.settings.SettingsEnums;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;

/** Special features > Experimental features: features still being tested. */
public class ExperimentalFeaturesFragment extends DashboardFragment {

    private static final String TAG = "ExperimentalFeatures";

    @Override
    public int getMetricsCategory() {
        return SettingsEnums.PAGE_UNKNOWN;
    }

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.somethingos_experimental_features;
    }
}
