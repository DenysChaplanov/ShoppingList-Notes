package com.denys.shoppinglist.dialogs

import android.content.Context
import android.text.InputFilter
import android.view.LayoutInflater
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
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    interface Listener {
        fun onClick(item: ShopListItem)
    }
}