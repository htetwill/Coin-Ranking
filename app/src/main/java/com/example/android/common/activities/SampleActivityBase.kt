package com.example.android.common.activities

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

/**
 * Base launcher activity.
 */
open class SampleActivityBase : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }
}
