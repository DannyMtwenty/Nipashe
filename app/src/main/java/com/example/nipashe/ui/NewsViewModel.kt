package com.example.nipashe.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.nipashe.api.NewsApi
import com.example.nipashe.data.Article
import com.example.nipashe.data.NewsResponse
import com.example.nipashe.pagination.ArticlePagination
import com.example.nipashe.pagination.BreakingNewsPagination
import com.example.nipashe.repository.NewsRepository
import com.example.nipashe.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor (private val newsRepository: NewsRepository,private val newsApi: NewsApi) : ViewModel() {

   //  val breakingNews : MutableLiveData<Result<NewsResponse>> = MutableLiveData()
   private val breakingNews = MutableLiveData<String>(default_country)
   // val pageNumber=1

   // val searchNews : MutableLiveData<Result<NewsResponse>> = MutableLiveData()
   private val searchNews = MutableLiveData<String>()
    //val searchNewsPageNumber=1

    val Articlelist=searchNews.switchMap { searchNews ->    //switchmap used when the search query changes

        Pager(PagingConfig(pageSize = 10)){    //api returns 10 items per page
            ArticlePagination(searchNews,newsApi)
        }.liveData.cachedIn(viewModelScope)
    }


    val breakingNewslist=breakingNews.switchMap { news ->

        Pager(PagingConfig(pageSize = 10)){    //api returns 10 items per page
            BreakingNewsPagination(news,newsApi)
        }.liveData.cachedIn(viewModelScope)
    }

   companion object{
       private const val default_country="us"
   }
    /*
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

      */

        //post to search query live data
      fun searchNews(searchQuery : String){
         searchNews.postValue(searchQuery)
      }


    fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Result<NewsResponse>{
        if (response.isSuccessful){
            response.body() ?.let { newsResponse ->  
                return Result.Success(newsResponse)
            }
        }
        return Result.Error(response.message())

    }

/*
    fun handleSearchNewsResponse(response: Response<NewsResponse>) : Result<NewsResponse>{
        if (response.isSuccessful){
            response.body() ?.let { newsResponse ->
                return Result.Success(newsResponse)
            }
        }
        return Result.Error(response.message())

    }

 */

    fun upsert(article: Article)=viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun delete(article: Article)=viewModelScope.launch {
        newsRepository.delete(article)
    }

    fun getSavedNews()=newsRepository.getSavedNews()

}