package com.example.moviedb.Api

import com.example.moviedb.Api.Vo.MovieDetails
import com.example.moviedb.Api.Vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovideBDInterface {

    //https://api.themoviedb.org/3/movie/top_rated?api_key=11459cff1c1ce00e3202addab99f3a91&page=1
    //https://api.themoviedb.org/3/movie/19404?api_key=11459cff1c1ce00e3202addab99f3a91
    //https://api.themoviedb.org/3/

    @GET("movie/top_rated")
    fun getTopRatedMovie(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>
}