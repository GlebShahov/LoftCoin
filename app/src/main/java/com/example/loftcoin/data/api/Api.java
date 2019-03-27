package com.example.loftcoin.data.api;

import com.example.loftcoin.data.api.model.RateResponse;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {

    String CONVERT = "USD,EUR,RUB";
    @GET("cryptocurrency/listings/latest")
    @Headers("X-CMC_PRO_API_KEY: 503bfad8-c36b-48c2-9174-97bae3561d17")
    Observable<RateResponse> rates(@Query("convert") String convert);
}
