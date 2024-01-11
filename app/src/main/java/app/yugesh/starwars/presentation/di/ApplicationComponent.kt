package app.yugesh.starwars.presentation.di

import app.yugesh.starwars.MainActivity
import app.yugesh.starwars.presentation.screens.allCharacters.AllCharactersFragment
import app.yugesh.starwars.presentation.screens.characterDetails.CharacterDetailsFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DataModule::class, RepositoryModule::class, ViewModelModule::class])
@Singleton
interface ApplicationComponent {
    // This tells Dagger that MainActivity requests injection so the graph needs to
    // satisfy all the dependencies of the fields that MainActivity is requesting.
    fun inject(activity: MainActivity)
    fun inject(allCharactersFragment: AllCharactersFragment)
    fun inject(characterDetailsFragment: CharacterDetailsFragment)
}
