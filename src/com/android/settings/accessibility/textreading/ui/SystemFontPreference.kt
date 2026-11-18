/*
 * SPDX-FileCopyrightText: 2026 The SomethingOS Project
 * SPDX-License-Identifier: Apache-2.0
 */
package com.android.settings.accessibility.textreading.ui

// SomethingOS: "Default font" row on the Font and display size page. The
// picker itself lives in vendor/somethingos/apps/FontPicker, which has the
// permissions needed to change the system font.

import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.android.settings.R
import com.android.settingslib.metadata.PreferenceAvailabilityProvider
import com.android.settingslib.metadata.PreferenceMetadata
import com.android.settingslib.metadata.PreferenceSummaryProvider
import com.android.settingslib.metadata.UI_ONLY_PREFERENCE
import com.android.settingslib.metadata.preferencesapi.preconditions.PreconditionStability
import org.json.JSONException
import org.json.JSONObject

/** Opens the SomethingOS system font picker; the summary shows the current font. */
class SystemFontPreference :
    PreferenceMetadata, PreferenceSummaryProvider, PreferenceAvailabilityProvider {

    override val key: String
        get() = KEY

    override val purpose: Int
        get() = R.string.somethingos_system_font_purpose

    override val title: Int
        get() = R.string.somethingos_system_font_title

    override fun tags(context: Context) = arrayOf(UI_ONLY_PREFERENCE)

    override fun intent(context: Context): Intent? =
        Intent(PICK_FONT_ACTION).setPackage(PICKER_PACKAGE)

    // Hidden if the picker app isn't installed.
    override val availabilityDescription = "The SomethingOS font picker must be installed."

    override fun getAvailabilityStability() = PreconditionStability.STABLE_UNTIL_APK_UPDATE

    override fun isAvailable(context: Context): Boolean =
        intent(context)?.let { context.packageManager.resolveActivity(it, 0) } != null

    // The picker enables the Roboto font overlay through SystemUI's theme
    // setting when Roboto is chosen, so that setting tells us the font.
    override fun getSummary(context: Context): CharSequence? {
        val json =
            Settings.Secure.getString(context.contentResolver, THEME_SETTING) ?: ""
        val overlay =
            try {
                if (json.isEmpty()) null else JSONObject(json).optString(FONT_CATEGORY)
            } catch (e: JSONException) {
                null
            }
        return context.getString(
            if (overlay == ROBOTO_OVERLAY) R.string.somethingos_system_font_roboto
            else R.string.somethingos_system_font_geist
        )
    }

    companion object {
        const val KEY = "somethingos_system_font"
        private const val PICKER_PACKAGE = "org.somethingos.fontpicker"
        private const val PICK_FONT_ACTION = "org.somethingos.fontpicker.action.PICK_FONT"
        private const val THEME_SETTING = "theme_customization_overlay_packages"
        private const val FONT_CATEGORY = "android.theme.customization.font"
        private const val ROBOTO_OVERLAY = "org.somethingos.overlay.font.roboto"
    }
}
