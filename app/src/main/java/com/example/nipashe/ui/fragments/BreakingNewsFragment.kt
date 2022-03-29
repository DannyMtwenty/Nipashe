package com.example.nipashe.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nipashe.R
import com.example.nipashe.adapters.BreakingNewsAdapter
import com.example.nipashe.adapters.NewsAdapter
import com.example.nipashe.ui.NewsViewModel
import com.example.nipashe.ui.NewsViewModelFactory
import com.example.nipashe.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BreakingNewsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar

    val TAG ="BreakingNewsFragment "
    val newsAdapter= BreakingNewsAdapter ()

    @Inject
    lateinit var viewModelFactory : NewsViewModelFactory



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breaking_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=view.findViewById(R.id.rvBreakingNews)
        progressBar=view.findViewById(R.id.paginationProgressBar)

        newsAdapter.setOnClickListener {
           //create bundle
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }

            findNavController().navigate(R.id.action_breakingNewsFragment_to_articleFragment,bundle)
        }


        //populate recyclerview
        setRecyclerView()

        val viewModel = ViewModelProviders.of(this,viewModelFactory).get(NewsViewModel::class.java)

        viewModel.breakingNewslist.observe(viewLifecycleOwner){ pagingdata ->

            newsAdapter.submitData(lifecycle,pagingdata)

        }
    }

    private fun hideProgressBar(){
        progressBar.visibility=View.INVISIBLE
    }
    private fun showProgressBar(){
        progressBar.visibility=View.VISIBLE
    }

    //set recyvler view
    private fun setRecyclerView(){

        recyclerView.apply {
            adapter=newsAdapter
            layoutManager=LinearLayoutManager(activity)
        }

    }

}