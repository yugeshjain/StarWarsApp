package app.yugesh.starwars.presentation.di

import app.yugesh.starwars.domain.repository.CharactersRepository
import app.yugesh.starwars.presentation.screens.allCharacters.AllCharactersViewModel
import app.yugesh.starwars.presentation.screens.characterDetails.CharacterDetailsViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {
    @Provides
    @Singleton
    fun provideViewModel(charactersRepository: CharactersRepository): AllCharactersViewModel =
        AllCharactersViewModel(charactersRepository)

    @Provides
    @Singleton
    fun provideCharacterDetailsViewModel(charactersRepository: CharactersRepository): CharacterDetailsViewModel =
        CharacterDetailsViewModel(charactersRepository)
}
