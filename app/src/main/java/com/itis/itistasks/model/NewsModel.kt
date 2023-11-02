package com.itis.itistasks.model

import androidx.annotation.DrawableRes
import com.itis.itistasks.base.holder.BaseModel

data class NewsModel(
    val id: Int,
    val title: String,
    val details: String? = null,
    @DrawableRes val image: Int? = null,
    var isLiked: Boolean = false
) : BaseModel()