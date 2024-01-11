package app.yugesh.starwars

import android.app.Application
import app.yugesh.starwars.presentation.di.ApplicationComponent
import app.yugesh.starwars.presentation.di.DaggerApplicationComponent
import app.yugesh.starwars.presentation.di.DataModule

// appComponent lives in the Application class to share its lifecycle
class SWApp : Application() {
    // Reference to the application graph that is used across the whole app
    val appComponent: ApplicationComponent = DaggerApplicationComponent.builder()
        .dataModule(DataModule(this))
        .build()
}
