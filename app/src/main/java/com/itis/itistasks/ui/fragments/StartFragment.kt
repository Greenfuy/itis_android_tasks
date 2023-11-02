package com.itis.itistasks.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.itis.itistasks.R
import com.itis.itistasks.databinding.FragmentStartBinding

class StartFragment : Fragment(R.layout.fragment_start) {

    private var binding: FragmentStartBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentStartBinding.bind(view)

        binding?.run {
            etNewsCount.addTextChangedListener {
                if (
                    etNewsCount.text.isEmpty()
                    || etNewsCount.text.toString().toInt() < 1
                    || etNewsCount.text.toString().toInt() > 45
                ) {
                    etNewsCount.error = getString(R.string.news_count_error)
                }
            }

            btnStart.setOnClickListener {
                if (etNewsCount.error == null && etNewsCount.text.isNotEmpty()) {
                    parentFragmentManager.beginTransaction()
                        .replace(
                            R.id.container,
                            NewsfeedFragment.newInstance(etNewsCount.text.toString().toInt())
                        )
                        .addToBackStack(START_FRAGMENT_TAG)
                        .commit()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val START_FRAGMENT_TAG: String = "START_FRAGMENT_TAG"
    }
}