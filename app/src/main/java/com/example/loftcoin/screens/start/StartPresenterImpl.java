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
        view = null;

    }

    @Override
    public void loadRates() {

        Call <RateResponse> call = api.rates(Api.CONVERT);
        call.enqueue(new Callback<RateResponse>() {
            @Override
            public void onResponse(Call<RateResponse> call, Response<RateResponse> response) {
                if(response.body() != null){
                    List<Coin> coins = response.body().data;
                    List<CoinEntity> coinEntities = coinEntityMapper.map(coins);
                    database.saveCoins(coinEntities);
                }

                if (view != null){
                    view.navigateToMainScreen();
                }

            }

            @Override
            public void onFailure(Call<RateResponse> call, Throwable t) {
                Timber.e(t);

            }
        });
    }
}
