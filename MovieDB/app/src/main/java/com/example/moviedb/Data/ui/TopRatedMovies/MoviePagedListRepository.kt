@file:Suppress("DEPRECATION")

package com.example.moviedb.Data.ui.TopRatedMovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviedb.Api.MovideBDInterface
import com.example.moviedb.Api.POST_PER_PAGE
import com.example.moviedb.Api.Repository.MovieDataSource
import com.example.moviedb.Api.Repository.MovieDataSourceFactory
import com.example.moviedb.Api.Repository.Network
import com.example.moviedb.Api.Vo.Movie
import io.reactivex.disposables.CompositeDisposable



class MoviePagedListRepository (private val apiService: MovideBDInterface){

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory


    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>>{

        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory,config).build()

        return moviePagedList

    }

    fun getNetwork() : LiveData<Network>{

        return Transformations.switchMap<MovieDataSource, Network>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState
        )
    }
}