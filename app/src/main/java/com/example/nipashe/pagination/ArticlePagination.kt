package com.example.nipashe.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nipashe.api.NewsApi
import com.example.nipashe.data.Article
import com.example.nipashe.utils.Constants

class ArticlePagination (val s :  String,val newsApi: NewsApi) :
PagingSource<Int, Article>() {   //PagingSource -> where to retrieve the items

    //for subsequent refresh calls to PagingSource.load()
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {

        //if anchorposition is not null
        return state.anchorPosition?.let {
            var anchorpage=state.closestPageToPosition(it)  //get closest pg position
            anchorpage?.prevKey?.plus(1)?:anchorpage?.nextKey?.minus(1)

        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {

        val page=params.key?:1  //return pg1 if null

        //get article data
        return try {

            val data =newsApi.searchForNews(s, page, Constants.NewsApiKey)
            Log.d("TAG", "load: ${data.body()}")
            LoadResult.Page(
                data = data.body()?.articles!!,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.body()?.articles?.isEmpty()!!) null else page + 1
            )


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
