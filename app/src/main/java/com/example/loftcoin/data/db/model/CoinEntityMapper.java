package com.example.loftcoin.data.db.model;

import com.example.loftcoin.data.api.model.Coin;

import java.util.List;

public interface CoinEntityMapper {
    List<CoinEntity> map (List<Coin> coins);
}
