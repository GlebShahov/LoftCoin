package com.example.loftcoin.data.db.room;

import com.example.loftcoin.data.db.model.CoinEntity;
import com.example.loftcoin.data.db.model.Transaction;
import com.example.loftcoin.data.db.model.Wallet;


import androidx.room.Database;

import androidx.room.RoomDatabase;


@Database(entities = {CoinEntity.class, Wallet.class, Transaction.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CoinDao coinDao();
    public abstract WalletDao walletDao();
}
