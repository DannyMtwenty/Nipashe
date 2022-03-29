package com.example.nipashe.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nipashe.R
import com.example.nipashe.data.Article

class BreakingNewsAdapter :  PagingDataAdapter<Article, BreakingNewsAdapter.articleViewHolder>(differCallback) {

    //viewholder class
    inner class articleViewHolder(itemview : View)  : RecyclerView.ViewHolder(itemview){
        val tvsource =itemview.findViewById<TextView>(R.id.tvSource)
        var tvTitle =itemview.findViewById<TextView>(R.id.tvTitle)
        var tvDesc=itemview.findViewById<TextView>(R.id.tvDescription)
        var tvPublishedAt=itemview.findViewById<TextView>(R.id.tvPublishedAt)
        var articleImage =itemview.findViewById<ImageView>(R.id.ivArticleImage)
    }


    //diff util
    companion object{

        val differCallback=object : DiffUtil.ItemCallback<Article>(){
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

    //val differ=AsyncListDiffer(this,differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): articleViewHolder {
        //layout to inflate to the recycler view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article_preview,parent,false)
        return articleViewHolder(view)
    }

    override fun onBindViewHolder(holder: articleViewHolder, position: Int) {
        //set values or listeners for the views
        var article=getItem(position)

        holder.itemView.apply {
            Glide.with(this).load(article?.urlToImage).into(holder.articleImage)
            holder.tvsource.text=article?.source?.name
            holder.tvTitle.text=article?.title
            holder.tvDesc.text=article?.description
            holder.tvPublishedAt.text=article?.publishedAt
            //listener
            setOnClickListener{
                onItemClickListener ?.let {
                    it(article!!)
                }

            }
        }





    }

//    override fun getItemCount(): Int {
//        return differ.currentList.size
//    }


    //onclick listener
    private var onItemClickListener : ((Article) -> Unit) ? =null

    fun setOnClickListener(listener : (Article) -> Unit){
        onItemClickListener=listener
    }
}