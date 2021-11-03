package com.example.pokedexandroid

import Pokemon
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import java.util.*

class StatsActivity : AppCompatActivity() {

    private var currentPokemon : Pokemon? = null

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        currentPokemon = intent.getSerializableExtra("EXTRA_POKEMON") as? Pokemon

        val icon = findViewById<ImageView>(R.id.pokemonIcon)

        val path:String?

            when (currentPokemon?.name){
                "Farfetch'd" -> {
                    path = "https://img.pokemondb.net/artwork/farfetchd.jpg"
                }

                "Nidoran♂" -> {
                    path = "https://img.pokemondb.net/artwork/nidoran-m.jpg"
                }

                "Nidoran♀" -> {
                    path = "https://img.pokemondb.net/artwork/nidoran-f.jpg"
                }

                "Mr. Mime" -> {
                    path = "https://img.pokemondb.net/artwork/mr-mime.jpg"
                }

                else -> {
                    path = "https://img.pokemondb.net/artwork/" + currentPokemon?.name?.toLowerCase(Locale.ROOT)+".jpg"
                }
            }

        Glide
            .with(this)
            .load(path)
            .into(icon)

        val name = findViewById<TextView>(R.id.pokemonName)
        name.text= currentPokemon?.name

        val id = findViewById<TextView>(R.id.pokemonId)
        val idText = "#" + currentPokemon?.id.toString()
        id.text = idText

        val type = findViewById<TextView>(R.id.type)
        val typeText = "Type: " + currentPokemon?.type
        type.text = typeText

        val total = findViewById<TextView>(R.id.total)
        val totalText = "Total: " + currentPokemon?.stats?.total.toString()
        total.text = totalText

        val hp = findViewById<TextView>(R.id.hp)
        val hpText =  "Hp: " + currentPokemon?.stats?.hp.toString()
        hp.text = hpText

        val attack = findViewById<TextView>(R.id.attack)
        val attackText = "Attack: " + currentPokemon?.stats?.attack.toString()
        attack.text = attackText

        val defense = findViewById<TextView>(R.id.defense)
        val defenseText = "Defense: " + currentPokemon?.stats?.defense.toString()
        defense.text = defenseText

        val spAtk = findViewById<TextView>(R.id.spAtk)
        val spAtkText = "Sp-atk: " + currentPokemon?.stats?.spAtk.toString()
        spAtk.text = spAtkText

        val spDef = findViewById<TextView>(R.id.spDef)
        val spDefText = "Sp-def: " + currentPokemon?.stats?.spDef.toString()
        spDef.text = spDefText

        val speed = findViewById<TextView>(R.id.speed)
        val speedText = "Speed: " + currentPokemon?.stats?.speed.toString()
        speed.text = speedText
    }

}