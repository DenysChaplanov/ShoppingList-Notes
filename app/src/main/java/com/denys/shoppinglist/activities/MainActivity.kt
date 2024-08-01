package com.denys.shoppinglist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.NetworkOnMainThreadException
import android.util.Log
import com.denys.shoppinglist.R
import com.denys.shoppinglist.databinding.ActivityMainBinding
import com.denys.shoppinglist.fragments.FragmentManager
import com.denys.shoppinglist.fragments.NoteFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomNavListener()
    }

    private fun setBottomNavListener(){
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.settings -> {
                    Log.d("MyLog", "Settings was clicked")
                }
                R.id.notes -> {
                    FragmentManager.setFragment(NoteFragment.newInstance(), this)
                }
                R.id.shop_list -> {
                    Log.d("MyLog", "Shop list was clicked")
                }
                R.id.new_item -> {
                    FragmentManager.currentFrag?.onClickNew()
                }
            }
            true
        }
    }
}