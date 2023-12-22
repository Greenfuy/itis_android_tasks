package com.itis.itistasks.adapter

import androidx.recyclerview.widget.DiffUtil
import com.itis.itistasks.data.model.RvFilmModel

class FilmDiffUtil(
    private val oldItemsList: List<RvFilmModel>,
    private val newItemsList: List<RvFilmModel>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItemsList.size

    override fun getNewListSize(): Int = newItemsList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItemsList[oldItemPosition]
        val newItem = newItemsList[newItemPosition]

        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItemsList[oldItemPosition]
        val newItem = newItemsList[newItemPosition]

        return (oldItem.name == newItem.name) &&
                (oldItem.year == newItem.year)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldItemsList[oldItemPosition]
        val newItem = newItemsList[newItemPosition]

        return if (oldItem.isFavorite != newItem.isFavorite) {
            newItem.isFavorite
        } else {
            super.getChangePayload(oldItemPosition, newItemPosition)
        }
    }
}