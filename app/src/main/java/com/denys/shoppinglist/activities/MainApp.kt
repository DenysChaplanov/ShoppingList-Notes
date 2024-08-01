package com.denys.shoppinglist.activities

import android.app.Application
import com.denys.shoppinglist.db.MainDataBase

class MainApp: Application() {
    val database by lazy { MainDataBase.getDataBase(this) }

}