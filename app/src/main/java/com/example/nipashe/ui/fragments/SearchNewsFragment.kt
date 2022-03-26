package com.example.nipashe.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nipashe.R
import com.example.nipashe.adapters.NewsAdapter
import com.example.nipashe.ui.NewsViewModel
import com.example.nipashe.ui.NewsViewModelFactory
import com.example.nipashe.utils.Constants.searchNewsDelay
import com.example.nipashe.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchNewsFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var editText: EditText

    val TAG ="SearchNewsFragment "
    val newsAdapter= NewsAdapter()

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
        return inflater.inflate(R.layout.fragment_search_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=view.findViewById(R.id.rvSearchNews)
        progressBar=view.findViewById(R.id.paginationProgressBar)
        editText=view.findViewById(R.id.etSearch)

        //populate recyclerview
        setRecyclerView()

        //item listener
        newsAdapter.setOnClickListener {
            //create bundle
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_searchNewsFragment_to_articleFragment,bundle)
        }

        val viewModel = ViewModelProviders.of(this,viewModelFactory).get(NewsViewModel::class.java)

        var job  : Job ? =null

        //add listener to etSearch
        editText.addTextChangedListener { editable ->
            job?.cancel()   //cancel current job when input is given in etSearch

            //start new job with delay of 500milisecs
            job= MainScope().launch {
                delay(searchNewsDelay)
                editable?.let {

                    if(editable.toString().isNotEmpty()){
                        viewModel.searchNews(editable.toString())
                    }
                }
            }


        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Result.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }

                is Result.Error -> {
                    hideProgressBar()
                    response.message?.let {message->
                        Log.e(TAG,"error occured: $message")
                    }

                }

                is Result.Loading ->{
                    showProgressBar()
                }
            }

        })
    }

    private fun hideProgressBar(){
        progressBar.visibility=View.INVISIBLE
    }
    private fun showProgressBar(){
        progressBar.visibility=View.VISIBLE
    }

    //set recyvler view
    private fun setRecyclerView() {


        recyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }
    }