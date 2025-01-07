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
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class MainActivity : AppCompatActivity(), NewListDialog.Listener {

    private lateinit var binding: ActivityMainBinding
    private var currentMenuItemId = R.id.shop_list
    private var currentTheme = ""
    private var iAd: InterstitialAd? = null
    private var adShowCounter = 0
    private var adShowCounterMax = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        currentTheme = ThemeUtils.getCurrentThemeKey(this)
        setTheme(ThemeUtils.applyTheme(this))
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
        setBottomNavListener()
        loadInterAd()
    }

    private fun loadInterAd() {
        val request = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            getString(R.string.inter_ad_id),
            request,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    iAd = ad
                }

                override fun onAdFailedToLoad(ad: LoadAdError) {
                    iAd = null
                }
            })
    }

    private fun showInterAd(adListener: AdListener) {
        if (iAd != null && adShowCounter > adShowCounterMax) {
            iAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    iAd = null
                    loadInterAd()
                    adListener.onFinish()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    iAd = null
                    loadInterAd()
                }

                override fun onAdShowedFullScreenContent() {
                    iAd = null
                    loadInterAd()
                }
            }
            adShowCounter = 0
            iAd?.show(this)
        } else {
            adShowCounter++
            adListener.onFinish()
        }
    }

    private fun setBottomNavListener() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.settings -> {
                    showInterAd(object : AdListener {
                        override fun onFinish() {
                            startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                        }

                    })
                }

                R.id.notes -> {
                    showInterAd(object : AdListener {
                        override fun onFinish() {
                            setActionBarTitle(getString(R.string.note_list_title))
                            currentMenuItemId = R.id.notes
                            FragmentManager.setFragment(
                                NoteFragment.newInstance(),
                                this@MainActivity
                            )
                        }

                    })
                }

                R.id.shop_list -> {
                    setActionBarTitle(getString(R.string.shop_list_title))
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

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
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

    interface AdListener {
        fun onFinish()
    }
}