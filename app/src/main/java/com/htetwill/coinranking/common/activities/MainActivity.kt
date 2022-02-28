package com.htetwill.coinranking.common.activities

import android.os.Bundle
import com.htetwill.coinranking.fragment.R

class MainActivity : CustomActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
