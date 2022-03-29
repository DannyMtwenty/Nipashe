package com.example.nipashe.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nipashe.api.NewsApi
import com.example.nipashe.repository.NewsRepository

class NewsViewModelFactory  constructor(private val repository: NewsRepository,private val newsApi: NewsApi): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
          NewsViewModel(this.repository,this.newsApi) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}