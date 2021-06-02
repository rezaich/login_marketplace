package com.zaich.login_marketplace

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigasiapp.adapter.categoryAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.gson.JsonObject
import com.zaich.login_marketplace.Model.CategoryModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBar: ActionBarDrawerToggle
    private lateinit var navDrawerView: NavigationView

    //Initialise the NavigationBottomBar
    private lateinit var bottomNavigation : BottomNavigationView
    lateinit var sharedPref: SharedPreferences
    var token: String = ""

    var myAdapter : categoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var progressDialog: ProgressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Loading")
        progressDialog.show();

        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            //your codes here
            sharedPref = getSharedPreferences("SharePref", Context.MODE_PRIVATE)
            token = sharedPref.getString("token", "")!!

            var apiInterface: ApiInterface = ApiClient().getApiClient()!!
                .create(ApiInterface::class.java)
            var requestCall: Call<JsonObject> = apiInterface
                .getCategories("Bearer "+token)
            requestCall.enqueue(object: Callback<JsonObject>{
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.d("gagal", t.toString())
                }

                override fun onResponse(call: Call<JsonObject>,
                                        response: Response<JsonObject>) {

                    val myJson = response.body()
                    val myData = myJson!!.getAsJsonArray("data")
                    myAdapter = categoryAdapter(this@MainActivity)

                    val arrayItem = ArrayList<CategoryModel>()
                    for (i in 0 until myData.size()) {
                        var myRecord: JsonObject = myData.get(i).asJsonObject
                        var id = myRecord.get("id").asInt
                        var name = myRecord.get("name").asString
                        var image_link = myRecord.get("image_link").asString

                        Log.d("Log "+i.toString(), myData.get(i).toString())
                        arrayItem.add(CategoryModel(id, name, image_link))
                    }
                    Log.d("Array Item", arrayItem.toString())
                    myAdapter!!.setData(arrayItem)

                    product_recycleview.layoutManager = LinearLayoutManager(this@MainActivity)
                    product_recycleview.adapter = myAdapter

                    progressDialog.dismiss();
                }


            })
        }
        //START BOTTOM NAVIGATION
        bottomNavigation = findViewById(R.id.navBottom)
        bottomNavigation.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.navigation_home -> {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_history -> {
                    Toast.makeText(this, "Go To history", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    false
                }
            }
        }
        drawerLayout = findViewById(R.id.drawer)
        actionBar = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar.syncState()


        }
    }