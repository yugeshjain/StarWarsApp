package app.yugesh.starwars.data.repository

import android.util.Log
import app.yugesh.starwars.data.db.CharacterDao
import app.yugesh.starwars.data.remote.CharacterService
import app.yugesh.starwars.domain.models.Character
import app.yugesh.starwars.domain.models.FilmDetailsResponse
import app.yugesh.starwars.domain.repository.CharactersRepository

private const val TAG = "CharactersRepository"

class CharactersRepositoryImpl(
    private val characterService: CharacterService,
    private val characterDao: CharacterDao,
) : CharactersRepository {
    override suspend fun getCharacters(page: Int): Result<List<Character>> {
        try {
            val dbCharacters = characterDao.getCharactersByPage(page)
            if (dbCharacters.isEmpty()) {
                // If DB is empty for this page, then fetch network results
                characterService.getCharacters(page).body()?.let { safeResponse ->
                    safeResponse.results?.let { networkCharacters ->

                        // Map network-fetched characters to the page used
                        val mappedNetworkCharacters = networkCharacters.mapNotNull { character ->
                            character?.copy(page = page)
                        }.toTypedArray()

                        // Insert the newly mapped characters
                        characterDao.insertAll(*mappedNetworkCharacters)

                        // Return the newly inserted characters
                        return Result.success(characterDao.getCharactersByPage(page))
                    }
                }
                throw Exception("Couldn't fetch characters! Please try again later.")
            } else {
                return Result.success(dbCharacters)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Couldn't fetch characters! Reason: ", e)
            return Result.failure(e)
        }
    }

    override suspend fun getCharacterDetail(url: String): Result<Character> {
        try {
            val characterDetails = characterDao.getCharactersByUrl(url = url)
            if (characterDetails.url.isEmpty()) {
                // If Character Url is empty for this character, then fetch network results
                characterService.getCharacterDetails(id = url.substringAfterLast("people/").removeSuffix("/").toInt()).body()?.let { safeResponse ->
                    safeResponse.let { networkCharacters ->
                        // Insert the newly mapped characters
                        characterDao.insertAll(networkCharacters)

                        // Return the newly inserted characters
                        return Result.success(characterDao.getCharactersByUrl(url = url))
                    }
                }
                throw Exception("Couldn't fetch character details! Please try again later.")
            } else {
                return Result.success(characterDetails)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Couldn't fetch character details! Reason: ", e)
            return Result.failure(e)
        }
    }

    override suspend fun getFilmDetails(urls: List<String>): Result<List<FilmDetailsResponse>> {
        try {
            val filmDetails = characterDao.getFilmsByUrls(urls = urls)
            return if (filmDetails.isEmpty()) {
                urls.forEach { url ->
                    characterService.getFilmDetails(id = url.substringAfterLast("films/").removeSuffix("/").toInt()).body()?.let { safeResponse ->
                        safeResponse.let { networkCharacters ->
                            characterDao.insertFilms(networkCharacters)
                        }
                    } ?: throw Exception("Couldn't fetch character details! Please try again later.")
                }
                Result.success(characterDao.getFilmsByUrls(urls = urls))
            } else {
                Result.success(filmDetails)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Couldn't fetch character details! Reason: ", e)
            return Result.failure(e)
        }
    }
}
