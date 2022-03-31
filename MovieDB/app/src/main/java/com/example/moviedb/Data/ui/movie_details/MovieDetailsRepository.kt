package com.example.moviedb.Data.ui.movie_details

import androidx.lifecycle.LiveData
import com.example.moviedb.Api.MovideBDInterface
import com.example.moviedb.Api.Repository.DataSource
import com.example.moviedb.Api.Repository.Network
import com.example.moviedb.Api.Vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository (private val apiService : MovideBDInterface) {

    lateinit var dataSource:DataSource

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable, movieId: Int): LiveData<MovieDetails>{

        dataSource = DataSource(apiService,compositeDisposable)
        dataSource.fetchMovieDetails(movieId)

        return dataSource.downloadMovieDetailsResponse
    }

    fun getDataSource(): LiveData<Network>{

        return dataSource.networkState
    }

}