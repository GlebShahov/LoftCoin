package com.example.loftcoin.screens.start;

import com.example.loftcoin.data.api.Api;
import com.example.loftcoin.data.api.model.Coin;
import com.example.loftcoin.data.api.model.RateResponse;
import com.example.loftcoin.data.db.Database;
import com.example.loftcoin.data.db.model.CoinEntity;
import com.example.loftcoin.data.db.model.CoinEntityMapper;
import com.example.loftcoin.data.prefs.Prefs;
import com.example.loftcoin.utils.Fiat;


import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.Scheduler;
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

public class StartPresenterImpl implements StartPresenter {

    private Prefs prefs;
    private Api api;
    private Database database;
    private CoinEntityMapper coinEntityMapper;

    @Nullable
    private StartView view;

    private CompositeDisposable disposables = new CompositeDisposable();

    public StartPresenterImpl(Prefs prefs, Api api, Database database, CoinEntityMapper coinEntityMapper) {
        this.prefs = prefs;
        this.api = api;
        this.database = database;
        this.coinEntityMapper = coinEntityMapper;
    }

    @Override
    public void attachView(StartView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        disposables.dispose();
        this.view = null;
    }

    @Override
    public void loadRates() {


        Disposable disposable = api.rates(Api.CONVERT)
                .subscribeOn(Schedulers.io())
                .map(rateResponse -> {
                    List<Coin> coins = rateResponse.data;
                    List<CoinEntity> coinEntities = coinEntityMapper.map(coins);
                    return coinEntities;
                })
                .doOnNext(coinEntities -> {
                    database.open();
                    database.saveCoins(coinEntities);
                    database.close();

                })

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        coinEntities -> {
                            if (view != null) {
                                view.navigateToMainScreen();
                            }
                        },
                        throwable -> {
                            Timber.e(throwable);
                        }
                );


        disposables.add(disposable);

    }
}