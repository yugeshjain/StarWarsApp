package app.yugesh.starwars.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "filmDetailsResponse")
data class FilmDetailsResponse(
    @SerializedName("characters")
    val characters: List<String?>? = null,
    @SerializedName("created")
    val created: String? = null, // 2014-12-10T14:23:31.880000Z
    @SerializedName("director")
    val director: String? = null, // George Lucas
    @SerializedName("edited")
    val edited: String? = null, // 2014-12-20T19:49:45.256000Z
    @SerializedName("episode_id")
    val episodeId: Int? = null, // 4
    @SerializedName("opening_crawl")
    val openingCrawl: String? = null, // It is a period of civil war.Rebel spaceships, strikingfrom a hidden base, have wontheir first victory againstthe evil Galactic Empire.During the battle, Rebelspies managed to steal secretplans to the Empire'sultimate weapon, the DEATHSTAR, an armored spacestation with enough powerto destroy an entire planet.Pursued by the Empire'ssinister agents, PrincessLeia races home aboard herstarship, custodian of thestolen plans that can save herpeople and restorefreedom to the galaxy....
    @SerializedName("planets")
    val planets: List<String?>? = null,
    @SerializedName("producer")
    val producer: String? = null, // Gary Kurtz, Rick McCallum
    @SerializedName("release_date")
    val releaseDate: String? = null, // 1977-05-25
    @SerializedName("species")
    val species: List<String?>? = null,
    @SerializedName("starships")
    val starships: List<String?>? = null,
    @SerializedName("title")
    val title: String? = null, // A New Hope
    @PrimaryKey
    @SerializedName("url")
    val url: String, // https://swapi.dev/api/films/1/
    @SerializedName("vehicles")
    val vehicles: List<String?>? = null
)
