package com.example.android.recyclerview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.android.data.util.viewModel
import com.example.android.di.annotation.Injectable
import com.example.android.recyclerview.databinding.RecyclerViewFragBinding
import com.example.android.recyclerview.viewmodel.V2RecyclerViewFragmentViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("SpellCheckingInspection")

class RecyclerViewFragment : Fragment() ,Injectable{

    @Inject lateinit var mViewModelFactory: ViewModelProvider.Factory
    private val mAdapter = CustomAdapter()
    private lateinit var v2ViewModel: V2RecyclerViewFragmentViewModel

    private val viewModel by lazy {
        val db = Room.databaseBuilder(
                requireActivity(),
                CarDatabase::class.java, "db"
        ).build()

        ViewModelProvider(this, RecyclerViewFragmentViewModel.Factory(
                ArticleRepository.getInstance(db.articleDao())
        )).get(RecyclerViewFragmentViewModel::class.java)
    }
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

        binding.swiperefresh.setOnRefreshListener(viewModel::fetchPost)
        binding.recyclerView.adapter = mAdapter
        viewModel.pagedList.observe(viewLifecycleOwner, Observer { articles ->
            mAdapter.submitList(articles)
        })
        setRecyclerViewLayoutManager()
        initDataset()
        v2ViewModel = viewModel(mViewModelFactory)
        initObserver()



        return rootView
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeFetch()
    }

    private fun initObserver() {
        v2ViewModel.fetchData()
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

    private fun initDataset() {
        viewModel.getPost().observe(viewLifecycleOwner, Observer { result ->
            binding.swiperefresh.isRefreshing = false
            when (result) {
                is ResultOf.Success -> {
                    if (result.value.content!!.isNotEmpty()) {
                        viewModel.refreshUI(result.value.content)
                    } else {
                        CoroutineScope(Dispatchers.IO).launch {
                            Room.databaseBuilder(
                                    requireActivity(),
                                    CarDatabase::class.java, "db"
                            ).build().clearAllTables()
                        }
                    }
                }
                is ResultOf.Failure -> {
                    result.message?.let { msg -> getSnackbar(msg).show() }
                }
            }
        })
    }

    private fun getSnackbar(msg: String): Snackbar {
        val snackbar = Snackbar.make(binding.layoutCoordinator, msg, LENGTH_INDEFINITE)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.snackbar_primary))
        snackbar.setAction("Refresh") {
            binding.swiperefresh.post {
                binding.swiperefresh.isRefreshing = true
                viewModel.fetchPost()
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
