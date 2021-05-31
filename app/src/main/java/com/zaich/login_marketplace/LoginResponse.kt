package com.zaich.login_marketplace

import com.google.gson.annotations.SerializedName

class LoginResponse (@SerializedName("token") val token : String?){
}