package com.bernardoghazi.skeleton.mostpopulararticles

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.bernardoghazi.skeleton.databinding.MostPopularArticlesActivityBinding

class MostPopularArticlesActivity : FragmentActivity() {

    private lateinit var binding: MostPopularArticlesActivityBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MostPopularArticlesActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
