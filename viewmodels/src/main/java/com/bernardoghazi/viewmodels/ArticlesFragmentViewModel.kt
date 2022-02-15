package com.bernardoghazi.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bernardoghazi.skeleton.domain.models.Content
import com.bernardoghazi.skeleton.domain.models.UseCaseOutcome
import com.bernardoghazi.skeleton.domain.usecases.FetchMostViewedArticlesUseCase
import kotlinx.coroutines.launch

//TODO: save/restore state (SavedStateHandle).
class ArticlesFragmentViewModel(
    private val fetchMostViewedArticlesUseCase: FetchMostViewedArticlesUseCase,
) : ViewModel() {

    private val _content = MutableLiveData<UseCaseOutcome<List<Content>>>()
    val content: LiveData<UseCaseOutcome<List<Content>>> = _content

    fun fetchMostViewedArticles() = viewModelScope.launch {
        _content.postValue(fetchMostViewedArticlesUseCase())
    }
}