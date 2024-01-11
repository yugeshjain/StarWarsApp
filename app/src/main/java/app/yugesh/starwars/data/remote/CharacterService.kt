package app.yugesh.starwars.data.remote

import app.yugesh.starwars.domain.models.Character
import app.yugesh.starwars.domain.models.CharactersResponse
import app.yugesh.starwars.domain.models.FilmDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {
    @GET("/api/people")
    suspend fun getCharacters(@Query("page") page: Int): Response<CharactersResponse>

    @GET("/api/people/{id}")
    suspend fun getCharacterDetails(@Path("id") id: Int): Response<Character>

    @GET("/api/films/{id}")
    suspend fun getFilmDetails(@Path("id") id: Int): Response<FilmDetailsResponse>
}
