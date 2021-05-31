package com.zaich.login_marketplace

import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @POST("/api/v1/register")
    fun addUser(@Body newUserModel: UserModel) : Call<UserModel>

    @FormUrlEncoded
    @POST("/api/v1/login")
    fun login(@Field("user_name")user_name:String,
              @Field("password") password:String
    ):Call<LoginResponse>

//    @POST("/api/v1/login")
//    fun login(@Body loginUser: LoginResponse):Call<LoginResponse>

}