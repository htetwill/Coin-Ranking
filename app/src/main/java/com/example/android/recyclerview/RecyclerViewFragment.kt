/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.android.recyclerview.databinding.RecyclerViewFragBinding
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

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
    private lateinit var dataset: Array<String>
    private var _binding: RecyclerViewFragBinding? = null
    private val binding get() = _binding!!

    enum class LayoutManagerType {  LINEAR_LAYOUT_MANAGER }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = RecyclerViewFragBinding.inflate(inflater, container, false)
        val rootView = binding.root
        layoutManager = LinearLayoutManager(activity)
        currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER

        if (savedInstanceState != null) {
            currentLayoutManagerType = savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER) as LayoutManagerType
        }

        binding.swiperefresh.setOnRefreshListener {
            viewModel.fetchPost()
        }
        binding.recyclerView.adapter = mAdapter
        viewModel.pagedList.observe(viewLifecycleOwner, Observer { articles -> mAdapter.submitList(articles) })
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
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, currentLayoutManagerType)
        super.onSaveInstanceState(savedInstanceState)
    }

    private fun initDataset() {
        viewModel.resultOfPost.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ResultOf.Success -> {
                    if (result.value.content!!.isNotEmpty()) {
                        binding.swiperefresh.isRefreshing = false
                        viewModel.refreshUI(result.value.content)
                        Toast.makeText(requireActivity(), "SUCCESS", Toast.LENGTH_SHORT).show()
                    } else {
                        binding.swiperefresh.isRefreshing = false
                        CoroutineScope(Dispatchers.IO).launch {
                            Room.databaseBuilder(
                                    requireActivity(),
                                    CarDatabase::class.java, "db"
                            ).build().clearAllTables()
                        }
                    }
                }
                // here as well
                is ResultOf.Failure -> {
                    getSnackbar("ERROR ${result.message}").show()
                    binding.swiperefresh.isRefreshing = false
                }
            }
        })
    }

    private fun getSnackbar(msg : String) : Snackbar {
        val snackbar = Snackbar.make(binding.layoutCoordinator, msg, LENGTH_SHORT)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.snackbar_primary))
        snackbar.setActionTextColor(ContextCompat.getColor(requireActivity(),R.color.snackbar_text))
        return snackbar
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private val TAG = "RecyclerViewFragment"
        private val KEY_LAYOUT_MANAGER = "layoutManager"
        private val SPAN_COUNT = 2
        private val DATASET_COUNT = 60
    }
}
