package com.example.loftcoin.screens.rate;

import com.example.loftcoin.data.api.Api;
import com.example.loftcoin.data.api.model.Coin;
import com.example.loftcoin.data.api.model.RateResponse;
import com.example.loftcoin.data.db.Database;
import com.example.loftcoin.data.db.model.CoinEntity;
import com.example.loftcoin.data.db.model.CoinEntityMapper;
import com.example.loftcoin.data.prefs.Prefs;

import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RatePresenterImpl implements RatePresenter {

    private Prefs prefs;
    private Api api;
    private Database database;
    private CoinEntityMapper coinEntityMapper;
    private CompositeDisposable disposables = new CompositeDisposable();


    @Nullable
    private RateView view;


    public RatePresenterImpl(Prefs prefs, Api api, Database database, CoinEntityMapper coinEntityMapper) {
        this.prefs = prefs;
        this.api = api;
        this.database = database;
        this.coinEntityMapper = coinEntityMapper;
    }

    @Override
    public void attachView(RateView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        disposables.dispose();
        view = null;
    }

    @Override
    public void getRate() {
        Disposable disposable = database.getCoins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(coinEntities -> {
                    if (view != null) {
                        view.setCoins(coinEntities);
                    }
                }, Timber::e);

        disposables.add(disposable);
    }

    private void loadRate(){

        Disposable disposable = api.rates(Api.CONVERT)
                .subscribeOn(Schedulers.io())
                .map(new Function<RateResponse, List<Coin>>() {
                    @Override
                    public List<Coin> apply(RateResponse rateResponse) throws Exception {
                        return rateResponse.data;
                    }
                })
                .map(new Function<List<Coin>, List<CoinEntity>>() {
                    @Override
                    public List<CoinEntity> apply(List<Coin> coins) throws Exception {
                        return coinEntityMapper.map(coins);
                    }
                })
                .doOnNext(new Consumer<List<CoinEntity>>() {
                    @Override
                    public void accept(List<CoinEntity> coinEntities) throws Exception {
                        database.saveCoins(coinEntities);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CoinEntity>>() {
                    @Override
                    public void accept(List<CoinEntity> coinEntities) throws Exception {
                        if(view != null){
                            view.setRefreshing(false);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.e(throwable);
                        if(view != null){
                            view.setRefreshing(false);
                        }
                    }
                });
        disposables.add(disposable);
    }
    @Override
    public void onRefresh() {
        loadRate();
    }
}
