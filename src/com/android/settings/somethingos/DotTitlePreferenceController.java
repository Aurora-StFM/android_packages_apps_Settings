/*
 * SPDX-FileCopyrightText: 2026 The SomethingOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.android.settings.somethingos;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

import com.android.settings.R;
import com.android.settings.core.SettingsBaseActivity;
import com.android.settings.core.TogglePreferenceController;

/** The Dot matrix title switch. Restyles the current page's title straight away. */
public class DotTitlePreferenceController extends TogglePreferenceController {

    public DotTitlePreferenceController(Context context, String key) {
        super(context, key);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public boolean isChecked() {
        return SomethingTitles.isDotEnabled(mContext);
    }

    @Override
    public boolean setChecked(boolean isChecked) {
        SomethingTitles.setDotEnabled(mContext, isChecked);
        // Other pages pick it up when they open; this one is already showing.
        Activity activity = findActivity(mContext);
        if (activity instanceof SettingsBaseActivity) {
            ((SettingsBaseActivity) activity).reapplyTitleStyle();
        }
        return true;
    }

    @Override
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_somethingos_special_features;
    }

    private static Activity findActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) return (Activity) context;
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}
