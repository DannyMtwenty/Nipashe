package com.example.nipashe.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nipashe.data.Article

@Database(
    entities = [Article::class,],
    version = 1)

@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase(){
    abstract fun getArticleDao() : ArticleDao

    companion object{
        @Volatile    //only one thread writing to an instance
        private var instance : ArticleDatabase ? =null

        //create a lock obj
        private val Lock=Any()
        //fn to call when an instance of ShopperDatabase is created
        //synchronized ->only one thread sets the instance
        operator fun invoke (context: Context) = instance?: synchronized(Lock){
            instance?: createItemDatabase(context).also { instance=it }  //assign the instance to the method results
        }

        fun createItemDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
           ArticleDatabase::class.java,
            "ArticleDb").build()
    }
}