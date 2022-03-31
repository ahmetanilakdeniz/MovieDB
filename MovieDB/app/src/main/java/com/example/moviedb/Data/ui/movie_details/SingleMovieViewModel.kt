package com.example.moviedb.Data.ui.movie_details


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.Api.Repository.Network
import com.example.moviedb.Api.Vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(private val movieRepository: MovieDetailsRepository, movieId: Int): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movie : LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }

    val networkState : LiveData<Network> by lazy {
        movieRepository.getDataSource()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}




