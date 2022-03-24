package com.example.nipashe.hilt

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.nipashe.api.NewsApi
import com.example.nipashe.db.ArticleDao
import com.example.nipashe.db.ArticleDatabase
import com.example.nipashe.repository.NewsRepository
import com.example.nipashe.ui.NewsViewModelFactory
import com.example.nipashe.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)  //creates obj once when the app is started
@Module
object HiltModules {
    //things to inject
    @Singleton
    @Provides
    fun getApiInterface(): NewsApi {
        return return Retrofit.Builder()
            .baseUrl(Constants.base_url)
            .addConverterFactory(GsonConverterFactory.create()) // we need to add converter factory to  convert JSON object to Java object
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    fun providesNewsRepository(articleDao: ArticleDao, newsApi: NewsApi): NewsRepository {
        return NewsRepository(articleDao, newsApi)
    }


    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun providesArticledb( context: Application) : ArticleDatabase{
        return ArticleDatabase.createItemDatabase(context)
    }


    @Singleton
    @Provides
    fun providesArtcleDao(db: ArticleDatabase) : ArticleDao{
        return db.getArticleDao() // The reason we can implement a Dao for the database
    }


    @Provides
    fun providesViewModelFactory(repository: NewsRepository) : NewsViewModelFactory{
        return  NewsViewModelFactory(repository)

    }

}