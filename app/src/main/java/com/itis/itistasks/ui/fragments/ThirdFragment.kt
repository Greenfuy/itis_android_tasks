package com.itis.itistasks.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.itis.itistasks.R
import com.itis.itistasks.databinding.FragmentThirdBinding

class ThirdFragment : Fragment(R.layout.fragment_third) {

    private var binding: FragmentThirdBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentThirdBinding.bind(view)

        binding?.run {
            val textFromFirstScreen = arguments?.getString("TEXT_FROM_FIRST_SCREEN")
            if (textFromFirstScreen.isNullOrEmpty()) {
                textView.text = getString(R.string.third_page)
            } else {
                textView.text = textFromFirstScreen
            }
        }
    }

    companion object {
        const val THIRD_FRAGMENT_TAG: String = "THIRD_FRAGMENT_TAG"

        fun newInstance(textFromFirstScreen: String?) = ThirdFragment().apply {
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