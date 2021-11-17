package com.cjbatausa.loginapp.api;

import com.cjbatausa.loginapp.batausa.Login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface  Requestplaceholder {

    @POST("login")
    Call<Login> login(@Body Login login);

}
