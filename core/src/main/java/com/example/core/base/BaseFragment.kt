package com.example.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.core.exension.showSnackbar
import com.example.core.util.AppLog
import com.example.core.util.SingleEvent
import com.example.core.util.dismissKeyboard
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


abstract class BaseFragment : Fragment() {
    protected lateinit var binding: ViewBinding

    @Inject
    lateinit var applog : AppLog

    abstract fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding
    abstract fun setUpObserver()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = initViewBinding(inflater, container)
        setUpObserver()
        return binding.root
    }

    fun showErrorMessage(message: SingleEvent<Any>){
        if(message.getContent() is String) {
            context?.dismissKeyboard(binding.root)
            binding.root.showSnackbar(message.getContent() as String, Snackbar.LENGTH_LONG)
        }
    }
}