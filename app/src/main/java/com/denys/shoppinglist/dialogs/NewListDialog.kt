package com.denys.shoppinglist.dialogs

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.denys.shoppinglist.R
import com.denys.shoppinglist.databinding.NewListDialogBinding

object NewListDialog {
    fun showDialog(context: Context, listener: Listener, name: String) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = NewListDialogBinding.inflate(
            LayoutInflater.from(context)
        )
        builder.setView(binding.root)
        binding.apply {
            editNewListName.setText(name)
            if(name.isNotEmpty()){
                buttonCreate.text = context.getString(R.string.update)
                tvTitle.text = context.getString(R.string.update_list_name)
            }
            buttonCreate.setOnClickListener {
                val listName = editNewListName.text.toString()
                if (listName.isNotEmpty()) {
                    listener.onClick(listName)
                }
                dialog?.dismiss()
            }
        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    interface Listener {
        fun onClick(name: String)
    }
}