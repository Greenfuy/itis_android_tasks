package com.itis.itistasks.ui.holders

import androidx.recyclerview.widget.RecyclerView
import com.itis.itistasks.R
import com.itis.itistasks.databinding.ItemNewsfeedCvBinding
import com.itis.itistasks.model.NewsModel

class NewsfeedViewHolder(
    private val viewBinding: ItemNewsfeedCvBinding,
    private val onNewsClicked: ((NewsModel) -> Unit),
    private val onLikeClicked: ((Int, NewsModel) -> Unit),
) : RecyclerView.ViewHolder(viewBinding.root) {

    private var item: NewsModel? = null

    init {
        viewBinding.root.setOnClickListener {
            this.item?.let(onNewsClicked)
        }
        viewBinding.ivBtnFavorite.setOnClickListener {
            this.item?.let {
                val data = it.copy(isLiked = !it.isLiked)
                onLikeClicked(absoluteAdapterPosition, data)
            }
        }
    }

    fun bindItem(item: NewsModel) {
        this.item = item
        with(viewBinding) {
            tvTitle.text = item.title
            item.image?.let { res ->
                ivImage.setImageResource(res)
            }
            changeLikeBtnStatus(isChecked = item.isLiked)
        }
    }

    fun changeLikeBtnStatus(isChecked: Boolean) {
        val likeDrawable =
            if (isChecked) R.drawable.baseline_star_24 else R.drawable.baseline_star_border_24
        viewBinding.ivBtnFavorite.setImageResource(likeDrawable)
    }
}