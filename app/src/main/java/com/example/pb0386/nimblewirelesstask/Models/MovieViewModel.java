package com.example.pb0386.nimblewirelesstask.Models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

import com.example.pb0386.nimblewirelesstask.Database.MovieDataSource;
import com.example.pb0386.nimblewirelesstask.Database.MovieDataSourceFactory;

public class MovieViewModel extends ViewModel {

    public LiveData<PagedList<Movie>> itemPagedList;
    public LiveData<PageKeyedDataSource<Integer, Movie>> liveDataSource;

    public MovieViewModel() {

        MovieDataSourceFactory itemDataSourceFactory = new MovieDataSourceFactory();
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(MovieDataSource.PAGE_SIZE)
                        .build();

        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, config)).build();

    }
}
