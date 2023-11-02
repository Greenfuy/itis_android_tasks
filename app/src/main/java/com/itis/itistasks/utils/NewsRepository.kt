package com.itis.itistasks.utils

import com.itis.itistasks.R
import com.itis.itistasks.base.holder.BaseModel
import com.itis.itistasks.model.ButtonModel
import com.itis.itistasks.model.DateModel
import com.itis.itistasks.model.NewsModel

object NewsRepository {
    private val itemsList: MutableList<BaseModel> = mutableListOf()
    private val dataList: MutableList<NewsModel> = mutableListOf()

    init {
        val list = mutableListOf(
            NewsModel(
                id = 0,
                title = "Duis eros turpis",
                details = "Duis eros turpis, vestibulum et quam at, tempus facilisis libero. Etiam venenatis nibh lobortis, volutpat dui ut, consequat urna. Nam tempus urna sed maximus rutrum. Donec at blandit urna. Proin scelerisque nibh sed dolor ultricies accumsan. Aenean vitae felis nisl. Proin malesuada felis turpis, vitae sagittis mi consequat eget.",
                image = R.drawable.img
            ),
            NewsModel(
                id = 1,
                title = "Morbi viverra risus",
                details = "Morbi viverra risus vel lorem iaculis ullamcorper. Proin malesuada erat nisi, efficitur laoreet nisl ornare id. Vestibulum sollicitudin aliquet velit in malesuada. Fusce sed finibus ex, id convallis tellus. Vestibulum ut ligula faucibus, rutrum velit non, iaculis neque. Cras nunc est, auctor ac aliquet mattis, faucibus ut erat. Integer vel suscipit sem. Sed scelerisque mauris in luctus egestas. Cras ut porta metus, id pulvinar leo. Sed sit amet quam sed nulla mollis tincidunt sit amet nec felis. Vivamus id nunc vitae erat mollis scelerisque eu nec nisl. In ac malesuada leo. Vivamus molestie, velit sed posuere tempor, urna libero elementum felis, in sollicitudin erat sapien ut orci.",
                image = R.drawable.img_1
            ),
            NewsModel(
                id = 2,
                title = "Vivamus dui nulla",
                details = "Vivamus dui nulla, malesuada a dignissim id, sodales vel enim. Morbi at libero erat. Sed ut enim massa. Proin ligula tortor, placerat vitae ultricies ut, porta non lectus. Mauris faucibus libero non vulputate lobortis. Mauris felis risus, blandit volutpat viverra quis, ultrices sit amet urna. Nam sit amet lorem id libero hendrerit fermentum ut sed dui. Integer venenatis tempus ligula, in vulputate augue cursus a. Nam in ligula sit amet sapien eleifend tincidunt. Aliquam placerat luctus lectus, non suscipit nisi efficitur sed.",
                image = R.drawable.img_2
            ),
            NewsModel(
                id = 3,
                title = "Morbi viverra risus",
                details = "Mauris accumsan interdum neque. Cras et tortor sed urna cursus vestibulum. Vivamus velit sapien, ultricies quis cursus id, ullamcorper sed purus. Maecenas eget accumsan ante. Vestibulum sollicitudin mi quis enim sodales viverra in sit amet quam. Pellentesque posuere augue accumsan convallis posuere. Sed convallis, magna a suscipit fringilla, nisl leo euismod metus, ut tristique lacus metus in turpis. Etiam nec risus nec erat consequat molestie vel at lectus.",
                image = R.drawable.img_3
            ),
            NewsModel(
                id = 4,
                title = "Integer ultrices condimentum",
                details = "Integer ultrices condimentum ante iaculis finibus. Phasellus elit quam, commodo ut purus at, dignissim rutrum est. Etiam augue dui, iaculis non ullamcorper sed, convallis at eros. Quisque sed luctus ipsum. Aenean sodales, augue et feugiat efficitur, magna arcu tempus tortor, id viverra tortor ante efficitur lectus. Sed blandit enim vitae mattis faucibus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Aliquam erat volutpat. Fusce nec diam in lorem maximus rutrum tincidunt vitae tellus. Maecenas a tristique lacus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Pellentesque vestibulum ac turpis et feugiat.",
                image = R.drawable.img_4
            ),
            NewsModel(
                id = 5,
                title = "Sed molestie et",
                details = "Sed molestie et mauris a auctor. Mauris id velit ornare, rutrum tortor et, sollicitudin enim. Morbi porttitor sapien id arcu lacinia aliquam. Proin sit amet libero nisl. Mauris in ligula sed diam bibendum eleifend. In et ipsum feugiat, dictum dolor vel, semper eros. Maecenas porta risus a ligula ultricies ornare. Nulla justo diam, ultricies vel tincidunt sit amet, pulvinar a est. Fusce pulvinar semper arcu sit amet aliquam. Curabitur semper a nibh et dignissim.",
                image = R.drawable.img_5
            ),
            NewsModel(
                id = 6,
                title = "Donec eget quam",
                details = "Donec eget quam tristique erat commodo vehicula. Ut dictum id ex id hendrerit. Donec viverra efficitur ultrices. Quisque vulputate varius magna nec scelerisque. Vestibulum mollis ligula eget ullamcorper tristique. Nulla consectetur lacus sit amet vestibulum interdum. Duis aliquam, augue vel pretium mollis, arcu enim interdum arcu, a laoreet justo dui non ligula. Donec ex sapien, faucibus et consectetur quis, dapibus id enim. Sed consequat eu nulla vel tincidunt. Donec nulla risus, suscipit at diam non, imperdiet efficitur ante. Suspendisse ac nisl dolor. In arcu libero, faucibus eget ultricies vel, aliquet volutpat tellus. Etiam porttitor lobortis nibh. Integer dignissim aliquam elit at vestibulum. Cras fringilla in enim vel fringilla. Phasellus ac mattis lorem, a molestie diam.",
                image = R.drawable.img_6
            )
        )
        dataList.addAll(list)
    }

    fun getItemsList() : MutableList<BaseModel> {
        return itemsList
    }

    fun addRandomNews(count: Int) {
        repeat(count) {
            val insertPosition = when (itemsList.size) {
                1 -> 1
                2 -> 2
                else -> (2 until itemsList.size).random()
            }
            val newsPosition = (1 until dataList.size).random()
            itemsList.add(
                insertPosition,
                NewsModel(
                    itemsList.size + 1,
                    dataList[newsPosition].title,
                    dataList[newsPosition].details,
                    dataList[newsPosition].image
                )
            )
        }
    }

    fun buildList(count: Int) {
        itemsList.clear()
        itemsList.add(ButtonModel())
        for (i in 1 until count) {
            if (i in getSteps()) {
                itemsList.add(DateModel(i))
            } else {
                val position = (dataList.indices).random()
                itemsList.add(
                    NewsModel(
                        itemsList.size + 1,
                        dataList[position].title,
                        dataList[position].details,
                        dataList[position].image
                    )
                )
            }
        }
    }

    fun markLike(newsModel: NewsModel) {
        val news =
            itemsList.find {
                (it as? NewsModel)?.id == newsModel.id
            } as NewsModel
        news.isLiked = !news.isLiked
    }
}
