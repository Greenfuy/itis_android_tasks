package com.itis.itistasks.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.itis.itistasks.R
import com.itis.itistasks.databinding.FragmentSecondBinding

class SecondFragment : Fragment(R.layout.fragment_second) {

    private var binding: FragmentSecondBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecondBinding.bind(view)

        binding?.run {
            val textFromFirstScreen = arguments?.getString("TEXT_FROM_FIRST_SCREEN")
            if (textFromFirstScreen.isNullOrEmpty()) {
                textView.text = getString(R.string.second_page)
            } else {
                textView.text = textFromFirstScreen
            }

            btnToThird.setOnClickListener {
                parentFragmentManager.popBackStack()

                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.container,
                        ThirdFragment.newInstance(textFromFirstScreen),
                        ThirdFragment.THIRD_FRAGMENT_TAG
                    )
                    .addToBackStack(null)
                    .commit()
            }

            btnToFirst.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.container,
                        FirstFragment(),
                        FirstFragment.FIRST_FRAGMENT_TAG
                    )
                    .commit()
            }
        }
    }

    companion object {
        const val SECOND_FRAGMENT_TAG: String = "SECOND_FRAGMENT_TAG"

        fun newInstance(textFromFirstScreen: String?) = SecondFragment().apply {
            arguments = Bundle().apply {
                putString("TEXT_FROM_FIRST_SCREEN", textFromFirstScreen)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}