package com.example.loftcoin.data.db.room;

import com.example.loftcoin.data.db.Database;
import com.example.loftcoin.data.db.model.CoinEntity;
import com.example.loftcoin.data.db.model.Wallet;
import com.example.loftcoin.data.db.model.WalletModel;

import java.util.List;

import io.reactivex.Flowable;

public class DatabaseImplRoom implements Database {

    private AppDatabase appDatabase;

    public DatabaseImplRoom(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public void saveCoins(List<CoinEntity> coins) {
       appDatabase.coinDao().saveCoins(coins);
    }

    @Override
    public Flowable <List <CoinEntity>> getCoins() {
        return appDatabase.coinDao().getCoins();
    }

    @Override
    public CoinEntity getCoin(String symbol) {
        return appDatabase.coinDao().getCoin(symbol);
    }

    @Override
    public void saveWallet(Wallet wallet) {
        appDatabase.walletDao().saveWallet(wallet);
    }

    @Override
    public Flowable<List<WalletModel>> getWallets() {
        return appDatabase.walletDao().getWallets();
    }
}
