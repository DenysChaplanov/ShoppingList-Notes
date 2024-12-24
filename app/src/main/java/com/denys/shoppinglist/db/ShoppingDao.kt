package com.denys.shoppinglist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.denys.shoppinglist.entities.NoteItem
import com.denys.shoppinglist.entities.ShopListNameItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {
    //note List
    @Query("SELECT * FROM note_list")
    fun getAllNotes():Flow<List<NoteItem>>

    @Query("DELETE FROM note_list WHERE id IS :id")
    suspend fun deleteNote(id: Int)

    @Insert
    suspend fun insertNote(note: NoteItem)

    @Update
    suspend fun updateNote(note: NoteItem)

    //shopping List
    @Query("SELECT * FROM shopping_list_names")
    fun getAllShopListNames():Flow<List<ShopListNameItem>>

    @Insert
    suspend fun insertShopListName(name: ShopListNameItem)

    @Query("DELETE FROM shopping_list_names WHERE id IS :id")
    suspend fun deleteShopListName(id: Int)

    @Update
    suspend fun updateListName(shopListName: ShopListNameItem)
}