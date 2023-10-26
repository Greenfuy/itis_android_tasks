package com.itis.itistasks.ui.fragments
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.itis.itistasks.R
import com.itis.itistasks.data.Questions
import com.itis.itistasks.databinding.FragmentQuestionBinding

class QuestionFragment(
    private val pos: Int,
    private val answers: Array<Int?>,
    private val isLast: Boolean
) : Fragment(R.layout.fragment_question) {
    private var binding: FragmentQuestionBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentQuestionBinding.bind(view)
        binding?.run {
            val question = Questions.getQuestions()[pos]
            tvCenter.text = question.questionText
            question.options.forEachIndexed { index, optionText ->
                val button = RadioButton(context)
                button.text = optionText
                button.id = index
                rgAnswers.addView(button)
            }

            rgAnswers.setOnCheckedChangeListener { radioGroup, _ ->
                val btnId = radioGroup.checkedRadioButtonId
                answers[pos] = btnId
                if (hasNoNulls(answers) && isLast) {
                    binding?.btnEnd?.visibility = View.VISIBLE
                }
            }

            btnEnd.setOnClickListener {
                Snackbar.make(view, R.string.test_is_finished, Snackbar.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.container,
                        StartFragment()
                    )
                    .commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun hasNoNulls(a: Array<Int?>) : Boolean {
        var fl = true
        a.forEach { i ->
            if ( i == null) fl = false
        }

        return fl
    }
}