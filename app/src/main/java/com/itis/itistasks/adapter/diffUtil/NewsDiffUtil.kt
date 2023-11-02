package com.itis.itistasks.adapter.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.itis.itistasks.base.holder.BaseModel
import com.itis.itistasks.model.NewsModel


class NewsDiffUtil(
    private val oldList: List<BaseModel>,
    private val newList: List<BaseModel>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        if (oldItem is NewsModel && newItem is NewsModel) {
            return oldItem.id == newItem.id
        }

        return false
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        if (oldItem is NewsModel && newItem is NewsModel) {
            return (oldItem.title == newItem.title) &&
                    (oldItem.details == newItem.details)
        }

        return false
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        if (oldItem is NewsModel && newItem is NewsModel) {
            if (oldItem.isLiked != newItem.isLiked) {
                return newItem.isLiked
            }
        }

        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}