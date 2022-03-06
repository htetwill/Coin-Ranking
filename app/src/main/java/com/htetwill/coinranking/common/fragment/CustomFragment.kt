package com.htetwill.coinranking.common.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import coil.ImageLoader
import com.htetwill.coinranking.common.activities.MainActivity
import com.htetwill.coinranking.common.adapter.CoinHeaderAdapter
import com.htetwill.coinranking.common.adapter.CoinListAdapter
import com.htetwill.coinranking.common.viewmodel.CustomViewModel
import com.htetwill.coinranking.data.event.ListItemEvent
import com.htetwill.coinranking.data.util.viewModel
import com.htetwill.coinranking.di.annotation.Injectable
import com.htetwill.coinranking.fragment.R
import com.htetwill.coinranking.fragment.databinding.RecyclerViewFragBinding
import com.htetwill.coinranking.fragment.observePeek
import com.htetwill.coinranking.fragment.observeSingle
import javax.inject.Inject

@Suppress("SpellCheckingInspection")

class CustomFragment : Fragment() ,Injectable{

    private lateinit var concatAdapter: ConcatAdapter
    private lateinit var mAdapter: CoinListAdapter
    private lateinit var mAdapterHeader: CoinHeaderAdapter
    @Inject lateinit var mViewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var mImageLoader : ImageLoader
    private lateinit var viewModel: CustomViewModel

    private lateinit var mLayoutManager: GridLayoutManager
    private var _binding: RecyclerViewFragBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = RecyclerViewFragBinding.inflate(inflater, container, false)
        val rootView = binding.root

        viewModel = viewModel(mViewModelFactory)
        binding.viewModel = viewModel
        setRecyclerViewLayoutManager()
        initAdapter()
        initObserver()
        initRecyclerView()
        initData()



        return rootView
    }

    private fun initRecyclerView() {
        concatAdapter = ConcatAdapter(
            ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build(),
            mAdapterHeader, mAdapter)
        binding.recyclerView.adapter = concatAdapter
    }

    private fun initData() {
        viewModel.getAllData()
    }

    private fun setRecyclerViewLayoutManager() {
        mLayoutManager = GridLayoutManager(activity,3)
        mLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                when(concatAdapter.getItemViewType(position)) {
                    R.layout.list_item_coin_header -> {
                        Log.wtf("setRecyclerViewLayoutManager", "R.layout.list_item_coin_header")
                        return 1
                    }
                    R.layout.list_item_coin-> {
                        Log.wtf("setRecyclerViewLayoutManager", "R.layout.list_item_coin")
                        return 3
                    }
                }
                Log.wtf("setRecyclerViewLayoutManager", "Unable to find")
                return 3
            }
        }
        with(binding.recyclerView) {
            layoutManager = mLayoutManager
        }
    }

    private fun initObserver() {
        observeFetch()
        observeGetAllData()
    }

    private fun observeGetAllData() {
        observePeek(viewModel.getDataLiveData,{
           if(it.isNotEmpty()) {
               val mMutableList = it.toMutableList()
               mAdapterHeader.updateList(mMutableList.slice(0..2))
               mMutableList.removeAll(mMutableList.slice(0..2))
               mAdapter.updateList(mMutableList)
           }
       })
    }

    private fun initAdapter() {
        mAdapter = CoinListAdapter(mImageLoader) {
            onItemClicked(it)
        }
        mAdapterHeader= CoinHeaderAdapter(mImageLoader) {
            onItemClicked(it)
        }
    }

    private fun onItemClicked(event: ListItemEvent) {
        when(event) {
            is ListItemEvent.ItemClicked -> {
                Toast.makeText(this.context, "onItemClicked: Clicked", Toast.LENGTH_SHORT).show()
                // TODO: 2022-02-22,3:11 htetwill WIP
/*
                navigationController().navigate(
                    OffersFragmentDirections.actionFragmentOffersToFragmentOfferPreview(
                        event.contentPreviewModel.id
                    )
                )
*/
            }
        } }

    private fun observeFetch() {
        observeSingle(viewModel.fetchLiveData, {
                Log.w("", "observeFetch: Success")
            }, onError = {
            viewModel.isLoading.set(false)
            (activity as? MainActivity)?.showError(it)
            Log.w("", "observeFetch: onError")
            Toast.makeText(this.context, "observeFetch: onError", Toast.LENGTH_SHORT).show()
            }, onHideLoading = {viewModel.isLoading.set(false)},
            onLoading = { viewModel.isLoading.set(true) }
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
