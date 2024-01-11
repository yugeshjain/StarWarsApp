package app.yugesh.starwars.presentation.screens.allCharacters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.yugesh.starwars.domain.models.Character
import app.yugesh.starwars.domain.repository.CharactersRepository
import app.yugesh.starwars.presentation.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AllCharactersViewModel(
    private val charactersRepository: CharactersRepository,
) : ViewModel() {
    private val pageState = MutableStateFlow(1)

    private val _charactersListState = MutableStateFlow<List<Character>>(emptyList())
    val charactersListState = _charactersListState.asStateFlow()

    private val _charactersState = MutableStateFlow<UIState<Unit>>(UIState.Empty)
    val charactersState = _charactersState.asStateFlow()

    init {
        getMoreCharacters()
    }

    fun getMoreCharacters() {
        viewModelScope.launch {
            _charactersState.emit(UIState.Loading)
            charactersRepository.getCharacters(pageState.value)
                .onSuccess { characters ->
                    _charactersListState.emit(_charactersListState.value + characters)
                    _charactersState.emit(UIState.Data(Unit))
                    pageState.emit(pageState.value + 1)
                }
                .onFailure { tr -> _charactersState.emit(UIState.Error(tr)) }
        }
    }
}
