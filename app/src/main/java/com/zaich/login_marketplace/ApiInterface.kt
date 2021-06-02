package com.zaich.login_marketplace

import com.google.gson.JsonObject
import com.zaich.login_marketplace.Model.UserModel
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @POST("register")
    fun addUser(@Body newUserModel: UserModel) : Call<UserModel>

    @FormUrlEncoded
    @POST("login")
    fun login(@Field("user_name")user_name:String,
              @Field("password") password:String
    ):Call<LoginResponse>

//    @POST("/api/v1/login")
//    fun login(@Body loginUser: LoginResponse):Call<LoginResponse>

    @GET("category")
    fun getCategories(@Header("Authorization")authHeader: String): Call<JsonObject>

    @GET("products/searchByCategory/{categoryId}")
    fun getProductsByCategory(@Header("Authorization")authHeader: String,
                              @Path("categoryId")id:Int):Call<JsonObject>
}