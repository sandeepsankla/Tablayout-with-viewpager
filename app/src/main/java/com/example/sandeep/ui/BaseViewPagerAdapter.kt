package com.example.sandeep.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.sandeep.repo.FragmentNames

class BaseViewPagerAdapter (fm: FragmentManager,  private val list: ArrayList<FragmentNames>) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {
        return list.get(position).fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list.getOrNull(position)?.name
    }

}