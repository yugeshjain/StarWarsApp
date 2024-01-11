package app.yugesh.starwars.presentation.di

import android.app.Application
import androidx.room.Room
import app.yugesh.starwars.data.db.CharacterDatabase
import app.yugesh.starwars.data.remote.CharacterService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// @Module informs Dagger that this class is a Dagger Module
@Module
class DataModule(private val application: Application) {
    // @Provides tell Dagger how to create instances of the type that this function
    // returns (i.e. CharacterService).
    // Function parameters are the dependencies of this type.
    @Provides
    @Singleton
    fun provideLoginRetrofitService(): CharacterService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        // Whenever Dagger needs to provide an instance of type LoginRetrofitService,
        // this code (the one inside the @Provides method) is run.
        return Retrofit.Builder()
            .baseUrl("https://swapi.dev")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CharacterService::class.java)
    }

    @Provides
    @Singleton
    fun provideCharacterDatabase(): CharacterDatabase =
        Room.databaseBuilder(
            application.applicationContext,
            CharacterDatabase::class.java,
            "characters-db"
        ).build()
}
