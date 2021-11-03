package com.example.pokedexandroid

import AdapterPokemon
import Pokemon
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type


class MainActivity : AppCompatActivity() {
    private lateinit var listPokemon: List<Pokemon>
    private var adapter = AdapterPokemon()

    private val tipos : Array<String> = arrayOf("all", "normal", "fire", "water", "grass" ,"bug", "poison", "electric", "ground", "fighting", "psychic", "rock", "flying", "ghost", "ice", "dragon", "fairy")
    private val ordenacoes : Array<String> = arrayOf("a-z", "z-a", "id(0-151)", "id(151-0)")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchJson()

        val filterBtn = findViewById<Button>(R.id.filterBtn)
        filterBtn.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            lateinit var dialog:AlertDialog
            builder.setTitle("Choose a pokemon type:")

            builder.setSingleChoiceItems(tipos,-1) { _, which ->
                val tipo = tipos[which]
                adapter.filter(tipo, "tipo")
                dialog.dismiss()
            }

            dialog = builder.create()
            dialog.show()
        }

        val sortBtn = findViewById<Button>(R.id.sortBtn)
        sortBtn.setOnClickListener{
            //adapter.sort("id1510")
            val builder = AlertDialog.Builder(this)
            lateinit var dialog:AlertDialog
            builder.setTitle("Choose a sort type:")

            builder.setSingleChoiceItems(ordenacoes,-1) { _, which ->
                val ordenacao = ordenacoes[which]
                adapter.sort(ordenacao)
                dialog.dismiss()
            }

            dialog = builder.create()
            dialog.show()
        }

        val searchBar = findViewById<SearchView>(R.id.search_bar)
        searchBar.queryHint = "Search a pokemon"

        searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.isEmpty()){
                    adapter.pokemonsLista = listPokemon
                }else{
                    adapter.filter(newText,"name")
                }
                return true
            }

        })

    }

    private fun fetchJson(){
        val url = "https://ifpb.github.io/intin-topicos/desafios/pokedex/code/data/pokedex.json"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()
                val listType: Type = object : TypeToken<ArrayList<Pokemon?>?>() {}.type
                listPokemon = gson.fromJson(body, listType)

                runOnUiThread {
                    val rv = findViewById<RecyclerView>(R.id.recyclerView)
                    rv.layoutManager = LinearLayoutManager(rv.context)
                    rv.adapter = adapter
                    adapter.pokemonsLista = listPokemon
                    adapter.pokemonsListaCopia = listPokemon
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("failedd")
            }

        })
    }

}
