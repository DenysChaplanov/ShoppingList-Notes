package com.denys.shoppinglist.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MenuItem.OnActionExpandListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.denys.shoppinglist.R
import com.denys.shoppinglist.databinding.ActivityShopListBinding
import com.denys.shoppinglist.db.MainViewModel
import com.denys.shoppinglist.entities.ShopListNameItem

class ShopListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShopListBinding
    private var shopListNameItem: ShopListNameItem? = null
    private lateinit var saveItem: MenuItem

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shop_list_menu, menu)
        saveItem = menu?.findItem(R.id.save_item)!!
        saveItem.isVisible = false
        val newItem = menu.findItem(R.id.new_item)
        newItem.setOnActionExpandListener(expandActionView())
        return true
    }

    private fun expandActionView(): OnActionExpandListener{
        return object : OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                saveItem.isVisible = true
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                saveItem.isVisible = false
                invalidateOptionsMenu()
                return true
            }

        }
    }

    private fun init(){
        shopListNameItem = intent.getSerializableExtra(SHOP_LIST_NAME) as ShopListNameItem
        binding.tvTest.text = shopListNameItem?.name
    }

    companion object{
        const val SHOP_LIST_NAME = "shop_list_name"
    }
}