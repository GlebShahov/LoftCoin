package com.example.loftcoin.screens.rate;

import com.example.loftcoin.data.api.Api;
import com.example.loftcoin.data.api.model.Coin;

import com.example.loftcoin.data.db.Database;
import com.example.loftcoin.data.db.model.CoinEntity;
import com.example.loftcoin.data.db.model.CoinEntityMapper;
import com.example.loftcoin.data.prefs.Prefs;
import com.example.loftcoin.utils.Fiat;

import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import io.reactivex.schedulers.Schedulers;

import timber.log.Timber;

public class RatePresenterImpl implements RatePresenter {

    private Prefs prefs;
    private Api api;
    private Database mainDatabase;
    private Database workerDatabase;
    private CoinEntityMapper coinEntityMapper;

    @Nullable
    private RateView view;

    private CompositeDisposable disposables = new CompositeDisposable();


    public RatePresenterImpl(Prefs prefs,
                             Api api,
                             Database mainDatabase,
                             Database workerDatabase,
                             CoinEntityMapper coinEntityMapper) {

        this.prefs = prefs;
        this.api = api;
        this.mainDatabase = mainDatabase;
        this.workerDatabase = workerDatabase;
        this.coinEntityMapper = coinEntityMapper;
    }

    @Override
    public void attachView(RateView view) {
        this.view = view;
        mainDatabase.open();
    }

    @Override
    public void detachView() {
        disposables.dispose();
        view = null;
        mainDatabase.close();
    }

    @Override
    public void getRate() {


        Disposable disposable = mainDatabase.getCoins()
                .subscribe(coinEntities -> {
                    if (view != null) {
                        view.setCoins(coinEntities);
                    }
                }, Timber::e);

        disposables.add(disposable);
    }

    private void loadRate() {


        Disposable disposable = api.rates(Api.CONVERT)
                .subscribeOn(Schedulers.io())
                .map(rateResponse -> {
                    List<Coin> coins = rateResponse.data;
                    List<CoinEntity> coinEntities = coinEntityMapper.map(coins);
                    return coinEntities;
                })
                .doOnNext(coinEntities -> {
                    workerDatabase.open();
                    workerDatabase.saveCoins(coinEntities);
                    workerDatabase.close();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        coinEntities -> {

                            if (view != null) {
                                view.setRefreshing(false);
                            }
                        },
                        throwable -> {
                            Timber.e(throwable);

                            if (view != null) {
                                view.setRefreshing(false);
                            }
                        }
                );


        disposables.add(disposable);
    }

    @Override
    public void onRefresh() {
        loadRate();
    }




    @Override
    public void onMenuItemCurrencyClick() {
        if (view != null) {
            view.showCurrencyDialog();
        }

    }

    @Override
    public void onFiatCurrencySelected(Fiat currency) {
        prefs.setFiatCurrency(currency);

        if (view != null) {
            view.invalidateRates();
        }

    }
}