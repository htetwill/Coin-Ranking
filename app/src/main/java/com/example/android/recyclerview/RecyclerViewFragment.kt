package com.example.android.recyclerview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.data.modal.CoinModel
import com.example.android.data.util.viewModel
import com.example.android.di.annotation.Injectable
import com.example.android.recyclerview.databinding.RecyclerViewFragBinding
import com.example.android.recyclerview.viewmodel.V2RecyclerViewFragmentViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

@Suppress("SpellCheckingInspection")

class RecyclerViewFragment : Fragment() ,Injectable{

    private lateinit var mAdapter: CoinListAdapter
    @Inject lateinit var mViewModelFactory: ViewModelProvider.Factory
    private lateinit var v2ViewModel: V2RecyclerViewFragmentViewModel

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

        v2ViewModel = viewModel(mViewModelFactory)
        binding.swiperefresh.setOnRefreshListener(v2ViewModel::fetchData)
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
        v2ViewModel.fetchData()
        v2ViewModel.getAllData()
    }

    private fun setRecyclerViewLayoutManager() {
        layoutManager = LinearLayoutManager(activity)
        currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER
        with(binding.recyclerView) {
            layoutManager = this@RecyclerViewFragment.layoutManager
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
        observePeek(v2ViewModel.getDataLiveData,{
           if(it.isNotEmpty()) {
               mAdapter.updateList(it)
           }
       })
    }

    private fun initAdapter() {
        mAdapter = CoinListAdapter() {
            onItemClicked(it)
        }
    }

    private fun onItemClicked(event: ListItemEvent) {
        when(event) {
            is ListItemEvent.ItemClicked -> {
                Toast.makeText(this.context, "Clicked", Toast.LENGTH_SHORT).show()
                // TODO: 2022-02-22,3:11 htetwill WIP
/*
                navigationController().navigate(
                    OffersFragmentDirections.actionFragmentOffersToFragmentOfferPreview(
                        event.contentPreviewModel.id
                    )
                )
*/
            }
        }
    }

    private fun observeFetch() {
        observeSingle(v2ViewModel.fetchLiveData, {
            Log.w("", "observeFetch: Success")
            Toast.makeText(this.context, "Success", Toast.LENGTH_SHORT).show()


        }, onError = {
            Log.w("", "observeFetch: Error")
            Toast.makeText(this.context, "Error", Toast.LENGTH_SHORT).show()
        }, onHideLoading = {}, onLoading = {})
    }

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
