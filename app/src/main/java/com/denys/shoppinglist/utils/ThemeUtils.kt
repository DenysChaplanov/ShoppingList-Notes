package com.denys.shoppinglist.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.denys.shoppinglist.R

object ThemeUtils {
    fun applyTheme(context: Context): Int {
        val defPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return if (defPref.getString("choose_theme_key", "blue") == "blue") {
            R.style.Base_Theme_ShoppingListBlue
        } else {
            R.style.Base_Theme_ShoppingListGreen
        }
    }
}