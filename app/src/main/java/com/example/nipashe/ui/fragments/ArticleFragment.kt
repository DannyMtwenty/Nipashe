package com.example.nipashe.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.example.nipashe.NewsActivity
import com.example.nipashe.R
import com.example.nipashe.ui.NewsViewModel
import com.example.nipashe.ui.NewsViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArticleFragment : Fragment() {
    val args : ArticleFragmentArgs by navArgs()
    lateinit var webView: WebView
    lateinit var progressBar : ProgressBar
    lateinit var floatingActionButton: FloatingActionButton

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
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView=view.findViewById(R.id.webView)
        progressBar=view.findViewById(R.id.progressBar)
        floatingActionButton=view.findViewById(R.id.fab)


        val article=args.article

        webView.apply {
            webViewClient= WebViewClient()  //to load within the app
            article.url?.let { loadUrl(it) }
        }

        val viewModel = ViewModelProviders.of(this,viewModelFactory).get(NewsViewModel::class.java)

        //listener on fab btn
        floatingActionButton.setOnClickListener{
            viewModel.upsert(article)
            Snackbar.make(view,"article saved successfully",Snackbar.LENGTH_SHORT).show()
        }


    }

    // Overriding WebViewClient functions
    inner class WebViewClient : android.webkit.WebViewClient() {



        // Load the URL
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return false
        }

        // ProgressBar will disappear once page is loaded
        override fun onPageFinished(view: WebView, url: String) {

            super.onPageFinished(view, url)
            progressBar.visibility = View.GONE
        }

    }


}
