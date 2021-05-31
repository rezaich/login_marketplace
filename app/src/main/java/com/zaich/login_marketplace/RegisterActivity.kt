package com.zaich.login_marketplace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create



class RegisterActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val login = Intent(application,LoginActivity::class.java)
        btRegis.setOnClickListener {
            addRecord()
            startActivity(login)
        }
    }
    fun addRecord(){
        val name = eName.text.toString()
        val userName = euserName.text.toString()
        val password = epassword.text.toString()
        val repeat = erpassword.text.toString()

        if(name == "" || userName == "" || password == "" ){
            Toast.makeText(this, "Masih ada field yang kosong", Toast.LENGTH_LONG).show()
        } else if (repeat != password){
            Toast.makeText(this, "Password tidak Sama", Toast.LENGTH_LONG).show()
            eName.setSelection(0)
            true
        }
        else {
            val newUser : UserModel = UserModel(name,userName,password)

            var apiInterface: ApiInterface = ApiClient().getApiClient()!!.create(ApiInterface::class.java)

            var requestCall : Call<UserModel> = apiInterface.addUser(newUser)

            requestCall.enqueue(object : Callback<UserModel> {
                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "Failed", Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Create Account Success",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(this@RegisterActivity, "Failed response", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }
}