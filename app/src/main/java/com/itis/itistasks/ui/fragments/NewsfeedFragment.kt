package com.itis.itistasks.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.itis.itistasks.R
import com.itis.itistasks.adapter.NewsAdapter
import com.itis.itistasks.adapter.decorations.HorizontalMarginDecorator
import com.itis.itistasks.adapter.decorations.VerticalDecorator
import com.itis.itistasks.databinding.FragmentNewsfeedBinding
import com.itis.itistasks.model.ButtonModel
import com.itis.itistasks.model.DateModel
import com.itis.itistasks.model.NewsModel
import com.itis.itistasks.utils.NewsRepository
import com.itis.itistasks.utils.ParamsKey
import com.itis.itistasks.utils.calculateRealItemCount
import com.itis.itistasks.utils.getValueInPx


class NewsfeedFragment : Fragment(R.layout.fragment_newsfeed) {

    private var binding: FragmentNewsfeedBinding? = null

    private var newsAdapter: NewsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsfeedBinding.bind(view)
        initRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        newsAdapter = null
    }

    private fun initRecyclerView() {
        newsAdapter = NewsAdapter(
            fragmentManager = parentFragmentManager,
            onNewsClicked = ::onNewsClicked,
            onLikeClicked = ::onLikeClicked
        )

        with(binding) {
            val layoutManager = GridLayoutManager(requireContext(), 1)
            val itemCount = arguments?.getInt(ParamsKey.ITEM_COUNT)
            if (itemCount != null && itemCount > 15) {
                layoutManager.spanCount = 2
                layoutManager.spanSizeLookup = object : SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (
                            newsAdapter?.getItems()?.get(position) is ButtonModel
                            || newsAdapter?.getItems()?.get(position) is DateModel
                            ) 2 else 1
                    }
                }
            }

            this?.newsfeedRv?.layoutManager = layoutManager
            this?.newsfeedRv?.adapter = newsAdapter

            val marginValue = 16.getValueInPx(resources.displayMetrics)
            this?.newsfeedRv?.addItemDecoration(HorizontalMarginDecorator(itemOffset = marginValue))
            this?.newsfeedRv?.addItemDecoration(VerticalDecorator(itemOffset = marginValue / 4))


            arguments?.getInt(ParamsKey.ITEM_COUNT)?.let {
                NewsRepository.buildList(it)
                newsAdapter?.setItems(NewsRepository.getItemsList())
            }
        }
    }

    private fun onNewsClicked(newsModel: NewsModel) {
        parentFragmentManager.beginTransaction()
            .add(
                R.id.container,
                NewsDescriptionFragment
                    .newInstance(
                        newsModel.title,
                        newsModel.details,
                        newsModel.image
                    )
            )
            .addToBackStack(NEWSFEED_FRAGMENT_TAG)
            .commit()
    }

    private fun onLikeClicked(position: Int, newsModel: NewsModel) {
        NewsRepository.markLike(newsModel)
        newsAdapter?.updateItem(position, newsModel)
    }

    companion object {
        const val NEWSFEED_FRAGMENT_TAG = "NEWSFEED_FRAGMENT_TAG"

        fun newInstance(itemCount: Int) : NewsfeedFragment {

            return NewsfeedFragment().apply {
                arguments = bundleOf(
                    ParamsKey.ITEM_COUNT to calculateRealItemCount(itemCount)
                )
            }
        }
    }
}