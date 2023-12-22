package com.itis.itistasks.ui.holders

import androidx.recyclerview.widget.RecyclerView
import com.itis.itistasks.R
import com.itis.itistasks.data.model.RvFilmModel
import com.itis.itistasks.databinding.ItemFilmBinding

class FilmViewHolder(
    private val viewBinding: ItemFilmBinding,
    private val onItemClicked: (RvFilmModel) -> Unit,
    private val onBtnClicked: (Int, RvFilmModel) -> Unit,
    private val onLongClicked: (RvFilmModel) -> Boolean
) : RecyclerView.ViewHolder(viewBinding.root) {

    private var item: RvFilmModel? = null

    init {
        viewBinding.root.setOnClickListener {
            item?.let(onItemClicked)
        }
        viewBinding.ivAddToFavorite.setOnClickListener {
            this.item?.let {
                val data = it.copy(isFavorite = !it.isFavorite)
                onBtnClicked(adapterPosition, data)
            }
        }
        viewBinding.root.setOnLongClickListener {
            item?.let { onLongClicked(it) } == true
        }
    }

    fun bindItem(item: RvFilmModel) {
        this.item = item
        with(viewBinding) {
            tvTitle.text = item.name
            tvYear.text = item.year.toString()
        }
        println("isFavorite: ${item.isFavorite}")
        changeBtnFavoriteStatus(isChecked = item.isFavorite)
    }

    fun changeBtnFavoriteStatus(isChecked: Boolean) {
        val likeDrawable =
            if (isChecked) R.drawable.baseline_star_24 else R.drawable.baseline_star_outline_24
        viewBinding.ivAddToFavorite.setImageResource(likeDrawable)
    }
}