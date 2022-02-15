package com.bernardoghazi.skeleton.mostviewedarticles

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.bernardoghazi.skeleton.databinding.MostViewedArticlesActivityBinding

class MostViewedArticlesActivity : FragmentActivity() {

    private lateinit var binding: MostViewedArticlesActivityBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MostViewedArticlesActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
