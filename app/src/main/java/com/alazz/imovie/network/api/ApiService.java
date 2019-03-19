package com.alazz.imovie.network.api;

import com.alazz.imovie.network.models.Movies;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie/top_rated")
    Observable<Movies> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int pageIndex
    );




}
