package com.itis.itistasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.itis.itistasks.data.model.RvFilmModel
import com.itis.itistasks.databinding.ItemFilmBinding
import com.itis.itistasks.ui.holders.FilmViewHolder

class FilmAdapter (
    private val onItemClicked: (RvFilmModel) -> Unit,
    private val onBtnClicked: (Int, RvFilmModel) -> Unit,
    private val onLongClicked: (RvFilmModel) -> Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemsList = mutableListOf<RvFilmModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FilmViewHolder(
            viewBinding = ItemFilmBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClicked = onItemClicked,
            onBtnClicked = onBtnClicked,
            onLongClicked = onLongClicked
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? FilmViewHolder)?.bindItem(item = itemsList[position])
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            (payloads.first() as? Boolean)?.let {
                (holder as? FilmViewHolder)?.changeBtnFavoriteStatus(it)
            }
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int = itemsList.size

    fun setItems(list: List<RvFilmModel>) {
        val diff = FilmDiffUtil(oldItemsList = itemsList, newItemsList = list)
        val diffResult = DiffUtil.calculateDiff(diff)
        itemsList.clear()
        itemsList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun updateItem(position: Int, item: RvFilmModel) {
        this.itemsList[position] = item
        notifyItemChanged(position, item.isFavorite)
    }
}