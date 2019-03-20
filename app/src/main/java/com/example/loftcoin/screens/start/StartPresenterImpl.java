package com.example.loftcoin.screens.start;

import com.example.loftcoin.data.api.Api;
import com.example.loftcoin.data.api.model.RateResponse;
import com.example.loftcoin.data.prefs.Prefs;
import com.example.loftcoin.utils.Fiat;


import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class StartPresenterImpl implements StartPresenter {
    private Prefs prefs;
    private Api api;

    @Nullable
    private StartView view;

    public StartPresenterImpl(Prefs prefs, Api api) {
        this.prefs = prefs;
        this.api = api;
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
        Fiat fiat = prefs.getFiatCurrency();

        Call <RateResponse> call = api.rates(Api.CONVERT);
        call.enqueue(new Callback<RateResponse>() {
            @Override
            public void onResponse(Call<RateResponse> call, Response<RateResponse> response) {
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