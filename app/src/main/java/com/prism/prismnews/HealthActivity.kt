package com.prism.prismnews

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class HealthActivity : AppCompatActivity() , NewsItemClicked {

    private lateinit var mAdapter: NewsListAdapter
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter= NewsListAdapter(this)
        recyclerView.adapter = mAdapter

        drawerLayout = findViewById(R.id.drawerLayout)
        val navigationView : NavigationView = findViewById(R.id.navView)
        toggle = ActionBarDrawerToggle(this, drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView.setNavigationItemSelectedListener {
            it.isChecked=true
            when(it.itemId){
                R.id.nav_home -> {
                    Toast.makeText(applicationContext, "Clicked Home", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                }
                R.id.nav_business -> {
                    Toast.makeText(applicationContext, "Clicked Business", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, BusinessActivity::class.java))
                }
                R.id.nav_entertainment -> {
                    Toast.makeText(applicationContext, "Clicked Entertainment", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, EntertainmentActivity::class.java))
                }
                R.id.nav_sports -> {
                    Toast.makeText(applicationContext, "Clicked Sports", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, SportsActivity::class.java))
                }
                R.id.nav_health -> {
                    Toast.makeText(applicationContext, "Clicked Health", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HealthActivity::class.java))
                }

            }
            true


        }


    }


    private fun fetchData() {

        val url = "https://newsapi.org/v2/top-headlines?category=health&country=in&apiKey=9edeb7c6b2574654828afb316dec47e0"

        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, url, null,

            { response ->
                val newsJsonArray = response.getJSONArray("articles")
                val newsArray = ArrayList<Newz>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = Newz(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }

                mAdapter.updateNewz(newsArray)

            },
            { _ ->

            })

        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        // Access the RequestQueue through your singleton class.
        NewzSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: Newz) {
        val builder = CustomTabsIntent.Builder()
        val customTabIntent = builder.build()
        customTabIntent.launchUrl(this, Uri.parse(item.url));
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}