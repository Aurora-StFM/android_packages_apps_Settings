/*
 * SPDX-FileCopyrightText: 2026 The SomethingOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.android.settings.somethingos;

import android.content.Context;
import android.graphics.Typeface;
import android.provider.Settings;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Locale;

/**
 * Fonts for Settings page titles, as in NothingOS: a serif by default, or capitals in the
 * dot-matrix font when Special features > Experimental features > Dot matrix title is on.
 */
public final class SomethingTitles {

    /** Settings.Secure key for the Dot matrix title switch (1 = on). */
    public static final String DOT_TITLES = "somethingos_dot_titles";

    // Font families registered in packages/overlays/Lineage (fonts_customization.xml).
    private static final String DOT_FAMILY = "somethingos-dot";
    private static final String SERIF_FAMILY = "somethingos-serif";

    private SomethingTitles() {}

    public static boolean isDotEnabled(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), DOT_TITLES, 0) == 1;
    }

    public static void setDotEnabled(Context context, boolean enabled) {
        Settings.Secure.putInt(context.getContentResolver(), DOT_TITLES, enabled ? 1 : 0);
    }

    /** Sets the title font, both expanded (large) and collapsed (in the top bar). */
    public static void applyTypeface(Context context, CollapsingToolbarLayout toolbar) {
        if (toolbar == null) return;
        Typeface typeface = Typeface.create(
                isDotEnabled(context) ? DOT_FAMILY : SERIF_FAMILY, Typeface.NORMAL);
        toolbar.setExpandedTitleTypeface(typeface);
        toolbar.setCollapsedTitleTypeface(typeface);
    }

    /** The title as shown: in capitals with the dot-matrix font. */
    public static CharSequence format(Context context, CharSequence title) {
        if (title == null || !isDotEnabled(context)) return title;
        return title.toString().toUpperCase(Locale.getDefault());
    }
}
