package com.denys.shoppinglist.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.denys.shoppinglist.databinding.EditListItemDialogBinding
import com.denys.shoppinglist.entities.ShopListItem

object EditListItemDialog {
    fun showDialog(context: Context, item: ShopListItem, listener: Listener) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = EditListItemDialogBinding.inflate(
            LayoutInflater.from(context)
        )
        builder.setView(binding.root)
        binding.apply {
            edName.setText(item.name)
            edInfo.setText(item.itemInfo)
            if(item.itemType == 1) edInfo.visibility = View.GONE
            bUpdate.setOnClickListener{
                if(edName.text.toString().isNotEmpty()){
                    listener.onClick(item.copy(
                        name = edName.text.toString(),
                        itemInfo = edInfo.text.toString()
                    ))
                }
                dialog?.dismiss()
            }
        }
        dialog = builder.create()
        dialog.window?.apply {
            setBackgroundDrawableResource(android.R.color.transparent)
        }

        dialog.show()
    }

    interface Listener {
        fun onClick(item: ShopListItem)
    }
}