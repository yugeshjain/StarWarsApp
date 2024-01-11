package app.yugesh.starwars.presentation.di

import app.yugesh.starwars.data.db.CharacterDatabase
import app.yugesh.starwars.data.remote.CharacterService
import app.yugesh.starwars.data.repository.CharactersRepositoryImpl
import app.yugesh.starwars.domain.repository.CharactersRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideCharactersRepository(
        characterService: CharacterService,
        characterDatabase: CharacterDatabase,
    ): CharactersRepository = CharactersRepositoryImpl(
        characterService = characterService,
        characterDao = characterDatabase.characterDao(),
    )
}
