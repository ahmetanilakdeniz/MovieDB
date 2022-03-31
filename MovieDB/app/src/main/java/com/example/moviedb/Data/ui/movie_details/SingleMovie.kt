package com.example.moviedb.Data.ui.movie_details

import com.example.moviedb.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.moviedb.Api.MovideBDInterface
import com.example.moviedb.Api.MovideDBClient
import com.example.moviedb.Api.POSTER_BASE_URL
import com.example.moviedb.Api.Repository.Network
import com.example.moviedb.Api.Vo.MovieDetails
import kotlinx.android.synthetic.main.activity_single_movie.*


class SingleMovie : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val movieId: Int = intent.getIntExtra("id",1)

        val apiService : MovideBDInterface =  MovideDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movie.observe(this, Observer {
                bindUI(it)

        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == Network.LOADİNG) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == Network.EROR) View.VISIBLE else View.GONE
        })
    }

    private fun bindUI(it: MovieDetails){

        movie_title.text = it.originalTitle
        movie_rating.text = it.rating.toString()
        movie_overview.text = it.overview
        movie_popularity.text = it.popularity.toString()

        val moviePosterUrl : String = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterUrl)
            .into(iv_movie_poster)
    }

    private fun getViewModel(movieId: Int): SingleMovieViewModel{
        return ViewModelProvider(this,object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRepository,movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
    }
//
//        return ViewModelProvider(ViewModelStoreOwner(this,object : ViewModelProvider.Factory{
//            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//                    @Suppress("UNCHECKED_CAST")
//                    return SingleMovieViewModel(movieRepository,movieId) as T
//            }
//
//    })
//                [SingleMovieViewModel::class.java]
//    }


