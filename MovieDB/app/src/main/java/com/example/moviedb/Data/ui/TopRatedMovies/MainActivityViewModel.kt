

package com.example.moviedb.Data.ui.TopRatedMovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.moviedb.Api.Repository.Network
import com.example.moviedb.Api.Vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val movieRepository: MoviePagedListRepository): ViewModel() {

    private val compositeDisposable=CompositeDisposable()

    @Suppress("DEPRECATION")

    val moviePagedList : LiveData<PagedList<Movie>> by lazy {
                movieRepository.fetchLiveMoviePagedList(compositeDisposable)
            }

    val networkState : LiveData<Network> by lazy {
        movieRepository.getNetwork()
    }

    fun listIsEmpty(): Boolean{
        return moviePagedList.value?.isEmpty()?: true

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}