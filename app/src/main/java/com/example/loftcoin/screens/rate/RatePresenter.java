package com.example.loftcoin.screens.rate;

import com.example.loftcoin.utils.Fiat;

public interface RatePresenter{

    void attachView(RateView view);

    void detachView();

    void getRate();

    void onMenuItemCurrencyClick();

    void onRefresh();

    void onFiatCurrencySelected(Fiat currency);
}

