package com.example.sandeep.ui


import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import androidx.viewpager.widget.ViewPager
import com.example.core.base.BaseActivity
import com.example.sandeep.MainViewModel
import com.example.sandeep.repo.FragmentNames
import com.example.sandeep.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val TAG = MainActivity::class.java.simpleName

    private val viewModel: MainViewModel by viewModels()
    private lateinit  var  currentFragment :Fragment
    override fun initViewBinding(): ViewBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun setUpObserver() {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(viewModel.mList.isNullOrEmpty()){
            viewModel.getData(page = viewModel.PAGE, limit = LIMIT)
        }
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val fragmentList = getFragmentList()
        tabLayout.removeAllTabs()
        viewPager?.removeAllViews()
        val pagerAdapter = BaseViewPagerAdapter(supportFragmentManager, fragmentList)
        viewPager?.adapter = pagerAdapter
        viewPager?.offscreenPageLimit = fragmentList.size
        tabLayout.setupWithViewPager(viewPager)
        viewPager.setCurrentItem(0, true)
        viewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                currentFragment = pagerAdapter.getItem(position)
            }

            override fun onPageSelected(position: Int) {
                Log.d(TAG, "onPageSelected")
            }

            override fun onPageScrollStateChanged(state: Int) {
                Log.d(TAG, "onPageScrollStateChanged")
            }

        })
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                  currentFragment = pagerAdapter.getItem(tab.position)
                  refreshFragment()
            }
        })

    }

    private fun getFragmentList(): ArrayList<FragmentNames> {
        return arrayListOf<FragmentNames>().apply {
            add(0, FragmentNames("tab1", HomeTabFragment.newInstance(1)))
            add(1, FragmentNames("tab2", HomeTabFragment.newInstance(2)))
        }
    }

    fun refreshFragment() {
        if(::currentFragment.isInitialized && currentFragment != null && currentFragment is HomeTabFragment)
            (currentFragment as HomeTabFragment).refreshAdapter()
    }
}