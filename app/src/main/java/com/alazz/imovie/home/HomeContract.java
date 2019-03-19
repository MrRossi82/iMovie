package com.alazz.imovie.home;

import com.alazz.imovie.home.base.BasePresenter;
import com.alazz.imovie.home.base.BaseView;
import com.alazz.imovie.network.models.Movies;

public interface HomeContract {

    interface View extends BaseView<Presenter> {

        void setTopRatedMoviesList(Movies moviesList);

        void setMoreTopRatedMoviesList(Movies moreTopRatedMoviesList);

        void setPresenter(HomeContract.Presenter presenter);

        void showProgressIndicator(boolean show);

        void setEmptyView();


    }


    interface Presenter extends BasePresenter {


        void getMoreTopRatedMovies(int page);


    }
}
