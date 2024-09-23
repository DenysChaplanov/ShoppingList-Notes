package com.denys.shoppinglist.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.denys.shoppinglist.activities.MainApp
import com.denys.shoppinglist.databinding.FragmentShopListNamesBinding
import com.denys.shoppinglist.db.MainViewModel
import com.denys.shoppinglist.dialogs.NewListDialog

class ShopListNamesFragment : BaseFragment() {
    private lateinit var binding: FragmentShopListNamesBinding

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onClickNew() {
        NewListDialog.showDialog(
            activity as AppCompatActivity,
            object : NewListDialog.Listener {
                override fun onClick(name: String) {
                    Log.d("MyLog", "Name: $name")
                }

            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopListNamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun initRcView() = with(binding) {

    }

    private fun observer() {
        mainViewModel.allNotes.observe(viewLifecycleOwner) {

        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = ShopListNamesFragment()
    }
}