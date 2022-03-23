package com.example.nipashe.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")  //to save Article to db
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id : Int ?=null,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)