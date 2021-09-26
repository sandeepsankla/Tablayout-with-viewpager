package com.example.sandeep.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sandeep.R
import com.example.sandeep.repo.DetailData
import com.example.sandeep.databinding.ItemTab1Binding
import com.example.sandeep.databinding.ItemTab2Binding

class HomeTabAdapter(private val viewType :Int, val mList :ArrayList<DetailData>, private val listener :AdaptorListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
    private val VIEW_TYPE_TAB1 = 1


    override fun onCreateViewHolder(parent: ViewGroup, vt: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_TAB1 -> {
                val binding = ItemTab1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                Tab1ViewHolder(binding)
            }
            else -> {
                val binding = ItemTab2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                Tab2ViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = mList.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item= mList[position]
        if(holder is Tab1ViewHolder){
            if(item.isChecked != null) {
               holder.binding.checkBox.isChecked =item.isChecked!!
           }else{
                holder.binding.checkBox.isChecked =false
            }
           holder.binding.tvName.text = item.name
           Glide.with(holder.binding.ivLogo).load(item.imageUrl).placeholder(R.drawable.ic_default_drawable).into(holder.binding.ivLogo)
       }else if(holder is Tab2ViewHolder){
           if(item.isChecked != null) {
               holder.mBinding.checkBox.isChecked =item.isChecked!!
           }else{
               holder.mBinding.checkBox.isChecked =false
           }
           holder.mBinding.tvName.text = item.name
           Glide.with(holder.mBinding.ivLogo).load(item.imageUrl).placeholder(R.drawable.ic_default_drawable).into(holder.mBinding.ivLogo)
       }
    }

    inner class Tab1ViewHolder(val binding: ItemTab1Binding): RecyclerView.ViewHolder(binding.root) {
       init {
           binding.checkBox.setOnCheckedChangeListener{buttonView, isChecked ->
               listener.onCheckedChangeListener(isChecked, adapterPosition, binding.checkBox.id)
           }
           binding.root.setOnClickListener{
               listener.onItemClick(adapterPosition)
           }
       }
    }
    inner class Tab2ViewHolder(val mBinding: ItemTab2Binding): RecyclerView.ViewHolder(mBinding.root) {
        init {
            mBinding.checkBox.setOnCheckedChangeListener{buttonView, isChecked ->
                listener.onCheckedChangeListener(isChecked, adapterPosition, mBinding.checkBox.id)
            }
            mBinding.root.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

interface AdaptorListener{
    fun onCheckedChangeListener(isChecked :Boolean, position :Int, id :Int)
    fun onItemClick(position:Int)
}

}