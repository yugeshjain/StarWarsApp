package app.yugesh.starwars.presentation.screens.characterDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.yugesh.starwars.domain.models.Character
import app.yugesh.starwars.domain.models.FilmDetailsResponse
import app.yugesh.starwars.domain.repository.CharactersRepository
import app.yugesh.starwars.presentation.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(
    private val characterRepository: CharactersRepository
) : ViewModel() {

    private val _characterDetailsState = MutableStateFlow<UIState<Character>>(UIState.Empty)
    val characterDetailsState = _characterDetailsState.asStateFlow()

    private val _characterFilmsState =
        MutableStateFlow<UIState<List<FilmDetailsResponse>>>(UIState.Empty)
    val characterFilmsState = _characterFilmsState.asStateFlow()

    fun getCharacterDetails(characterUrl: String) {
        viewModelScope.launch {
            _characterDetailsState.emit(UIState.Loading)
            characterRepository.getCharacterDetail(url = characterUrl)
                .onSuccess { characterDetails ->
                    _characterDetailsState.emit(
                        UIState.Data(characterDetails)
                    )
                    getFilmName(characterDetails.films?.mapNotNull {
                        it.orEmpty()
                    } ?: emptyList())
                }
                .onFailure { tr -> _characterDetailsState.emit(UIState.Error(tr)) }
        }
    }


    fun getFilmName(films: List<String>) {
        viewModelScope.launch {
            _characterFilmsState.emit(UIState.Loading)
            characterRepository.getFilmDetails(urls = films)
                .onSuccess { characterDetails ->
                    _characterFilmsState.emit(
                        UIState.Data(characterDetails)
                    )
                }
                .onFailure { tr -> _characterFilmsState.emit(UIState.Error(tr)) }
        }
    }
}
