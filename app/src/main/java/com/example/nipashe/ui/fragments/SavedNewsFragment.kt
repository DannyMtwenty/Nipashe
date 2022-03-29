package com.example.nipashe.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchUIUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nipashe.R
import com.example.nipashe.adapters.NewsAdapter
import com.example.nipashe.adapters.SavedNewsAdapter
import com.example.nipashe.ui.NewsViewModel
import com.example.nipashe.ui.NewsViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SavedNewsFragment : Fragment() {
 lateinit var recyclerView : RecyclerView
 val newsAdapter= SavedNewsAdapter()

 @Inject
 lateinit var newsViewModelFactory: NewsViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=view.findViewById(R.id.rvSavedNews)
       // progressBar=view.findViewById(R.id.paginationProgressBar)



        //news item click
        newsAdapter.setOnClickListener {
            //create bundle
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_savedNewsFragment_to_articleFragment,bundle)
        }


        //populate recyclerview
        setRecyclerView()

        val viewModel = ViewModelProviders.of(this,newsViewModelFactory).get(NewsViewModel::class.java)


        //delete on swipe
        var itemTouchHelperCallback=object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,

            ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.absoluteAdapterPosition // position of the swapped item
                var article=newsAdapter.differ.currentList[position]
                viewModel.delete(article)
                Snackbar.make(view,"item deleted!",Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.upsert(article)
                    }
                    show()
                }

            }
        }

      //real item touchHelper
      ItemTouchHelper(itemTouchHelperCallback).apply {
          attachToRecyclerView(recyclerView)
      }

        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer { articles ->

            newsAdapter.differ.submitList(articles)
        })
    }


    //set recyvler view
    private fun setRecyclerView() {


        recyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

}