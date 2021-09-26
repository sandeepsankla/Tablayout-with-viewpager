package com.example.sandeep.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.core.base.BaseFragment
import com.example.core.exension.hide
import com.example.core.exension.inVisible
import com.example.core.exension.observe
import com.example.core.exension.show
import com.example.core.util.*
import com.example.sandeep.MainViewModel
import com.example.sandeep.databinding.FragmentHomeTabBinding
import com.example.sandeep.repo.DetailData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home_tab.*
import kotlinx.android.synthetic.main.fragment_home_tab.view.*

private const val ARG_PARAM1 = "param1"
const val LIMIT =20
@AndroidEntryPoint
class HomeTabFragment : BaseFragment(){
    private var viewType: Int? = null
    private lateinit var adapter:HomeTabAdapter
    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let { viewType = it.getInt(ARG_PARAM1) }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
    }

    private  val listener = object : HomeTabAdapter.AdaptorListener {
        override fun onCheckedChangeListener(isChecked: Boolean, position: Int, id:Int) {
            viewModel.mList[position].isChecked = isChecked
        }

        override fun onItemClick(position: Int) {
            DetailFragment.getInstance(position).show(childFragmentManager, "")
        }

    }

    private fun setUpAdapter() {
        linearLayoutManager = LinearLayoutManager(context)
        binding.root.recyclerview.layoutManager = linearLayoutManager
        adapter = HomeTabAdapter(viewType!!, viewModel.mList, listener)
        binding.root.recyclerview.adapter  = adapter
        binding.root.recyclerview.addOnScrollListener(endlessRecyclerListener)
    }

    private val endlessRecyclerListener by lazy {
        object : EndlessRecycleListener(linearLayoutManager) {
            override fun onLoadMore(currentPage: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.PAGE +=1
                viewModel.getData(page = viewModel.PAGE, limit = LIMIT)
            }
        }
    }



    override fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding =
      FragmentHomeTabBinding.inflate(inflater, container, false)


    override fun setUpObserver() {
            observe(viewModel.tabData, ::tabDataListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun tabDataListener(response: Resource<ArrayList<DetailData>>) {
            when (response.status) {
                Status.SUCCESS -> {
                    applog.d("TAG"," ${response._data}")
                    response._data?.let {
                        if (viewModel.PAGE==1) {
                            viewModel.mList.clear()
                            endlessRecyclerListener.resetState()
                        }
                        viewModel.mList.addAll(it)
                        adapter.notifyDataSetChanged()

                    }
                    progressBar.inVisible()

                }
                Status.ERROR -> {
                    progressBar.inVisible()
                    viewModel.showSnackBarMessage(response.message.toString())
                }
                Status.LOADING -> {
                    progressBar.show()
                }

            }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshAdapter(){
           Log.d("sasa", "${viewType}")
            if(::adapter.isInitialized){
                adapter.notifyDataSetChanged()
            }
    }


    companion object {
        @JvmStatic
        fun newInstance(viewType: Int) = HomeTabFragment().apply { arguments = Bundle().apply { putInt(ARG_PARAM1, viewType) }
        }
    }
}