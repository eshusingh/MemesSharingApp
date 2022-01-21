package com.example.memessharingapp

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var CurrentImageurl : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
    }

    private fun loadmeme()
    {
        progressbar.visibility = View.VISIBLE
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
         CurrentImageurl = "https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, CurrentImageurl, null,
            Response.Listener { response ->

               CurrentImageurl = response.getString("url")
                Glide.with(this).load(CurrentImageurl).listener(object:RequestListener<Drawable>{

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility = View.GONE
                        return false
                    }


                }).into(memeimage)
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
                Toast.makeText(this, "somethinng wennt wrong", Toast.LENGTH_SHORT).show()



            })
        queue.add(jsonObjectRequest)

    }

    fun sharememe(view: android.view.View)
    {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey check out this cool meme from Reddit $CurrentImageurl")
        val chooser = Intent.createChooser(intent,"Share this meme using....")
        startActivity(chooser)
    }

    fun nextmeme(view: android.view.View) {
        loadmeme()
    }
}