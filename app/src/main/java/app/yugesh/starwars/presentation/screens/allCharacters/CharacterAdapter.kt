package app.yugesh.starwars.presentation.screens.allCharacters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.yugesh.starwars.R
import app.yugesh.starwars.databinding.CharacterItemBinding
import app.yugesh.starwars.domain.models.Character

class CharactersAdapter(
    private val charactersCallbacks: CharactersCallbacks
) : RecyclerView.Adapter<CharactersAdapter.CharactersAdapterVh>() {
    class CharactersAdapterVh(var binding: CharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun saveData(dataResponse: List<Character>) {
        asyncListDiffer.submitList(dataResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            CharactersAdapterVh {
        val binding = CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersAdapterVh(binding)
    }

    override fun onBindViewHolder(
        holder: CharactersAdapterVh,
        position: Int
    ) {
        val context = holder.itemView.context
        val data = asyncListDiffer.currentList[position]
        holder.binding.apply {
            nameTextView.text = data?.name
            heightTextView.text = context.getString(R.string.height_text, data?.height)
            massTextView.text = context.getString(R.string.mass_text, data?.height)
            skinColorTextView.text = context.getString(R.string.skin_color_text, data?.height)
            eyeColorColorTextView.text = context.getString(R.string.eye_color_text, data?.height)

            root.setOnClickListener {
                charactersCallbacks.launchCharactersDetail(characterUrl = data.url)
            }
        }
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }
}
