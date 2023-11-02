package com.itis.itistasks.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.itis.itistasks.R
import com.itis.itistasks.adapter.NewsAdapter
import com.itis.itistasks.databinding.FragmentAddNewsDialogBinding
import com.itis.itistasks.utils.NewsRepository

class AddNewsDialogFragment(private val adapter: NewsAdapter?) :
    BottomSheetDialogFragment(R.layout.fragment_add_news_dialog) {

    private var binding: FragmentAddNewsDialogBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddNewsDialogBinding.bind(view)
        calculateDialogHeight()

        binding?.run {
            etAddNews.addTextChangedListener {
                if (
                    etAddNews.text.isEmpty()
                    || etAddNews.text.toString().toInt() < 1
                    || etAddNews.text.toString().toInt() > 5
                ) {
                    etAddNews.error = getString(R.string.error_news_count)
                }
            }

            btnSubmit.setOnClickListener {
                if (etAddNews.error == null && etAddNews.text.isNotEmpty()) {
                    NewsRepository.addRandomNews(etAddNews.text.toString().toInt())
                    adapter?.setItems(NewsRepository.getItemsList())
                }
            }
        }
    }


    private fun calculateDialogHeight() {
        val displayMetrics = requireContext().resources.displayMetrics
        val dialogHeight = displayMetrics.heightPixels / 10

        val layoutParams =
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
                .apply {
                    height = dialogHeight
                }

        this.binding?.root?.layoutParams = layoutParams
    }

    companion object {
        const val ADD_NEWS_DIALOG_FRAGMENT_TAG = "ADD_NEWS_DIALOG_FRAGMENT_TAG"
    }
}