package app.yugesh.starwars.presentation.screens.allCharacters

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.yugesh.starwars.R
import app.yugesh.starwars.SWApp
import app.yugesh.starwars.domain.models.Character
import app.yugesh.starwars.presentation.utils.UIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllCharactersFragment : Fragment(R.layout.fragment_all_characters), CharactersCallbacks {
    @Inject
    lateinit var viewModel: AllCharactersViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Inject Dagger component into the fragment
        (activity?.application as? SWApp)?.appComponent?.inject(allCharactersFragment = this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (recyclerView.canScrollVertically(2).not()) {
                    viewModel.getMoreCharacters()
                }
            }
        })

        lifecycleScope.launch {
            viewModel.charactersState.collect { state ->
                view.findViewById<ProgressBar>(R.id.all_characters_progress).isVisible = state is UIState.Loading
            }
        }

        lifecycleScope.launch {
            viewModel.charactersListState.collect { newCharacters ->
                updateAdapter(
                    character = newCharacters.toList(),
                    recyclerView = recyclerView,
                    charactersCallbacks = this@AllCharactersFragment
                )
            }
        }
    }



    override fun launchCharactersDetail(characterUrl: String) {
        val bundle = Bundle()
        bundle.putString("characterUrl", characterUrl)
        findNavController().navigate(
            R.id.action_AllCharactersFragment_to_CharacterDetailsFragment, bundle
        )
    }
}

fun updateAdapter(
    character: List<Character>,
    recyclerView: RecyclerView,
    charactersCallbacks: CharactersCallbacks
) {
    with(recyclerView) {
        this.adapter?.let {
            if (it is CharactersAdapter) {
                it.saveData(character)
            }
        } ?: run {
            //set adapter here
            this.layoutManager = GridLayoutManager(context, 2)
            this.adapter = CharactersAdapter(
                charactersCallbacks = charactersCallbacks
            ).apply {
                saveData(character)
            }
        }
    }
}
