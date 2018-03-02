package com.ckw.multimediaplayer.api;

import com.ckw.multimediaplayer.repository.Repository;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ckw
 * on 2018/3/2.
 */

public interface ApiService {

    @GET("users/{username}/repos")
    Observable<List<Repository>> getPublicRepositories(@Path("username") String userName);
}
