package com.calseto.pokeapii24

import Modelo.Pokemon
import Modelo.PokemonApiService
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonActivity : AppCompatActivity() {
    // variables de los componentes de la vista
    private lateinit var pokemonApiService: PokemonApiService
    private lateinit var etBuscar: EditText
    private lateinit var btnBuscar: Button
    private lateinit var tvId: TextView
    private lateinit var tvName: TextView
    private lateinit var tvHeigh: TextView
    private lateinit var tvWeight: TextView
    private lateinit var ivPokemon: ImageView
    private lateinit var tvAbilities: TextView
    private lateinit var tvMoves: TextView
    private lateinit var tvGameIndex: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)


        // pasamos los id de la vista
        pokemonApiService = PokemonApiService()
        etBuscar = findViewById(R.id.etBuscar)
        btnBuscar = findViewById(R.id.btnBuscar)
        tvId = findViewById(R.id.tvId)
        tvName = findViewById(R.id.tvName)
        tvHeigh = findViewById(R.id.tvHeigh)
        tvWeight = findViewById(R.id.tvWeight)
        ivPokemon = findViewById(R.id.ivPokemon)
        tvAbilities = findViewById(R.id.tvAbilities)
        tvMoves = findViewById(R.id.tvMoves)
        tvGameIndex = findViewById(R.id.tvGameIndex)

        btnBuscar.setOnClickListener {
            val input = etBuscar.text.toString()
            if (input.isBlank()) {
                mostrarToast("Ingresa un ID Crack")
            } else if (!input.matches(Regex("\\d+"))) {
                mostrarToast("Eso no se permite Crack")
            } else {
                buscarPokemon(input)
            }
        }
    }

    private fun buscarPokemon(pokemonId: String) {
        lifecycleScope.launch {
            try {
                val pokemon = withContext(Dispatchers.IO) {
                    pokemonApiService.fetchPokemon(pokemonId)
                }
                actualizarUI(pokemon)
            } catch (e: Exception) {
                mostrarErrorToast("Ese Pokémon no existe Crack")
            }
        }
    }


    private fun actualizarUI(pokemon: Pokemon) {
        tvId.text = "ID: ${pokemon.id}"
        tvName.text = "Name: ${pokemon.name}"
        tvHeigh.text = "Height: ${pokemon.height}"
        tvWeight.text = "Weight: ${pokemon.weight}"
        Glide.with(this).load(pokemon.sprites.other.officialArtwork.frontDefault).into(ivPokemon)
        tvAbilities.text = "Abilidades: " + pokemon.abilities.joinToString(", ") { it.ability.name }
        tvMoves.text = "Moves: " + pokemon.moves.take(1).joinToString(" ") { it.move.name }
        tvGameIndex.text = "Game Indices: " + pokemon.game_indices.joinToString(", ") {
            "${it.version.name} (${it.game_index})"
        }
    }

    private fun mostrarErrorToast(mensaje: String) {
        mostrarToast(mensaje)
        tvId.text = "ID: "
        tvName.text = "Name: "
        tvHeigh.text = "Height: "
        tvWeight.text = "Weight: "
        tvAbilities.text = "Abilities: "
        tvMoves.text = "Moves: -"
        tvGameIndex.text = "Game Indices: "
        ivPokemon.setImageResource(android.R.color.transparent)
    }

    private fun mostrarToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}


