package com.denys.shoppinglist.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.denys.shoppinglist.R
import com.denys.shoppinglist.databinding.ActivityMainBinding
import com.denys.shoppinglist.dialogs.NewListDialog
import com.denys.shoppinglist.fragments.FragmentManager
import com.denys.shoppinglist.fragments.NoteFragment
import com.denys.shoppinglist.fragments.ShopListNamesFragment
import com.denys.shoppinglist.settings.SettingsActivity
import com.denys.shoppinglist.utils.ThemeUtils

class MainActivity : AppCompatActivity(), NewListDialog.Listener {

    private lateinit var binding: ActivityMainBinding
    private var currentMenuItemId = R.id.shop_list
    private var currentTheme = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        currentTheme = ThemeUtils.getCurrentThemeKey(this)
        setTheme(ThemeUtils.applyTheme(this))
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
        setBottomNavListener()
    }

    private fun setBottomNavListener() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                }
                R.id.notes -> {
                    currentMenuItemId = R.id.notes
                    FragmentManager.setFragment(NoteFragment.newInstance(), this)
                }
                R.id.shop_list -> {
                    currentMenuItemId = R.id.shop_list
                    FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
                }
                R.id.new_item -> {
                    FragmentManager.currentFrag?.onClickNew()
                }
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigation.selectedItemId = currentMenuItemId
        if (ThemeUtils.getCurrentThemeKey(this) != currentTheme) {
            recreate()
        }
    }

    override fun onClick(name: String) {
        Log.d("MyLog", "Name: $name")
    }
}