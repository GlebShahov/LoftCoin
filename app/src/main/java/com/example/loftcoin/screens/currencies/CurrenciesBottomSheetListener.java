package com.example.loftcoin.screens.currencies;

import com.example.loftcoin.data.db.model.CoinEntity;

public interface CurrenciesBottomSheetListener {
    void onCurrencySelected(CoinEntity coin);
}
