import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedexandroid.R
import com.example.pokedexandroid.StatsActivity
import java.util.*

class AdapterPokemon: RecyclerView.Adapter<AdapterPokemon.HolderPokemon>(){

    var pokemonsLista: List<Pokemon> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var pokemonsListaCopia : List<Pokemon> = emptyList()

    private var pokemonAtual : Pokemon? = null

    class HolderPokemon(view: View) : RecyclerView.ViewHolder(view) {
        private val image = view.findViewById<ImageView>(R.id.fotoPokemon)
        private val name = view.findViewById<TextView>(R.id.nomePokemon)
        private val pokemonListItem= view.findViewById<LinearLayout>(R.id.pokemonListItem)
        private val intent = Intent(view.context, StatsActivity::class.java)

        fun bind(pokemon: Pokemon) {

            pokemonListItem.setOnClickListener{
                intent.putExtra("EXTRA_POKEMON", pokemon)
                itemView.context.startActivity(intent)
            }

            name.text = pokemon.name
            val path:String?

            when (pokemon.name){
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
                    path = "https://img.pokemondb.net/artwork/" + pokemon.name?.toLowerCase(Locale.ROOT)+".jpg"
                }
            }

            Glide
                .with(itemView)
                .load(path)
                //.load("https://i.imgur.com/0HGY476.jpeg")
                .into(image)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderPokemon {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_pokemon, parent, false)

        return HolderPokemon(view)
    }

    override fun onBindViewHolder(holderPokemon: HolderPokemon, position: Int) {
        holderPokemon.bind(pokemonsLista[position])
        pokemonAtual = pokemonsLista[position]
    }

    override fun getItemCount(): Int {
        return pokemonsLista.size
    }

    fun filter(text: String, tipoFiltro: String) {

        pokemonsLista = emptyList()
        if (text.isEmpty()) {
            pokemonsLista = pokemonsListaCopia
        } else {

            when (tipoFiltro){
                "name" -> {
                    for (pokemon in pokemonsListaCopia) {
                        if (pokemon.name?.toLowerCase(Locale.ROOT)?.contains(text.toLowerCase(Locale.ROOT)) == true) {
                            pokemonsLista += pokemon
                        }
                    }
                }

                "tipo" -> {
                    if (text=="all"){
                        pokemonsLista=pokemonsListaCopia
                    }else{
                        for (pokemon in pokemonsListaCopia) {
                            if (pokemon.type?.toString()?.toLowerCase(Locale.ROOT)?.contains(text.toLowerCase(Locale.ROOT)) == true) {
                                pokemonsLista += pokemon
                            }
                        }
                    }

                }

            }

        }
        notifyDataSetChanged()
    }

    fun sort(tipoSort: String) {
        when (tipoSort) {
            "a-z" -> {
                pokemonsLista=pokemonsLista.sortedWith(compareBy { it.name })
            }
            "z-a" -> {
                pokemonsLista=pokemonsLista.sortedWith(compareBy{ it.name }).reversed()
            }
            "id(0-151)" -> {
                pokemonsLista=pokemonsLista.sortedWith(compareBy { it.id })
            }
            "id(151-0)" -> {
                pokemonsLista=pokemonsLista.sortedWith(compareBy { it.id }).reversed()
            }
        }

        notifyDataSetChanged()
    }


}
