package com.example.pb0386.nimblewirelesstask.Database;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.example.pb0386.nimblewirelesstask.Models.Movie;
import com.example.pb0386.nimblewirelesstask.Models.MoviesResponse;
import com.example.pb0386.nimblewirelesstask.rest.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Integer, Movie> {

    private static final int FIRST_PAGE = 1;
    public static final int PAGE_SIZE = 3;
    private final static String API_KEY = "8d757b4dfd0776e8ba9a3c3b473f5d51";

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Movie> callback) {

        ApiClient.getInsance()
                .getApi()
                .getTopRatedMovies(API_KEY,FIRST_PAGE)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

                        if(response.body() != null){

                            callback.onResult(response.body().getResults(), null, FIRST_PAGE + 1);

                        }
                    }
                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movie> callback) {

        ApiClient.getInsance()
                .getApi()
                .getTopRatedMovies(API_KEY,FIRST_PAGE)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

                        if(response.body() != null){
                            Integer key = (params.key > 1) ? params.key - 1 : null;
                            callback.onResult(response.body().getResults(), key);
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {

                    }
                });

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movie> callback) {

        ApiClient.getInsance()
                .getApi()
                .getTopRatedMovies(API_KEY,FIRST_PAGE)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

                        if(response.body() != null){
                            Integer key = response.body().getTotalPages() > FIRST_PAGE ? params.key + 1 : null;
                            callback.onResult(response.body().getResults(), key);
                        }

                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {

                    }
                });


    }
}
