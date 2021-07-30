package com.example.toolbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.ShareActionProvider
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat


class MainActivity : AppCompatActivity() {

    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        toolbar?.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack. setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }

    // Con este metodo podemos usar la barra del menu
    // acá va toda la configuración de los elementos de la toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val itemSearch = menu?.findItem(R.id.search)
        val viewSearch = itemSearch?.actionView as androidx.appcompat.widget.SearchView

        // Con las 3 lineas podemos sacar las opciones de compartir en diferentes aplicaciones
        val itemShare = menu?.findItem(R.id.share)
        val shareActionProvider = MenuItemCompat.getActionProvider(itemShare) as ShareActionProvider
        shareIntent(shareActionProvider)

        // Así se van a ver el hint en la barra de buscar
        viewSearch.queryHint = "Type your name"
        viewSearch.setOnQueryTextFocusChangeListener { v, hasFocus ->
            Log.d("LISTENERFOCUS", hasFocus.toString())
        }

    // COn estos métodos se va tomando el texto que el usuario ecribe
        viewSearch.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{

            // Lo que se va escribiendo en la casilla
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("OnQueryTextChange", newText.toString())
                return true
            }

            // Lo que se envia finalmente
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("onQueryTextSubmit", query.toString())
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.btnFav -> {
                Toast.makeText(this, "Button fav", Toast.LENGTH_LONG).show()
            }
            R.id.btnBack -> {
                Toast.makeText(this, "Button back", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun shareIntent(shareActionProvider: ShareActionProvider){
        shareActionProvider!!
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Este es un mensaje compartido ")
        shareActionProvider.setShareIntent(intent)
    }
}