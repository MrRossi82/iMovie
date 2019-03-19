package com.alazz.imovie.home;


import com.alazz.imovie.network.api.ApiClient;
import com.alazz.imovie.network.api.ApiService;
import com.alazz.imovie.network.models.Movies;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.alazz.imovie.utils.Constant.API_KEY;

public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View mView;
    private final CompositeDisposable mCompositeDisposable;
    private ApiService mApiService;
    HomePresenter(HomeContract.View view) {
        mView = view;
        mCompositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
        mApiService = ApiClient.getClient().create(ApiService.class);


    }

    private void getTopRatedMovies(){

        ///  Method for all get movie top rared from  provider themoviedb.org ///


        mView.showProgressIndicator(true);

        mCompositeDisposable.add(mApiService.getTopRatedMovies(API_KEY,"en",1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Movies>() {

                    @Override
                    public void onNext(Movies movies) {

                        ///// get movie model from api and add this to list movies then it is set list in view (adapter )   ///

                        mView.showProgressIndicator(false);

                        if (movies.getPage()==0){  ///// after get movies check is list movies is empty so show Empty View   ///


                            mView.setEmptyView();
                        } else {

                            mView.setTopRatedMoviesList(movies);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        ///// If any error occurs, show message(Toast) will appear to the user and show empty view or error view    ///

                        mView.showProgressIndicator(false);
                        mView.makeToast(e.getLocalizedMessage());
                        mView.setEmptyView();

                    }

                    @Override

                    public void onComplete() {
                        mView.showProgressIndicator(false);

                    }
                })
        );

    }

    @Override
    public void getMoreTopRatedMovies(int page) {


        ///  Method for  more get movie top when user scroll  ///


        mView.showProgressIndicator(true);

        mCompositeDisposable.add(mApiService.getTopRatedMovies(API_KEY,"en",page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Movies>() {

                    @Override
                    public void onNext(Movies movies) {
                        mView.showProgressIndicator(false);
                        mView.setMoreTopRatedMoviesList(movies);

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showProgressIndicator(false);
                        mView.makeToast(e.getLocalizedMessage());

                    }

                    @Override

                    public void onComplete() {
                        mView.showProgressIndicator(false);

                    }
                })
        );

    }



    @Override
    public void subscribe() {
        // this method run when calling onActivityCreated in Home Fragment  ///

        getTopRatedMovies();
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear(); // clear CompositeDisposable  when calling onDestroy in Home Fragment  ///

    }


}