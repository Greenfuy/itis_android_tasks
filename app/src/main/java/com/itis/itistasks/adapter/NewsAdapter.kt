package com.itis.itistasks.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.itis.itistasks.adapter.diffUtil.NewsDiffUtil
import com.itis.itistasks.base.holder.BaseModel
import com.itis.itistasks.databinding.ItemButtonBinding
import com.itis.itistasks.databinding.ItemDateBinding
import com.itis.itistasks.databinding.ItemNewsfeedCvBinding
import com.itis.itistasks.model.ButtonModel
import com.itis.itistasks.model.DateModel
import com.itis.itistasks.model.NewsModel
import com.itis.itistasks.ui.holders.ButtonViewHolder
import com.itis.itistasks.ui.holders.DateViewHolder
import com.itis.itistasks.ui.holders.NewsfeedViewHolder

class NewsAdapter(
    private val fragmentManager: FragmentManager,
    private val onNewsClicked: ((NewsModel) -> Unit),
    private val onLikeClicked: ((Int, NewsModel) -> Unit),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemsList = mutableListOf<BaseModel>()

    override fun getItemViewType(position: Int): Int {
        return if (itemsList[position] is ButtonModel) {
            KEY_BUTTON
        } else if (itemsList[position] is DateModel) {
            KEY_DATE
        } else {
            KEY_NEWS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            KEY_BUTTON -> ButtonViewHolder(
                ItemButtonBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                fragmentManager,
                this
            )
            KEY_DATE -> DateViewHolder(
                ItemDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
            else -> NewsfeedViewHolder(
                viewBinding = ItemNewsfeedCvBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onNewsClicked = onNewsClicked,
                onLikeClicked = onLikeClicked,
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType) {
            KEY_BUTTON -> (holder as? ButtonViewHolder)?.bindItem()
            KEY_DATE -> (holder as? DateViewHolder)?.bindItem(
                item = itemsList[position] as DateModel
            )
            else -> (holder as? NewsfeedViewHolder)?.bindItem(
                item = itemsList[position] as NewsModel
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            (payloads.first() as? Boolean)?.let {
                (holder as? NewsfeedViewHolder)?.changeLikeBtnStatus(it)
            }
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: MutableList<BaseModel>) {
        val diff = NewsDiffUtil(oldList = itemsList, newList = list)
        val diffResult = DiffUtil.calculateDiff(diff)
        itemsList.clear()
        itemsList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getItems(): MutableList<BaseModel> = itemsList

    fun updateItem(position: Int, item: NewsModel) {
        if (itemsList[position] is NewsModel) {
            this.itemsList[position] = item
            notifyItemChanged(position, item.isLiked)
        }
    }

    companion object {
        const val KEY_BUTTON = 0
        const val KEY_DATE = 1
        const val KEY_NEWS = 2
    }
}