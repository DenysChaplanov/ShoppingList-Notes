package com.denys.shoppinglist.utils

import android.content.Intent
import com.denys.shoppinglist.entities.ShopListItem

object ShareHelper {
    fun shareShopList(shopList: List<ShopListItem>, listName: String): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plane"
        intent.apply {
            putExtra(Intent.EXTRA_TEXT, makeShareText(shopList, listName))
        }
        return intent
    }

    private fun makeShareText(shopList: List<ShopListItem>, listName: String): String {
        return buildString {
            appendLine("<<$listName>>")
            shopList.forEachIndexed { index, item ->
                val itemInfo = if (item.itemInfo.isNotBlank()) " (${item.itemInfo})" else ""
                appendLine("${index + 1} — ${item.name}$itemInfo")
            }
        }
    }
}