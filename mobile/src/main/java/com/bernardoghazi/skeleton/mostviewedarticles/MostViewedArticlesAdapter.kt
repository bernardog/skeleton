package com.bernardoghazi.skeleton.mostviewedarticles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bernardoghazi.skeleton.common.ImageLoader
import com.bernardoghazi.skeleton.databinding.MostViewedArticlesFragmentArticleItemBinding
import com.bernardoghazi.skeleton.databinding.MostViewedArticlesFragmentDividerItemBinding
import com.bernardoghazi.skeleton.databinding.MostViewedArticlesFragmentHeaderItemBinding
import com.bernardoghazi.skeleton.domain.models.Article
import com.bernardoghazi.skeleton.domain.models.Content
import com.bernardoghazi.skeleton.domain.models.Header
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.ParseException

class MostViewedArticlesAdapter(
    private val externalScope: CoroutineScope,
//    private val fetchSubscribersCount: suspend (String) -> UseCaseOutcome<Int>,
    private val onItemClick: suspend (Article) -> Unit,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var content: MutableList<Content> = mutableListOf()

    fun setContent(posts: List<Content>) {
        this.content.clear()
        this.content.addAll(posts)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = content.size

    override fun getItemViewType(position: Int): Int {
        return when (content[position]) {
            is Header -> {
                ViewType.HEADER.ordinal
            }
            is Article -> {
                ViewType.ARTICLE.ordinal
            }
            else -> {
                ViewType.DIVIDER.ordinal
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.HEADER.ordinal -> {
                HeaderViewHolder(
                    MostViewedArticlesFragmentHeaderItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ViewType.ARTICLE.ordinal -> {
                ArticleViewHolder(
                    MostViewedArticlesFragmentArticleItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                DividerViewHolder(
                    MostViewedArticlesFragmentDividerItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ViewType.HEADER.ordinal -> (holder as HeaderViewHolder).bind(content[position] as Header)
            ViewType.ARTICLE.ordinal -> (holder as ArticleViewHolder).bind(content[position] as Article)
            ViewType.DIVIDER.ordinal -> (holder as DividerViewHolder)
        }
    }


    internal enum class ViewType {
        HEADER,
        ARTICLE,
        DIVIDER
    }

    //TODO: keep top margin only when it's not the first header in the list.
    private inner class HeaderViewHolder(private val binding: MostViewedArticlesFragmentHeaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(header: Header) {
            try {
                binding.header.text = header.date
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
    }

    private inner class ArticleViewHolder(private val binding: MostViewedArticlesFragmentArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {

            with(binding) {
                root.setOnClickListener { externalScope.launch { onItemClick(article) } }
                title.text = article.title
                summary.text = article.abstract
                authorName.text = article.mediaUrl
                with(article.mediaUrl) { if (this.isNotEmpty()) imageLoader.loadImageByUrl(this, image) }
            }

//            externalScope.launch {
//                fetchSubscribersCount(Uri.parse(article.author.url).host.toString()).let { outcome ->
//                    binding.subscribersCount.text = if (outcome is UseCaseOutcome.Success) {
//                        outcome.data.toString()
//                    } else {
//                        binding.root.context.getString(R.string.fetching_subscribers_count_error)
//                    }//TODO: present error feedback to user
//                }
//            }
        }
    }

    private inner class DividerViewHolder(binding: MostViewedArticlesFragmentDividerItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
