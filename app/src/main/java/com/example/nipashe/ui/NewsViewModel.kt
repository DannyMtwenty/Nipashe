package com.example.nipashe.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nipashe.data.NewsResponse
import com.example.nipashe.repository.NewsRepository
import com.example.nipashe.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor (private val newsRepository: NewsRepository) : ViewModel() {

     val breakingNews : MutableLiveData<Result<NewsResponse>> = MutableLiveData()
    val pageNumber=1

    val searchNews : MutableLiveData<Result<NewsResponse>> = MutableLiveData()
    val searchNewsPageNumber=1

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode : String)=viewModelScope.launch {
        breakingNews.postValue(Result.Loading())
        val response=newsRepository.getBreakingNews(countryCode,pageNumber)

        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun searchNews(searchQuery : String)=viewModelScope.launch {
        searchNews.postValue(Result.Loading())
        val response=newsRepository.searchNews(searchQuery,searchNewsPageNumber)

        searchNews.postValue(handleSearchNewsResponse(response))
    }

    fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Result<NewsResponse>{
        if (response.isSuccessful){
            response.body() ?.let { newsResponse ->  
                return Result.Success(newsResponse)
            }
        }
        return Result.Error(response.message())

    }


    fun handleSearchNewsResponse(response: Response<NewsResponse>) : Result<NewsResponse>{
        if (response.isSuccessful){
            response.body() ?.let { newsResponse ->
                return Result.Success(newsResponse)
            }
        }
        return Result.Error(response.message())

    }

}