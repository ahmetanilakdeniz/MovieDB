package com.example.moviedb.Api.Repository


import android.arch.lifecycle.MutableLiveData
import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.moviedb.Api.FISRT_PAGE
import com.example.moviedb.Api.MovideBDInterface
import com.example.moviedb.Api.Vo.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@Suppress("DEPRECATION")
class MovieDataSource(private val apiService: MovideBDInterface, private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int, Movie>() {


    private val page = FISRT_PAGE

    val networkState : MutableLiveData<Network> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>)
    {
        networkState.postValue(Network.LOADİNG)

        compositeDisposable.add(
            apiService.getTopRatedMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe({
                           callback.onResult(it.movieList, null, page+1)
                           networkState.postValue(Network.LOADED)
                },
                {
                    networkState.postValue(Network.EROR)
                    Log.e("MovieDataSource", it.message!!)
                })
        )
    }
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(Network.LOADİNG)

        compositeDisposable.add(
            apiService.getTopRatedMovie(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe({
                           if (it.totalPages >= params.key){
                               callback.onResult(it.movieList,params.key+1)
                               networkState.postValue(Network.LOADED)
                           }
                           else{
                               networkState.postValue(Network.ENDOFLİST)
                           }
                },
                    {
                        networkState.postValue(Network.EROR)
                        Log.e("MovieDataSource", it.message!!)
                    })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }
}