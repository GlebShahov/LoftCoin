package com.example.loftcoin.data.prefs;

import com.example.loftcoin.utils.Fiat;

public interface Prefs {
    boolean isFirstLaunch();
    void setFirstLaunch(boolean firstLaunch);

    Fiat getFiatCurrency();
    void setFiatCurrency(Fiat fiat);

}
