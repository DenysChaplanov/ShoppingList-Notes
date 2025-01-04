package com.denys.shoppinglist.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.denys.shoppinglist.R

object ThemeUtils {
    fun applyTheme(context: Context): Int {
        return when (getCurrentThemeKey(context)) {
            "green" -> R.style.Base_Theme_ShoppingListGreen
            else -> R.style.Base_Theme_ShoppingListBlue
        }
    }

    fun getCurrentThemeKey(context: Context): String {
        val defPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return defPref.getString("choose_theme_key", "blue") ?: "blue"
    }
}