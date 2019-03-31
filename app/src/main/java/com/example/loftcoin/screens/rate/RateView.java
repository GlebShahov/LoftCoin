package com.example.loftcoin.screens.rate;

import com.example.loftcoin.data.api.model.Coin;
import com.example.loftcoin.data.db.model.CoinEntity;

import java.util.List;

public interface RateView {

    void setCoins(List<CoinEntity> coins);

    void setRefreshing(Boolean refreshing);

    void showCurrencyDialog();

    void invalidateRates();
}
