package com.itis.itistasks.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.itis.itistasks.R
import com.itis.itistasks.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {

    private var binding: FragmentFirstBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)

        binding?.run {
            button.setOnClickListener {
                val textFromEditText = editText.text.toString()
                parentFragmentManager.beginTransaction()
                    .add(
                        R.id.container,
                        SecondFragment.newInstance(textFromEditText),
                        SecondFragment.SECOND_FRAGMENT_TAG
                    )
                    .addToBackStack(null)
                    .commit()

                parentFragmentManager.beginTransaction()
                    .add(
                        R.id.container,
                        ThirdFragment.newInstance(textFromEditText),
                        ThirdFragment.THIRD_FRAGMENT_TAG
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val FIRST_FRAGMENT_TAG: String = "FIRST_FRAGMENT_TAG"
    }
}