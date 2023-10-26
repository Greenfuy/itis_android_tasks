package com.itis.itistasks.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.itis.itistasks.R
import com.itis.itistasks.databinding.FragmentStartBinding


class StartFragment : Fragment(R.layout.fragment_start) {
    private var binding: FragmentStartBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentStartBinding.bind(view)

        binding?.run {
            etPhoneNumber.addTextChangedListener(MaskWatcher("+7(***) ***-**-**"))
            etPhoneNumber.addTextChangedListener {
                if (!etPhoneNumber.text.matches(
                        Regex("^\\+7\\(9\\d{2}\\) \\d{3}-\\d{2}-\\d{2}$")
                    )
                ) {
                    etPhoneNumber.error = "Number does not match format +7(9**) ***-**-**"
                }
            }

            etQuestionCount.addTextChangedListener {
                if (
                    etQuestionCount.text.isEmpty()
                    || etQuestionCount.text.toString().toInt() < 1
                    || etQuestionCount.text.toString().toInt() > 10
                ) {
                    etQuestionCount.error = "Count of questions must be between 1 and 10!"
                }
            }

            btnStart.setOnClickListener {
                if (
                    etPhoneNumber.error == null
                    && etQuestionCount.error == null
                ) {
                    parentFragmentManager.beginTransaction()
                        .replace(
                            R.id.container,
                            ViewPagerFragment(etQuestionCount.text.toString().toInt())
                        )
                        .commit()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    inner class MaskWatcher(private val mask: String) : TextWatcher {
        private var isRunning = false
        private var isDeleting = false
        override fun beforeTextChanged(
            charSequence: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
            val etPhoneNumber = binding?.etPhoneNumber
            etPhoneNumber?.removeTextChangedListener(this)

            if (charSequence.isEmpty()) {
                etPhoneNumber?.setText("+7(9")
            }

            etPhoneNumber?.addTextChangedListener(this)
            etPhoneNumber?.setSelection(etPhoneNumber.text.length)

            isDeleting = count > after
        }

        override fun onTextChanged(
            charSequence: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
        }

        override fun afterTextChanged(editable: Editable) {
            if (isRunning || isDeleting) {
                return
            }
            isRunning = true
            val editableLength = editable.length

            if (editableLength < mask.length) {
                if (mask[editableLength] != '*') {
                    editable.append(mask[editableLength])
                } else if (mask[editableLength - 1] != '*') {
                    editable.insert(editableLength - 1, mask, editableLength - 1, editableLength)
                }
            }
            isRunning = false
        }
    }


    companion object {
        const val START_FRAGMENT_TAG: String = "START_FRAGMENT_TAG"
    }
}