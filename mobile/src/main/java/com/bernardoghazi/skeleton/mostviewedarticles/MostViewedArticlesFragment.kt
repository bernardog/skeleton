package com.bernardoghazi.skeleton.mostviewedarticles

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bernardoghazi.skeleton.common.ImageLoader
import com.bernardoghazi.skeleton.databinding.MostViewedArticlesFragmentBinding
import com.bernardoghazi.skeleton.domain.models.Article
import com.bernardoghazi.skeleton.domain.models.UseCaseOutcome
import com.bernardoghazi.viewmodels.ArticlesFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MostViewedArticlesFragment : Fragment() {
    private var _binding: MostViewedArticlesFragmentBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var postsAdapter: MostViewedArticlesAdapter
    private val viewModel: ArticlesFragmentViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MostViewedArticlesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postsAdapter = MostViewedArticlesAdapter(
            externalScope = viewLifecycleOwner.lifecycleScope,
//            fetchSubscribersCount = ::fetchSubscribersCount,
            onItemClick = ::onItemClick,
            imageLoader = ImageLoader(requireContext())
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = postsAdapter
        observePosts()
    }

    override fun onResume() {
        super.onResume()
        fetchPosts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClick(article: Article) {
        val browseIntent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = article.url.toUri()
        }
        requireActivity().startActivity(browseIntent)
    }

    private fun fetchPosts() = viewModel.fetchMostViewedArticles()
    private fun observePosts() = viewModel.content.observe(viewLifecycleOwner, {
        if (it is UseCaseOutcome.Success) {
            postsAdapter.setContent(it.data)
        } else {
            //TODO: present error feedback to user.
        }
    })

}