package com.htetwill.coinranking.common.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.htetwill.coinranking.common.adapter.CoinListAdapter
import com.htetwill.coinranking.common.viewmodel.CustomViewModel
import com.htetwill.coinranking.data.event.ListItemEvent
import com.htetwill.coinranking.data.util.viewModel
import com.htetwill.coinranking.di.annotation.Injectable
import com.htetwill.coinranking.fragment.databinding.RecyclerViewFragBinding
import com.htetwill.coinranking.fragment.observePeek
import com.htetwill.coinranking.fragment.observeSingle
import javax.inject.Inject

@Suppress("SpellCheckingInspection")

class CustomFragment : Fragment() ,Injectable{

    private lateinit var mAdapter: CoinListAdapter
    @Inject lateinit var mViewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var mImageLoader : ImageLoader
    private lateinit var viewModel: CustomViewModel

    private lateinit var currentLayoutManagerType: LayoutManagerType
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private var _binding: RecyclerViewFragBinding? = null
    private val binding get() = _binding!!

    enum class LayoutManagerType { LINEAR_LAYOUT_MANAGER }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = RecyclerViewFragBinding.inflate(inflater, container, false)
        val rootView = binding.root
        layoutManager = LinearLayoutManager(activity)
        currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER

        if (savedInstanceState != null) {
            currentLayoutManagerType = savedInstanceState
                    .getSerializable("KEY_LAYOUT_MANAGER") as LayoutManagerType
        }

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
        binding.recyclerView.adapter = mAdapter
    }

    private fun initData() {
        viewModel.getAllData()
    }

    private fun setRecyclerViewLayoutManager() {
        layoutManager = LinearLayoutManager(activity)
        currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER
        with(binding.recyclerView) {
            layoutManager = this@CustomFragment.layoutManager
        }

    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putSerializable("KEY_LAYOUT_MANAGER", currentLayoutManagerType)
        super.onSaveInstanceState(savedInstanceState)
    }

    private fun initObserver() {
        observeFetch()
        observeGetAllData()
    }

    private fun observeGetAllData() {
        observePeek(viewModel.getDataLiveData,{
           if(it.isNotEmpty()) {
               mAdapter.updateList(it)
           }
       })
    }

    private fun initAdapter() {
        mAdapter = CoinListAdapter(mImageLoader) {
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
                Toast.makeText(this.context, "observeFetch: Success", Toast.LENGTH_SHORT).show()
            }, onError = {
                viewModel.isLoading.set(false)
                Log.w("", "observeFetch: onError")
                Toast.makeText(this.context, "observeFetch: onError", Toast.LENGTH_SHORT).show()
            }, onHideLoading = {viewModel.isLoading.set(false)},
            onLoading = { viewModel.isLoading.set(true) }
        )
    }

/*
    private fun getSnackbar(msg: String): Snackbar {
        val snackbar = Snackbar.make(binding.layoutCoordinator, msg, LENGTH_INDEFINITE)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.snackbar_primary))
        snackbar.setAction("Refresh") {
            binding.swiperefresh.post {
                binding.swiperefresh.isRefreshing = true
                v2ViewModel.fetchData()
            }
        }
        snackbar.setActionTextColor(ContextCompat.getColor(requireActivity(), R.color.snackbar_text))
        snackbar.setTextColor(ContextCompat.getColor(requireActivity(),R.color.snackbar_text))
        return snackbar
    }
*/

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
