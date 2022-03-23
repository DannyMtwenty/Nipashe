package com.example.nipashe.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.nipashe.data.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //replace if exists
    suspend fun upinsert(article: Article): Long   //returns article id

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("SELECT * FROM article")
    fun getAllArticles () : LiveData<List<Article>>
}