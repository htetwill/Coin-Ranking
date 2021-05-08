package com.example.android.recyclerview

import android.os.Bundle
import com.example.android.common.activities.SampleActivityBase

class MainActivity : SampleActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBar(findViewById(R.id.toolbar))

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().run {
                replace(R.id.sample_content_fragment, RecyclerViewFragment())
                commit()
            }
        }
    }

    companion object {
        val TAG = "MainActivity"
    }
}
