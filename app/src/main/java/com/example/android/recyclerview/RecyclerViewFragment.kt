package com.example.android.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.android.recyclerview.databinding.RecyclerViewFragBinding
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.recycler_view_frag.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//TODO:
// 1 show UI with preview
// 2 retrieve database to display
// 3 loading indicator to check internet
// 4 overwrite with new data
// 5 display updated data with animation

class RecyclerViewFragment : Fragment() {

    private val mAdapter = CustomAdapter()
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
                              savedInstanceState: Bundle?): View? {
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
            if(articles.isEmpty())
                getSnackbar(getString(R.string.error_internal_server)).show()
        })
        setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER)
        initDataset()

        return rootView
    }

    private fun setRecyclerViewLayoutManager(layoutManagerType: LayoutManagerType) {
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
                swiperefresh.isRefreshing = true
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
