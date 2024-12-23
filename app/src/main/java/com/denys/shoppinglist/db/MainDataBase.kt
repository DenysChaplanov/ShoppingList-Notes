package com.denys.shoppinglist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.denys.shoppinglist.entities.LibraryItem
import com.denys.shoppinglist.entities.NoteItem
import com.denys.shoppinglist.entities.ShoppingListItem
import com.denys.shoppinglist.entities.ShoppingListName

@Database(entities = [LibraryItem::class, NoteItem::class, ShoppingListItem::class, ShoppingListName::class],
    version = 1)

abstract class MainDataBase : RoomDatabase() {
    abstract fun shoppingDao(): ShoppingDao

    companion object{
        @Volatile
        private var INSTANCE: MainDataBase? = null
        fun getDataBase(context: Context): MainDataBase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDataBase::class.java,
                    "shopping_list.db"
                ).build()
                instance
            }
        }
    }
}