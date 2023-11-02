package com.itis.itistasks.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.itis.itistasks.R
import com.itis.itistasks.databinding.FragmentNewsDescriptionBinding
import com.itis.itistasks.utils.ParamsKey

class NewsDescriptionFragment : Fragment(R.layout.fragment_news_description) {

    private var binding: FragmentNewsDescriptionBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNewsDescriptionBinding.bind(view)

        binding?.run {

            arguments?.getInt(ParamsKey.NEWS_MODEL_IMAGE)?.let { ivImage.setImageResource(it) }
            tvTitle.text = arguments?.getString(ParamsKey.NEWS_MODEL_TITLE)
            tvDesc.text = arguments?.getString(ParamsKey.NEWS_MODEL_DETAILS)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    
    companion object {
        fun newInstance(
            newsModelTitle: String?,
            newsModelDetails: String?,
            newsModelImage: Int?
        ): NewsDescriptionFragment {
            return NewsDescriptionFragment().apply {
                arguments = bundleOf(
                    ParamsKey.NEWS_MODEL_TITLE to newsModelTitle,
                    ParamsKey.NEWS_MODEL_DETAILS to newsModelDetails,
                    ParamsKey.NEWS_MODEL_IMAGE to newsModelImage
                    )
            }
        }
    }
}