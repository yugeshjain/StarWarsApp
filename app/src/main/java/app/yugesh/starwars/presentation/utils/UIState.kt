package app.yugesh.starwars.presentation.utils

sealed class UIState<out T> {
    data object Empty : UIState<Nothing>()
    data object Loading : UIState<Nothing>()
    data class Data<T>(val data: T) : UIState<T>()
    data class Error(val throwable: Throwable) : UIState<Nothing>()
}
