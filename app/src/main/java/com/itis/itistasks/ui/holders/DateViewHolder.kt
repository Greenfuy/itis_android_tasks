package com.itis.itistasks.ui.holders

import androidx.recyclerview.widget.RecyclerView
import com.itis.itistasks.databinding.ItemDateBinding
import com.itis.itistasks.model.DateModel

class DateViewHolder(
    private val viewBinding: ItemDateBinding
) : RecyclerView.ViewHolder(viewBinding.root) {

    private var item: DateModel? = null

    fun bindItem(item: DateModel) {
        this.item = item
        with(viewBinding) {
            tvDate.text = item.getDate()
        }
    }
}