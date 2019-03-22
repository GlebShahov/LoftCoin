package com.example.loftcoin.screens.rate;

import com.example.loftcoin.data.api.model.Coin;

import java.util.List;

public interface RateView {

    void setCoins(List<Coin> coins);

    void setRefreshing(Boolean refreshing);

    void showCurrencyDialog();
}
