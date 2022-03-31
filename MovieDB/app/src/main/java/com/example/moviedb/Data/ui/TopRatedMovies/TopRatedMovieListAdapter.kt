@file:Suppress("DEPRECATION")
package com.example.moviedb.Data.ui.TopRatedMovies

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedb.Api.POSTER_BASE_URL
import com.example.moviedb.Api.Repository.Network
import com.example.moviedb.Api.Vo.Movie
import com.example.moviedb.R
import com.example.moviedb.Data.ui.movie_details.SingleMovie
import kotlinx.android.synthetic.main.movie_list_item.view.*
import kotlinx.android.synthetic.main.network_state_item.view.*

class TopRatedMovieListAdapter(private val context: Context): PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    val MOVIVE_VIEW_TYPE = 1
    val NETWORK_VİEW_TYPE = 2

    private var networkState : Network? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == MOVIVE_VIEW_TYPE){
            view = layoutInflater.inflate(R.layout.movie_list_item,parent,false)
            return MovieItemViewHolder(view)
        }else{
            view = layoutInflater.inflate(R.layout.network_state_item,parent,false)
            return networkStateViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIVE_VIEW_TYPE){
            (holder as MovieItemViewHolder).bind(getItem(position),context)
        }else {
            (holder as networkStateViewHolder).bind(networkState)
        }
    }

    private fun hasExtraRow(): Boolean{
        return networkState != null && networkState != Network.LOADED
    }
    override fun getItemCount(): Int{
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if(hasExtraRow() && position == itemCount - 1){
            NETWORK_VİEW_TYPE
        }else{
            MOVIVE_VIEW_TYPE
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
    class MovieItemViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(movie: Movie?, context: Context){

                itemView.cv_movie_title.text = movie?.originalTitle

            val moviePosterUrl : String = POSTER_BASE_URL + movie?.posterPath
            Glide.with(itemView.context)
                .load(moviePosterUrl)
                .into(itemView.cv_iv_movie_poster)

            itemView.setOnClickListener {
                val intent = Intent(context, SingleMovie::class.java)
                intent.putExtra("id",movie?.id)
                context.startActivity(intent)
            }
        }
    }

    class networkStateViewHolder(view: View): RecyclerView.ViewHolder(view){

        fun bind(networkState: Network?){
            if (networkState != null && networkState == Network.LOADİNG) {

                itemView.progress_bar_item.visibility = View.VISIBLE
            }
            else{
                itemView.progress_bar_item.visibility = View.GONE
            }
            if (networkState != null && networkState == Network.EROR){
                itemView.error_msg_item.visibility = View.VISIBLE
                itemView.error_msg_item.text = networkState.msg
            }
            else if(networkState != null && networkState == Network.ENDOFLİST){
                itemView.error_msg_item.visibility = View.VISIBLE
                itemView.error_msg_item.text = networkState.msg
            }
            else{
                itemView.error_msg_item.visibility = View.GONE
            }
        }
    }

    fun setnetworkStateItem(newnetworkState: Network){
        val previousState : Network? = this.networkState
        val hadExtraRow : Boolean = hasExtraRow()
        this.networkState = newnetworkState
        val hasExtraRow: Boolean = hasExtraRow()

        if (hadExtraRow != hasExtraRow){
            if (hadExtraRow){
                notifyItemRemoved(super.getItemCount())
            }else{
                notifyItemInserted(super.getItemCount())
            }
        }else if (hasExtraRow && previousState != newnetworkState){
            notifyItemChanged(itemCount-1)
        }
    }
}