package com.example.sandeep.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.sandeep.MainViewModel
import com.example.sandeep.databinding.LayoutDetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class DetailFragment : DialogFragment(),View.OnClickListener {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: LayoutDetailFragmentBinding
    private  var position :Int = -1
    private var refreshPreviousPage = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutDetailFragmentBinding.inflate(inflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
        position = it.getInt(ARG_PARAM1)
         setData()
        }
        binding.closeButton.setOnClickListener(this)
        binding.checkBox.setOnCheckedChangeListener{ _, isChecked ->
            refreshPreviousPage = true
            viewModel.mList[position].isChecked = isChecked
        }
    }

    private fun setData() {
        val item =viewModel.mList[position]
        item.isChecked?.let {
            binding.checkBox.isChecked = it
        }
        binding.tvTitle.text = item.name
        Glide.with(binding.imageView).load(item.imageUrl).into(binding.imageView)
    }

    override fun onResume() {
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
        super.onResume()
    }

    companion object {
        @JvmStatic
        fun getInstance(position: Int) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, position)
                }
            }
    }

    override fun onClick(v: View?) {
       when(v?.id){
           binding.closeButton.id-> {
               if(refreshPreviousPage && activity != null){
                   (activity as MainActivity).refreshFragment()
               }
               dismissAllowingStateLoss()
           }
       }
    }

}