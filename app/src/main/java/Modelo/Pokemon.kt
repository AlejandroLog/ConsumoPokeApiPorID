package Modelo

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService{
    @GET("pokemon/{name}") suspend fun getPokemon(@Path("name") pokemonName: String): Pokemon
}

class PokemonApiService{
    private val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
    suspend fun fetchPokemon(pokemonName: String):Pokemon{
        return api.getPokemon(pokemonName)
    }
}



data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
    val abilities: List<Ability>,
    val moves: List<Move>,
    val game_indices: List<GameIndex>
)



data class OfficialArtwork(
    @SerializedName("front_default") val imageUrl: String
)

data class Ability(
    val ability: NamedResource
)

data class Move(
    val move: NamedResource
)

data class GameIndex(
    val game_index: Int,
    val version: NamedResource
)

data class NamedResource(
    val name: String
)


data class Sprites(
    val other: Other
)

data class Other(
    @SerializedName("official-artwork")
    val officialArtwork: Artwork
)

data class Artwork(
    @SerializedName("front_default")
    val frontDefault: String
)


