package com.example.moviedb.Api.Repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.moviedb.Api.MovideBDInterface
import com.example.moviedb.Api.Vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(private val apiService: MovideBDInterface, private val compositeDisposable: CompositeDisposable):
    DataSource.Factory<Int, Movie>() {

    val  moviesLiveDataSource = MutableLiveData<MovieDataSource>()


    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService,compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)

        return movieDataSource
    }
}