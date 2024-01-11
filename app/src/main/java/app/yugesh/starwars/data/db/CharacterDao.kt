package app.yugesh.starwars.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.yugesh.starwars.domain.models.Character
import app.yugesh.starwars.domain.models.FilmDetailsResponse

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character WHERE page=:page")
    suspend fun getCharactersByPage(page: Int): List<Character>

    @Query("SELECT * FROM character WHERE url=:url")
    suspend fun getCharactersByUrl(url: String): Character

    @Query("SELECT * FROM filmDetailsResponse WHERE url IN (:urls)")
    suspend fun getFilmsByUrls(urls: List<String>): List<FilmDetailsResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg characters: Character)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilms(vararg films: FilmDetailsResponse)
}
