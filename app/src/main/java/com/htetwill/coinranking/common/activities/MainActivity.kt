package com.htetwill.coinranking.common.activities

import android.os.Bundle
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.htetwill.coinranking.error.CustomError
import com.htetwill.coinranking.error.ExceptionError
import com.htetwill.coinranking.error.NoInternetConnectionError
import com.htetwill.coinranking.fragment.R
import com.htetwill.coinranking.fragment.databinding.ActivityMainBinding

class MainActivity : CustomActivityBase() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showSnackbar("error.exception.localizedMessage")
    }

    fun showError(error: CustomError) {
        when (error) {
            is NoInternetConnectionError -> {
                showSnackbar(getString(R.string.error_please_check_internet_connection))
            }

            is ExceptionError -> {
                showSnackbar(error.exception.toString())
            }

            is UnknownError -> {
                showSnackbar(getString(R.string.error_unknown))
            }
        }
    }

    private fun showSnackbar(msg: String){
        val snackbar = Snackbar.make(binding.mainLayout, msg,
            BaseTransientBottomBar.LENGTH_SHORT
        )
/*
        snackbar.setAction("Refresh") {
            binding.swiperefresh.post {
                binding.swiperefresh.isRefreshing = true
                viewModel.fetchData()
            }
        }
*/
        snackbar.show()
    }


}
