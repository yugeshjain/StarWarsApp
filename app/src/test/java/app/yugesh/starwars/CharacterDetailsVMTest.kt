package app.yugesh.starwars

import app.yugesh.starwars.data.repository.CharactersRepositoryImpl
import app.yugesh.starwars.domain.models.Character
import app.yugesh.starwars.domain.models.FilmDetailsResponse
import app.yugesh.starwars.presentation.screens.characterDetails.CharacterDetailsViewModel
import app.yugesh.starwars.presentation.utils.UIState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CharacterDetailsVMTest {

    @get:Rule
    val dispatcherRule = ViewModelTestRule()

    @MockK
    private lateinit var charactersRepository: CharactersRepositoryImpl
    private lateinit var viewModel: CharacterDetailsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = CharacterDetailsViewModel(
            characterRepository = charactersRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCharacterDetails emits Data state`() = runTest {
        coEvery { charactersRepository.getCharacterDetail(any()) } coAnswers {
            Result.success(Character(url = "people/1/"))
        }

        coEvery { charactersRepository.getFilmDetails(any()) } coAnswers {
            Result.success(listOf(FilmDetailsResponse(url = "films/1/")))
        }

        // Act
        viewModel.getCharacterDetails("people/1/")

        // Assert
        assertEquals(UIState.Data(Character(url = "people/1/")), viewModel.characterDetailsState.value)
    }

    @Test
    fun `getFilmName emits Data state`() = runTest {
        coEvery { charactersRepository.getFilmDetails(urls = listOf("films/1/")) } coAnswers {
            Result.success(listOf(FilmDetailsResponse(url = "films/1/")))
        }

        // Act
        viewModel.getFilmName(films = listOf("films/1/"))

        // Assert
        assertEquals(
            UIState.Data(listOf(FilmDetailsResponse(url = "films/1/"))),
            viewModel.characterFilmsState.value
        )

        assertTrue(viewModel.characterFilmsState.value is UIState.Data)
    }
}
