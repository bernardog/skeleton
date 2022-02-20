package com.bernardoghazi.skeleton.mostpopulararticles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bernardoghazi.skeleton.common.ImageLoader
import com.bernardoghazi.skeleton.databinding.MostPopularArticlesFragmentBinding
import com.bernardoghazi.skeleton.domain.models.Article
import com.bernardoghazi.skeleton.domain.models.UseCaseOutcome
import com.bernardoghazi.viewmodels.ArticlesFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MostPopularArticlesFragment : Fragment() {
    private var _binding: MostPopularArticlesFragmentBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var articlesAdapter: MostPopularArticlesAdapter
    private val viewModel: ArticlesFragmentViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MostPopularArticlesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articlesAdapter = MostPopularArticlesAdapter(
            externalScope = viewLifecycleOwner.lifecycleScope,
            onItemClick = ::onItemClick,
            imageLoader = ImageLoader(requireContext())
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = articlesAdapter
        observeArticles()
        fetchArticles()
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

    private fun fetchArticles() = viewModel.fetchMostPopularArticles()
    private fun observeArticles() = viewModel.content.observe(viewLifecycleOwner, {
        when (it) {
            is UseCaseOutcome.Success -> {
                articlesAdapter.setContent(it.data)
            }
            is UseCaseOutcome.Error -> {
                //TODO: present error feedback to user.
                it.errorStringResId?.let { errorId -> Log.d("temp", resources.getString(errorId)) }
            }
        }
    })

}