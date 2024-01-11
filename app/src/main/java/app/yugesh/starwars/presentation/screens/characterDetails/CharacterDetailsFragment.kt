package app.yugesh.starwars.presentation.screens.characterDetails

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import app.yugesh.starwars.R
import app.yugesh.starwars.SWApp
import app.yugesh.starwars.presentation.utils.UIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {

    @Inject
    lateinit var viewModel: CharacterDetailsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Inject Dagger component into the fragment
        (requireActivity().application as SWApp).appComponent.inject(characterDetailsFragment = this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            launch {
                viewModel.getCharacterDetails(
                    characterUrl = arguments?.getString("characterUrl").orEmpty()
                )
            }
            launch {
                viewModel.characterDetailsState.collect { state ->
                    when (state) {
                        is UIState.Empty -> {}
                        is UIState.Loading -> {}
                        is UIState.Error -> {}
                        is UIState.Data -> {
                            view.findViewById<TextView>(R.id.characterName).text = state.data.name
                        }
                    }
                }
            }
            launch {
                viewModel.characterFilmsState.collect { state ->
                    when (state) {
                        is UIState.Empty -> {}
                        is UIState.Loading -> {}
                        is UIState.Error -> {}
                        is UIState.Data -> {
                            view.findViewById<TextView>(R.id.characterMovies).text =
                                state.data.joinToString("\n") {
                                    it.title.orEmpty()
                                }
                        }
                    }
                }
            }
        }
    }
}
