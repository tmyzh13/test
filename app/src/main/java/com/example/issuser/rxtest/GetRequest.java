package com.example.issuser.rxtest;


import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by issuser on 2018/2/7.
 */

public interface GetRequest  {

    @GET(Urls.test)
    Observable<Translation> getCall();

    @GET(Urls.test1)
    Observable<Translation> getCall1();
}
