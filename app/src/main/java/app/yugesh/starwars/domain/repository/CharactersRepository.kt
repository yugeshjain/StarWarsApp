package app.yugesh.starwars.domain.repository

import app.yugesh.starwars.domain.models.Character
import app.yugesh.starwars.domain.models.FilmDetailsResponse

interface CharactersRepository {
    suspend fun getCharacters(page: Int): Result<List<Character>>
    suspend fun getCharacterDetail(url: String): Result<Character>
    suspend fun getFilmDetails(urls: List<String>): Result<List<FilmDetailsResponse>>
}
