package com.example.moviedb.Api.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviedb.Api.MovideBDInterface
import com.example.moviedb.Api.Vo.Movie
import com.example.moviedb.Api.Vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class DataSource (private val apiService: MovideBDInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState = MutableLiveData<Network>()
    val networkState: LiveData<Network>
        get() = _networkState

    private val _downloadMovieDetailsResponse = MutableLiveData<MovieDetails>()
    val downloadMovieDetailsResponse: LiveData<MovieDetails>
        get() = _downloadMovieDetailsResponse

    fun fetchMovieDetails(movieId: Int){

        _networkState.postValue(Network.LOADİNG)

        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe({

                        _downloadMovieDetailsResponse.postValue(it)
                        _networkState.postValue(Network.LOADED)
                    },
                    {

                        _networkState.postValue(Network.EROR)
                        Log.e("MovieDetailsDataSource", it.message!!)
                    })
            )
        }
        catch (e: Exception){
                                Log.e("MovieDetailsDataSource", e.message!!)


        }
    }
}