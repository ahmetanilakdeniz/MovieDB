

package com.example.moviedb.Data.ui.TopRatedMovies

import android.arch.lifecycle.ViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStore
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedb.Api.MovideBDInterface
import com.example.moviedb.Api.MovideDBClient
import com.example.moviedb.Api.Repository.Network
import com.example.moviedb.R
import kotlinx.android.synthetic.main.activity_main.*
import android.arch.lifecycle.ViewModelProvider as ViewModelProvider

class MainActivity : AppCompatActivity(), () -> ViewModelStore {

    private lateinit var viewModel: MainActivityViewModel

    lateinit var movieRepository: MoviePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService: MovideBDInterface = MovideDBClient.getClient()

        movieRepository = MoviePagedListRepository(apiService)
        viewModel = getViewModel()

        val movieAdapter = TopRatedMovieListAdapter(this)
        val gridLayoutManager = GridLayoutManager(this,3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){

            override fun getSpanSize(position: Int): Int {
                val viewType: Int = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIVE_VIEW_TYPE) return 1
                else return 3
            }
        }
        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = movieAdapter

        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_rated.visibility = if (viewModel.listIsEmpty() && it == Network.LOADİNG) View.VISIBLE else View.GONE
            txt_error_rated.visibility = if (viewModel.listIsEmpty() && it == Network.EROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()){
                movieAdapter.setnetworkStateItem(it)
            }
        })
    }


    private fun getViewModel(): MainActivityViewModel{

        return ViewModelProvider(this,object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(movieRepository) as T
            }
    })[MainActivityViewModel::class.java]
//
//             return ViewModelProvider(ViewModelStoreOwner(this,object : ViewModelProvider.Factory{
//            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//                @Suppress("UNCHECKED_CAST")
//                return MainActivityViewModel(movieRepository) as T
//             }
//         })[MainActivityViewModel::class.java]
//    }
}
}