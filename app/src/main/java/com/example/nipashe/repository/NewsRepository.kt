package com.example.nipashe.repository

import com.example.nipashe.api.NewsApi
import com.example.nipashe.data.Article
import com.example.nipashe.db.ArticleDao
import com.example.nipashe.db.ArticleDatabase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class NewsRepository @Inject constructor(private val articleDao: ArticleDao, private val newsApi: NewsApi) {

     suspend fun getBreakingNews(countryCode : String, pageNumber : Int)=newsApi.getBreakingNews(countryCode,pageNumber)

     suspend fun searchNews(searchQuery : String, pageNumber : Int)=newsApi.searchForNews(searchQuery,pageNumber)

     suspend fun upsert(article: Article) =articleDao.upinsert(article)

     suspend fun delete(article: Article)=articleDao.deleteArticle(article)

     fun getSavedNews()=articleDao.getAllArticles()
}