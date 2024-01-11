package app.yugesh.starwars.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.yugesh.starwars.domain.models.Character
import app.yugesh.starwars.domain.models.CharacterConverter
import app.yugesh.starwars.domain.models.FilmDetailsResponse

@Database(entities = [Character::class, FilmDetailsResponse::class], version = 1, exportSchema = false)
@TypeConverters(CharacterConverter::class)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
