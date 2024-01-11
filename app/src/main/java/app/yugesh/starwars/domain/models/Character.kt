package app.yugesh.starwars.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

@Entity
data class Character(
    @SerializedName("birth_year")
    val birthYear: String? = null,
    @SerializedName("created")
    val created: String? = null,
    @SerializedName("edited")
    val edited: String? = null,
    @SerializedName("eye_color")
    val eyeColor: String? = null,
    @SerializedName("films")
    val films: List<String?>? = null,
    @SerializedName("gender")
    val gender: String? = null,
    @SerializedName("hair_color")
    val hairColor: String? = null,
    @SerializedName("height")
    val height: String? = null,
    @SerializedName("homeworld")
    val homeworld: String? = null,
    @SerializedName("mass")
    val mass: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("skin_color")
    val skinColor: String? = null,
    @SerializedName("species")
    val species: List<String?>? = null,
    @SerializedName("starships")
    val starships: List<String?>? = null,
    @PrimaryKey
    @SerializedName("url")
    val url: String,
    @SerializedName("vehicles")
    val vehicles: List<String?>? = null,
    val page: Int? = null,
)

class CharacterConverter {
    @TypeConverter
    fun listToJson(value: List<String?>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String?>::class.java).toList()
}
